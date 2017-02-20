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

	public Peer() {

	}

	public Peer(String id, String host, Integer port, Boolean hasFile) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.hasFile = hasFile;
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

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(Boolean hasFile) {
		this.hasFile = hasFile;
	}

	public CommonPeerProperties getCommonProps() {
		return commonProps;
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
				createNewConnection(serverSocket.accept());
				
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


	private void createNewConnection(Socket socket){
		new Thread(new PeerConnection(Integer.parseInt(id), -1)).start();
	}
	
	public void connectToRemotePeers(List<RemotePeer> remotePeers) {
		for (RemotePeer remotePeer : remotePeers) {
			try {
				Socket socket = new Socket(remotePeer.getIpAddress(),
						remotePeer.getPort());
				
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
