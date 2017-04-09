package edu.ufl.cise.p2p.message;

import java.util.BitSet;

public class Bitfield extends Message {
	
	public Bitfield(){
		super();
	}

	public Bitfield(byte[] payload) {
		super(5, payload);
	}

	public Bitfield(BitSet bitSet) {
		super(5, bitSet.toByteArray());
	}

	public BitSet getBitSet() {
		if (payload != null) {
			return BitSet.valueOf(payload);
		}
		return null;
	}
}
