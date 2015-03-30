package org.whispersystems.libaxolotl.j2me;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class BouncyCBCBlockCipher implements BlockCipher {

  private final BufferedBlockCipher cipher;

  public BouncyCBCBlockCipher(boolean encrypt, byte[] key, byte[] iv) {
    this.cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
    cipher.init(encrypt, new ParametersWithIV(new KeyParameter(key), iv));
  }

  public int process(byte[] input, int inputOffset, int inputLength,
                     byte[] output, int outputOffset)
  {
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
