package org.whispersystems.libaxolotl.state;

import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.ecc.ECPrivateKey;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.state.protos.SignedPreKeyRecordStructure;
import org.whispersystems.libaxolotl.j2me.AssertionError;

import java.io.IOException;


public class SignedPreKeyRecord {

  private SignedPreKeyRecordStructure structure;

  public SignedPreKeyRecord(int id, long timestamp, ECKeyPair keyPair, byte[] signature) {
    this.structure = new SignedPreKeyRecordStructure();
    structure.setId(id);
    structure.setPublickey(keyPair.getPublicKey().serialize());
    structure.setPrivatekey(keyPair.getPrivateKey().serialize());
    structure.setSignature(signature);
    structure.setTimestamp(timestamp);
  }

  public SignedPreKeyRecord(byte[] serialized) throws IOException {
    this.structure = SignedPreKeyRecordStructure.fromBytes(serialized);
  }

  public int getId() {
    return this.structure.getId();
  }

  public long getTimestamp() {
    return this.structure.getTimestamp();
  }

  public ECKeyPair getKeyPair() {
    try {
      ECPublicKey publicKey = Curve.decodePoint(this.structure.getPublickey(), 0);
      ECPrivateKey privateKey = Curve.decodePrivatePoint(this.structure.getPrivatekey());

      return new ECKeyPair(publicKey, privateKey);
    } catch (InvalidKeyException e) {
      throw new AssertionError(e);
    }
  }

  public byte[] getSignature() {
    return this.structure.getSignature();
  }

  public byte[] serialize() {
    return this.structure.toBytes();
  }
}
