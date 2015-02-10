package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class SignedPreKeyRecordStructure implements Message {
    
  
  
    protected int id; // 1
    protected boolean _hasId;
    protected byte[] publickey; // 2
    protected boolean _hasPublickey;
    protected byte[] privatekey; // 3
    protected boolean _hasPrivatekey;
    protected byte[] signature; // 4
    protected boolean _hasSignature;
    protected long timestamp; // 5
    protected boolean _hasTimestamp;
    
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
    public byte[] getPublickey() {
        return publickey;
    }
    
    public void setPublickey(byte[] publickey) {
        this.publickey = publickey;
        this._hasPublickey = true;
    }
    
    public void clearPublickey() {
        _hasPublickey = false;
    }
    
    public boolean hasPublickey() {
        return _hasPublickey;
    }
    public byte[] getPrivatekey() {
        return privatekey;
    }
    
    public void setPrivatekey(byte[] privatekey) {
        this.privatekey = privatekey;
        this._hasPrivatekey = true;
    }
    
    public void clearPrivatekey() {
        _hasPrivatekey = false;
    }
    
    public boolean hasPrivatekey() {
        return _hasPrivatekey;
    }
    public byte[] getSignature() {
        return signature;
    }
    
    public void setSignature(byte[] signature) {
        this.signature = signature;
        this._hasSignature = true;
    }
    
    public void clearSignature() {
        _hasSignature = false;
    }
    
    public boolean hasSignature() {
        return _hasSignature;
    }
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        this._hasTimestamp = true;
    }
    
    public void clearTimestamp() {
        _hasTimestamp = false;
    }
    
    public boolean hasTimestamp() {
        return _hasTimestamp;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasId)
            out.writeUInt32(1, id);
        
        if(_hasPublickey)
            out.writeBytes(2, publickey);
        
        if(_hasPrivatekey)
            out.writeBytes(3, privatekey);
        
        if(_hasSignature)
            out.writeBytes(4, signature);
        
        if(_hasTimestamp)
            out.writeFixed64(5, timestamp);
        
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
                    publickey = in.readBytes();
                    _hasPublickey = true;
                    break; }
                case 26: {
                    privatekey = in.readBytes();
                    _hasPrivatekey = true;
                    break; }
                case 34: {
                    signature = in.readBytes();
                    _hasSignature = true;
                    break; }
                case 41: {
                    timestamp = in.readFixed64();
                    _hasTimestamp = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SignedPreKeyRecordStructure fromBytes(byte[] in) throws EncodingException {
        SignedPreKeyRecordStructure message = new SignedPreKeyRecordStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



