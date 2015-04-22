package org.whispersystems.libaxolotl.j2me.jce;

import org.whispersystems.curve25519.SecureRandomProvider;
import org.whispersystems.libaxolotl.j2me.jce.ciphers.BlockCipher;
import org.whispersystems.libaxolotl.j2me.jce.ciphers.BouncyCBCBlockCipher;
import org.whispersystems.libaxolotl.j2me.jce.ciphers.BouncyCTRBlockCipher;
import org.whispersystems.libaxolotl.j2me.jce.mac.BouncyMacSha256;
import org.whispersystems.libaxolotl.j2me.jce.mac.Mac;

public class BCJmeSecurityProvider implements JmeSecurityProvider {

  private final SecureRandomProvider secureRandomProvider;

  public BCJmeSecurityProvider(SecureRandomProvider secureRandomProvider) {
    this.secureRandomProvider = secureRandomProvider;
  }

  public SecureRandomProvider createSecureRandom() {
    return secureRandomProvider;
  }

  public BlockCipher createCbcCipher(boolean encrypt, byte[] key, byte[] iv) {
    return new BouncyCBCBlockCipher(encrypt, key, iv);
  }

  public BlockCipher createCtrCipher(boolean encrypt, byte[] key, int counter) {
    return new BouncyCTRBlockCipher(encrypt, key, counter);
  }

  public Mac createMacSha256(byte[] key) {
    return new BouncyMacSha256(key);
  }
}
