package edu.ufl.cise.p2p.message;

import java.util.BitSet;

import edu.ufl.cise.p2p.FileHandler;

public class MessageProcessor {

	FileHandler fileHandler;
	
	public MessageProcessor(FileHandler fileHandler){
		this.fileHandler = fileHandler;
	}
	
	public Message createResponse(Handshake handshake){
		BitSet bitSet = fileHandler.getBitSet();
		if(bitSet.isEmpty()) return null;
		return new Bitfield(bitSet);
	}

}
