package org.whispersystems.libaxolotl.j2me.jce.mac;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class BouncyMacSha256 implements Mac {

  private final HMac mac;

  public BouncyMacSha256(byte[] key) {
    this.mac = new HMac(new SHA256Digest());
    this.mac.init(new KeyParameter(key, 0, key.length));
  }

  public void update(byte[] input, int offset, int length) {
    mac.update(input, offset, length);
  }

  public void update(byte b) {
    mac.update(b);
  }

  public void doFinal(byte[] output, int offset) {
    mac.doFinal(output, offset);
  }
}
