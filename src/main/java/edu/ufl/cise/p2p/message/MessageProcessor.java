package edu.ufl.cise.p2p.message;

import java.util.BitSet;

import edu.ufl.cise.p2p.FileHandler;
import edu.ufl.cise.p2p.RemotePeer;

public class MessageProcessor {

	boolean isChoked;
	FileHandler fileHandler;

	public MessageProcessor(FileHandler fileHandler) {
		isChoked = true;
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
			System.out
					.println("Received CHOKE from peer :" + rPeer.getPeerId());
			isChoked = true;
			break;
		case 1:
			System.out.println("Received UNCHOKE from peer :"
					+ rPeer.getPeerId());
			isChoked = false;
			// Request a Piece
			break;
		case 2:
			System.out.println("Received INTERESTED from peer :"
					+ rPeer.getPeerId());
			System.out.println("Marking [" + rPeer.getPeerId()
					+ "] as interested");
			rPeer.getIsInterested().set(true);
			break;
		case 3:
			System.out.println("Received NOT INTERESTED from peer :"
					+ rPeer.getPeerId());
			System.out.println("Marking [" + rPeer.getPeerId()
					+ "] as NOT interested");
			rPeer.getIsInterested().set(false);
			break;
		case 4:
			Have have = (Have) message;
			int index = have.getIndex();
			if (fileHandler.getBitSet().get(index)) {
				System.out.println("Peer already has part of index: " + index
						+ ". Sending NOT interested");
				return new NotInterested();
			} else {
				System.out.println("Peer does not have part of index: " + index
						+ ". Sending interested");
				return new Interested();
			}
		case 5:
			Bitfield bitfield = (Bitfield) message;
			BitSet bitSet = bitfield.getBitSet();
			System.out.println("BitSet of size :" + bitSet.length()
					+ " received from peer" + rPeer.getPeerId());
			rPeer.setBitSet(bitSet);
			bitSet.andNot(fileHandler.getBitSet());
			if (!bitSet.isEmpty()) {
				return new Interested();
			} else {
				return new NotInterested();
			}
		case 6:
			break;
		case 7:
			break;
		}
		return null;
	}

}
