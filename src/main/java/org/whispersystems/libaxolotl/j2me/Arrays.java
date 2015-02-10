package org.whispersystems.libaxolotl.j2me;

public class Arrays {

  public static boolean equals(byte[] a, byte[] a2) {
    if (a==a2)
      return true;
    if (a==null || a2==null)
      return false;

    int length = a.length;
    if (a2.length != length)
      return false;

    for (int i=0; i<length; i++)
      if (a[i] != a2[i])
        return false;

    return true;
  }

  public static int hashCode(byte a[]) {
    if (a == null)
      return 0;

    int result = 1;
    for (int i = 0; i< a.length ; i++)
      result = 31 * result + a[i];

    return result;
  }

  public static void fill(byte[] a, byte val) {
    for (int i = 0, len = a.length; i < len; i++)
      a[i] = val;
  }


}