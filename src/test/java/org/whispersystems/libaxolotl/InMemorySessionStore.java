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
//  private Map<Pair<Long, Integer>, byte[]> sessions = new HashMap<>();

  public InMemorySessionStore() {}

//  @Override
  public synchronized SessionRecord loadSession(long recipientId, int deviceId) {
    try {
      if (containsSession(recipientId, deviceId)) {
        return new SessionRecord((byte[])sessions.get(new Pair(new Long(recipientId), new Integer(deviceId))));
      } else {
        return new SessionRecord();
      }
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

//  @Override
  public synchronized Vector getSubDeviceSessions(long recipientId) {
    Vector      deviceIds = new Vector();
    Enumeration keys      = sessions.keys();

    while (keys.hasMoreElements()) {
      Pair key = (Pair)keys.nextElement();

      if (((Long)key.first()).longValue() == recipientId) {
        deviceIds.addElement((Integer)key.second());
      }
    }

    return deviceIds;
  }

//  @Override
  public synchronized void storeSession(long recipientId, int deviceId, SessionRecord record) {
    sessions.put(new Pair(new Long(recipientId), new Integer(deviceId)), record.serialize());
  }

//  @Override
  public synchronized boolean containsSession(long recipientId, int deviceId) {
    return sessions.containsKey(new Pair(new Long(recipientId), new Integer(deviceId)));
  }

//  @Override
  public synchronized void deleteSession(long recipientId, int deviceId) {
    sessions.remove(new Pair(new Long(recipientId), new Integer(deviceId)));
  }

//  @Override
  public synchronized void deleteAllSessions(long recipientId) {
    Enumeration enumeration = sessions.keys();

    while (enumeration.hasMoreElements()) {
      Pair key = (Pair)enumeration.nextElement();

      if (((Long)key.first()).longValue() == recipientId) {
        sessions.remove(key);
      }
    }
  }
}
