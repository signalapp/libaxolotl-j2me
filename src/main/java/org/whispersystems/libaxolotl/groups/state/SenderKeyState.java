/**
 * Copyright (C) 2014-2015 Open Whisper Systems
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
package org.whispersystems.libaxolotl.groups.state;

import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.ecc.ECPrivateKey;
import org.whispersystems.libaxolotl.ecc.ECPublicKey;
import org.whispersystems.libaxolotl.groups.ratchet.SenderChainKey;
import org.whispersystems.libaxolotl.groups.ratchet.SenderMessageKey;
import org.whispersystems.libaxolotl.state.protos.SenderKeyStateStructure;
import org.whispersystems.libaxolotl.util.guava.Optional;

import java.util.Vector;

/**
 * Represents the state of an individual SenderKey ratchet.
 *
 * @author Moxie Marlinspike
 */
public class SenderKeyState {

  private static final int MAX_MESSAGE_KEYS = 2000;

  private SenderKeyStateStructure senderKeyStateStructure;

  public SenderKeyState(int id, int iteration, byte[] chainKey, ECPublicKey signatureKey) {
    this(id, iteration, chainKey, signatureKey, Optional.absent());
  }

  public SenderKeyState(int id, int iteration, byte[] chainKey, ECKeyPair signatureKey) {
    this(id, iteration, chainKey, signatureKey.getPublicKey(), Optional.of(signatureKey.getPrivateKey()));
  }

  private SenderKeyState(int id, int iteration, byte[] chainKey,
                        ECPublicKey signatureKeyPublic,
                        Optional signatureKeyPrivate)
  {
    SenderKeyStateStructure.SenderChainKey senderChainKeyStructure =
        new SenderKeyStateStructure.SenderChainKey();

    senderChainKeyStructure.setIteration(iteration);
    senderChainKeyStructure.setSeed(chainKey);

    SenderKeyStateStructure.SenderSigningKey signingKeyStructure =
        new SenderKeyStateStructure.SenderSigningKey();

    signingKeyStructure.setPublic(signatureKeyPublic.serialize());

    if (signatureKeyPrivate.isPresent()) {
      signingKeyStructure.setPrivate(((ECPrivateKey)signatureKeyPrivate.get()).serialize());
    }

    this.senderKeyStateStructure = new SenderKeyStateStructure();
    this.senderKeyStateStructure.setSenderkeyid(id);
    this.senderKeyStateStructure.setSenderchainkey(senderChainKeyStructure);
    this.senderKeyStateStructure.setSendersigningkey(signingKeyStructure);
  }

  public SenderKeyState(SenderKeyStateStructure senderKeyStateStructure) {
    this.senderKeyStateStructure = senderKeyStateStructure;
  }

  public int getKeyId() {
    return senderKeyStateStructure.getSenderkeyid();
  }

  public SenderChainKey getSenderChainKey() {
    return new SenderChainKey(senderKeyStateStructure.getSenderchainkey().getIteration(),
                              senderKeyStateStructure.getSenderchainkey().getSeed());
  }

  public void setSenderChainKey(SenderChainKey chainKey) {
    SenderKeyStateStructure.SenderChainKey senderChainKeyStructure =
        new SenderKeyStateStructure.SenderChainKey();

    senderChainKeyStructure.setIteration(chainKey.getIteration());
    senderChainKeyStructure.setSeed(chainKey.getSeed());

    this.senderKeyStateStructure.setSenderchainkey(senderChainKeyStructure);
  }

  public ECPublicKey getSigningKeyPublic() throws InvalidKeyException {
    return Curve.decodePoint(senderKeyStateStructure.getSendersigningkey()
                                                    .getPublic(), 0);
  }

  public ECPrivateKey getSigningKeyPrivate() {
    return Curve.decodePrivatePoint(senderKeyStateStructure.getSendersigningkey()
                                                           .getPrivate());
  }

  public boolean hasSenderMessageKey(int iteration) {
    for (int i=0;i<senderKeyStateStructure.getSendermessagekeysVector().size();i++) {
      SenderKeyStateStructure.SenderMessageKey senderMessageKey = (SenderKeyStateStructure.SenderMessageKey)senderKeyStateStructure.getSendermessagekeysVector().elementAt(i);
      if (senderMessageKey.getIteration() == iteration) return true;
    }

    return false;
  }

  public void addSenderMessageKey(SenderMessageKey senderMessageKey) {
    SenderKeyStateStructure.SenderMessageKey senderMessageKeyStructure =
        new SenderKeyStateStructure.SenderMessageKey();

    senderMessageKeyStructure.setIteration(senderMessageKey.getIteration());
    senderMessageKeyStructure.setSeed(senderMessageKey.getSeed());

    this.senderKeyStateStructure.addSendermessagekeys(senderMessageKeyStructure);

    if (this.senderKeyStateStructure.getSendermessagekeysCount() > MAX_MESSAGE_KEYS) {
      this.senderKeyStateStructure.getSendermessagekeysVector().removeElementAt(0);
    }

//
//    this.senderKeyStateStructure = this.senderKeyStateStructure.toBuilder()
//                                                               .addSenderMessageKeys(senderMessageKeyStructure)
//                                                               .build();
  }

  public SenderMessageKey removeSenderMessageKey(int iteration) {
    Vector keys = senderKeyStateStructure.getSendermessagekeysVector();
//
//
//    List<SenderKeyStateStructure.SenderMessageKey>     keys     = new LinkedList<>(senderKeyStateStructure.getSenderMessageKeysList());
//    Iterator<SenderKeyStateStructure.SenderMessageKey> iterator = keys.iterator();

    SenderKeyStateStructure.SenderMessageKey result = null;

    for (int i=0;i<keys.size();i++) {
      SenderKeyStateStructure.SenderMessageKey senderMessageKey = (SenderKeyStateStructure.SenderMessageKey)keys.elementAt(i);

      if (senderMessageKey.getIteration() == iteration) {
        result = senderMessageKey;
        keys.removeElementAt(i);
//        iterator.remove();
        break;
      }
    }


//    this.senderKeyStateStructure = this.senderKeyStateStructure.toBuilder()
//                                                               .clearSenderMessageKeys()
//                                                               .addAllSenderMessageKeys(keys)
//                                                               .build();

    if (result != null) {
      return new SenderMessageKey(result.getIteration(), result.getSeed());
    } else {
      return null;
    }
  }

  public SenderKeyStateStructure getStructure() {
    return senderKeyStateStructure;
  }
}
