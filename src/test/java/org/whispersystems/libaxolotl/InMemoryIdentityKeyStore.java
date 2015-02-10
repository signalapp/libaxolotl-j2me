package org.whispersystems.libaxolotl;

import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.j2me.FakeSecureRandomProvider;
import org.whispersystems.libaxolotl.state.IdentityKeyStore;

import java.util.Hashtable;

public class InMemoryIdentityKeyStore implements IdentityKeyStore {

  private final Hashtable trustedKeys = new Hashtable();
//  private final Map<Long, IdentityKey> trustedKeys = new HashMap<>();

  private final IdentityKeyPair identityKeyPair;
  private final int             localRegistrationId;

  public InMemoryIdentityKeyStore() {
    ECKeyPair identityKeyPairKeys = Curve.generateKeyPair(new FakeSecureRandomProvider());

    this.identityKeyPair = new IdentityKeyPair(new IdentityKey(identityKeyPairKeys.getPublicKey()),
                                               identityKeyPairKeys.getPrivateKey());
    this.localRegistrationId = new FakeSecureRandomProvider().nextInt(16380) + 1;
  }

//  @Override
  public IdentityKeyPair getIdentityKeyPair() {
    return identityKeyPair;
  }

//  @Override
  public int getLocalRegistrationId() {
    return localRegistrationId;
  }

//  @Override
  public void saveIdentity(long recipientId, IdentityKey identityKey) {
    trustedKeys.put(new Long(recipientId), identityKey);
  }

//  @Override
  public boolean isTrustedIdentity(long recipientId, IdentityKey identityKey) {
    IdentityKey trusted = (IdentityKey)trustedKeys.get(new Long(recipientId));
    return (trusted == null || trusted.equals(identityKey));
  }
}
