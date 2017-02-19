package edu.ufl.cise.p2p;

public class CommonPeerProperties {

	Integer numberOfPreferredNeighbors;
	Integer unchokingInterval;
	Integer optimisticUnchokingInterval;
	String fileName;
	Integer fileSize;

	public CommonPeerProperties(Integer numberOfPreferredNeighbors,
			Integer unchokingInterval, Integer optimisticUnchokingInterval,
			String fileName, Integer fileSize) {
		this.numberOfPreferredNeighbors = numberOfPreferredNeighbors;
		this.unchokingInterval = unchokingInterval;
		this.optimisticUnchokingInterval = optimisticUnchokingInterval;
		this.fileName = fileName;
		this.fileSize = fileSize;

	}

}
