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

		System.out.println("Read common properties");
		List<RemotePeer> remotePeers = PeerConfigReader.getPeerInfoList(peerId);

		for (RemotePeer peer : remotePeers) {
			System.out.print(peer.getPeerId() + "\t");
			System.out.print(peer.getIpAddress() + "\t");
			System.out.print(peer.getPort() + "\t");
			System.out.print(peer.getHasFile());
			System.out.println();
		}

		System.out.println("Read current and remote peer info");
		RemotePeer current = remotePeers.get(remotePeers.size() - 1);
		remotePeers.remove(remotePeers.size() - 1);
		Peer peer = new Peer(current.getPeerId(), current.getIpAddress(),
				current.getPort(), current.getHasFile());
		peer.setCommonProps(common);
		Thread serverThread = new Thread(peer);
		serverThread.start();
		
		peer.connectToRemotePeers(remotePeers);

	}

}
