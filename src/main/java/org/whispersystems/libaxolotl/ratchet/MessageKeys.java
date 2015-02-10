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
package org.whispersystems.libaxolotl.ratchet;

import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class MessageKeys {

  private final KeyParameter     cipherKey;
  private final KeyParameter     macKey;
  private final ParametersWithIV iv;
  private final int              counter;

  public MessageKeys(KeyParameter cipherKey, KeyParameter macKey, ParametersWithIV iv, int counter) {
    this.cipherKey = cipherKey;
    this.macKey    = macKey;
    this.iv        = iv;
    this.counter   = counter;
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

  public int getCounter() {
    return counter;
  }
}
