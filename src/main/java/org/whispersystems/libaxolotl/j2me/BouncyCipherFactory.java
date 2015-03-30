package org.whispersystems.libaxolotl.j2me;

public class BouncyCipherFactory implements CipherFactory {
  public BlockCipher createCbc(boolean encrypt, byte[] key, byte[] iv) {
    return new BouncyCBCBlockCipher(encrypt, key, iv);
  }

  public BlockCipher createCtr(boolean encrypt, byte[] key, int counter) {
    return new BouncyCTRBlockCipher(encrypt, key, counter);
  }
}
