package org.whispersystems.libaxolotl;

import org.whispersystems.libaxolotl.j2me.NestedException;

public class NoSessionException extends NestedException {
  public NoSessionException(String s) {
    super(s);
  }

  public NoSessionException(Exception nested) {
    super(nested);
  }
}
