package edu.ufl.cise.p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.ufl.cise.p2p.message.Handshake;

public class PeerConnection implements Runnable {

	Socket socket;
	String localPeerId;
	String remotePeerId;
	boolean isClient;
	ObjectInputStream inStream;
	ObjectOutputStream outStream;

	public PeerConnection(Socket socket, String localPeerId, String remotePeer,
			boolean isClient) throws IOException {
		this.socket = socket;
		this.localPeerId = localPeerId;
		this.remotePeerId = remotePeer;
		this.isClient = isClient;
		this.outStream = new ObjectOutputStream(socket.getOutputStream());
		this.outStream.flush();
		this.inStream = new ObjectInputStream(socket.getInputStream());
	}

	public void run() {
		System.out.println("Connection created between :" + localPeerId
				+ "\tand:" + remotePeerId);
		try {

			outStream.writeObject(new Handshake(Integer.parseInt(localPeerId)));

			Handshake handShakeReceived = (Handshake) inStream.readObject();
			System.out.println("Peer :[" + localPeerId
					+ "] received Handshake from Peer :["
					+ handShakeReceived.getPeerId() + "]");
			//If it's a client, we can verify expected server

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
