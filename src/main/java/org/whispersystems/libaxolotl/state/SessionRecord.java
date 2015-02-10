package org.whispersystems.libaxolotl.state;

import org.whispersystems.libaxolotl.state.protos.RecordStructure;
import org.whispersystems.libaxolotl.state.protos.SessionStructure;
import org.whispersystems.libaxolotl.j2me.Arrays;

import java.io.IOException;
import java.util.Vector;


/**
 * A SessionRecord encapsulates the state of an ongoing session.
 *
 * @author Moxie Marlinspike
 */
public class SessionRecord {

  private static final int ARCHIVED_STATES_MAX_LENGTH = 40;

  private SessionState sessionState   = new SessionState();
  private Vector       previousStates = new Vector();
  private boolean      fresh          = false;

  public SessionRecord() {
    this.fresh = true;
  }

  public SessionRecord(SessionState sessionState) {
    this.sessionState = sessionState;
    this.fresh        = false;
  }

  public SessionRecord(byte[] serialized) throws IOException {
    RecordStructure record = RecordStructure.fromBytes(serialized);
    this.sessionState = new SessionState(record.getCurrentsession());
    this.fresh        = false;

    for (int i=0;i<record.getPrevioussessionsVector().size();i++) {
      previousStates.addElement(new SessionState((SessionStructure)record.getPrevioussessionsVector().elementAt(i)));
    }
  }

  public boolean hasSessionState(int version, byte[] aliceBaseKey) {
    if (sessionState.getSessionVersion() == version &&
        Arrays.equals(aliceBaseKey, sessionState.getAliceBaseKey()))
    {
      return true;
    }

    for (int i=0;i<previousStates.size();i++) {
      SessionState state = (SessionState)previousStates.elementAt(i);
      if (state.getSessionVersion() == version &&
          Arrays.equals(aliceBaseKey, state.getAliceBaseKey()))
      {
        return true;
      }
    }

    return false;
  }

  public SessionState getSessionState() {
    return sessionState;
  }

  /**
   * @return the list of all currently maintained "previous" session states.
   */
  public Vector getPreviousSessionStates() {
    return previousStates;
  }


  public boolean isFresh() {
    return fresh;
  }

  /**
   * Move the current {@link SessionState} into the list of "previous" session states,
   * and replace the current {@link org.whispersystems.libaxolotl.state.SessionState}
   * with a fresh reset instance.
   */
  public void archiveCurrentState() {
    promoteState(new SessionState());
  }

  public void promoteState(SessionState promotedState) {
    this.previousStates.insertElementAt(sessionState, 0);
    this.sessionState = promotedState;

    if (previousStates.size() > ARCHIVED_STATES_MAX_LENGTH) {
      previousStates.removeElementAt(previousStates.size() - 1);
    }
  }

  public void setState(SessionState sessionState) {
    this.sessionState = sessionState;
  }

  /**
   * @return a serialized version of the current SessionRecord.
   */
  public byte[] serialize() {
    RecordStructure record = new RecordStructure();
    record.setCurrentsession(sessionState.getStructure());

    for (int i=0;i<previousStates.size();i++) {
      SessionState previousState = (SessionState)previousStates.elementAt(i);
      record.addPrevioussessions(previousState.getStructure());
    }

    return record.toBytes();
  }

}
