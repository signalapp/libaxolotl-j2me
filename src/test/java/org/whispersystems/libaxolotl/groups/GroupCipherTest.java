package org.whispersystems.libaxolotl.groups;

import junit.framework.TestCase;
import org.whispersystems.libaxolotl.AxolotlAddress;
import org.whispersystems.libaxolotl.DuplicateMessageException;
import org.whispersystems.libaxolotl.InvalidMessageException;
import org.whispersystems.libaxolotl.LegacyMessageException;
import org.whispersystems.libaxolotl.NoSessionException;
import org.whispersystems.libaxolotl.j2me.AssertionError;
import org.whispersystems.libaxolotl.j2me.FakeSecureRandomProvider;
import org.whispersystems.libaxolotl.protocol.SenderKeyDistributionMessage;

import java.util.Vector;

public class GroupCipherTest extends TestCase {

  private static final AxolotlAddress SENDER_ADDRESS = new AxolotlAddress("+14150001111", 1);
  private static final SenderKeyName  GROUP_SENDER   = new SenderKeyName("nihilist history reading group", SENDER_ADDRESS);

  public GroupCipherTest(String name) {
    super(name);
  }

  public void testBasicEncryptDecrypt()
      throws LegacyMessageException, DuplicateMessageException, InvalidMessageException, NoSessionException
  {
    InMemorySenderKeyStore aliceStore = new InMemorySenderKeyStore();
    InMemorySenderKeyStore bobStore   = new InMemorySenderKeyStore();

    GroupSessionBuilder aliceSessionBuilder = new GroupSessionBuilder(aliceStore);
    GroupSessionBuilder bobSessionBuilder   = new GroupSessionBuilder(bobStore);

    GroupCipher aliceGroupCipher = new GroupCipher(new FakeSecureRandomProvider(), aliceStore, GROUP_SENDER);
    GroupCipher bobGroupCipher   = new GroupCipher(new FakeSecureRandomProvider(), bobStore, GROUP_SENDER);

    SenderKeyDistributionMessage aliceDistributionMessage =
        aliceSessionBuilder.create(GROUP_SENDER, new FakeSecureRandomProvider());

    bobSessionBuilder.process(GROUP_SENDER, aliceDistributionMessage);

    byte[] ciphertextFromAlice = aliceGroupCipher.encrypt("smert ze smert".getBytes());
    byte[] plaintextFromAlice  = bobGroupCipher.decrypt(ciphertextFromAlice);

    assertTrue(new String(plaintextFromAlice).equals("smert ze smert"));
  }

  public void testBasicRatchet()
      throws LegacyMessageException, DuplicateMessageException, InvalidMessageException, NoSessionException
  {
    InMemorySenderKeyStore aliceStore = new InMemorySenderKeyStore();
    InMemorySenderKeyStore bobStore   = new InMemorySenderKeyStore();

    GroupSessionBuilder aliceSessionBuilder = new GroupSessionBuilder(aliceStore);
    GroupSessionBuilder bobSessionBuilder   = new GroupSessionBuilder(bobStore);

    SenderKeyName aliceName = GROUP_SENDER;

    GroupCipher aliceGroupCipher = new GroupCipher(new FakeSecureRandomProvider(), aliceStore, aliceName);
    GroupCipher bobGroupCipher   = new GroupCipher(new FakeSecureRandomProvider(), bobStore, aliceName);

    SenderKeyDistributionMessage aliceDistributionMessage =
        aliceSessionBuilder.create(aliceName, new FakeSecureRandomProvider());

    bobSessionBuilder.process(aliceName, aliceDistributionMessage);

    byte[] ciphertextFromAlice  = aliceGroupCipher.encrypt("smert ze smert".getBytes());
    byte[] ciphertextFromAlice2 = aliceGroupCipher.encrypt("smert ze smert2".getBytes());
    byte[] ciphertextFromAlice3 = aliceGroupCipher.encrypt("smert ze smert3".getBytes());

    byte[] plaintextFromAlice   = bobGroupCipher.decrypt(ciphertextFromAlice);

    try {
      bobGroupCipher.decrypt(ciphertextFromAlice);
      throw new AssertionError("Should have ratcheted forward!");
    } catch (DuplicateMessageException dme) {
      // good
    }

    byte[] plaintextFromAlice2  = bobGroupCipher.decrypt(ciphertextFromAlice2);
    byte[] plaintextFromAlice3  = bobGroupCipher.decrypt(ciphertextFromAlice3);

    assertTrue(new String(plaintextFromAlice).equals("smert ze smert"));
    assertTrue(new String(plaintextFromAlice2).equals("smert ze smert2"));
    assertTrue(new String(plaintextFromAlice3).equals("smert ze smert3"));
  }

