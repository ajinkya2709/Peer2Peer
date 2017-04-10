package edu.ufl.cise.p2p;

import java.util.List;

import edu.ufl.cise.p2p.reader.CommonPropReader;
import edu.ufl.cise.p2p.reader.PeerConfigReader;

public class peerProcess {

	public static void main(String[] args) throws Exception {
		if (args.length != 1)
			throw new Exception("Invalid arguments");
		String peerId = args[0];
		CommonPeerProperties common = CommonPropReader.read();

		System.out.println(common.getFileName());
		System.out.println(common.getFileSize());
		System.out.println(common.getNumberOfPreferredNeighbors());
		System.out.println(common.getUnchokingInterval());
		System.out.println(common.getOptimisticUnchokingInterval());
		System.out.println(common.getPieceSize());

		System.out.println("Read common properties");
		List<RemotePeer> remotePeers = PeerConfigReader.getPeerInfoList(peerId);

		Peer peer = null;
		for (int i = 0;i < remotePeers.size();i++) {
			RemotePeer rPeer = remotePeers.get(i);
			if(rPeer.getPeerId().equalsIgnoreCase(peerId)){
				peer = new Peer(rPeer.getPeerId(),rPeer.getIpAddress(),rPeer.getPort(),rPeer.getHasFile(),remotePeers, common);
				remotePeers.remove(i);
				break;
			}
		}

		System.out.println("Read current and remote peer info");
		//peer.setCommonProps(common);
		peer.init();
		Thread serverThread = new Thread(peer);
		serverThread.start();
		peer.connectToRemotePeers(remotePeers);

	}

}
