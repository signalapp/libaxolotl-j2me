package org.whispersystems.libaxolotl.util;

import org.whispersystems.curve25519.SecureRandomProvider;
import org.whispersystems.libaxolotl.IdentityKey;
import org.whispersystems.libaxolotl.IdentityKeyPair;
import org.whispersystems.libaxolotl.InvalidKeyException;
import org.whispersystems.libaxolotl.ecc.Curve;
import org.whispersystems.libaxolotl.ecc.ECKeyPair;
import org.whispersystems.libaxolotl.state.PreKeyRecord;
import org.whispersystems.libaxolotl.state.SignedPreKeyRecord;

import java.util.Vector;

/**
 * Helper class for generating keys of different types.
 *
 * @author Moxie Marlinspike
 */
public class KeyHelper {

  private KeyHelper() {}

  /**
   * Generate an identity key pair.  Clients should only do this once,
   * at install time.
   *
   * @return the generated IdentityKeyPair.
   */
  public static IdentityKeyPair generateIdentityKeyPair(SecureRandomProvider secureRandom) {
    ECKeyPair   keyPair   = Curve.generateKeyPair(secureRandom);
    IdentityKey publicKey = new IdentityKey(keyPair.getPublicKey());
    return new IdentityKeyPair(publicKey, keyPair.getPrivateKey());
  }

  /**
   * Generate a registration ID.  Clients should only do this once,
   * at install time.
   *
   * @param extendedRange By default (false), the generated registration
   *                      ID is sized to require the minimal possible protobuf
   *                      encoding overhead. Specify true if the caller needs
   *                      the full range of MAX_INT at the cost of slightly
   *                      higher encoding overhead.
   * @return the generated registration ID.
   */
  public static int generateRegistrationId(SecureRandomProvider secureRandom, boolean extendedRange) {
    if (extendedRange) return secureRandom.nextInt(Integer.MAX_VALUE - 1) + 1;
    else               return secureRandom.nextInt(16380) + 1;
  }

  public static int getRandomSequence(SecureRandomProvider secureRandomProvider, int max) {
    return secureRandomProvider.nextInt(max);
  }

  /**
   * Generate a list of PreKeys.  Clients should do this at install time, and
   * subsequently any time the list of PreKeys stored on the server runs low.
   * <p>
   * PreKey IDs are shorts, so they will eventually be repeated.  Clients should
   * store PreKeys in a circular buffer, so that they are repeated as infrequently
   * as possible.
   *
   * @param start The starting PreKey ID, inclusive.
   * @param count The number of PreKeys to generate.
   * @return the list of generated PreKeyRecords.
   */
  public static Vector generatePreKeys(SecureRandomProvider secureRandom, int start, int count) {
    Vector results = new Vector();

    start--;

    for (int i=0;i<count;i++) {
      results.addElement(new PreKeyRecord(((start + i) % (Medium.MAX_VALUE - 1)) + 1, Curve.generateKeyPair(secureRandom)));
    }

    return results;
  }

  /**
   * Generate the last resort PreKey.  Clients should do this only once, at install
   * time, and durably store it for the length of the install.
   *
   * @return the generated last resort PreKeyRecord.
   */
  public static PreKeyRecord generateLastResortPreKey(SecureRandomProvider secureRandom) {
    ECKeyPair keyPair = Curve.generateKeyPair(secureRandom);
    return new PreKeyRecord(Medium.MAX_VALUE, keyPair);
  }

  /**
   * Generate a signed PreKey
   *
   * @param identityKeyPair The local client's identity key pair.
   * @param signedPreKeyId The PreKey id to assign the generated signed PreKey
   *
   * @return the generated signed PreKey
   * @throws InvalidKeyException when the provided identity key is invalid
   */
  public static SignedPreKeyRecord generateSignedPreKey(SecureRandomProvider secureRandom,
                                                        IdentityKeyPair identityKeyPair, int signedPreKeyId)
      throws InvalidKeyException
  {
    ECKeyPair keyPair   = Curve.generateKeyPair(secureRandom);
    byte[]    signature = Curve.calculateSignature(secureRandom, identityKeyPair.getPrivateKey(), keyPair.getPublicKey().serialize());

    return new SignedPreKeyRecord(signedPreKeyId, System.currentTimeMillis(), keyPair, signature);
  }


  public static ECKeyPair generateSenderSigningKey(SecureRandomProvider secureRandom) {
    return Curve.generateKeyPair(secureRandom);
  }

  public static byte[] generateSenderKey(SecureRandomProvider secureRandom) {
    byte[] key = new byte[32];
    secureRandom.nextBytes(key);

    return key;
  }

  public static int generateSenderKeyId(SecureRandomProvider secureRandom) {
    return secureRandom.nextInt(Integer.MAX_VALUE);
  }

}
