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

package org.whispersystems.libaxolotl.state;


import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.whispersystems.libaxolotl.IdentityKey;
import org.whispersystems.libaxolotl.IdentityKeyPair;
import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.ecc.ECPrivateKey;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.kdf.HKDF;
import org.whispersystems.libaxolotl.logging.Log;
import org.whispersystems.libaxolotl.ratchet.ChainKey;
import org.whispersystems.libaxolotl.ratchet.MessageKeys;
import org.whispersystems.libaxolotl.ratchet.RootKey;
import org.whispersystems.libaxolotl.state.protos.SessionStructure;
import org.whispersystems.libaxolotl.j2me.AssertionError;
import org.whispersystems.libaxolotl.util.Pair;
import org.whispersystems.libaxolotl.util.guava.Optional;

import java.util.Vector;

public class SessionState {

  private SessionStructure sessionStructure;

  public SessionState() {
    this.sessionStructure = new SessionStructure();
  }

  public SessionState(SessionStructure sessionStructure) {
    this.sessionStructure = sessionStructure;
  }

  public SessionState(SessionState copy) {
    this.sessionStructure = SessionStructure.fromBytes(copy.sessionStructure.toBytes());
  }

  public SessionStructure getStructure() {
    return sessionStructure;
  }

  public byte[] getAliceBaseKey() {
    return this.sessionStructure.getAlicebasekey();
  }

  public void setAliceBaseKey(byte[] aliceBaseKey) {
    this.sessionStructure.setAlicebasekey(aliceBaseKey);
  }

  public void setSessionVersion(int version) {
    this.sessionStructure.setSessionversion(version);;
  }

  public int getSessionVersion() {
    int sessionVersion = this.sessionStructure.getSessionversion();

    if (sessionVersion == 0) return 2;
    else                     return sessionVersion;
  }

  public void setRemoteIdentityKey(IdentityKey identityKey) {
    this.sessionStructure.setRemoteidentitypublic(identityKey.serialize());
  }

  public void setLocalIdentityKey(IdentityKey identityKey) {
    this.sessionStructure.setLocalidentitypublic(identityKey.serialize());
  }

  public IdentityKey getRemoteIdentityKey() {
    try {
      if (this.sessionStructure.getRemoteidentitypublic() == null) {
        return null;
      }

      return new IdentityKey(this.sessionStructure.getRemoteidentitypublic(), 0);
    } catch (InvalidKeyException e) {
      Log.w("SessionRecordV2", e);
      return null;
    }
  }

  public IdentityKey getLocalIdentityKey() {
    try {
      return new IdentityKey(this.sessionStructure.getLocalidentitypublic(), 0);
    } catch (InvalidKeyException e) {
      throw new AssertionError(e);
    }
  }

  public int getPreviousCounter() {
    return sessionStructure.getPreviouscounter();
  }

  public void setPreviousCounter(int previousCounter) {
    this.sessionStructure.setPreviouscounter(previousCounter);
  }

  public RootKey getRootKey() {
    return new RootKey(HKDF.createFor(getSessionVersion()),
                       this.sessionStructure.getRootkey());
  }

  public void setRootKey(RootKey rootKey) {
    this.sessionStructure.setRootkey(rootKey.getKeyBytes());
  }

  public ECPublicKey getSenderRatchetKey() {
    try {
      return Curve.decodePoint(sessionStructure.getSenderchain().getSenderratchetkey(), 0);
    } catch (InvalidKeyException e) {
      throw new AssertionError(e);
    }
  }

  public ECKeyPair getSenderRatchetKeyPair() {
    ECPublicKey  publicKey  = getSenderRatchetKey();
    ECPrivateKey privateKey = Curve.decodePrivatePoint(sessionStructure.getSenderchain()
                                                                       .getSenderratchetkeyprivate());

    return new ECKeyPair(publicKey, privateKey);
  }

  public boolean hasReceiverChain(ECPublicKey senderEphemeral) {
    return getReceiverChain(senderEphemeral) != null;
  }

  public boolean hasSenderChain() {
    return sessionStructure.hasSenderchain();
  }

