package edu.ufl.cise.p2p.message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;

public class Handshake implements Externalizable {

	String handshakeHeader = "P2PFILESHARINGPROJ";
	byte[] header;
	byte[] zeroBits;
	byte[] peerId;

	public Handshake() {

	}

	public Handshake(int peerId) {
		header = ByteBuffer.allocate(18).put(handshakeHeader.getBytes())
				.array();
		zeroBits = new byte[10];
		this.peerId = ByteBuffer.allocate(4).putInt(peerId).array();
	}

	public int getPeerId() {
		return ByteBuffer.wrap(peerId).getInt();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("writeExternal triggered. Sending handshake to outstream");
		out.write(header, 0, header.length);
		out.write(zeroBits, 0, zeroBits.length);
		out.write(peerId, 0, peerId.length);
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		System.out.println("readExternal triggered. Reading handshake object from instream");
		in.read(header, 0, header.length);
		in.read(zeroBits, 0, zeroBits.length);
		in.read(peerId, 0, peerId.length);
	}

}
