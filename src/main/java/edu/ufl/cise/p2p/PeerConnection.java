package edu.ufl.cise.p2p;

public class PeerConnection implements Runnable {

	Integer localPeerId;
	Integer remotePeerId;

	public PeerConnection(Integer localPeerId, Integer remotePeer) {
		this.localPeerId = localPeerId;
		this.remotePeerId = remotePeer;
	}

	public void run() {
		System.out.println("Connection created between :" + localPeerId
				+ "\tand:" + remotePeerId);
	}

}
