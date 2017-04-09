package edu.ufl.cise.p2p;

import java.util.BitSet;

public class FileHandler {

	private int pieceSize;
	private int fileSize;
	private int bitSetLength;
	private String completeFilePath;
	private BitSet bitSet;

	public FileHandler(int pieceSize, int fileSize, String completeFilePath) {
		this.pieceSize = pieceSize;
		this.fileSize = fileSize;
		this.bitSetLength = (int) Math.ceil(1.0 * fileSize / pieceSize);
		this.bitSet = new BitSet(bitSetLength);
		this.completeFilePath = completeFilePath;
	}

	public int getPieceSize() {
		return pieceSize;
	}

	public void setPieceSize(int pieceSize) {
		this.pieceSize = pieceSize;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getBitSetLength() {
		return bitSetLength;
	}

	public void setBitSetLength(int bitSetLength) {
		this.bitSetLength = bitSetLength;
	}

	public String getCompleteFilePath() {
		return completeFilePath;
	}

	public void setCompleteFilePath(String completeFilePath) {
		this.completeFilePath = completeFilePath;
	}

	public BitSet getBitSet() {
		return bitSet;
	}

	public void setBitSet(BitSet bitSet) {
		this.bitSet = bitSet;
	}

	public void setAllPieces() {
		System.out.println("Size of bitset is:" + bitSet.size());
		for (int i = 0; i < bitSet.size(); i++)
			bitSet.set(i);
	}

}
