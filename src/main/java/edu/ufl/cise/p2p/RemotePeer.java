package edu.ufl.cise.p2p;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
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
	private AtomicBoolean isChoked;
	private AtomicBoolean isUnchoked;
	private AtomicBoolean isPreferredNeighbor;
	private AtomicBoolean isOptimisticallyUnchoked;
	private Set<Integer> requestedPieces;

	public RemotePeer(String ipAddress, int port, String peerId, Boolean hasFile) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.peerId = peerId;
		this.hasFile = hasFile;
		this.isInterested = new AtomicBoolean(false);
		this.bytesDownloaded = new AtomicInteger(0);
		this.isChoked = new AtomicBoolean(false);
		this.isUnchoked = new AtomicBoolean(false);
		this.isPreferredNeighbor = new AtomicBoolean(false);
		this.isOptimisticallyUnchoked = new AtomicBoolean(false);
		this.requestedPieces = new HashSet<Integer>();
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

	public AtomicBoolean getIsChoked() {
		return isChoked;
	}

	public void setIsChoked(AtomicBoolean isChoked) {
		this.isChoked = isChoked;
	}

	public AtomicBoolean getIsUnchoked() {
		return isUnchoked;
	}

	public void setIsUnchoked(AtomicBoolean isUnchoked) {
		this.isUnchoked = isUnchoked;
	}

	public AtomicBoolean getIsPreferredNeighbor() {
		return isPreferredNeighbor;
	}

	public void setIsPreferredNeighbor(AtomicBoolean isPreferredNeighbor) {
		this.isPreferredNeighbor = isPreferredNeighbor;
	}

	public AtomicBoolean getIsOptimisticallyUnchoked() {
		return isOptimisticallyUnchoked;
	}

	public void setIsOptimisticallyUnchoked(
			AtomicBoolean isOptimisticallyUnchoked) {
		this.isOptimisticallyUnchoked = isOptimisticallyUnchoked;
	}

	public Set<Integer> getRequestedPieces() {
		return requestedPieces;
	}

	public void setRequestedPieces(Set<Integer> requestedPieces) {
		this.requestedPieces = requestedPieces;
	}

}
