package org.whispersystems.libaxolotl.j2me.jce;

public class JmeSecurity {

  private static JmeSecurityProvider provider = null;

  public static void setProvider(JmeSecurityProvider provider) {
    JmeSecurity.provider = provider;
  }

  public static JmeSecurityProvider getProvider() {
    if (provider == null) {
      throw new IllegalStateException("No provider configured!");
    }

    return provider;
  }

}
