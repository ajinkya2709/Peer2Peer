package edu.ufl.cise.p2p;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Aditya on 2/18/17.
 */
public class RemotePeer {
	private String ipAddress;
	private int port;
	private String peerId;
	private Boolean hasFile;
	private PeerConnection connection;
	private AtomicBoolean isInterested;

	public RemotePeer(String ipAddress, int port, String peerId, Boolean hasFile) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.peerId = peerId;
		this.hasFile = hasFile;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPeerId() {
		return peerId;
	}

	public void setPeerId(String peerId) {
		this.peerId = peerId;
	}

	public Boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(Boolean hasFile) {
		this.hasFile = hasFile;
	}

}
