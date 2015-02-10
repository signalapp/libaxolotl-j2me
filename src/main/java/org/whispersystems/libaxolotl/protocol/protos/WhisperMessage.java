package org.whispersystems.libaxolotl.protocol.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class WhisperMessage implements Message {
    
  
  
    protected byte[] ratchetkey; // 1
    protected boolean _hasRatchetkey;
    protected int counter; // 2
    protected boolean _hasCounter;
    protected int previouscounter; // 3
    protected boolean _hasPreviouscounter;
    protected byte[] ciphertext; // 4
    protected boolean _hasCiphertext;
    
    public byte[] getRatchetkey() {
        return ratchetkey;
    }
    
    public void setRatchetkey(byte[] ratchetkey) {
        this.ratchetkey = ratchetkey;
        this._hasRatchetkey = true;
    }
    
    public void clearRatchetkey() {
        _hasRatchetkey = false;
    }
    
    public boolean hasRatchetkey() {
        return _hasRatchetkey;
    }
    public int getCounter() {
        return counter;
    }
    
    public void setCounter(int counter) {
        this.counter = counter;
        this._hasCounter = true;
    }
    
    public void clearCounter() {
        _hasCounter = false;
    }
    
    public boolean hasCounter() {
        return _hasCounter;
    }
    public int getPreviouscounter() {
        return previouscounter;
    }
    
    public void setPreviouscounter(int previouscounter) {
        this.previouscounter = previouscounter;
        this._hasPreviouscounter = true;
    }
    
    public void clearPreviouscounter() {
        _hasPreviouscounter = false;
    }
    
    public boolean hasPreviouscounter() {
        return _hasPreviouscounter;
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
        if(_hasRatchetkey)
            out.writeBytes(1, ratchetkey);
        
        if(_hasCounter)
            out.writeUInt32(2, counter);
        
        if(_hasPreviouscounter)
            out.writeUInt32(3, previouscounter);
        
        if(_hasCiphertext)
            out.writeBytes(4, ciphertext);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 10: {
                    ratchetkey = in.readBytes();
                    _hasRatchetkey = true;
                    break; }
                case 16: {
                    counter = in.readUInt32();
                    _hasCounter = true;
                    break; }
                case 24: {
                    previouscounter = in.readUInt32();
                    _hasPreviouscounter = true;
                    break; }
                case 34: {
                    ciphertext = in.readBytes();
                    _hasCiphertext = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static WhisperMessage fromBytes(byte[] in) throws EncodingException {
        WhisperMessage message = new WhisperMessage();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



