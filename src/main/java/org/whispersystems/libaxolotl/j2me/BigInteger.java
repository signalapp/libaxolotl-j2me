package org.whispersystems.libaxolotl.j2me;

public class BigInteger  {
  /**
   * The signum of this BigInteger: -1 for negative, 0 for zero, or
   * 1 for positive.  Note that the BigInteger zero <i>must</i> have
   * a signum of 0.  This is necessary to ensures that there is exactly one
   * representation for each BigInteger value.
   *
   * @serial
   */
  final int signum;

  /**
   * The magnitude of this BigInteger, in <i>big-endian</i> order: the
   * zeroth element of this array is the most-significant int of the
   * magnitude.  The magnitude must be "minimal" in that the most-significant
   * int ({@code mag[0]}) must be non-zero.  This is necessary to
   * ensure that there is exactly one representation for each BigInteger
   * value.  Note that this implies that the BigInteger zero has a
   * zero-length mag array.
   */
  final int[] mag;

  /**
   * This mask is used to obtain the value of an int as if it were unsigned.
   */
  final static long LONG_MASK = 0xffffffffL;

  /**
   * This constant limits {@code mag.length} of BigIntegers to the supported
   * range.
   */
  private static final int MAX_MAG_LENGTH = Integer.MAX_VALUE / 32 + 1; // (1 << 26)

  /**
   * Translates a byte array containing the two's-complement binary
   * representation of a BigInteger into a BigInteger.  The input array is
   * assumed to be in <i>big-endian</i> byte-order: the most significant
   * byte is in the zeroth element.
   *
   * @param  val big-endian two's-complement binary representation of
   *         BigInteger.
   * @throws NumberFormatException {@code val} is zero bytes long.
   */
  public BigInteger(byte[] val) {
    if (val.length == 0)
      throw new NumberFormatException("Zero length BigInteger");

    if (val[0] < 0) {
      mag = makePositive(val);
      signum = -1;
    } else {
      mag = stripLeadingZeroBytes(val);
      signum = (mag.length == 0 ? 0 : 1);
    }
    if (mag.length >= MAX_MAG_LENGTH) {
      checkRange();
    }
  }


  /**
   * Throws an {@code ArithmeticException} if the {@code BigInteger} would be
   * out of the supported range.
   *
   * @throws ArithmeticException if {@code this} exceeds the supported range.
   */
  private void checkRange() {
    if (mag.length > MAX_MAG_LENGTH || mag.length == MAX_MAG_LENGTH && mag[0] < 0) {
      reportOverflow();
    }
  }

  private static void reportOverflow() {
    throw new ArithmeticException("BigInteger would overflow supported range");
  }

  /**
   * Compares this BigInteger with the specified BigInteger.  This
   * method is provided in preference to individual methods for each
   * of the six boolean comparison operators ({@literal <}, ==,
   * {@literal >}, {@literal >=}, !=, {@literal <=}).  The suggested
   * idiom for performing these comparisons is: {@code
   * (x.compareTo(y)} &lt;<i>op</i>&gt; {@code 0)}, where
   * &lt;<i>op</i>&gt; is one of the six comparison operators.
   *
   * @param  val BigInteger to which this BigInteger is to be compared.
   * @return -1, 0 or 1 as this BigInteger is numerically less than, equal
   *         to, or greater than {@code val}.
   */
  public int compareTo(BigInteger val) {
    if (signum == val.signum) {
      switch (signum) {
        case 1:
          return compareMagnitude(val);
        case -1:
          return val.compareMagnitude(this);
        default:
          return 0;
      }
    }
    return signum > val.signum ? 1 : -1;
  }

  /**
   * Compares the magnitude array of this BigInteger with the specified
   * BigInteger's. This is the version of compareTo ignoring sign.
   *
   * @param val BigInteger whose magnitude array to be compared.
   * @return -1, 0 or 1 as this magnitude array is less than, equal to or
   *         greater than the magnitude aray for the specified BigInteger's.
   */
  final int compareMagnitude(BigInteger val) {
    int[] m1 = mag;
    int len1 = m1.length;
    int[] m2 = val.mag;
    int len2 = m2.length;
    if (len1 < len2)
      return -1;
    if (len1 > len2)
      return 1;
    for (int i = 0; i < len1; i++) {
      int a = m1[i];
      int b = m2[i];
      if (a != b)
        return ((a & LONG_MASK) < (b & LONG_MASK)) ? -1 : 1;
    }
    return 0;
  }


