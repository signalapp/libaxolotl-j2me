package org.whispersystems.libaxolotl.protocol;

import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.InvalidMessageException;
import org.whispersystems.libaxolotl.LegacyMessageException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.util.ByteUtil;

public class SenderKeyDistributionMessage implements CiphertextMessage {

  private final int         id;
  private final int         iteration;
  private final byte[]      chainKey;
  private final ECPublicKey signatureKey;
  private final byte[]      serialized;

  public SenderKeyDistributionMessage(int id, int iteration, byte[] chainKey, ECPublicKey signatureKey) {
    byte[] version = {ByteUtil.intsToByteHighAndLow(CURRENT_VERSION, CURRENT_VERSION)};

    this.id           = id;
    this.iteration    = iteration;
    this.chainKey     = chainKey;
    this.signatureKey = signatureKey;

    org.whispersystems.libaxolotl.protocol.protos.SenderKeyDistributionMessage structure =
        new org.whispersystems.libaxolotl.protocol.protos.SenderKeyDistributionMessage();

    structure.setId(id);
    structure.setIteration(iteration);
    structure.setChainkey(chainKey);
    structure.setSigningkey(signatureKey.serialize());

    this.serialized = ByteUtil.combine(version, structure.toBytes());
  }

  public SenderKeyDistributionMessage(byte[] serialized) throws LegacyMessageException, InvalidMessageException {
    try {
      byte[][] messageParts = ByteUtil.split(serialized, 1, serialized.length - 1);
      byte     version      = messageParts[0][0];
      byte[]   message      = messageParts[1];

      if (ByteUtil.highBitsToInt(version) < CiphertextMessage.CURRENT_VERSION) {
        throw new LegacyMessageException("Legacy message: " + ByteUtil.highBitsToInt(version));
      }

      if (ByteUtil.highBitsToInt(version) > CURRENT_VERSION) {
        throw new InvalidMessageException("Unknown version: " + ByteUtil.highBitsToInt(version));
      }

      org.whispersystems.libaxolotl.protocol.protos.SenderKeyDistributionMessage distributionMessage =
          org.whispersystems.libaxolotl.protocol.protos.SenderKeyDistributionMessage.fromBytes(message);

      if (!distributionMessage.hasId()        ||
          !distributionMessage.hasIteration() ||
          !distributionMessage.hasChainkey()  ||
          !distributionMessage.hasSigningkey())
      {
        throw new InvalidMessageException("Incomplete message.");
      }

      this.serialized   = serialized;
      this.id           = distributionMessage.getId();
      this.iteration    = distributionMessage.getIteration();
      this.chainKey     = distributionMessage.getChainkey();
      this.signatureKey = Curve.decodePoint(distributionMessage.getSigningkey(), 0);
    } catch (InvalidKeyException e) {
      throw new InvalidMessageException(e);
    }
  }

//  @Override
  public byte[] serialize() {
    return serialized;
  }

//  @Override
  public int getType() {
    return SENDERKEY_DISTRIBUTION_TYPE;
  }

  public int getIteration() {
    return iteration;
  }

  public byte[] getChainKey() {
    return chainKey;
  }

  public ECPublicKey getSignatureKey() {
    return signatureKey;
  }

  public int getId() {
    return id;
  }
}
