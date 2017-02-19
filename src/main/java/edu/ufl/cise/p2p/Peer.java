package edu.ufl.cise.p2p;

import java.io.IOException;
import java.net.ServerSocket;

public class Peer {

	CommonPeerProperties commonProps;
	String id;
	String host;
	Integer port;
	Boolean hasFile;

	public Peer(String id, String host, Integer port, Boolean hasFile,
			CommonPeerProperties commonProps) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.hasFile = hasFile;
		this.commonProps = commonProps;
	}

	private class Server implements Runnable {

		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				new Client();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static class Client implements Runnable {

		public void run() {

		}

	}

}
