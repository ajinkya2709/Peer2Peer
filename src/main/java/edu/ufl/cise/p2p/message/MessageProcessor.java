package edu.ufl.cise.p2p.message;

import java.util.BitSet;

import edu.ufl.cise.p2p.FileHandler;
import edu.ufl.cise.p2p.RemotePeer;

public class MessageProcessor {

	FileHandler fileHandler;

	public MessageProcessor(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	public Message createResponse(Handshake handshake) {
		BitSet bitSet = fileHandler.getBitSet();
		if (bitSet.isEmpty())
			return null;
		return new Bitfield(bitSet);
	}

	public Message createResponse(Message message, RemotePeer rPeer) {
		int type = message.getType();
		switch (type) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			Bitfield bitfield = (Bitfield) message;
			System.out.println("BitSet of size :"
					+ bitfield.getBitSet().length() + " received from peer"
					+ rPeer.getPeerId());
			rPeer.setBitSet(bitfield.getBitSet());
			break;
		case 6:
			break;
		case 7:
			break;
		}
		return null;
	}

}
