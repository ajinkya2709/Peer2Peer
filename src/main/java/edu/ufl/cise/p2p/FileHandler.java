package edu.ufl.cise.p2p;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class FileHandler {

	private int pieceSize;
	private int fileSize;
	private int bitSetLength;
	private String completeFilePath;
	private BitSet bitSet;
	private int totalParts;
	private String partsDirectory = "parts";
	private String forPeerId;
	private Set<Integer> neededPieces;

	public FileHandler(int pieceSize, int fileSize, String completeFilePath,
			String forPeerId) {
		this.pieceSize = pieceSize;
		this.fileSize = fileSize;
		this.bitSetLength = (int) Math.ceil(1.0 * fileSize / pieceSize);
		this.bitSet = new BitSet(bitSetLength);
		this.completeFilePath = completeFilePath;
		this.forPeerId = forPeerId;
		this.neededPieces = new HashSet<Integer>();
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

	public void splitFile(String fileName) {
		File file = new File(fileName);
		File filePartFolder = new File(file.getParent() + "/" + partsDirectory);
		filePartFolder.mkdirs();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String partName = null;
		int totalFileSize = (int) file.length(), read = 0, chunkSize = pieceSize, partNumber = 0;
		File filePart = null;
		try {
			fis = new FileInputStream(file);
			while (totalFileSize > 0) {
				if (totalFileSize < chunkSize)
					chunkSize = totalFileSize;
				byte[] bytes = new byte[chunkSize];
				read = fis.read(bytes, 0, bytes.length);
				// System.out.println(new String(bytes));
				totalFileSize -= read;
				partNumber++;
				partName = filePartFolder + "/" + "file.part"
						+ String.valueOf(partNumber - 1);
				filePart = new File(partName);
				if (!filePart.exists()) {
					filePart.createNewFile();
				}
				fos = new FileOutputStream(filePart);
				fos.write(bytes);
				fos.flush();
				fos.close();
				fos = null;
				bytes = null;
			}
			fis.close();
			totalParts = partNumber;
			System.out.println("Total Parts :" + totalParts);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mergeFilesInto(int parts) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String partName = null;
		byte[] bytes = null;
		try {
			File original = new File(completeFilePath);
			original.createNewFile();
			File filePart = null;
			fos = new FileOutputStream(original, true);
			// change needed to use totalParts variable
			System.out.println("total parts :" + parts);
			for (int i = 0; i < parts; i++) {
				partName = original.getParent() + "/" + partsDirectory + "/"
						+ "file.part" + String.valueOf(i);
				filePart = new File(partName);
				System.out.println(filePart.getAbsolutePath() + "  "
						+ filePart.exists());
				fis = new FileInputStream(filePart);
				bytes = new byte[(int) filePart.length()];
				fis.read(bytes, 0, (int) filePart.length());
				System.out.println(new String(bytes));
				fos.write(bytes);
				fos.flush();
				bytes = null;
				fis.close();
				fis = null;
			}
			fos.close();
			fos = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public byte[] getDataFromPiece(int index) {
		FileInputStream fis = null;
		String partName = null;
		File filePart = null;

		File original = new File(completeFilePath);
		partName = original.getParent() + "/" + partsDirectory + "/"
				+ "file.part" + String.valueOf(index);
		filePart = new File(partName);
		byte[] bytes = null;
		if (!filePart.exists())
			return null;
		try {
			fis = new FileInputStream(filePart);
			bytes = new byte[(int) filePart.length()];
			fis.read(bytes, 0, (int) filePart.length());
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bytes;
	}

	public void writePieceData(int index, byte[] data) {
		File file = new File(completeFilePath);
		File filePartFolder = new File(file.getParent() + "/" + partsDirectory);
		filePartFolder.mkdirs();
		FileOutputStream fos = null;

		try {
			File part = new File(file.getParent() + "/" + partsDirectory + "/"
					+ "file.part" + String.valueOf(index));
			part.createNewFile();
			fos = new FileOutputStream(part);
			fos.write(data, 0, data.length);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getTotalParts() {
		return totalParts;
	}

	public void setTotalParts(int totalParts) {
		this.totalParts = totalParts;
	}

	public String getPartsDirectory() {
		return partsDirectory;
	}

	public void setPartsDirectory(String partsDirectory) {
		this.partsDirectory = partsDirectory;
	}

	public String getForPeerId() {
		return forPeerId;
	}

	public void setForPeerId(String forPeerId) {
		this.forPeerId = forPeerId;
	}

	public Set<Integer> getNeededPieces() {
		return neededPieces;
	}

	public void setNeededPieces(Set<Integer> neededPieces) {
		this.neededPieces = neededPieces;
	}

	public void calculateRequiredPieces() {
		for (int i = 0; i < bitSetLength; i++)
			neededPieces.add(i);
	}

	/*
	 * public static void main(String[] args) { String completeFilePath =
	 * "C:\\Users\\ajinkya\\Desktop\\split\\sample.txt"; FileHandler fh = new
	 * FileHandler(5, 1000, completeFilePath); fh.splitFile(completeFilePath);
	 * 
	 * fh.mergeFilesInto(completeFilePath, 88); }
	 */

}
