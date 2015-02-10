package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class PreKeyRecordStructure implements Message {
    
  
  
    protected int id; // 1
    protected boolean _hasId;
    protected byte[] publickey; // 2
    protected boolean _hasPublickey;
    protected byte[] privatekey; // 3
    protected boolean _hasPrivatekey;
    
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
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasId)
            out.writeUInt32(1, id);
        
        if(_hasPublickey)
            out.writeBytes(2, publickey);
        
        if(_hasPrivatekey)
            out.writeBytes(3, privatekey);
        
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
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static PreKeyRecordStructure fromBytes(byte[] in) throws EncodingException {
        PreKeyRecordStructure message = new PreKeyRecordStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



