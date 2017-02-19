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
	private Integer port;
	private Boolean hasFile;

	public Peer(String id, String host, Integer port, Boolean hasFile) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.hasFile = hasFile;
	}

	public void setCommonProps(CommonPeerProperties commonProps) {
		this.commonProps = commonProps;
	}

	public void run() {
		boolean shouldRun = true;
		ServerSocket serverSocket = null;
		while (shouldRun) {
			try {
				serverSocket = new ServerSocket(port);
				Client client = new Client(serverSocket.accept());
				new Thread(client).start();
				System.out.println("Connection established between :"
						+ this.host + "  and:"
						+ client.socket.getInetAddress().getHostName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class Client implements Runnable {
		Socket socket;

		public Client(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			// some client logic
			System.out.println("Client running");
			System.out.println(socket.getRemoteSocketAddress());
		}

	}
	
	public void connectToRemotePeers(List<RemotePeer> remotePeers){
		for(RemotePeer remotePeer : remotePeers){
			try {
				Socket socket = new Socket(remotePeer.getIpAddress(),remotePeer.getPort());
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
