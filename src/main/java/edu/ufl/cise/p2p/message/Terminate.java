package edu.ufl.cise.p2p.message;

public class Terminate extends Message {

	public Terminate() {
		super();
	}

	public Terminate(int code) {
		super(code, null);
	}

}
