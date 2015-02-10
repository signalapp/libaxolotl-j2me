package org.whispersystems.libaxolotl.groups;

import org.whispersystems.libaxolotl.groups.state.SenderKeyRecord;
import org.whispersystems.libaxolotl.groups.state.SenderKeyStore;
import org.whispersystems.libaxolotl.j2me.AssertionError;

import java.io.IOException;
import java.util.Hashtable;

public class InMemorySenderKeyStore implements SenderKeyStore {

  private final Hashtable store = new Hashtable();
//  private final Map<String, SenderKeyRecord> store = new HashMap<>();

//  @Override
  public void storeSenderKey(String senderKeyId, SenderKeyRecord record) {
    store.put(senderKeyId, record);
  }

//  @Override
  public SenderKeyRecord loadSenderKey(String senderKeyId) {
    try {
      SenderKeyRecord record = (SenderKeyRecord)store.get(senderKeyId);

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
