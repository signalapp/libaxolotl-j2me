package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class RecordStructure implements Message {
    
  
  
    protected SessionStructure currentsession; // 1
    protected Vector previoussessions = new Vector(); // 2
    
    public SessionStructure getCurrentsession() {
        return currentsession;
    }
    
    public void setCurrentsession(SessionStructure currentsession) {
        this.currentsession = currentsession;
    }
    
    public void clearCurrentsession() {
        currentsession = null;
    }
    
    public boolean hasCurrentsession() {
        return currentsession != null;
    }
    public void addPrevioussessions(SessionStructure value) {
        this.previoussessions.addElement(value);
    }

    public int getPrevioussessionsCount() {
        return this.previoussessions.size();
    }

    public SessionStructure getPrevioussessions(int index) {
        return (SessionStructure)this.previoussessions.elementAt(index);
    }

    public Vector getPrevioussessionsVector() {
        return this.previoussessions;
    }

    public void setPrevioussessionsVector(Vector value) {
        this.previoussessions = value;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        out.writeMessage(1, currentsession);
        
        for(int i = 0; i < getPrevioussessionsCount(); i++) {
            out.writeMessage(2, getPrevioussessions(i));
        }
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 10: {
                    currentsession = new SessionStructure();
                    in.readMessage(currentsession);
                    break; }
                case 18: {
                    SessionStructure message = new SessionStructure();
                    in.readMessage(message);
                    addPrevioussessions(message);
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static RecordStructure fromBytes(byte[] in) throws EncodingException {
        RecordStructure message = new RecordStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



