package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class SenderKeyStateStructure implements Message {
    
    public static class SenderChainKey implements Message {
      
  
  
    protected int iteration; // 1
    protected boolean _hasIteration;
    protected byte[] seed; // 2
    protected boolean _hasSeed;
    
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
    public byte[] getSeed() {
        return seed;
    }
    
    public void setSeed(byte[] seed) {
        this.seed = seed;
        this._hasSeed = true;
    }
    
    public void clearSeed() {
        _hasSeed = false;
    }
    
    public boolean hasSeed() {
        return _hasSeed;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasIteration)
            out.writeUInt32(1, iteration);
        
        if(_hasSeed)
            out.writeBytes(2, seed);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    iteration = in.readUInt32();
                    _hasIteration = true;
                    break; }
                case 18: {
                    seed = in.readBytes();
                    _hasSeed = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderChainKey fromBytes(byte[] in) throws EncodingException {
        SenderChainKey message = new SenderChainKey();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
    public static class SenderMessageKey implements Message {
      
  
  
    protected int iteration; // 1
    protected boolean _hasIteration;
    protected byte[] seed; // 2
    protected boolean _hasSeed;
    
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
    public byte[] getSeed() {
        return seed;
    }
    
    public void setSeed(byte[] seed) {
        this.seed = seed;
        this._hasSeed = true;
    }
    
    public void clearSeed() {
        _hasSeed = false;
    }
    
    public boolean hasSeed() {
        return _hasSeed;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasIteration)
            out.writeUInt32(1, iteration);
        
        if(_hasSeed)
            out.writeBytes(2, seed);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    iteration = in.readUInt32();
                    _hasIteration = true;
                    break; }
                case 18: {
                    seed = in.readBytes();
                    _hasSeed = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderMessageKey fromBytes(byte[] in) throws EncodingException {
        SenderMessageKey message = new SenderMessageKey();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
    public static class SenderSigningKey implements Message {
      
  
  
    protected byte[] _public; // 1
    protected boolean _hasPublic;
    protected byte[] _private; // 2
    protected boolean _hasPrivate;
    
    public byte[] getPublic() {
        return _public;
    }
    
    public void setPublic(byte[] _public) {
        this._public = _public;
        this._hasPublic = true;
    }
    
    public void clearPublic() {
        _hasPublic = false;
    }
    
    public boolean hasPublic() {
        return _hasPublic;
    }
    public byte[] getPrivate() {
        return _private;
    }
    
    public void setPrivate(byte[] _private) {
        this._private = _private;
        this._hasPrivate = true;
    }
    
    public void clearPrivate() {
        _hasPrivate = false;
    }
    
    public boolean hasPrivate() {
        return _hasPrivate;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasPublic)
            out.writeBytes(1, _public);
        
        if(_hasPrivate)
            out.writeBytes(2, _private);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 10: {
                    _public = in.readBytes();
                    _hasPublic = true;
                    break; }
                case 18: {
                    _private = in.readBytes();
                    _hasPrivate = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderSigningKey fromBytes(byte[] in) throws EncodingException {
        SenderSigningKey message = new SenderSigningKey();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
  
  
    protected int senderkeyid; // 1
    protected boolean _hasSenderkeyid;
    protected SenderKeyStateStructure.SenderChainKey senderchainkey; // 2
    protected SenderKeyStateStructure.SenderSigningKey sendersigningkey; // 3
    protected Vector sendermessagekeys = new Vector(); // 4
    
    public int getSenderkeyid() {
        return senderkeyid;
    }
    
    public void setSenderkeyid(int senderkeyid) {
        this.senderkeyid = senderkeyid;
        this._hasSenderkeyid = true;
    }
    
    public void clearSenderkeyid() {
        _hasSenderkeyid = false;
    }
    
    public boolean hasSenderkeyid() {
        return _hasSenderkeyid;
    }
    public SenderKeyStateStructure.SenderChainKey getSenderchainkey() {
        return senderchainkey;
    }
    
    public void setSenderchainkey(SenderKeyStateStructure.SenderChainKey senderchainkey) {
        this.senderchainkey = senderchainkey;
    }
    
    public void clearSenderchainkey() {
        senderchainkey = null;
    }
    
    public boolean hasSenderchainkey() {
        return senderchainkey != null;
    }
    public SenderKeyStateStructure.SenderSigningKey getSendersigningkey() {
        return sendersigningkey;
    }
    
    public void setSendersigningkey(SenderKeyStateStructure.SenderSigningKey sendersigningkey) {
        this.sendersigningkey = sendersigningkey;
    }
    
    public void clearSendersigningkey() {
        sendersigningkey = null;
    }
    
    public boolean hasSendersigningkey() {
        return sendersigningkey != null;
    }
    public void addSendermessagekeys(SenderKeyStateStructure.SenderMessageKey value) {
        this.sendermessagekeys.addElement(value);
    }

    public int getSendermessagekeysCount() {
        return this.sendermessagekeys.size();
    }

    public SenderKeyStateStructure.SenderMessageKey getSendermessagekeys(int index) {
        return (SenderKeyStateStructure.SenderMessageKey)this.sendermessagekeys.elementAt(index);
    }

    public Vector getSendermessagekeysVector() {
        return this.sendermessagekeys;
    }

    public void setSendermessagekeysVector(Vector value) {
        this.sendermessagekeys = value;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasSenderkeyid)
            out.writeUInt32(1, senderkeyid);
        
        out.writeMessage(2, senderchainkey);
        
        out.writeMessage(3, sendersigningkey);
        
        for(int i = 0; i < getSendermessagekeysCount(); i++) {
            out.writeMessage(4, getSendermessagekeys(i));
        }
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    senderkeyid = in.readUInt32();
                    _hasSenderkeyid = true;
                    break; }
                case 18: {
                    senderchainkey = new SenderKeyStateStructure.SenderChainKey();
                    in.readMessage(senderchainkey);
                    break; }
                case 26: {
                    sendersigningkey = new SenderKeyStateStructure.SenderSigningKey();
                    in.readMessage(sendersigningkey);
                    break; }
                case 34: {
                    SenderKeyStateStructure.SenderMessageKey message = new SenderKeyStateStructure.SenderMessageKey();
                    in.readMessage(message);
                    addSendermessagekeys(message);
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderKeyStateStructure fromBytes(byte[] in) throws EncodingException {
        SenderKeyStateStructure message = new SenderKeyStateStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



