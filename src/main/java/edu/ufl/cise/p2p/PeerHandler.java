package edu.ufl.cise.p2p;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class PeerHandler implements Runnable{
    ArrayList<RemotePeer> remotePeers;
    CommonPeerProperties commonProp;
    AtomicBoolean hasFile;

    public PeerHandler(ArrayList<RemotePeer> peers, CommonPeerProperties prop, Boolean hasFile){
        remotePeers = peers;
        commonProp = prop;
        this.hasFile.set(hasFile);
    }

    public void run(){

    }

}