  private Pair getReceiverChain(ECPublicKey senderEphemeral) {
    Vector receiverChains = sessionStructure.getReceiverchainsVector();
    int    index          = 0;

    for (int i=0;i<receiverChains.size();i++) {
      SessionStructure.Chain receiverChain = (SessionStructure.Chain)receiverChains.elementAt(i);

      try {
        ECPublicKey chainSenderRatchetKey = Curve.decodePoint(receiverChain.getSenderratchetkey(), 0);

        if (chainSenderRatchetKey.equals(senderEphemeral)) {
          return new Pair(receiverChain,new Integer(index));
        }
      } catch (InvalidKeyException e) {
        Log.w("SessionRecordV2", e);
      }

      index++;
    }

    return null;
  }

  public ChainKey getReceiverChainKey(ECPublicKey senderEphemeral) {
    Pair                   receiverChainAndIndex = getReceiverChain(senderEphemeral);
    SessionStructure.Chain receiverChain         = (SessionStructure.Chain) receiverChainAndIndex.first();

    if (receiverChain == null) {
      return null;
    } else {
      return new ChainKey(HKDF.createFor(getSessionVersion()),
                          receiverChain.getChainkey().getKey(),
                          receiverChain.getChainkey().getIndex());
    }
  }

  public void addReceiverChain(ECPublicKey senderRatchetKey, ChainKey chainKey) {
    SessionStructure.Chain.ChainKey chainKeyStructure = new SessionStructure.Chain.ChainKey();
    chainKeyStructure.setKey(chainKey.getKey());
    chainKeyStructure.setIndex(chainKey.getIndex());

    SessionStructure.Chain chain = new SessionStructure.Chain();
    chain.setChainkey(chainKeyStructure);
    chain.setSenderratchetkey(senderRatchetKey.serialize());

    this.sessionStructure.addReceiverchains(chain);

    if (this.sessionStructure.getReceiverchainsVector().size() > 5) {
      this.sessionStructure.getReceiverchainsVector().removeElementAt(0);
    }
  }

  public void setSenderChain(ECKeyPair senderRatchetKeyPair, ChainKey chainKey) {
    SessionStructure.Chain.ChainKey chainKeyStructure = new SessionStructure.Chain.ChainKey();
    chainKeyStructure.setKey(chainKey.getKey());
    chainKeyStructure.setIndex(chainKey.getIndex());

    SessionStructure.Chain senderChain = new SessionStructure.Chain();
    senderChain.setSenderratchetkey(senderRatchetKeyPair.getPublicKey().serialize());
    senderChain.setSenderratchetkeyprivate(senderRatchetKeyPair.getPrivateKey().serialize());
    senderChain.setChainkey(chainKeyStructure);

    this.sessionStructure.setSenderchain(senderChain);
  }

  public ChainKey getSenderChainKey() {
    SessionStructure.Chain.ChainKey chainKeyStructure = sessionStructure.getSenderchain().getChainkey();
    return new ChainKey(HKDF.createFor(getSessionVersion()),
                        chainKeyStructure.getKey(), chainKeyStructure.getIndex());
  }


  public void setSenderChainKey(ChainKey nextChainKey) {
    SessionStructure.Chain.ChainKey chainKey = new SessionStructure.Chain.ChainKey();
    chainKey.setKey(nextChainKey.getKey());
    chainKey.setIndex(nextChainKey.getIndex());

    sessionStructure.getSenderchain().setChainkey(chainKey);
  }

  public boolean hasMessageKeys(ECPublicKey senderEphemeral, int counter) {
    Pair                   chainAndIndex = getReceiverChain(senderEphemeral);
    SessionStructure.Chain chain         = (SessionStructure.Chain) chainAndIndex.first();

    if (chain == null) {
      return false;
    }

    Vector messageKeyList = chain.getMessagekeysVector();

    for (int i=0;i<messageKeyList.size();i++) {
      SessionStructure.Chain.MessageKey messageKey = (SessionStructure.Chain.MessageKey)messageKeyList.elementAt(i);

      if (messageKey.getIndex() == counter) {
        return true;
      }
    }

    return false;
  }