  public void testOutOfOrder()
      throws LegacyMessageException, DuplicateMessageException, InvalidMessageException, NoSessionException
  {
    InMemorySenderKeyStore aliceStore = new InMemorySenderKeyStore();
    InMemorySenderKeyStore bobStore   = new InMemorySenderKeyStore();

    GroupSessionBuilder aliceSessionBuilder = new GroupSessionBuilder(aliceStore);
    GroupSessionBuilder bobSessionBuilder   = new GroupSessionBuilder(bobStore);

    SenderKeyName aliceName = GROUP_SENDER;

    GroupCipher aliceGroupCipher = new GroupCipher(new FakeSecureRandomProvider(), aliceStore, aliceName);
    GroupCipher bobGroupCipher   = new GroupCipher(new FakeSecureRandomProvider(), bobStore, aliceName);

    SenderKeyDistributionMessage aliceDistributionMessage =
        aliceSessionBuilder.create(aliceName, new FakeSecureRandomProvider());

    bobSessionBuilder.process(aliceName, aliceDistributionMessage);

    Vector ciphertexts = new Vector(100);

    for (int i=0;i<100;i++) {
      ciphertexts.addElement(aliceGroupCipher.encrypt("up the punks".getBytes()));
    }

    while (ciphertexts.size() > 0) {
      int    index      = randomInt() % ciphertexts.size();
      byte[] ciphertext = (byte[])ciphertexts.elementAt(index);
      ciphertexts.removeElementAt(index);
      byte[] plaintext  = bobGroupCipher.decrypt(ciphertext);

      assertTrue(new String(plaintext).equals("up the punks"));
    }
  }

  public void testEncryptNoSession() {
    InMemorySenderKeyStore aliceStore = new InMemorySenderKeyStore();
    GroupCipher aliceGroupCipher = new GroupCipher(new FakeSecureRandomProvider(), aliceStore, new SenderKeyName("coolio groupio", new AxolotlAddress("+10002223333", 1)));

    try {
      aliceGroupCipher.encrypt("up the punks".getBytes());
      throw new AssertionError("Should have failed!");
    } catch (NoSessionException nse) {
      // good
    }
  }

  public void testLateJoin() throws NoSessionException, InvalidMessageException, LegacyMessageException, DuplicateMessageException {
    InMemorySenderKeyStore aliceStore = new InMemorySenderKeyStore();
    InMemorySenderKeyStore bobStore   = new InMemorySenderKeyStore();

    GroupSessionBuilder aliceSessionBuilder = new GroupSessionBuilder(aliceStore);


    SenderKeyName aliceName = GROUP_SENDER;

    GroupCipher aliceGroupCipher = new GroupCipher(new FakeSecureRandomProvider(), aliceStore, aliceName);


    SenderKeyDistributionMessage aliceDistributionMessage = aliceSessionBuilder.create(aliceName, new FakeSecureRandomProvider());
    // Send off to some people.

    for (int i=0;i<100;i++) {
      aliceGroupCipher.encrypt("up the punks up the punks up the punks".getBytes());
    }

    // Now Bob Joins.
    GroupSessionBuilder bobSessionBuilder = new GroupSessionBuilder(bobStore);
    GroupCipher         bobGroupCipher    = new GroupCipher(new FakeSecureRandomProvider(), bobStore, aliceName);


    SenderKeyDistributionMessage distributionMessageToBob = aliceSessionBuilder.create(aliceName, new FakeSecureRandomProvider());
    bobSessionBuilder.process(aliceName, new SenderKeyDistributionMessage(distributionMessageToBob.serialize()));

    byte[] ciphertext = aliceGroupCipher.encrypt("welcome to the group".getBytes());
    byte[] plaintext  = bobGroupCipher.decrypt(ciphertext);

    assertEquals(new String(plaintext), "welcome to the group");
  }

  private int randomInt() {
    return new FakeSecureRandomProvider().nextInt(Integer.MAX_VALUE);
  }
}
