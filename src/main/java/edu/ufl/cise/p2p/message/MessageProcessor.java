package edu.ufl.cise.p2p.message;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

import edu.ufl.cise.p2p.FileHandler;
import edu.ufl.cise.p2p.RemotePeer;
import edu.ufl.cise.p2p.log.Log;

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
			Log.logChoking(rPeer.getPeerId());
			System.out
					.println("Received CHOKE from peer :" + rPeer.getPeerId());
			isChoked = true;
			break;
		case 1:
			Log.logUnchoking(rPeer.getPeerId());
			System.out.println("Received UNCHOKE from peer :"
					+ rPeer.getPeerId());
			isChoked = false;
			return getRandomPieceIndex(rPeer);

		case 2:
			Log.logReceivedInterested(rPeer.getPeerId());
			System.out.println("Received INTERESTED from peer :"
					+ rPeer.getPeerId());
			System.out.println("Marking [" + rPeer.getPeerId()
					+ "] as interested");
			rPeer.getIsInterested().set(true);
			break;
		case 3:
			Log.logReceivedNotInterested(rPeer.getPeerId());
			System.out.println("Received NOT INTERESTED from peer :"
					+ rPeer.getPeerId());
			System.out.println("Marking [" + rPeer.getPeerId()
					+ "] as NOT interested");
			rPeer.getIsInterested().set(false);
			break;
		case 4:
			System.out.println("Received HAVE from peer :" + rPeer.getPeerId());
			Have have = (Have) message;
			int index = have.getIndex();
			Log.logReceivedHave(rPeer.getPeerId(), have.getIndex());
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
					+ " received from peer :" + rPeer.getPeerId());
			rPeer.setBitSet(bitSet);
			bitSet.andNot(fileHandler.getBitSet());
			if (!bitSet.isEmpty()) {
				return new Interested();
			} else {
				return new NotInterested();
			}
		case 6:
			Request request = (Request) message;
			int indexOfPieceRequested = request.getPieceIndex();
			System.out.println("Index :" + indexOfPieceRequested
					+ " requested by peer [" + rPeer.getPeerId() + "]");
			byte[] data = fileHandler.getDataFromPiece(indexOfPieceRequested);
			return new Piece(indexOfPieceRequested, data);
		case 7:
			Piece piece = (Piece) message;
			System.out.println("Piece received from peer [" + rPeer.getPeerId()
					+ "]");
			System.out.println("Piece index [" + piece.getIndex()
					+ new String(piece.getContent()));
			break;
		}
		return null;
	}

	private Message getRandomPieceIndex(RemotePeer rPeer) {
		BitSet copy = (BitSet) rPeer.getBitSet().clone();
		copy.andNot(fileHandler.getBitSet());
		List<Integer> neededPieceIndices = new ArrayList<Integer>();
		for (int i = copy.nextSetBit(0); i >= 0; i = copy.nextSetBit(i + 1)) {
			neededPieceIndices.add(i);
		}
		if (neededPieceIndices.isEmpty())
			return null;
		Random r = new Random();
		int randomListIndex = r.nextInt(neededPieceIndices.size());
		System.out.println("Requesting piece index :"
				+ neededPieceIndices.get(randomListIndex));
		return new Request(neededPieceIndices.get(randomListIndex));
	}

}
