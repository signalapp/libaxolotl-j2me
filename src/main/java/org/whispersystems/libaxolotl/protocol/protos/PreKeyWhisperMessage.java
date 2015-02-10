package org.whispersystems.libaxolotl.protocol.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class PreKeyWhisperMessage implements Message {
    
  
  
    protected int registrationid; // 5
    protected boolean _hasRegistrationid;
    protected int prekeyid; // 1
    protected boolean _hasPrekeyid;
    protected int signedprekeyid; // 6
    protected boolean _hasSignedprekeyid;
    protected byte[] basekey; // 2
    protected boolean _hasBasekey;
    protected byte[] identitykey; // 3
    protected boolean _hasIdentitykey;
    protected byte[] message; // 4
    protected boolean _hasMessage;
    
    public int getRegistrationid() {
        return registrationid;
    }
    
    public void setRegistrationid(int registrationid) {
        this.registrationid = registrationid;
        this._hasRegistrationid = true;
    }
    
    public void clearRegistrationid() {
        _hasRegistrationid = false;
    }
    
    public boolean hasRegistrationid() {
        return _hasRegistrationid;
    }
    public int getPrekeyid() {
        return prekeyid;
    }
    
    public void setPrekeyid(int prekeyid) {
        this.prekeyid = prekeyid;
        this._hasPrekeyid = true;
    }
    
    public void clearPrekeyid() {
        _hasPrekeyid = false;
    }
    
    public boolean hasPrekeyid() {
        return _hasPrekeyid;
    }
    public int getSignedprekeyid() {
        return signedprekeyid;
    }
    
    public void setSignedprekeyid(int signedprekeyid) {
        this.signedprekeyid = signedprekeyid;
        this._hasSignedprekeyid = true;
    }
    
    public void clearSignedprekeyid() {
        _hasSignedprekeyid = false;
    }
    
    public boolean hasSignedprekeyid() {
        return _hasSignedprekeyid;
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
    public byte[] getMessage() {
        return message;
    }
    
    public void setMessage(byte[] message) {
        this.message = message;
        this._hasMessage = true;
    }
    
    public void clearMessage() {
        _hasMessage = false;
    }
    
    public boolean hasMessage() {
        return _hasMessage;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasRegistrationid)
            out.writeUInt32(5, registrationid);
        
        if(_hasPrekeyid)
            out.writeUInt32(1, prekeyid);
        
        if(_hasSignedprekeyid)
            out.writeUInt32(6, signedprekeyid);
        
        if(_hasBasekey)
            out.writeBytes(2, basekey);
        
        if(_hasIdentitykey)
            out.writeBytes(3, identitykey);
        
        if(_hasMessage)
            out.writeBytes(4, message);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 40: {
                    registrationid = in.readUInt32();
                    _hasRegistrationid = true;
                    break; }
                case 8: {
                    prekeyid = in.readUInt32();
                    _hasPrekeyid = true;
                    break; }
                case 48: {
                    signedprekeyid = in.readUInt32();
                    _hasSignedprekeyid = true;
                    break; }
                case 18: {
                    basekey = in.readBytes();
                    _hasBasekey = true;
                    break; }
                case 26: {
                    identitykey = in.readBytes();
                    _hasIdentitykey = true;
                    break; }
                case 34: {
                    message = in.readBytes();
                    _hasMessage = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static PreKeyWhisperMessage fromBytes(byte[] in) throws EncodingException {
        PreKeyWhisperMessage message = new PreKeyWhisperMessage();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



