package edu.ufl.cise.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Peer implements Runnable {

	private CommonPeerProperties commonProps;
	private String id;
	private String host;
	private int port;
	private Boolean hasFile;

	public Peer() {

	}

	// Method to initialize Peer handler and File handler
	public void init() {

	}

	public Peer(String id, String host, int port, Boolean hasFile) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.hasFile = hasFile;
	}

	public CommonPeerProperties getCommonProps() {
		return commonProps;
	}

	public void setCommonProps(CommonPeerProperties commonProps) {
		this.commonProps = commonProps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(Boolean hasFile) {
		this.hasFile = hasFile;
	}

	public void run() {
		boolean shouldRun = true;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (shouldRun) {
				createNewConnection(serverSocket.accept(), id, "", false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createNewConnection(Socket socket, String peerId,
			String remotePeerId, boolean isClient) {
		try {
			new Thread(new PeerConnection(socket, id, remotePeerId, isClient))
					.start();
		} catch (IOException e) {
			System.out.println("IO Exception while creating a new Connection");
			e.printStackTrace();
		}
	}

	public void connectToRemotePeers(List<RemotePeer> remotePeers) {
		for (RemotePeer rPeer : remotePeers) {
			try {
				if (rPeer.getPeerId().compareTo(id) <= 0)
					break;
				System.out.println("Connecting to peer:" + rPeer.getIpAddress()
						+ " at:" + rPeer.getPort());
				Socket socket = new Socket(rPeer.getIpAddress(),
						rPeer.getPort());
				createNewConnection(socket, id, rPeer.getPeerId(), true);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
