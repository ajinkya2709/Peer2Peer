package edu.ufl.cise.p2p.message;

import java.nio.ByteBuffer;

public class Request extends Message {

	public Request() {
		super();
	}

	public Request(int pieceIndex) {
		super(6, ByteBuffer.allocate(4).putInt(pieceIndex).array());
	}

	public int getPieceIndex() {
		return ByteBuffer.wrap(payload).getInt();
	}
}
