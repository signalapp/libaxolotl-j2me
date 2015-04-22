package org.whispersystems.libaxolotl;

import junit.framework.TestCase;
import org.whispersystems.libaxolotl.j2me.FakeSecureRandomProvider;
import org.whispersystems.libaxolotl.j2me.jce.BCJmeSecurityProvider;
import org.whispersystems.libaxolotl.j2me.jce.JmeSecurity;

public abstract class AxolotlBaseTestCase extends TestCase {
  public AxolotlBaseTestCase(String name) {
    super(name);
  }

  public void setUp() {
    JmeSecurity.setProvider(new BCJmeSecurityProvider(new FakeSecureRandomProvider()));
  }
}
