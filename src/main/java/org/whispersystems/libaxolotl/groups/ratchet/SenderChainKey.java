package org.whispersystems.libaxolotl.groups.ratchet;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class SenderChainKey {

  private static final byte[] MESSAGE_KEY_SEED = {0x01};
  private static final byte[] CHAIN_KEY_SEED   = {0x02};

  private final int    iteration;
  private final byte[] chainKey;

  public SenderChainKey(int iteration, byte[] chainKey) {
    this.iteration = iteration;
    this.chainKey  = chainKey;
  }

  public int getIteration() {
    return iteration;
  }

  public SenderMessageKey getSenderMessageKey() {
    return new SenderMessageKey(iteration, getDerivative(MESSAGE_KEY_SEED, chainKey));
  }

  public SenderChainKey getNext() {
    return new SenderChainKey(iteration + 1, getDerivative(CHAIN_KEY_SEED, chainKey));
  }

  public byte[] getSeed() {
    return chainKey;
  }

  private byte[] getDerivative(byte[] seed, byte[] key) {
    HMac   mac    = new HMac(new SHA256Digest());
    byte[] output = new byte[32];
    mac.init(new KeyParameter(key, 0, key.length));

    mac.update(seed, 0, seed.length);
    mac.doFinal(output, 0);

    return output;
  }

}