  public MessageKeys removeMessageKeys(ECPublicKey senderEphemeral, int counter) {
    Pair                   chainAndIndex = getReceiverChain(senderEphemeral);
    SessionStructure.Chain chain         = (SessionStructure.Chain) chainAndIndex.first();

    if (chain == null) {
      return null;
    }

    Vector     messageKeyList     = chain.getMessagekeysVector();
//    Iterator messageKeyIterator = messageKeyList.iterator();
    MessageKeys                result             = null;

    for (int i=0;i<messageKeyList.size();i++) {
      SessionStructure.Chain.MessageKey messageKey = (SessionStructure.Chain.MessageKey)messageKeyList.elementAt(i);

      if (messageKey.getIndex() == counter) {
        result = new MessageKeys(new KeyParameter(messageKey.getCipherkey()),
                                 new KeyParameter(messageKey.getMackey()),
                                 new ParametersWithIV(null, messageKey.getIv()),
                                 messageKey.getIndex());

        messageKeyList.removeElementAt(i);
        break;
      }
    }

//
//    SessionStructure.Chain updatedChain = chain.getMessagekeysVector().removeAllElements();.clearMessageKeys()
//                              .addAllMessageKeys(messageKeyList)
//                              .build();
//
//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .setReceiverChains(chainAndIndex.second(), updatedChain)
//                                                 .build();

    return result;
  }

  public void setMessageKeys(ECPublicKey senderEphemeral, MessageKeys messageKeys) {
    Pair                              chainAndIndex       = getReceiverChain(senderEphemeral);
    SessionStructure.Chain            chain               = (SessionStructure.Chain) chainAndIndex.first();
    SessionStructure.Chain.MessageKey messageKeyStructure = new SessionStructure.Chain.MessageKey();

    messageKeyStructure.setCipherkey(messageKeys.getCipherKey().getKey());
    messageKeyStructure.setMackey(messageKeys.getMacKey().getKey());
    messageKeyStructure.setIndex(messageKeys.getCounter());
    messageKeyStructure.setIv(messageKeys.getIv().getIV());

    chain.addMessagekeys(messageKeyStructure);
//    this.sessionStructure.setR
//
//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .setReceiverChains(chainAndIndex.second(), updatedChain)
//                                                 .build();
  }

  public void setReceiverChainKey(ECPublicKey senderEphemeral, ChainKey chainKey) {
    Pair                   chainAndIndex = getReceiverChain(senderEphemeral);
    SessionStructure.Chain chain         = (SessionStructure.Chain) chainAndIndex.first();

    SessionStructure.Chain.ChainKey chainKeyStructure = new SessionStructure.Chain.ChainKey();
    chainKeyStructure.setKey(chainKey.getKey());
    chainKeyStructure.setIndex(chainKey.getIndex());

    chain.setChainkey(chainKeyStructure);

//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .setReceiverChains(chainAndIndex.second(), updatedChain)
//                                                 .build();
  }

  public void setPendingKeyExchange(int sequence,
                                    ECKeyPair ourBaseKey,
                                    ECKeyPair ourRatchetKey,
                                    IdentityKeyPair ourIdentityKey)
  {
    SessionStructure.PendingKeyExchange structure = new SessionStructure.PendingKeyExchange();
    structure.setSequence(sequence);
    structure.setLocalbasekey(ourBaseKey.getPublicKey().serialize());
    structure.setLocalbasekeyprivate(ourBaseKey.getPrivateKey().serialize());
    structure.setLocalratchetkey(ourRatchetKey.getPublicKey().serialize());
    structure.setLocalratchetkeyprivate(ourRatchetKey.getPrivateKey().serialize());
    structure.setLocalidentitykey(ourIdentityKey.getPublicKey().serialize());
    structure.setLocalidentitykeyprivate(ourIdentityKey.getPrivateKey().serialize());

    this.sessionStructure.setPendingkeyexchange(structure);
  }

  public int getPendingKeyExchangeSequence() {
    return sessionStructure.getPendingkeyexchange().getSequence();
  }

  public ECKeyPair getPendingKeyExchangeBaseKey() throws InvalidKeyException {
    ECPublicKey publicKey   = Curve.decodePoint(sessionStructure.getPendingkeyexchange()
                                                                .getLocalbasekey(), 0);

    ECPrivateKey privateKey = Curve.decodePrivatePoint(sessionStructure.getPendingkeyexchange()
                                                                       .getLocalbasekeyprivate());

    return new ECKeyPair(publicKey, privateKey);
  }

