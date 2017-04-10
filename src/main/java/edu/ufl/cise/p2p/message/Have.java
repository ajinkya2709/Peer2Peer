package edu.ufl.cise.p2p.message;

import java.nio.ByteBuffer;

public class Have extends Message {

	public Have() {
		super();
	}

	public Have(int index) {
		super(4, ByteBuffer.allocate(4).putInt(index).array());
	}
	
	public int getIndex(){
		return ByteBuffer.wrap(payload).getInt();
	}
}
