package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class SenderKeyRecordStructure implements Message {
    
  
  
    protected Vector senderkeystates = new Vector(); // 1
    
    public void addSenderkeystates(SenderKeyStateStructure value) {
        this.senderkeystates.addElement(value);
    }

    public int getSenderkeystatesCount() {
        return this.senderkeystates.size();
    }

    public SenderKeyStateStructure getSenderkeystates(int index) {
        return (SenderKeyStateStructure)this.senderkeystates.elementAt(index);
    }

    public Vector getSenderkeystatesVector() {
        return this.senderkeystates;
    }

    public void setSenderkeystatesVector(Vector value) {
        this.senderkeystates = value;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        for(int i = 0; i < getSenderkeystatesCount(); i++) {
            out.writeMessage(1, getSenderkeystates(i));
        }
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 10: {
                    SenderKeyStateStructure message = new SenderKeyStateStructure();
                    in.readMessage(message);
                    addSenderkeystates(message);
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderKeyRecordStructure fromBytes(byte[] in) throws EncodingException {
        SenderKeyRecordStructure message = new SenderKeyRecordStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



