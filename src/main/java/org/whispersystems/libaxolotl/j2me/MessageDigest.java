package org.whispersystems.libaxolotl.j2me;

public class MessageDigest {

  public static boolean isEqual(byte[] digesta, byte[] digestb) {
    if (digesta.length != digestb.length) {
      return false;
    }

    int result = 0;
    // time-constant comparison
    for (int i = 0; i < digesta.length; i++) {
      result |= digesta[i] ^ digestb[i];
    }
    return result == 0;
  }

}
