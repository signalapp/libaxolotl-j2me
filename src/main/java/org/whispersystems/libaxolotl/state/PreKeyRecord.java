package org.whispersystems.libaxolotl.state;


import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.ecc.ECPrivateKey;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.state.protos.PreKeyRecordStructure;
import org.whispersystems.libaxolotl.j2me.AssertionError;

import java.io.IOException;


public class PreKeyRecord {

  private PreKeyRecordStructure structure;

  public PreKeyRecord(int id, ECKeyPair keyPair) {
    this.structure = new PreKeyRecordStructure();
    this.structure.setId(id);
    this.structure.setPublickey(keyPair.getPublicKey().serialize());
    this.structure.setPrivatekey(keyPair.getPrivateKey().serialize());
  }

  public PreKeyRecord(byte[] serialized) throws IOException {
    this.structure = PreKeyRecordStructure.fromBytes(serialized);
  }

  public int getId() {
    return this.structure.getId();
  }

  public ECKeyPair getKeyPair() {
    try {
      ECPublicKey  publicKey  = Curve.decodePoint       (this.structure.getPublickey (), 0);
      ECPrivateKey privateKey = Curve.decodePrivatePoint(this.structure.getPrivatekey()   );

      return new ECKeyPair(publicKey, privateKey);
    } catch (InvalidKeyException e) {
      throw new AssertionError(e);
    }
  }

  public byte[] serialize() {
    return this.structure.toBytes();
  }
}
