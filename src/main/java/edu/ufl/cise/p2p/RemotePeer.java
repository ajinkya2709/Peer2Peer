package edu.ufl.cise.p2p;

/**
 * Created by aditya on 2/18/17.
 */
public class RemotePeer {
    String ipAddress;
    int port;
    String peerId;
    Boolean hasFile;

    public RemotePeer(String ipAddress, int port, String peerId, Boolean hasFile){
        this.ipAddress = ipAddress;
        this.port = port;
        this.peerId = peerId;
        this.hasFile = hasFile;
    }

    public String getAddress(){
        return ipAddress;
    }

    public int getPort(){
        return port;
    }

    public String getPeerId(){
        return peerId;
    }

    public Boolean hasFile(){
        return hasFile;
    }
}
