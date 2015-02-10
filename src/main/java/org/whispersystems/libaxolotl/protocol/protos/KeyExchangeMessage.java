package org.whispersystems.libaxolotl.protocol.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class KeyExchangeMessage implements Message {
    
  
  
    protected int id; // 1
    protected boolean _hasId;
    protected byte[] basekey; // 2
    protected boolean _hasBasekey;
    protected byte[] ratchetkey; // 3
    protected boolean _hasRatchetkey;
    protected byte[] identitykey; // 4
    protected boolean _hasIdentitykey;
    protected byte[] basekeysignature; // 5
    protected boolean _hasBasekeysignature;
    
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
    public byte[] getBasekey() {
        return basekey;
    }
    
    public void setBasekey(byte[] basekey) {
        this.basekey = basekey;
        this._hasBasekey = true;
    }
    
    public void clearBasekey() {
        _hasBasekey = false;
    }
    
    public boolean hasBasekey() {
        return _hasBasekey;
    }
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
    public byte[] getIdentitykey() {
        return identitykey;
    }
    
    public void setIdentitykey(byte[] identitykey) {
        this.identitykey = identitykey;
        this._hasIdentitykey = true;
    }
    
    public void clearIdentitykey() {
        _hasIdentitykey = false;
    }
    
    public boolean hasIdentitykey() {
        return _hasIdentitykey;
    }
    public byte[] getBasekeysignature() {
        return basekeysignature;
    }
    
    public void setBasekeysignature(byte[] basekeysignature) {
        this.basekeysignature = basekeysignature;
        this._hasBasekeysignature = true;
    }
    
    public void clearBasekeysignature() {
        _hasBasekeysignature = false;
    }
    
    public boolean hasBasekeysignature() {
        return _hasBasekeysignature;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasId)
            out.writeUInt32(1, id);
        
        if(_hasBasekey)
            out.writeBytes(2, basekey);
        
        if(_hasRatchetkey)
            out.writeBytes(3, ratchetkey);
        
        if(_hasIdentitykey)
            out.writeBytes(4, identitykey);
        
        if(_hasBasekeysignature)
            out.writeBytes(5, basekeysignature);
        
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
                case 18: {
                    basekey = in.readBytes();
                    _hasBasekey = true;
                    break; }
                case 26: {
                    ratchetkey = in.readBytes();
                    _hasRatchetkey = true;
                    break; }
                case 34: {
                    identitykey = in.readBytes();
                    _hasIdentitykey = true;
                    break; }
                case 42: {
                    basekeysignature = in.readBytes();
                    _hasBasekeysignature = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static KeyExchangeMessage fromBytes(byte[] in) throws EncodingException {
        KeyExchangeMessage message = new KeyExchangeMessage();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



