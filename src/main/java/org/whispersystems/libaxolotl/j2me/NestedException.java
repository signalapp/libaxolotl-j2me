package org.whispersystems.libaxolotl.j2me;

public class NestedException extends Exception {

  private final Throwable nested;

  public NestedException() {
    super();
    this.nested = null;
  }

  public NestedException(String message) {
    this(message, null);
  }

  public NestedException(Throwable parent) {
    this(parent.getMessage(), parent);
  }

  public NestedException(String detail, Throwable parent) {
    super(detail);
    this.nested = parent;
  }

  public Throwable getNested() {
    return nested;
  }
}
