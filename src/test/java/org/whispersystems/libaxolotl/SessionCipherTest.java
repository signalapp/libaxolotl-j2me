package org.whispersystems.libaxolotl;


import junit.framework.TestCase;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.j2me.Collections;
import org.whispersystems.libaxolotl.j2me.FakeSecureRandomProvider;
import org.whispersystems.libaxolotl.j2me.jce.BCJmeSecurityProvider;
import org.whispersystems.libaxolotl.j2me.jce.JmeSecurity;
import org.whispersystems.libaxolotl.protocol.CiphertextMessage;
import org.whispersystems.libaxolotl.protocol.WhisperMessage;
import org.whispersystems.libaxolotl.ratchet.AliceAxolotlParameters;
import org.whispersystems.libaxolotl.ratchet.BobAxolotlParameters;
import org.whispersystems.libaxolotl.ratchet.RatchetingSession;
import org.whispersystems.libaxolotl.state.AxolotlStore;
import org.whispersystems.libaxolotl.state.SessionRecord;
import org.whispersystems.libaxolotl.state.SessionState;
import org.whispersystems.libaxolotl.j2me.Arrays;
import org.whispersystems.libaxolotl.util.guava.Optional;

import java.util.Random;
import java.util.Vector;



public class SessionCipherTest extends AxolotlBaseTestCase {

  public SessionCipherTest(String name) {
    super(name);
  }

  public void testBasicSessionV2()
      throws InvalidKeyException, DuplicateMessageException,
      LegacyMessageException, InvalidMessageException, NoSessionException
  {
    SessionRecord aliceSessionRecord = new SessionRecord();
    SessionRecord bobSessionRecord   = new SessionRecord();

    initializeSessionsV2(aliceSessionRecord.getSessionState(), bobSessionRecord.getSessionState());
    runInteraction(aliceSessionRecord, bobSessionRecord);
  }

  public void testBasicSessionV3()
      throws InvalidKeyException, DuplicateMessageException,
      LegacyMessageException, InvalidMessageException, NoSessionException
  {
    SessionRecord aliceSessionRecord = new SessionRecord();
    SessionRecord bobSessionRecord   = new SessionRecord();

    initializeSessionsV3(aliceSessionRecord.getSessionState(), bobSessionRecord.getSessionState());
    runInteraction(aliceSessionRecord, bobSessionRecord);
  }

  private void runInteraction(SessionRecord aliceSessionRecord, SessionRecord bobSessionRecord)
      throws DuplicateMessageException, LegacyMessageException, InvalidMessageException, NoSessionException {
    AxolotlStore aliceStore = new InMemoryAxolotlStore();
    AxolotlStore bobStore   = new InMemoryAxolotlStore();

    aliceStore.storeSession(new AxolotlAddress("+14159999999", 1), aliceSessionRecord);
    bobStore.storeSession(new AxolotlAddress("+14158888888", 1), bobSessionRecord);

    SessionCipher     aliceCipher    = new SessionCipher(aliceStore, new AxolotlAddress("+14159999999", 1));
    SessionCipher     bobCipher      = new SessionCipher(bobStore, new AxolotlAddress("+14158888888", 1));

    byte[]            alicePlaintext = "This is a plaintext message.".getBytes();
    CiphertextMessage message        = aliceCipher.encrypt(alicePlaintext);
    byte[]            bobPlaintext   = bobCipher.decrypt(new WhisperMessage(message.serialize()));

    assertTrue(Arrays.equals(alicePlaintext, bobPlaintext));

    byte[]            bobReply      = "This is a message from Bob.".getBytes();
    CiphertextMessage reply         = bobCipher.encrypt(bobReply);
    byte[]            receivedReply = aliceCipher.decrypt(new WhisperMessage(reply.serialize()));

    assertTrue(Arrays.equals(bobReply, receivedReply));

    Vector aliceCiphertextMessages = new Vector();
    Vector alicePlaintextMessages  = new Vector();

    for (int i=0;i<50;i++) {
      alicePlaintextMessages.addElement(("смерть за смерть " + i).getBytes());
      aliceCiphertextMessages.addElement(aliceCipher.encrypt(("смерть за смерть " + i).getBytes()));
    }

    long seed = System.currentTimeMillis();

    Collections.shuffle(aliceCiphertextMessages, new Random(seed));
    Collections.shuffle(alicePlaintextMessages, new Random(seed));

    for (int i=0;i<aliceCiphertextMessages.size() / 2;i++) {
      byte[] receivedPlaintext = bobCipher.decrypt(new WhisperMessage(((CiphertextMessage)aliceCiphertextMessages.elementAt(i)).serialize()));
      assertTrue(Arrays.equals(receivedPlaintext, (byte[])alicePlaintextMessages.elementAt(i)));
    }

    Vector bobCiphertextMessages = new Vector();
    Vector bobPlaintextMessages  = new Vector();

    for (int i=0;i<20;i++) {
      bobPlaintextMessages.addElement(("смерть за смерть " + i).getBytes());
      bobCiphertextMessages.addElement(bobCipher.encrypt(("смерть за смерть " + i).getBytes()));
    }

    seed = System.currentTimeMillis();

    Collections.shuffle(bobCiphertextMessages, new Random(seed));
    Collections.shuffle(bobPlaintextMessages, new Random(seed));

    for (int i=0;i<bobCiphertextMessages.size() / 2;i++) {
      byte[] receivedPlaintext = aliceCipher.decrypt(new WhisperMessage(((CiphertextMessage)bobCiphertextMessages.elementAt(i)).serialize()));
      assertTrue(Arrays.equals(receivedPlaintext, (byte[])bobPlaintextMessages.elementAt(i)));
    }

    for (int i=aliceCiphertextMessages.size()/2;i<aliceCiphertextMessages.size();i++) {
      byte[] receivedPlaintext = bobCipher.decrypt(new WhisperMessage(((CiphertextMessage)aliceCiphertextMessages.elementAt(i)).serialize()));
      assertTrue(Arrays.equals(receivedPlaintext, (byte[])alicePlaintextMessages.elementAt(i)));
    }

    for (int i=bobCiphertextMessages.size() / 2;i<bobCiphertextMessages.size();i++) {
      byte[] receivedPlaintext = aliceCipher.decrypt(new WhisperMessage(((CiphertextMessage)bobCiphertextMessages.elementAt(i)).serialize()));
      assertTrue(Arrays.equals(receivedPlaintext, (byte[])bobPlaintextMessages.elementAt(i)));
    }
  }


