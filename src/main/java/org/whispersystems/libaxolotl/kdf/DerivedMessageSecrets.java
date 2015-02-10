/**
 * Copyright (C) 2014 Open Whisper Systems
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

import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.whispersystems.libaxolotl.j2me.AssertionError;
import org.whispersystems.libaxolotl.util.ByteUtil;
import org.whispersystems.libaxolotl.j2me.ParseException;

public class DerivedMessageSecrets {

  public  static final int SIZE              = 80;
  private static final int CIPHER_KEY_LENGTH = 32;
  private static final int MAC_KEY_LENGTH    = 32;
  private static final int IV_LENGTH         = 16;

  private final KeyParameter     cipherKey;
  private final KeyParameter     macKey;
  private final ParametersWithIV iv;

  public DerivedMessageSecrets(byte[] okm) {
    try {
      byte[][] keys = ByteUtil.split(okm, CIPHER_KEY_LENGTH, MAC_KEY_LENGTH, IV_LENGTH);

      this.cipherKey = new KeyParameter(keys[0], 0, keys[0].length);
      this.macKey    = new KeyParameter(keys[1], 0, keys[1].length);
      this.iv        = new ParametersWithIV(null, keys[2], 0, keys[2].length);
    } catch (ParseException e) {
      throw new AssertionError(e);
    }
  }

  public KeyParameter getCipherKey() {
    return cipherKey;
  }

  public KeyParameter getMacKey() {
    return macKey;
  }

  public ParametersWithIV getIv() {
    return iv;
  }
}
