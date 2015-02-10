package org.whispersystems.libaxolotl.protocol.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class SenderKeyDistributionMessage implements Message {
    
  
  
    protected int id; // 1
    protected boolean _hasId;
    protected int iteration; // 2
    protected boolean _hasIteration;
    protected byte[] chainkey; // 3
    protected boolean _hasChainkey;
    protected byte[] signingkey; // 4
    protected boolean _hasSigningkey;
    
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
    public byte[] getChainkey() {
        return chainkey;
    }
    
    public void setChainkey(byte[] chainkey) {
        this.chainkey = chainkey;
        this._hasChainkey = true;
    }
    
    public void clearChainkey() {
        _hasChainkey = false;
    }
    
    public boolean hasChainkey() {
        return _hasChainkey;
    }
    public byte[] getSigningkey() {
        return signingkey;
    }
    
    public void setSigningkey(byte[] signingkey) {
        this.signingkey = signingkey;
        this._hasSigningkey = true;
    }
    
    public void clearSigningkey() {
        _hasSigningkey = false;
    }
    
    public boolean hasSigningkey() {
        return _hasSigningkey;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasId)
            out.writeUInt32(1, id);
        
        if(_hasIteration)
            out.writeUInt32(2, iteration);
        
        if(_hasChainkey)
            out.writeBytes(3, chainkey);
        
        if(_hasSigningkey)
            out.writeBytes(4, signingkey);
        
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
                    chainkey = in.readBytes();
                    _hasChainkey = true;
                    break; }
                case 34: {
                    signingkey = in.readBytes();
                    _hasSigningkey = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SenderKeyDistributionMessage fromBytes(byte[] in) throws EncodingException {
        SenderKeyDistributionMessage message = new SenderKeyDistributionMessage();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



