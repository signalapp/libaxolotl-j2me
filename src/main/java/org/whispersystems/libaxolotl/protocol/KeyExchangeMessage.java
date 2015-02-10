package org.whispersystems.libaxolotl.protocol;


import org.whispersystems.libaxolotl.IdentityKey;
import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.InvalidMessageException;
import org.whispersystems.libaxolotl.InvalidVersionException;
import org.whispersystems.libaxolotl.LegacyMessageException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.util.ByteUtil;


public class KeyExchangeMessage {

  public static final int INITIATE_FLAG              = 0x01;
  public static final int RESPONSE_FLAG              = 0X02;
  public static final int SIMULTAENOUS_INITIATE_FLAG = 0x04;

  private final int         version;
  private final int         supportedVersion;
  private final int         sequence;
  private final int         flags;

  private final ECPublicKey baseKey;
  private final byte[]      baseKeySignature;
  private final ECPublicKey ratchetKey;
  private final IdentityKey identityKey;
  private final byte[]      serialized;

  public KeyExchangeMessage(int messageVersion, int sequence, int flags,
                            ECPublicKey baseKey, byte[] baseKeySignature,
                            ECPublicKey ratchetKey,
                            IdentityKey identityKey)
  {
    this.supportedVersion = CiphertextMessage.CURRENT_VERSION;
    this.version          = messageVersion;
    this.sequence         = sequence;
    this.flags            = flags;
    this.baseKey          = baseKey;
    this.baseKeySignature = baseKeySignature;
    this.ratchetKey       = ratchetKey;
    this.identityKey      = identityKey;

    byte[]  version = {ByteUtil.intsToByteHighAndLow(this.version, this.supportedVersion)};

    org.whispersystems.libaxolotl.protocol.protos.KeyExchangeMessage structure = new org.whispersystems.libaxolotl.protocol.protos.KeyExchangeMessage();
    structure.setId((sequence << 5) | flags);
    structure.setBasekey(baseKey.serialize());
    structure.setRatchetkey(ratchetKey.serialize());
    structure.setIdentitykey(identityKey.serialize());

    if (messageVersion >= 3) {
      structure.setBasekeysignature(baseKeySignature);
    }

    this.serialized = ByteUtil.combine(version, structure.toBytes());
  }

  public KeyExchangeMessage(byte[] serialized)
      throws InvalidMessageException, InvalidVersionException, LegacyMessageException
  {
    try {
      byte[][] parts        = ByteUtil.split(serialized, 1, serialized.length - 1);
      this.version          = ByteUtil.highBitsToInt(parts[0][0]);
      this.supportedVersion = ByteUtil.lowBitsToInt(parts[0][0]);

      if (this.version <= CiphertextMessage.UNSUPPORTED_VERSION) {
        throw new LegacyMessageException("Unsupported legacy version: " + this.version);
      }

      if (this.version > CiphertextMessage.CURRENT_VERSION) {
        throw new InvalidVersionException("Unknown version: " + this.version);
      }

      org.whispersystems.libaxolotl.protocol.protos.KeyExchangeMessage message =
          org.whispersystems.libaxolotl.protocol.protos.KeyExchangeMessage.fromBytes(parts[1]);

      if (!message.hasId()         || !message.hasBasekey()     ||
          !message.hasRatchetkey() || !message.hasIdentitykey() ||
          (this.version >=3 && message.getBasekeysignature() == null))
      {
        throw new InvalidMessageException("Some required fields missing!");
      }

      this.sequence         = message.getId() >> 5;
      this.flags            = message.getId() & 0x1f;
      this.serialized       = serialized;
      this.baseKey          = Curve.decodePoint(message.getBasekey(), 0);
      this.baseKeySignature = message.getBasekeysignature();
      this.ratchetKey       = Curve.decodePoint(message.getRatchetkey(), 0);
      this.identityKey      = new IdentityKey(message.getIdentitykey(), 0);
    } catch (InvalidKeyException ike) {
      throw new InvalidMessageException(ike);
    }
  }

  public int getVersion() {
    return version;
  }

  public ECPublicKey getBaseKey() {
    return baseKey;
  }

  public byte[] getBaseKeySignature() {
    return baseKeySignature;
  }

  public ECPublicKey getRatchetKey() {
    return ratchetKey;
  }

  public IdentityKey getIdentityKey() {
    return identityKey;
  }

  public boolean hasIdentityKey() {
    return true;
  }

  public int getMaxVersion() {
    return supportedVersion;
  }

  public boolean isResponse() {
    return ((flags & RESPONSE_FLAG) != 0);
  }

  public boolean isInitiate() {
    return (flags & INITIATE_FLAG) != 0;
  }

  public boolean isResponseForSimultaneousInitiate() {
    return (flags & SIMULTAENOUS_INITIATE_FLAG) != 0;
  }

  public int getFlags() {
    return flags;
  }

  public int getSequence() {
    return sequence;
  }

  public byte[] serialize() {
    return serialized;
  }
}
