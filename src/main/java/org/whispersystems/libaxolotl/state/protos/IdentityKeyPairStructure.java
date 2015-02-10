package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class IdentityKeyPairStructure implements Message {
    
  
  
    protected byte[] publickey; // 1
    protected boolean _hasPublickey;
    protected byte[] privatekey; // 2
    protected boolean _hasPrivatekey;
    
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
        if(_hasPublickey)
            out.writeBytes(1, publickey);
        
        if(_hasPrivatekey)
            out.writeBytes(2, privatekey);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 10: {
                    publickey = in.readBytes();
                    _hasPublickey = true;
                    break; }
                case 18: {
                    privatekey = in.readBytes();
                    _hasPrivatekey = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static IdentityKeyPairStructure fromBytes(byte[] in) throws EncodingException {
        IdentityKeyPairStructure message = new IdentityKeyPairStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



