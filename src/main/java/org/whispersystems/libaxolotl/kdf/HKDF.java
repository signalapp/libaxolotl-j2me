/**
 * Copyright (C) 2013 Open Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.whispersystems.libaxolotl.kdf;

import org.whispersystems.libaxolotl.j2me.AssertionError;
import org.whispersystems.libaxolotl.j2me.jce.JmeSecurity;
import org.whispersystems.libaxolotl.j2me.jce.mac.Mac;

import java.io.ByteArrayOutputStream;

public abstract class HKDF {

  private static final int HASH_OUTPUT_SIZE  = 32;

  public static HKDF createFor(int messageVersion) {
    switch (messageVersion) {
      case 2:  return new HKDFv2();
      case 3:  return new HKDFv3();
      default: throw new AssertionError("Unknown version: " + messageVersion);
    }
  }

  public byte[] deriveSecrets(byte[] inputKeyMaterial, byte[] info, int outputLength) {
    byte[] salt = new byte[HASH_OUTPUT_SIZE];
    return deriveSecrets(inputKeyMaterial, salt, info, outputLength);
  }

  public byte[] deriveSecrets(byte[] inputKeyMaterial, byte[] salt, byte[] info, int outputLength) {
    byte[] prk = extract(salt, inputKeyMaterial);
    return expand(prk, info, outputLength);
  }

  private byte[] extract(byte[] salt, byte[] inputKeyMaterial) {
    Mac    mac    = JmeSecurity.getProvider().createMacSha256(salt);
    byte[] output = new byte[32];

    mac.update(inputKeyMaterial, 0, inputKeyMaterial.length);
    mac.doFinal(output, 0);

    return output;
  }

  private byte[] expand(byte[] prk, byte[] info, int outputSize) {
    int                   iterations     = (int) Math.ceil((double) outputSize / (double) HASH_OUTPUT_SIZE);
    byte[]                mixin          = new byte[0];
    ByteArrayOutputStream results        = new ByteArrayOutputStream();
    int                   remainingBytes = outputSize;

    for (int i= getIterationStartOffset();i<iterations + getIterationStartOffset();i++) {
      Mac    mac        = JmeSecurity.getProvider().createMacSha256(prk);
      byte[] stepResult = new byte[32];

      mac.update(mixin, 0, mixin.length);
      if (info != null) {
        mac.update(info, 0, info.length);
      }
      mac.update((byte)i);

      mac.doFinal(stepResult, 0);

      int stepSize = Math.min(remainingBytes, stepResult.length);

      results.write(stepResult, 0, stepSize);

      mixin          = stepResult;
      remainingBytes -= stepSize;
    }

    return results.toByteArray();
  }

  protected abstract int getIterationStartOffset();

}