  public ECKeyPair getPendingKeyExchangeRatchetKey() throws InvalidKeyException {
    ECPublicKey publicKey   = Curve.decodePoint(sessionStructure.getPendingkeyexchange()
                                                                .getLocalratchetkey(), 0);

    ECPrivateKey privateKey = Curve.decodePrivatePoint(sessionStructure.getPendingkeyexchange()
                                                                       .getLocalratchetkeyprivate());

    return new ECKeyPair(publicKey, privateKey);
  }

  public IdentityKeyPair getPendingKeyExchangeIdentityKey() throws InvalidKeyException {
    IdentityKey publicKey = new IdentityKey(sessionStructure.getPendingkeyexchange()
                                                            .getLocalidentitykey(), 0);

    ECPrivateKey privateKey = Curve.decodePrivatePoint(sessionStructure.getPendingkeyexchange()
                                                                       .getLocalidentitykeyprivate());

    return new IdentityKeyPair(publicKey, privateKey);
  }

  public boolean hasPendingKeyExchange() {
    return sessionStructure.hasPendingkeyexchange();
  }

  public void setUnacknowledgedPreKeyMessage(Optional preKeyId, int signedPreKeyId, ECPublicKey baseKey) {
    SessionStructure.PendingPreKey pending = new SessionStructure.PendingPreKey();
    pending.setSignedprekeyid(signedPreKeyId);
    pending.setBasekey(baseKey.serialize());

    if (preKeyId.isPresent()) {
      pending.setPrekeyid(((Integer) preKeyId.get()).intValue());
    }

    this.sessionStructure.setPendingprekey(pending);
//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .setPendingPreKey(pending.build())
//                                                 .build();
  }

  public boolean hasUnacknowledgedPreKeyMessage() {
    return this.sessionStructure.getPendingprekey() != null;
  }

  public UnacknowledgedPreKeyMessageItems getUnacknowledgedPreKeyMessageItems() {
    try {
      Optional preKeyId;

      if (sessionStructure.getPendingprekey().hasPrekeyid()) {
        preKeyId = Optional.of(new Integer(sessionStructure.getPendingprekey().getPrekeyid()));
      } else {
        preKeyId = Optional.absent();
      }

      return
          new UnacknowledgedPreKeyMessageItems(preKeyId,
                                               sessionStructure.getPendingprekey().getSignedprekeyid(),
                                               Curve.decodePoint(sessionStructure.getPendingprekey()
                                                                                 .getBasekey(), 0));
    } catch (InvalidKeyException e) {
      throw new AssertionError(e);
    }
  }

  public void clearUnacknowledgedPreKeyMessage() {
    this.sessionStructure.clearPendingprekey();
//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .clearPendingPreKey()
//                                                 .build();
  }

  public void setRemoteRegistrationId(int registrationId) {
    this.sessionStructure.setRemoteregistrationid(registrationId);
//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .setRemoteRegistrationId(registrationId)
//                                                 .build();
  }

  public int getRemoteRegistrationId() {
    return this.sessionStructure.getRemoteregistrationid();
//    return this.sessionStructure.getRemoteRegistrationId();
  }

  public void setLocalRegistrationId(int registrationId) {
    this.sessionStructure.setLocalregistrationid(registrationId);
//    this.sessionStructure = this.sessionStructure.toBuilder()
//                                                 .setLocalRegistrationId(registrationId)
//                                                 .build();
  }

  public int getLocalRegistrationId() {
    return this.sessionStructure.getLocalregistrationid();
//    return this.sessionStructure.getLocalRegistrationId();
  }

  public byte[] serialize() {
    return sessionStructure.toBytes();
//    return sessionStructure.toByteArray();
  }

  public static class UnacknowledgedPreKeyMessageItems {
    private final Optional preKeyId;
    private final int               signedPreKeyId;
    private final ECPublicKey       baseKey;

    public UnacknowledgedPreKeyMessageItems(Optional preKeyId,
                                            int signedPreKeyId,
                                            ECPublicKey baseKey)
    {
      this.preKeyId       = preKeyId;
      this.signedPreKeyId = signedPreKeyId;
      this.baseKey        = baseKey;
    }


    public Optional getPreKeyId() {
      return preKeyId;
    }

    public int getSignedPreKeyId() {
      return signedPreKeyId;
    }

    public ECPublicKey getBaseKey() {
      return baseKey;
    }
  }
}
