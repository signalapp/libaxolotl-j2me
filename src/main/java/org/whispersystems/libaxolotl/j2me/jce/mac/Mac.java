package org.whispersystems.libaxolotl.j2me.jce.mac;

public interface Mac {
  public void update(byte[] input, int offset, int length);
  public void update(byte b);
  public void doFinal(byte[] output, int offset);
}
