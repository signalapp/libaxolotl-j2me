package org.whispersystems.libaxolotl.j2me.jce;

import org.whispersystems.curve25519.SecureRandomProvider;
import org.whispersystems.libaxolotl.j2me.jce.ciphers.BlockCipher;
import org.whispersystems.libaxolotl.j2me.jce.mac.Mac;

public interface JmeSecurityProvider {
  public SecureRandomProvider createSecureRandom();
  public BlockCipher createCbcCipher(boolean encrypt, byte[] key, byte[] iv);
  public BlockCipher createCtrCipher(boolean encrypt, byte[] key, int counter);
  public Mac createMacSha256(byte[] key);
}
