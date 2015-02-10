package org.whispersystems.libaxolotl.protocol.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class SenderKeyMessage implements Message {
    
  
  
    protected int id; // 1
    protected boolean _hasId;
    protected int iteration; // 2
    protected boolean _hasIteration;
    protected byte[] ciphertext; // 3
    protected boolean _hasCiphertext;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
        this._hasId = true;
    }
    
    public void clearId() {
        _hasId = false;
    }
    
    public boolean hasId() {
        return _hasId;
    }
    public int getIteration() {
        return iteration;
    }
    
    public void setIteration(int iteration) {
        this.iteration = iteration;
        this._hasIteration = true;
    }
    
    public void clearIteration() {
        _hasIteration = false;
    }
    
    public boolean hasIteration() {
        return _hasIteration;
    }
    public byte[] getCiphertext() {
        return ciphertext;
    }
    
    public void setCiphertext(byte[] ciphertext) {
        this.ciphertext = ciphertext;
        this._hasCiphertext = true;
    }
    
    public void clearCiphertext() {
        _hasCiphertext = false;
    }
    
    public boolean hasCiphertext() {
        return _hasCiphertext;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasId)
            out.writeUInt32(1, id);
        
        if(_hasIteration)
            out.writeUInt32(2, iteration);
        
        if(_hasCiphertext)
            out.writeBytes(3, ciphertext);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    id = in.readUInt32();
                    _hasId = true;
                    break; }
                case 16: {
                    iteration = in.readUInt32();
                    _hasIteration = true;
                    break; }
                case 26: {
                    ciphertext = in.readBytes();
                    _hasCiphertext = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderKeyMessage fromBytes(byte[] in) throws EncodingException {
        SenderKeyMessage message = new SenderKeyMessage();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



