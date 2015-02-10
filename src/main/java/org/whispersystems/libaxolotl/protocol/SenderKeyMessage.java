package org.whispersystems.libaxolotl.protocol;

import org.whispersystems.curve25519.SecureRandomProvider;
import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.InvalidMessageException;
import org.whispersystems.libaxolotl.LegacyMessageException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECPrivateKey;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.j2me.AssertionError;
import org.whispersystems.libaxolotl.util.ByteUtil;
import org.whispersystems.libaxolotl.j2me.ParseException;


public class SenderKeyMessage implements CiphertextMessage {

  private static final int SIGNATURE_LENGTH = 64;

  private final int         messageVersion;
  private final int         keyId;
  private final int         iteration;
  private final byte[]      ciphertext;
  private final byte[]      serialized;

  public SenderKeyMessage(byte[] serialized) throws InvalidMessageException, LegacyMessageException {
    try {
      byte[][] messageParts = ByteUtil.split(serialized, 1, serialized.length - 1 - SIGNATURE_LENGTH, SIGNATURE_LENGTH);
      byte     version      = messageParts[0][0];
      byte[]   message      = messageParts[1];
      byte[]   signature    = messageParts[2];

      if (ByteUtil.highBitsToInt(version) < 3) {
        throw new LegacyMessageException("Legacy message: " + ByteUtil.highBitsToInt(version));
      }

      if (ByteUtil.highBitsToInt(version) > CURRENT_VERSION) {
        throw new InvalidMessageException("Unknown version: " + ByteUtil.highBitsToInt(version));
      }

      org.whispersystems.libaxolotl.protocol.protos.SenderKeyMessage structure =
          org.whispersystems.libaxolotl.protocol.protos.SenderKeyMessage.fromBytes(message);

      if (!structure.hasId() ||
          !structure.hasIteration() ||
          !structure.hasCiphertext())
      {
        throw new InvalidMessageException("Incomplete message.");
      }

      this.serialized     = serialized;
      this.messageVersion = ByteUtil.highBitsToInt(version);
      this.keyId          = structure.getId();
      this.iteration      = structure.getIteration();
      this.ciphertext     = structure.getCiphertext();
    } catch (ParseException e) {
      throw new InvalidMessageException(e);
    }
  }

  public SenderKeyMessage(SecureRandomProvider secureRandom,
                          int keyId, int iteration, byte[] ciphertext, ECPrivateKey signatureKey)
  {
    org.whispersystems.libaxolotl.protocol.protos.SenderKeyMessage structure =
        new org.whispersystems.libaxolotl.protocol.protos.SenderKeyMessage();

    structure.setId(keyId);
    structure.setIteration(iteration);
    structure.setCiphertext(ciphertext);

    byte[] version   = {ByteUtil.intsToByteHighAndLow(CURRENT_VERSION, CURRENT_VERSION)};
    byte[] message   = structure.toBytes();
    byte[] signature = getSignature(secureRandom, signatureKey, ByteUtil.combine(version, message));

    this.serialized       = ByteUtil.combine(version, message, signature);
    this.messageVersion   = CURRENT_VERSION;
    this.keyId            = keyId;
    this.iteration        = iteration;
    this.ciphertext       = ciphertext;
  }

  public int getKeyId() {
    return keyId;
  }

  public int getIteration() {
    return iteration;
  }

  public byte[] getCipherText() {
    return ciphertext;
  }

  public void verifySignature(ECPublicKey signatureKey)
      throws InvalidMessageException
  {
    try {
      byte[][] parts    = ByteUtil.split(serialized, serialized.length - SIGNATURE_LENGTH, SIGNATURE_LENGTH);

      if (!Curve.verifySignature(signatureKey, parts[0], parts[1])) {
        throw new InvalidMessageException("Invalid signature!");
      }

    } catch (InvalidKeyException e) {
      throw new InvalidMessageException(e);
    }
  }

  private byte[] getSignature(SecureRandomProvider secureRandom, ECPrivateKey signatureKey, byte[] serialized) {
    try {
      return Curve.calculateSignature(secureRandom, signatureKey, serialized);
    } catch (InvalidKeyException e) {
      throw new AssertionError(e);
    }
  }

//  @Override
  public byte[] serialize() {
    return serialized;
  }

//  @Override
  public int getType() {
    return CiphertextMessage.SENDERKEY_TYPE;
  }
}
