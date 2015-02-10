package org.whispersystems.libaxolotl.j2me;

public class AssertionError extends RuntimeException {

  private final Throwable nested;

  public AssertionError(String message) {
    super(message);
    this.nested = null;
  }

  public AssertionError(Throwable nested) {
    super("Caused by");
    this.nested = nested;
  }
}
