package org.whispersystems.libaxolotl.j2me;

/**
 * Interface that implements a buffered block cipher.
 */
public interface BlockCipher {

  public int process(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset);
  public int doFinal(byte[] output, int offset) throws InvalidCipherTextException;
  public int getOutputSize(int inputLength);

  public static class InvalidCipherTextException extends NestedException {
    public InvalidCipherTextException(Throwable t) {
      super(t);
    }
  }
}