  /**
   * Compares this BigInteger with the specified Object for equality.
   *
   * @param  x Object to which this BigInteger is to be compared.
   * @return {@code true} if and only if the specified Object is a
   *         BigInteger whose value is numerically equal to this BigInteger.
   */
  public boolean equals(Object x) {
    // This test is just an optimization, which may or may not help
    if (x == this)
      return true;

    if (!(x instanceof BigInteger))
      return false;

    BigInteger xInt = (BigInteger) x;
    if (xInt.signum != signum)
      return false;

    int[] m = mag;
    int len = m.length;
    int[] xm = xInt.mag;
    if (len != xm.length)
      return false;

    for (int i = 0; i < len; i++)
      if (xm[i] != m[i])
        return false;

    return true;
  }

  // Hash Function

  /**
   * Returns the hash code for this BigInteger.
   *
   * @return hash code for this BigInteger.
   */
  public int hashCode() {
    int hashCode = 0;

    for (int i=0; i < mag.length; i++)
      hashCode = (int)(31*hashCode + (mag[i] & LONG_MASK));

    return hashCode * signum;
  }

  /**
   * Returns a copy of the input array stripped of any leading zero bytes.
   */
  private static int[] stripLeadingZeroBytes(byte a[]) {
    int byteLength = a.length;
    int keep;

    // Find first nonzero byte
    for (keep = 0; keep < byteLength && a[keep] == 0; keep++)
      ;

    // Allocate new array and copy relevant part of input array
    int intLength = ((byteLength - keep) + 3) >>> 2;
    int[] result = new int[intLength];
    int b = byteLength - 1;
    for (int i = intLength-1; i >= 0; i--) {
      result[i] = a[b--] & 0xff;
      int bytesRemaining = b - keep + 1;
      int bytesToTransfer = Math.min(3, bytesRemaining);
      for (int j=8; j <= (bytesToTransfer << 3); j += 8)
        result[i] |= ((a[b--] & 0xff) << j);
    }
    return result;
  }

  /**
   * Takes an array a representing a negative 2's-complement number and
   * returns the minimal (no leading zero bytes) unsigned whose value is -a.
   */
  private static int[] makePositive(byte a[]) {
    int keep, k;
    int byteLength = a.length;

    // Find first non-sign (0xff) byte of input
    for (keep=0; keep < byteLength && a[keep] == -1; keep++)
      ;


        /* Allocate output array.  If all non-sign bytes are 0x00, we must
         * allocate space for one extra output byte. */
    for (k=keep; k < byteLength && a[k] == 0; k++)
      ;

    int extraByte = (k == byteLength) ? 1 : 0;
    int intLength = ((byteLength - keep + extraByte) + 3) >>> 2;
    int result[] = new int[intLength];

        /* Copy one's complement of input into output, leaving extra
         * byte (if it exists) == 0x00 */
    int b = byteLength - 1;
    for (int i = intLength-1; i >= 0; i--) {
      result[i] = a[b--] & 0xff;
      int numBytesToTransfer = Math.min(3, b-keep+1);
      if (numBytesToTransfer < 0)
        numBytesToTransfer = 0;
      for (int j=8; j <= 8*numBytesToTransfer; j += 8)
        result[i] |= ((a[b--] & 0xff) << j);

      // Mask indicates which bits must be complemented
      int mask = -1 >>> (8*(3-numBytesToTransfer));
      result[i] = ~result[i] & mask;
    }

    // Add one to one's complement to generate two's complement
    for (int i=result.length-1; i >= 0; i--) {
      result[i] = (int)((result[i] & LONG_MASK) + 1);
      if (result[i] != 0)
        break;
    }

    return result;
  }
}