  private void initializeSessionsV2(SessionState aliceSessionState, SessionState bobSessionState)
      throws InvalidKeyException
  {
    ECKeyPair       aliceIdentityKeyPair = Curve.generateKeyPair(new FakeSecureRandomProvider());
    IdentityKeyPair aliceIdentityKey     = new IdentityKeyPair(new IdentityKey(aliceIdentityKeyPair.getPublicKey()),
                                                               aliceIdentityKeyPair.getPrivateKey());
    ECKeyPair       aliceBaseKey         = Curve.generateKeyPair(new FakeSecureRandomProvider());
    ECKeyPair       aliceEphemeralKey    = Curve.generateKeyPair(new FakeSecureRandomProvider());

    ECKeyPair       bobIdentityKeyPair   = Curve.generateKeyPair(new FakeSecureRandomProvider());
    IdentityKeyPair bobIdentityKey       = new IdentityKeyPair(new IdentityKey(bobIdentityKeyPair.getPublicKey()),
                                                               bobIdentityKeyPair.getPrivateKey());
    ECKeyPair       bobBaseKey           = Curve.generateKeyPair(new FakeSecureRandomProvider());
    ECKeyPair       bobEphemeralKey      = bobBaseKey;

    AliceAxolotlParameters aliceParameters = AliceAxolotlParameters.newBuilder()
        .setOurIdentityKey(aliceIdentityKey)
        .setOurBaseKey(aliceBaseKey)
        .setTheirIdentityKey(bobIdentityKey.getPublicKey())
        .setTheirSignedPreKey(bobEphemeralKey.getPublicKey())
        .setTheirRatchetKey(bobEphemeralKey.getPublicKey())
        .setTheirOneTimePreKey(Optional.absent())
        .create();

    BobAxolotlParameters bobParameters = BobAxolotlParameters.newBuilder()
        .setOurIdentityKey(bobIdentityKey)
        .setOurOneTimePreKey(Optional.absent())
        .setOurRatchetKey(bobEphemeralKey)
        .setOurSignedPreKey(bobBaseKey)
        .setTheirBaseKey(aliceBaseKey.getPublicKey())
        .setTheirIdentityKey(aliceIdentityKey.getPublicKey())
        .create();

    RatchetingSession.initializeSession(new FakeSecureRandomProvider(), aliceSessionState, 2, aliceParameters);
    RatchetingSession.initializeSession(bobSessionState, 2, bobParameters);
  }

  private void initializeSessionsV3(SessionState aliceSessionState, SessionState bobSessionState)
      throws InvalidKeyException
  {
    ECKeyPair       aliceIdentityKeyPair = Curve.generateKeyPair(new FakeSecureRandomProvider());
    IdentityKeyPair aliceIdentityKey     = new IdentityKeyPair(new IdentityKey(aliceIdentityKeyPair.getPublicKey()),
                                                               aliceIdentityKeyPair.getPrivateKey());
    ECKeyPair       aliceBaseKey         = Curve.generateKeyPair(new FakeSecureRandomProvider());
    ECKeyPair       aliceEphemeralKey    = Curve.generateKeyPair(new FakeSecureRandomProvider());

    ECKeyPair       alicePreKey          = aliceBaseKey;

    ECKeyPair       bobIdentityKeyPair   = Curve.generateKeyPair(new FakeSecureRandomProvider());
    IdentityKeyPair bobIdentityKey       = new IdentityKeyPair(new IdentityKey(bobIdentityKeyPair.getPublicKey()),
                                                               bobIdentityKeyPair.getPrivateKey());
    ECKeyPair       bobBaseKey           = Curve.generateKeyPair(new FakeSecureRandomProvider());
    ECKeyPair       bobEphemeralKey      = bobBaseKey;

    ECKeyPair       bobPreKey            = Curve.generateKeyPair(new FakeSecureRandomProvider());

    AliceAxolotlParameters aliceParameters = AliceAxolotlParameters.newBuilder()
        .setOurBaseKey(aliceBaseKey)
        .setOurIdentityKey(aliceIdentityKey)
        .setTheirOneTimePreKey(Optional.absent())
        .setTheirRatchetKey(bobEphemeralKey.getPublicKey())
        .setTheirSignedPreKey(bobBaseKey.getPublicKey())
        .setTheirIdentityKey(bobIdentityKey.getPublicKey())
        .create();

    BobAxolotlParameters bobParameters = BobAxolotlParameters.newBuilder()
        .setOurRatchetKey(bobEphemeralKey)
        .setOurSignedPreKey(bobBaseKey)
        .setOurOneTimePreKey(Optional.absent())
        .setOurIdentityKey(bobIdentityKey)
        .setTheirIdentityKey(aliceIdentityKey.getPublicKey())
        .setTheirBaseKey(aliceBaseKey.getPublicKey())
        .create();

    RatchetingSession.initializeSession(new FakeSecureRandomProvider(), aliceSessionState, 3, aliceParameters);
    RatchetingSession.initializeSession(bobSessionState, 3, bobParameters);
  }
}
