package org.whispersystems.libaxolotl.j2me;

public interface CipherFactory {
  public BlockCipher createCbc(boolean encrypt, byte[] key, byte[] iv);
  public BlockCipher createCtr(boolean encrypt, byte[] key, int counter);
}
