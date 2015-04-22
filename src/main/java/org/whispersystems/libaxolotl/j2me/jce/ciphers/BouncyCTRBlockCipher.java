package org.whispersystems.libaxolotl.j2me.jce.ciphers;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.whispersystems.libaxolotl.util.ByteUtil;

public class BouncyCTRBlockCipher implements BlockCipher {

  private final BufferedBlockCipher cipher;

  public BouncyCTRBlockCipher(boolean encrypt, byte[] key, int counter) {
    byte[] ivBytes = new byte[16];
    ByteUtil.intToByteArray(ivBytes, 0, counter);

    this.cipher = new BufferedBlockCipher(new SICBlockCipher(new AESEngine()));
    cipher.init(encrypt, new ParametersWithIV(new KeyParameter(key), ivBytes));
  }


  public int process(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset) {
    return cipher.processBytes(input, inputOffset, inputLength, output, outputOffset);
  }

  public int doFinal(byte[] output, int offset) throws InvalidCipherTextException {
    try {
      return cipher.doFinal(output, offset);
    } catch (org.bouncycastle.crypto.InvalidCipherTextException e) {
      throw new InvalidCipherTextException(e);
    }
  }

  public int getOutputSize(int inputLength) {
    return cipher.getOutputSize(inputLength);
  }
}
