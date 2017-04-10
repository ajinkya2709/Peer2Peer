package edu.ufl.cise.p2p.message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;

public class Message implements Externalizable {

	protected byte[] length;
	protected byte type;
	protected byte[] payload;

	protected Message() {
		length = new byte[4];
	}

	protected Message(int type, byte[] payLoad) {
		this.type = (byte) type;
		this.payload = payLoad;
		int messageLength = (payLoad == null ? 0 : payLoad.length) + 1;
		this.length = ByteBuffer.allocate(4).putInt(messageLength).array();

	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.write(length, 0, length.length);
		out.write(type);
		out.write(payload, 0, payload.length);
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		in.read(length, 0, 4);
		type = (byte) in.read();
		int messageLength = ByteBuffer.wrap(length).getInt();
		payload = new byte[messageLength - 1];
		in.read(payload, 0, messageLength - 1);
	}

	public int getType() {
		return (int) type;
	}
}
