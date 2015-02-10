package org.whispersystems.libaxolotl;

import org.whispersystems.libaxolotl.state.SignedPreKeyRecord;
import org.whispersystems.libaxolotl.state.SignedPreKeyStore;
import org.whispersystems.libaxolotl.j2me.AssertionError;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class InMemorySignedPreKeyStore implements SignedPreKeyStore {

  private final Hashtable store = new Hashtable();
//  private final Map<Integer, byte[]> store = new HashMap<>();

//  @Override
  public SignedPreKeyRecord loadSignedPreKey(int signedPreKeyId) throws InvalidKeyIdException {
    try {
      if (!store.containsKey(new Integer(signedPreKeyId))) {
        throw new InvalidKeyIdException("No such signedprekeyrecord! " + signedPreKeyId);
      }

      return new SignedPreKeyRecord((byte[])store.get(new Integer(signedPreKeyId)));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

//  @Override
  public Vector loadSignedPreKeys() {
    try {
//      List<SignedPreKeyRecord> results = new LinkedList<>();
      Vector      results     = new Vector();
      Enumeration enumeration = store.elements();

      while (enumeration.hasMoreElements()) {
        results.addElement(new SignedPreKeyRecord((byte[]) enumeration.nextElement()));
      }
//      for (byte[] serialized : store.values()) {
//        results.add(new SignedPreKeyRecord(serialized));
//      }

      return results;
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
//
//  @Override
  public void storeSignedPreKey(int signedPreKeyId, SignedPreKeyRecord record) {
    store.put(new Integer(signedPreKeyId), record.serialize());
  }

//  @Override
  public boolean containsSignedPreKey(int signedPreKeyId) {
    return store.containsKey(new Integer(signedPreKeyId));
  }

//  @Override
  public void removeSignedPreKey(int signedPreKeyId) {
    store.remove(new Integer(signedPreKeyId));
  }
}
