package edu.ufl.cise.p2p;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	private AtomicInteger bytesDownloaded;
	private BitSet bitSet;

	public RemotePeer(String ipAddress, int port, String peerId, Boolean hasFile) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.peerId = peerId;
		this.hasFile = hasFile;
		this.isInterested = new AtomicBoolean(false);
		this.bytesDownloaded = new AtomicInteger(0);
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

	public PeerConnection getConnection() {
		return connection;
	}

	public void setConnection(PeerConnection connection) {
		this.connection = connection;
	}

	public AtomicBoolean getIsInterested() {
		return isInterested;
	}

	public void setIsInterested(AtomicBoolean isInterested) {
		this.isInterested = isInterested;
	}

	public AtomicInteger getBytesDownloaded() {
		return bytesDownloaded;
	}

	public void setBytesDownloaded(AtomicInteger bytesDownloaded) {
		this.bytesDownloaded = bytesDownloaded;
	}

	public BitSet getBitSet() {
		return bitSet;
	}

	public void setBitSet(BitSet bitSet) {
		this.bitSet = bitSet;
	}

}
