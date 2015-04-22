/**
 * Copyright (C) 2014 Open Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.whispersystems.libaxolotl.protocol;

import org.bouncycastle.crypto.params.KeyParameter;
import org.whispersystems.libaxolotl.IdentityKey;
import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.InvalidMessageException;
import org.whispersystems.libaxolotl.LegacyMessageException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.j2me.MessageDigest;
import org.whispersystems.libaxolotl.j2me.ParseException;
import org.whispersystems.libaxolotl.j2me.jce.JmeSecurity;
import org.whispersystems.libaxolotl.j2me.jce.mac.Mac;
import org.whispersystems.libaxolotl.util.ByteUtil;

public class WhisperMessage implements CiphertextMessage {

  private static final int MAC_LENGTH = 8;

  private final int         messageVersion;
  private final ECPublicKey senderRatchetKey;
  private final int         counter;
  private final int         previousCounter;
  private final byte[]      ciphertext;
  private final byte[]      serialized;

  public WhisperMessage(byte[] serialized) throws InvalidMessageException, LegacyMessageException {
    try {
      byte[][] messageParts = ByteUtil.split(serialized, 1, serialized.length - 1 - MAC_LENGTH, MAC_LENGTH);
      byte     version      = messageParts[0][0];
      byte[]   message      = messageParts[1];
      byte[]   mac          = messageParts[2];

      if (ByteUtil.highBitsToInt(version) <= CiphertextMessage.UNSUPPORTED_VERSION) {
        throw new LegacyMessageException("Legacy message: " + ByteUtil.highBitsToInt(version));
      }

      if (ByteUtil.highBitsToInt(version) > CURRENT_VERSION) {
        throw new InvalidMessageException("Unknown version: " + ByteUtil.highBitsToInt(version));
      }

      org.whispersystems.libaxolotl.protocol.protos.WhisperMessage structure =
          org.whispersystems.libaxolotl.protocol.protos.WhisperMessage.fromBytes(message);

      if (!structure.hasCiphertext() ||
          !structure.hasCounter() ||
          !structure.hasRatchetkey())
      {
        throw new InvalidMessageException("Incomplete message.");
      }

      this.serialized       = serialized;
      this.senderRatchetKey = Curve.decodePoint(structure.getRatchetkey(), 0);
      this.messageVersion   = ByteUtil.highBitsToInt(version);
      this.counter          = structure.getCounter();
      this.previousCounter  = structure.getPreviouscounter();
      this.ciphertext       = structure.getCiphertext();
    } catch (InvalidKeyException ike) {
      throw new InvalidMessageException(ike);
    } catch (ParseException e) {
      throw new InvalidMessageException(e);
    }
  }

  public WhisperMessage(int messageVersion, KeyParameter macKey, ECPublicKey senderRatchetKey,
                        int counter, int previousCounter, byte[] ciphertext,
                        IdentityKey senderIdentityKey,
                        IdentityKey receiverIdentityKey)
  {
    org.whispersystems.libaxolotl.protocol.protos.WhisperMessage structure =
        new org.whispersystems.libaxolotl.protocol.protos.WhisperMessage();

    structure.setRatchetkey(senderRatchetKey.serialize());
    structure.setCounter(counter);
    structure.setPreviouscounter(previousCounter);
    structure.setCiphertext(ciphertext);

    byte[] version = {ByteUtil.intsToByteHighAndLow(messageVersion, CURRENT_VERSION)};
    byte[] message = structure.toBytes();
    byte[] mac     = getMac(messageVersion, senderIdentityKey, receiverIdentityKey, macKey,
                            ByteUtil.combine(version, message));

    this.serialized       = ByteUtil.combine(version, message, mac);
    this.senderRatchetKey = senderRatchetKey;
    this.counter          = counter;
    this.previousCounter  = previousCounter;
    this.ciphertext       = ciphertext;
    this.messageVersion   = messageVersion;
  }

  public ECPublicKey getSenderRatchetKey()  {
    return senderRatchetKey;
  }

  public int getMessageVersion() {
    return messageVersion;
  }

  public int getCounter() {
    return counter;
  }

  public byte[] getBody() {
    return ciphertext;
  }

  public void verifyMac(int messageVersion, IdentityKey senderIdentityKey,
                        IdentityKey receiverIdentityKey, KeyParameter macKey)
      throws InvalidMessageException
  {
    byte[][] parts    = ByteUtil.split(serialized, serialized.length - MAC_LENGTH, MAC_LENGTH);
    byte[]   ourMac   = getMac(messageVersion, senderIdentityKey, receiverIdentityKey, macKey, parts[0]);
    byte[]   theirMac = parts[1];

    if (!MessageDigest.isEqual(ourMac, theirMac)) {
      throw new InvalidMessageException("Bad Mac!");
    }
  }

  private byte[] getMac(int messageVersion,
                        IdentityKey senderIdentityKey,
                        IdentityKey receiverIdentityKey,
                        KeyParameter macKey, byte[] serialized)
  {
    Mac    mac    = JmeSecurity.getProvider().createMacSha256(macKey.getKey());
    byte[] output = new byte[32];

    if (messageVersion >= 3) {
      byte[] senderIdentity = senderIdentityKey.getPublicKey().serialize();
      byte[] receiverIdentity = receiverIdentityKey.getPublicKey().serialize();

      mac.update(senderIdentity, 0, senderIdentity.length);
      mac.update(receiverIdentity, 0, receiverIdentity.length);
    }

    mac.update(serialized, 0, serialized.length);
    mac.doFinal(output, 0);

    return ByteUtil.trim(output, MAC_LENGTH);
  }

//  @Override
  public byte[] serialize() {
    return serialized;
  }

//  @Override
  public int getType() {
    return CiphertextMessage.WHISPER_TYPE;
  }

  public static boolean isLegacy(byte[] message) {
    return message != null && message.length >= 1 &&
        ByteUtil.highBitsToInt(message[0]) <= CiphertextMessage.UNSUPPORTED_VERSION;
  }

}
