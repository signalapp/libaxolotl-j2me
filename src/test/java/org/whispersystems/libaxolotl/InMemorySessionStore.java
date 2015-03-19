package org.whispersystems.libaxolotl;

import org.whispersystems.libaxolotl.state.SessionRecord;
import org.whispersystems.libaxolotl.state.SessionStore;
import org.whispersystems.libaxolotl.j2me.AssertionError;
import org.whispersystems.libaxolotl.util.Pair;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class InMemorySessionStore implements SessionStore {

  private Hashtable sessions = new Hashtable();
//  private Map<AxolotlAddress, byte[]> sessions = new HashMap<>();

  public InMemorySessionStore() {}

//  @Override
  public synchronized SessionRecord loadSession(AxolotlAddress address) {
    try {
      if (containsSession(address)) {
        return new SessionRecord((byte[])sessions.get(address));
      } else {
        return new SessionRecord();
      }
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

//  @Override
  public synchronized Vector getSubDeviceSessions(String name) {
    Vector      deviceIds = new Vector();
    Enumeration keys      = sessions.keys();

    while (keys.hasMoreElements()) {
      AxolotlAddress key = (AxolotlAddress)keys.nextElement();

      if (key.getName().equals(name)) {
        deviceIds.addElement(new Long(key.getDeviceId()));
      }
    }

    return deviceIds;
  }

//  @Override
  public synchronized void storeSession(AxolotlAddress address, SessionRecord record) {
    sessions.put(address, record.serialize());
  }

//  @Override
  public synchronized boolean containsSession(AxolotlAddress address) {
    return sessions.containsKey(address);
  }

//  @Override
  public synchronized void deleteSession(AxolotlAddress address) {
    sessions.remove(address);
  }

//  @Override
  public synchronized void deleteAllSessions(String name) {
    Enumeration enumeration = sessions.keys();

    while (enumeration.hasMoreElements()) {
      AxolotlAddress key = (AxolotlAddress)enumeration.nextElement();

      if (key.getName().equals(name)) {
        sessions.remove(key);
      }
    }
  }
}
