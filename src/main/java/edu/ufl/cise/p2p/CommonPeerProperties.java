package edu.ufl.cise.p2p;

public class CommonPeerProperties {

	Integer numberOfPreferredNeighbors;
	Integer unchokingInterval;
	Integer optimisticUnchokingInterval;
	String fileName;
	Integer fileSize;
	Integer pieceSize;

	public CommonPeerProperties() {

	}

	public Integer getNumberOfPreferredNeighbors() {
		return numberOfPreferredNeighbors;
	}

	public void setNumberOfPreferredNeighbors(Integer numberOfPreferredNeighbors) {
		this.numberOfPreferredNeighbors = numberOfPreferredNeighbors;
	}

	public Integer getUnchokingInterval() {
		return unchokingInterval;
	}

	public void setUnchokingInterval(Integer unchokingInterval) {
		this.unchokingInterval = unchokingInterval;
	}

	public Integer getOptimisticUnchokingInterval() {
		return optimisticUnchokingInterval;
	}

	public void setOptimisticUnchokingInterval(
			Integer optimisticUnchokingInterval) {
		this.optimisticUnchokingInterval = optimisticUnchokingInterval;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getPieceSize() {
		return pieceSize;
	}

	public void setPieceSize(Integer pieceSize) {
		this.pieceSize = pieceSize;
	}

}
