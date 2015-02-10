package org.whispersystems.libaxolotl;

import org.whispersystems.libaxolotl.state.PreKeyRecord;
import org.whispersystems.libaxolotl.state.PreKeyStore;
import org.whispersystems.libaxolotl.j2me.AssertionError;

import java.io.IOException;
import java.util.Hashtable;

public class InMemoryPreKeyStore implements PreKeyStore {

  private final Hashtable store = new Hashtable();
//  private final Map<Integer, byte[]> store = new HashMap<>();

//  @Override
  public PreKeyRecord loadPreKey(int preKeyId) throws InvalidKeyIdException {
    try {
      if (!store.containsKey(new Integer(preKeyId))) {
        throw new InvalidKeyIdException("No such prekeyrecord!");
      }

      return new PreKeyRecord((byte[])store.get(new Integer(preKeyId)));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

//  @Override
  public void storePreKey(int preKeyId, PreKeyRecord record) {
    store.put(new Integer(preKeyId), record.serialize());
  }

//  @Override
  public boolean containsPreKey(int preKeyId) {
    return store.containsKey(new Integer(preKeyId));
  }

//  @Override
  public void removePreKey(int preKeyId) {
    store.remove(new Integer(preKeyId));
  }
}
