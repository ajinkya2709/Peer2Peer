package edu.ufl.cise.p2p.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

import edu.ufl.cise.p2p.FileHandler;
import edu.ufl.cise.p2p.Peer;
import edu.ufl.cise.p2p.RemotePeer;
import edu.ufl.cise.p2p.log.Log;
import edu.ufl.cise.p2p.log.Logfile;

public class MessageProcessor {

	boolean isChoked;
	FileHandler fileHandler;
	List<RemotePeer> remotePeers;
	Peer locaPeer;
	Logfile log;

	public MessageProcessor(FileHandler fileHandler,
			List<RemotePeer> remotePeers, Peer localPeer) throws IOException {
		isChoked = true;
		this.fileHandler = fileHandler;
		this.remotePeers = remotePeers;
		this.locaPeer = localPeer;
		this.log=new Logfile(localPeer.getId());
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
			log.logChoking(rPeer.getPeerId());
			System.out
					.println("Received CHOKE from peer :" + rPeer.getPeerId());
			isChoked = true;
			fileHandler.getNeededPieces().addAll(rPeer.getRequestedPieces());
			rPeer.getRequestedPieces().clear();
			break;
		case 1:
			log.logUnchoking(rPeer.getPeerId());
			System.out.println("Received UNCHOKE from peer :"
					+ rPeer.getPeerId());
			isChoked = false;
			return getRequestMessage(rPeer);

		case 2:
			log.logReceivedInterested(rPeer.getPeerId());
			System.out.println("Received INTERESTED from peer :"
					+ rPeer.getPeerId());
			System.out.println("Marking [" + rPeer.getPeerId()
					+ "] as interested");
			rPeer.getIsInterested().set(true);
			break;
		case 3:
			log.logReceivedNotInterested(rPeer.getPeerId());
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
			log.logReceivedHave(rPeer.getPeerId(), have.getIndex());
			rPeer.getBitSet().set(index);
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
			System.out.println("BitSet of size :" + bitSet.size()
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
			int pieceIndex = piece.getIndex();
			rPeer.getRequestedPieces().remove(pieceIndex);
			System.out.println("Piece of index [" + piece.getIndex()
					+ "] received from peer [" + rPeer.getPeerId() + "]");
			//System.out.println(new String(piece.getContent()));
			fileHandler.writePieceData(pieceIndex, piece.getContent());
			fileHandler.getBitSet().set(pieceIndex);
			rPeer.getBytesDownloaded().getAndAdd(piece.getContent().length);
			for (RemotePeer remote : remotePeers) {
				if (remote.getConnection() == null)
					continue;
				if (!remote.getBitSet().get(pieceIndex)) {
					System.out.println("Sending HAVE Message to ["
							+ remote.getPeerId() + "]");
					remote.getConnection().sendMessage(new Have(pieceIndex));
				}
			}
			if (fileHandler.getNeededPieces().isEmpty()) {
				fileHandler.mergeFilesInto(fileHandler.getBitSetLength());
				break;
			}
			if (!isChoked)
				return getRequestMessage(rPeer);
		}
		return null;
	}

	private Message getRequestMessage(RemotePeer rPeer) {
		BitSet copy = (BitSet) rPeer.getBitSet().clone();
		copy.andNot(fileHandler.getBitSet());
		List<Integer> reqPieceIndices = new ArrayList<Integer>();
		for (int i = copy.nextSetBit(0); i >= 0
				&& fileHandler.getNeededPieces().contains(i); i = copy
				.nextSetBit(i + 1)) {
			reqPieceIndices.add(i);
		}
		if (reqPieceIndices.isEmpty()) {
			locaPeer.getHasFile().set(true);
			return null;
		}

		Random r = new Random();
		int randomListIndex = r.nextInt(reqPieceIndices.size());
		System.out.println("Requesting piece index :"
				+ reqPieceIndices.get(randomListIndex));
		int pieceIndex = reqPieceIndices.get(randomListIndex);
		fileHandler.getNeededPieces().remove(pieceIndex);
		rPeer.getRequestedPieces().add(pieceIndex);
		return new Request(pieceIndex);
	}

}
