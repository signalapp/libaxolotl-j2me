package org.whispersystems.libaxolotl.groups;

import org.whispersystems.libaxolotl.groups.state.SenderKeyRecord;
import org.whispersystems.libaxolotl.groups.state.SenderKeyStore;
import org.whispersystems.libaxolotl.j2me.AssertionError;

import java.io.IOException;
import java.util.Hashtable;

public class InMemorySenderKeyStore implements SenderKeyStore {

  // SenderKeyName -> SenderKeyRecord
  private final Hashtable store = new Hashtable();

  public void storeSenderKey(SenderKeyName senderKeyName, SenderKeyRecord record) {
    store.put(senderKeyName, record);
  }

  public SenderKeyRecord loadSenderKey(SenderKeyName senderKeyName) {
    try {
      SenderKeyRecord record = (SenderKeyRecord)store.get(senderKeyName);

      if (record == null) {
        return new SenderKeyRecord();
      } else {
        return new SenderKeyRecord(record.serialize());
      }
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
