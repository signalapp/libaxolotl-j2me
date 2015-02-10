package org.whispersystems.libaxolotl.groups.state;

import org.whispersystems.libaxolotl.InvalidKeyIdException;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.state.protos.SenderKeyRecordStructure;
import org.whispersystems.libaxolotl.state.protos.SenderKeyStateStructure;

import java.io.IOException;
import java.util.Vector;

public class SenderKeyRecord {

  // SenderKeyState
  private Vector senderKeyStates = new Vector();

  public SenderKeyRecord() {}

  public SenderKeyRecord(byte[] serialized) throws IOException {
    SenderKeyRecordStructure senderKeyRecordStructure = SenderKeyRecordStructure.fromBytes(serialized);
    Vector                   senderKeyStates          = senderKeyRecordStructure.getSenderkeystatesVector();

    for (int i=0;i<senderKeyStates.size();i++) {
      this.senderKeyStates.addElement(new SenderKeyState((SenderKeyStateStructure)senderKeyStates.elementAt(i)));
    }
  }

  public SenderKeyState getSenderKeyState() throws InvalidKeyIdException {
    if (!senderKeyStates.isEmpty()) {
      return (SenderKeyState)senderKeyStates.elementAt(0);
    } else {
      throw new InvalidKeyIdException("No key state in record!");
    }
  }

  public SenderKeyState getSenderKeyState(int keyId) throws InvalidKeyIdException {
    for (int i=0;i<senderKeyStates.size();i++) {
      if (((SenderKeyState)senderKeyStates.elementAt(i)).getKeyId() == keyId) {
        return (SenderKeyState)senderKeyStates.elementAt(i);
      }
    }

    throw new InvalidKeyIdException("No keys for: " + keyId);
  }

  public void addSenderKeyState(int id, int iteration, byte[] chainKey, ECPublicKey signatureKey) {
    senderKeyStates.addElement(new SenderKeyState(id, iteration, chainKey, signatureKey));
  }

  public void setSenderKeyState(int id, int iteration, byte[] chainKey, ECKeyPair signatureKey) {
    senderKeyStates.removeAllElements();
    senderKeyStates.addElement(new SenderKeyState(id, iteration, chainKey, signatureKey));
  }

  public byte[] serialize() {
    SenderKeyRecordStructure recordStructure = new SenderKeyRecordStructure();

    for (int i=0;i<senderKeyStates.size();i++) {
      SenderKeyState state = (SenderKeyState)senderKeyStates.elementAt(i);
      recordStructure.addSenderkeystates(state.getStructure());
    }

    return recordStructure.toBytes();
  }
}
