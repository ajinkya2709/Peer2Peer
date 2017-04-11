package edu.ufl.cise.p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import edu.ufl.cise.p2p.message.Handshake;
import edu.ufl.cise.p2p.message.Message;
import edu.ufl.cise.p2p.message.MessageProcessor;
import edu.ufl.cise.p2p.log.Log;
import edu.ufl.cise.p2p.log.Logfile;


public class PeerConnection implements Runnable {

	Socket socket;
	String localPeerId;
	String remotePeerId;
	boolean isClient;
	ObjectInputStream inStream;
	ObjectOutputStream outStream;
	FileHandler fileHandler;
	Map<String, RemotePeer> remotePeerMap;

	public PeerConnection(Socket socket, String localPeerId, String remotePeer,
			boolean isClient, FileHandler fileHandler,
			Map<String, RemotePeer> remotePeerMap) throws IOException {
		this.socket = socket;
		this.localPeerId = localPeerId;
		this.remotePeerId = remotePeer;
		this.isClient = isClient;
		this.outStream = new ObjectOutputStream(socket.getOutputStream());
		this.outStream.flush();
		this.inStream = new ObjectInputStream(socket.getInputStream());
		this.fileHandler = fileHandler;
		this.remotePeerMap = remotePeerMap;
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
			// If it's a client, we have to verify expected server
			if (remotePeerId.isEmpty()) {
				remotePeerId = String.valueOf(handShakeReceived.getPeerId());
				remotePeerMap.get(remotePeerId).setConnection(this);
			}
			Log.logTCPConnection(localPeerId, remotePeerId);

			MessageProcessor processor = new MessageProcessor(fileHandler);
			Message response = processor.createResponse(handShakeReceived);
			sendMessage(response);

			// Code to test Bitfield.
			/*Bitfield bitfield = (Bitfield) inStream.readObject();
			BitSet received = bitfield.getBitSet();
			System.out.println("Peer :" + handShakeReceived
					+ " has bitfield of size :" + received.length());*/

			while (true) {
				Message message = (Message) inStream.readObject();
				response = processor.createResponse(message,
						remotePeerMap.get(remotePeerId));
				sendMessage(response);
			}

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

	public void sendMessage(Message response) throws IOException {
		if (response == null)
			return;
		outStream.writeObject(response);
	}

}
