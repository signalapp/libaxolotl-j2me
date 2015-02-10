package org.whispersystems.libaxolotl.state.protos;

import java.util.Vector;
import java.io.IOException;

import com.ponderingpanda.protobuf.*;


public class SessionStructure implements Message {
    
    public static class Chain implements Message {
      
    public static class ChainKey implements Message {
      
  
  
    protected int index; // 1
    protected boolean _hasIndex;
    protected byte[] key; // 2
    protected boolean _hasKey;
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
        this._hasIndex = true;
    }
    
    public void clearIndex() {
        _hasIndex = false;
    }
    
    public boolean hasIndex() {
        return _hasIndex;
    }
    public byte[] getKey() {
        return key;
    }
    
    public void setKey(byte[] key) {
        this.key = key;
        this._hasKey = true;
    }
    
    public void clearKey() {
        _hasKey = false;
    }
    
    public boolean hasKey() {
        return _hasKey;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasIndex)
            out.writeUInt32(1, index);
        
        if(_hasKey)
            out.writeBytes(2, key);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    index = in.readUInt32();
                    _hasIndex = true;
                    break; }
                case 18: {
                    key = in.readBytes();
                    _hasKey = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static ChainKey fromBytes(byte[] in) throws EncodingException {
        ChainKey message = new ChainKey();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
    public static class MessageKey implements Message {
      
  
  
    protected int index; // 1
    protected boolean _hasIndex;
    protected byte[] cipherkey; // 2
    protected boolean _hasCipherkey;
    protected byte[] mackey; // 3
    protected boolean _hasMackey;
    protected byte[] iv; // 4
    protected boolean _hasIv;
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
        this._hasIndex = true;
    }
    
    public void clearIndex() {
        _hasIndex = false;
    }
    
    public boolean hasIndex() {
        return _hasIndex;
    }
    public byte[] getCipherkey() {
        return cipherkey;
    }
    
    public void setCipherkey(byte[] cipherkey) {
        this.cipherkey = cipherkey;
        this._hasCipherkey = true;
    }
    
    public void clearCipherkey() {
        _hasCipherkey = false;
    }
    
    public boolean hasCipherkey() {
        return _hasCipherkey;
    }
    public byte[] getMackey() {
        return mackey;
    }
    
    public void setMackey(byte[] mackey) {
        this.mackey = mackey;
        this._hasMackey = true;
    }
    
    public void clearMackey() {
        _hasMackey = false;
    }
    
    public boolean hasMackey() {
        return _hasMackey;
    }
    public byte[] getIv() {
        return iv;
    }
    
    public void setIv(byte[] iv) {
        this.iv = iv;
        this._hasIv = true;
    }
    
    public void clearIv() {
        _hasIv = false;
    }
    
    public boolean hasIv() {
        return _hasIv;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasIndex)
            out.writeUInt32(1, index);
        
        if(_hasCipherkey)
            out.writeBytes(2, cipherkey);
        
        if(_hasMackey)
            out.writeBytes(3, mackey);
        
        if(_hasIv)
            out.writeBytes(4, iv);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    index = in.readUInt32();
                    _hasIndex = true;
                    break; }
                case 18: {
                    cipherkey = in.readBytes();
                    _hasCipherkey = true;
                    break; }
                case 26: {
                    mackey = in.readBytes();
                    _hasMackey = true;
                    break; }
                case 34: {
                    iv = in.readBytes();
                    _hasIv = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static MessageKey fromBytes(byte[] in) throws EncodingException {
        MessageKey message = new MessageKey();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
  
  
    protected byte[] senderratchetkey; // 1
    protected boolean _hasSenderratchetkey;
    protected byte[] senderratchetkeyprivate; // 2
    protected boolean _hasSenderratchetkeyprivate;
    protected SessionStructure.Chain.ChainKey chainkey; // 3
    protected Vector messagekeys = new Vector(); // 4
    
    public byte[] getSenderratchetkey() {
        return senderratchetkey;
    }
    
    public void setSenderratchetkey(byte[] senderratchetkey) {
        this.senderratchetkey = senderratchetkey;
        this._hasSenderratchetkey = true;
    }
    
    public void clearSenderratchetkey() {
        _hasSenderratchetkey = false;
    }
    
    public boolean hasSenderratchetkey() {
        return _hasSenderratchetkey;
    }
    public byte[] getSenderratchetkeyprivate() {
        return senderratchetkeyprivate;
    }
    
    public void setSenderratchetkeyprivate(byte[] senderratchetkeyprivate) {
        this.senderratchetkeyprivate = senderratchetkeyprivate;
        this._hasSenderratchetkeyprivate = true;
    }
    
    public void clearSenderratchetkeyprivate() {
        _hasSenderratchetkeyprivate = false;
    }
    
    public boolean hasSenderratchetkeyprivate() {
        return _hasSenderratchetkeyprivate;
    }
    public SessionStructure.Chain.ChainKey getChainkey() {
        return chainkey;
    }
    
    public void setChainkey(SessionStructure.Chain.ChainKey chainkey) {
        this.chainkey = chainkey;
    }
    
    public void clearChainkey() {
        chainkey = null;
    }
    
    public boolean hasChainkey() {
        return chainkey != null;
    }
    public void addMessagekeys(SessionStructure.Chain.MessageKey value) {
        this.messagekeys.addElement(value);
    }

    public int getMessagekeysCount() {
        return this.messagekeys.size();
    }

    public SessionStructure.Chain.MessageKey getMessagekeys(int index) {
        return (SessionStructure.Chain.MessageKey)this.messagekeys.elementAt(index);
    }

    public Vector getMessagekeysVector() {
        return this.messagekeys;
    }

    public void setMessagekeysVector(Vector value) {
        this.messagekeys = value;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasSenderratchetkey)
            out.writeBytes(1, senderratchetkey);
        
        if(_hasSenderratchetkeyprivate)
            out.writeBytes(2, senderratchetkeyprivate);
        
        out.writeMessage(3, chainkey);
        
        for(int i = 0; i < getMessagekeysCount(); i++) {
            out.writeMessage(4, getMessagekeys(i));
        }
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 10: {
                    senderratchetkey = in.readBytes();
                    _hasSenderratchetkey = true;
                    break; }
                case 18: {
                    senderratchetkeyprivate = in.readBytes();
                    _hasSenderratchetkeyprivate = true;
                    break; }
                case 26: {
                    chainkey = new SessionStructure.Chain.ChainKey();
                    in.readMessage(chainkey);
                    break; }
                case 34: {
                    SessionStructure.Chain.MessageKey message = new SessionStructure.Chain.MessageKey();
                    in.readMessage(message);
                    addMessagekeys(message);
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static Chain fromBytes(byte[] in) throws EncodingException {
        Chain message = new Chain();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
    public static class PendingKeyExchange implements Message {
      
  
  
    protected int sequence; // 1
    protected boolean _hasSequence;
    protected byte[] localbasekey; // 2
    protected boolean _hasLocalbasekey;
    protected byte[] localbasekeyprivate; // 3
    protected boolean _hasLocalbasekeyprivate;
    protected byte[] localratchetkey; // 4
    protected boolean _hasLocalratchetkey;
    protected byte[] localratchetkeyprivate; // 5
    protected boolean _hasLocalratchetkeyprivate;
    protected byte[] localidentitykey; // 7
    protected boolean _hasLocalidentitykey;
    protected byte[] localidentitykeyprivate; // 8
    protected boolean _hasLocalidentitykeyprivate;
    
    public int getSequence() {
        return sequence;
    }
    
    public void setSequence(int sequence) {
        this.sequence = sequence;
        this._hasSequence = true;
    }
    
    public void clearSequence() {
        _hasSequence = false;
    }
    
    public boolean hasSequence() {
        return _hasSequence;
    }
    public byte[] getLocalbasekey() {
        return localbasekey;
    }
    
    public void setLocalbasekey(byte[] localbasekey) {
        this.localbasekey = localbasekey;
        this._hasLocalbasekey = true;
    }
    
    public void clearLocalbasekey() {
        _hasLocalbasekey = false;
    }
    
    public boolean hasLocalbasekey() {
        return _hasLocalbasekey;
    }
    public byte[] getLocalbasekeyprivate() {
        return localbasekeyprivate;
    }
    
    public void setLocalbasekeyprivate(byte[] localbasekeyprivate) {
        this.localbasekeyprivate = localbasekeyprivate;
        this._hasLocalbasekeyprivate = true;
    }
    
    public void clearLocalbasekeyprivate() {
        _hasLocalbasekeyprivate = false;
    }
    
    public boolean hasLocalbasekeyprivate() {
        return _hasLocalbasekeyprivate;
    }
    public byte[] getLocalratchetkey() {
        return localratchetkey;
    }
    
    public void setLocalratchetkey(byte[] localratchetkey) {
        this.localratchetkey = localratchetkey;
        this._hasLocalratchetkey = true;
    }
    
    public void clearLocalratchetkey() {
        _hasLocalratchetkey = false;
    }
    
    public boolean hasLocalratchetkey() {
        return _hasLocalratchetkey;
    }
    public byte[] getLocalratchetkeyprivate() {
        return localratchetkeyprivate;
    }
    
    public void setLocalratchetkeyprivate(byte[] localratchetkeyprivate) {
        this.localratchetkeyprivate = localratchetkeyprivate;
        this._hasLocalratchetkeyprivate = true;
    }
    
    public void clearLocalratchetkeyprivate() {
        _hasLocalratchetkeyprivate = false;
    }
    
    public boolean hasLocalratchetkeyprivate() {
        return _hasLocalratchetkeyprivate;
    }
    public byte[] getLocalidentitykey() {
        return localidentitykey;
    }
    
    public void setLocalidentitykey(byte[] localidentitykey) {
        this.localidentitykey = localidentitykey;
        this._hasLocalidentitykey = true;
    }
    
    public void clearLocalidentitykey() {
        _hasLocalidentitykey = false;
    }
    
    public boolean hasLocalidentitykey() {
        return _hasLocalidentitykey;
    }
    public byte[] getLocalidentitykeyprivate() {
        return localidentitykeyprivate;
    }
    
    public void setLocalidentitykeyprivate(byte[] localidentitykeyprivate) {
        this.localidentitykeyprivate = localidentitykeyprivate;
        this._hasLocalidentitykeyprivate = true;
    }
    
    public void clearLocalidentitykeyprivate() {
        _hasLocalidentitykeyprivate = false;
    }
    
    public boolean hasLocalidentitykeyprivate() {
        return _hasLocalidentitykeyprivate;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasSequence)
            out.writeUInt32(1, sequence);
        
        if(_hasLocalbasekey)
            out.writeBytes(2, localbasekey);
        
        if(_hasLocalbasekeyprivate)
            out.writeBytes(3, localbasekeyprivate);
        
        if(_hasLocalratchetkey)
            out.writeBytes(4, localratchetkey);
        
        if(_hasLocalratchetkeyprivate)
            out.writeBytes(5, localratchetkeyprivate);
        
        if(_hasLocalidentitykey)
            out.writeBytes(7, localidentitykey);
        
        if(_hasLocalidentitykeyprivate)
            out.writeBytes(8, localidentitykeyprivate);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    sequence = in.readUInt32();
                    _hasSequence = true;
                    break; }
                case 18: {
                    localbasekey = in.readBytes();
                    _hasLocalbasekey = true;
                    break; }
                case 26: {
                    localbasekeyprivate = in.readBytes();
                    _hasLocalbasekeyprivate = true;
                    break; }
                case 34: {
                    localratchetkey = in.readBytes();
                    _hasLocalratchetkey = true;
                    break; }
                case 42: {
                    localratchetkeyprivate = in.readBytes();
                    _hasLocalratchetkeyprivate = true;
                    break; }
                case 58: {
                    localidentitykey = in.readBytes();
                    _hasLocalidentitykey = true;
                    break; }
                case 66: {
                    localidentitykeyprivate = in.readBytes();
                    _hasLocalidentitykeyprivate = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static PendingKeyExchange fromBytes(byte[] in) throws EncodingException {
        PendingKeyExchange message = new PendingKeyExchange();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
    public static class PendingPreKey implements Message {
      
  
  
    protected int prekeyid; // 1
    protected boolean _hasPrekeyid;
    protected int signedprekeyid; // 3
    protected boolean _hasSignedprekeyid;
    protected byte[] basekey; // 2
    protected boolean _hasBasekey;
    
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
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasPrekeyid)
            out.writeUInt32(1, prekeyid);
        
        if(_hasSignedprekeyid)
            out.writeInt32(3, signedprekeyid);
        
        if(_hasBasekey)
            out.writeBytes(2, basekey);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    prekeyid = in.readUInt32();
                    _hasPrekeyid = true;
                    break; }
                case 24: {
                    signedprekeyid = in.readInt32();
                    _hasSignedprekeyid = true;
                    break; }
                case 18: {
                    basekey = in.readBytes();
                    _hasBasekey = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static PendingPreKey fromBytes(byte[] in) throws EncodingException {
        PendingPreKey message = new PendingPreKey();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

    }
  
  
    protected int sessionversion; // 1
    protected boolean _hasSessionversion;
    protected byte[] localidentitypublic; // 2
    protected boolean _hasLocalidentitypublic;
    protected byte[] remoteidentitypublic; // 3
    protected boolean _hasRemoteidentitypublic;
    protected byte[] rootkey; // 4
    protected boolean _hasRootkey;
    protected int previouscounter; // 5
    protected boolean _hasPreviouscounter;
    protected SessionStructure.Chain senderchain; // 6
    protected Vector receiverchains = new Vector(); // 7
    protected SessionStructure.PendingKeyExchange pendingkeyexchange; // 8
    protected SessionStructure.PendingPreKey pendingprekey; // 9
    protected int remoteregistrationid; // 10
    protected boolean _hasRemoteregistrationid;
    protected int localregistrationid; // 11
    protected boolean _hasLocalregistrationid;
    protected boolean needsrefresh; // 12
    protected boolean _hasNeedsrefresh;
    protected byte[] alicebasekey; // 13
    protected boolean _hasAlicebasekey;
    
    public int getSessionversion() {
        return sessionversion;
    }
    
    public void setSessionversion(int sessionversion) {
        this.sessionversion = sessionversion;
        this._hasSessionversion = true;
    }
    
    public void clearSessionversion() {
        _hasSessionversion = false;
    }
    
    public boolean hasSessionversion() {
        return _hasSessionversion;
    }
    public byte[] getLocalidentitypublic() {
        return localidentitypublic;
    }
    
    public void setLocalidentitypublic(byte[] localidentitypublic) {
        this.localidentitypublic = localidentitypublic;
        this._hasLocalidentitypublic = true;
    }
    
    public void clearLocalidentitypublic() {
        _hasLocalidentitypublic = false;
    }
    
    public boolean hasLocalidentitypublic() {
        return _hasLocalidentitypublic;
    }
    public byte[] getRemoteidentitypublic() {
        return remoteidentitypublic;
    }
    
    public void setRemoteidentitypublic(byte[] remoteidentitypublic) {
        this.remoteidentitypublic = remoteidentitypublic;
        this._hasRemoteidentitypublic = true;
    }
    
    public void clearRemoteidentitypublic() {
        _hasRemoteidentitypublic = false;
    }
    
    public boolean hasRemoteidentitypublic() {
        return _hasRemoteidentitypublic;
    }
    public byte[] getRootkey() {
        return rootkey;
    }
    
    public void setRootkey(byte[] rootkey) {
        this.rootkey = rootkey;
        this._hasRootkey = true;
    }
    
    public void clearRootkey() {
        _hasRootkey = false;
    }
    
    public boolean hasRootkey() {
        return _hasRootkey;
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
    public SessionStructure.Chain getSenderchain() {
        return senderchain;
    }
    
    public void setSenderchain(SessionStructure.Chain senderchain) {
        this.senderchain = senderchain;
    }
    
    public void clearSenderchain() {
        senderchain = null;
    }
    
    public boolean hasSenderchain() {
        return senderchain != null;
    }
    public void addReceiverchains(SessionStructure.Chain value) {
        this.receiverchains.addElement(value);
    }

    public int getReceiverchainsCount() {
        return this.receiverchains.size();
    }

    public SessionStructure.Chain getReceiverchains(int index) {
        return (SessionStructure.Chain)this.receiverchains.elementAt(index);
    }

    public Vector getReceiverchainsVector() {
        return this.receiverchains;
    }

    public void setReceiverchainsVector(Vector value) {
        this.receiverchains = value;
    }
    public SessionStructure.PendingKeyExchange getPendingkeyexchange() {
        return pendingkeyexchange;
    }
    
    public void setPendingkeyexchange(SessionStructure.PendingKeyExchange pendingkeyexchange) {
        this.pendingkeyexchange = pendingkeyexchange;
    }
    
    public void clearPendingkeyexchange() {
        pendingkeyexchange = null;
    }
    
    public boolean hasPendingkeyexchange() {
        return pendingkeyexchange != null;
    }
    public SessionStructure.PendingPreKey getPendingprekey() {
        return pendingprekey;
    }
    
    public void setPendingprekey(SessionStructure.PendingPreKey pendingprekey) {
        this.pendingprekey = pendingprekey;
    }
    
    public void clearPendingprekey() {
        pendingprekey = null;
    }
    
    public boolean hasPendingprekey() {
        return pendingprekey != null;
    }
    public int getRemoteregistrationid() {
        return remoteregistrationid;
    }
    
    public void setRemoteregistrationid(int remoteregistrationid) {
        this.remoteregistrationid = remoteregistrationid;
        this._hasRemoteregistrationid = true;
    }
    
    public void clearRemoteregistrationid() {
        _hasRemoteregistrationid = false;
    }
    
    public boolean hasRemoteregistrationid() {
        return _hasRemoteregistrationid;
    }
    public int getLocalregistrationid() {
        return localregistrationid;
    }
    
    public void setLocalregistrationid(int localregistrationid) {
        this.localregistrationid = localregistrationid;
        this._hasLocalregistrationid = true;
    }
    
    public void clearLocalregistrationid() {
        _hasLocalregistrationid = false;
    }
    
    public boolean hasLocalregistrationid() {
        return _hasLocalregistrationid;
    }
    public boolean getNeedsrefresh() {
        return needsrefresh;
    }
    
    public void setNeedsrefresh(boolean needsrefresh) {
        this.needsrefresh = needsrefresh;
        this._hasNeedsrefresh = true;
    }
    
    public void clearNeedsrefresh() {
        _hasNeedsrefresh = false;
    }
    
    public boolean hasNeedsrefresh() {
        return _hasNeedsrefresh;
    }
    public byte[] getAlicebasekey() {
        return alicebasekey;
    }
    
    public void setAlicebasekey(byte[] alicebasekey) {
        this.alicebasekey = alicebasekey;
        this._hasAlicebasekey = true;
    }
    
    public void clearAlicebasekey() {
        _hasAlicebasekey = false;
    }
    
    public boolean hasAlicebasekey() {
        return _hasAlicebasekey;
    }
    
    public final void serialize(CodedOutputStream out) throws IOException {
        if(_hasSessionversion)
            out.writeUInt32(1, sessionversion);
        
        if(_hasLocalidentitypublic)
            out.writeBytes(2, localidentitypublic);
        
        if(_hasRemoteidentitypublic)
            out.writeBytes(3, remoteidentitypublic);
        
        if(_hasRootkey)
            out.writeBytes(4, rootkey);
        
        if(_hasPreviouscounter)
            out.writeUInt32(5, previouscounter);
        
        out.writeMessage(6, senderchain);
        
        for(int i = 0; i < getReceiverchainsCount(); i++) {
            out.writeMessage(7, getReceiverchains(i));
        }
        
        out.writeMessage(8, pendingkeyexchange);
        
        out.writeMessage(9, pendingprekey);
        
        if(_hasRemoteregistrationid)
            out.writeUInt32(10, remoteregistrationid);
        
        if(_hasLocalregistrationid)
            out.writeUInt32(11, localregistrationid);
        
        if(_hasNeedsrefresh)
            out.writeBool(12, needsrefresh);
        
        if(_hasAlicebasekey)
            out.writeBytes(13, alicebasekey);
        
    }

    public final void deserialize(CodedInputStream in) throws IOException {
        while(true) {
            int tag = in.readTag();
            switch(tag) {
                case 0:
                    return;
                case 8: {
                    sessionversion = in.readUInt32();
                    _hasSessionversion = true;
                    break; }
                case 18: {
                    localidentitypublic = in.readBytes();
                    _hasLocalidentitypublic = true;
                    break; }
                case 26: {
                    remoteidentitypublic = in.readBytes();
                    _hasRemoteidentitypublic = true;
                    break; }
                case 34: {
                    rootkey = in.readBytes();
                    _hasRootkey = true;
                    break; }
                case 40: {
                    previouscounter = in.readUInt32();
                    _hasPreviouscounter = true;
                    break; }
                case 50: {
                    senderchain = new SessionStructure.Chain();
                    in.readMessage(senderchain);
                    break; }
                case 58: {
                    SessionStructure.Chain message = new SessionStructure.Chain();
                    in.readMessage(message);
                    addReceiverchains(message);
                    break; }
                case 66: {
                    pendingkeyexchange = new SessionStructure.PendingKeyExchange();
                    in.readMessage(pendingkeyexchange);
                    break; }
                case 74: {
                    pendingprekey = new SessionStructure.PendingPreKey();
                    in.readMessage(pendingprekey);
                    break; }
                case 80: {
                    remoteregistrationid = in.readUInt32();
                    _hasRemoteregistrationid = true;
                    break; }
                case 88: {
                    localregistrationid = in.readUInt32();
                    _hasLocalregistrationid = true;
                    break; }
                case 96: {
                    needsrefresh = in.readBool();
                    _hasNeedsrefresh = true;
                    break; }
                case 106: {
                    alicebasekey = in.readBytes();
                    _hasAlicebasekey = true;
                    break; }
                default:
                    in.skipTag(tag);
            }
        }
    }
    
    public static SessionStructure fromBytes(byte[] in) throws EncodingException {
        SessionStructure message = new SessionStructure();
        ProtoUtil.messageFromBytes(in, message);
        return message;
    }
    
    public byte[] toBytes() throws EncodingException {
        return ProtoUtil.messageToBytes(this);
    }
    

}



