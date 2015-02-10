package org.whispersystems.libaxolotl.j2me;

import org.whispersystems.curve25519.SecureRandomProvider;

import java.util.Random;


public class FakeSecureRandomProvider implements SecureRandomProvider {
  public void nextBytes(byte[] output) {
    Random random    = new Random(System.currentTimeMillis());
    long   something = random.nextLong();

    output[0] = (byte)(something & 0xFF);
    output[1] = (byte)((something >> 8) & 0xFF);
    output[2] = (byte)((something >> 16) & 0xFF);
    output[3] = (byte)((something >> 24) & 0xFF);
    output[4] = (byte)((something >> 32) & 0xFF);

  }

  public int nextInt(int maxValue) {
    return new Random(System.currentTimeMillis()).nextInt(maxValue);
  }
}
