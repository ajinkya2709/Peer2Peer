package edu.ufl.cise.p2p.message;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Piece extends Message {

	public Piece() {
		super();
	}

	public Piece(int index, byte[] content) {
		super(7, getTotalPayload(index, content));

	}

	private static byte[] getTotalPayload(int index, byte[] content) {
		byte[] totalPayload = new byte[4 + (content == null ? 0
				: content.length)];
		byte[] pieceIndex = ByteBuffer.allocate(4).putInt(index).array();
		System.arraycopy(pieceIndex, 0, totalPayload, 0, pieceIndex.length);
		if(content != null)
		System.arraycopy(content, 0, totalPayload, pieceIndex.length,
				content.length);
		return totalPayload;

	}

	public int getIndex() {
		return ByteBuffer.wrap(Arrays.copyOfRange(payload, 0, 4)).getInt();
	}

	public byte[] getContent() {
		return Arrays.copyOfRange(payload, 4, payload.length);
	}
}
