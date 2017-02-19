package edu.ufl.cise.p2p.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.ufl.cise.p2p.RemotePeer;

public class PeerConfigReader {

	private static final String fileName = "/PeerInfo.cfg";
	final static String delimiter = "\\s+";
	static List<RemotePeer> peerInfoList;

	static {
		peerInfoList = new ArrayList<RemotePeer>();
	}

	public static List<RemotePeer> getPeerInfoList(String peerId) {
		try {
			InputStream in = PeerConfigReader.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String line = null;
			String[] tokens = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				tokens = line.split(delimiter);
				if(tokens[0].compareTo(peerId) > 0) break;
				Boolean hasFile = (tokens[3].trim().compareTo("1") == 0);
				peerInfoList.add(new RemotePeer(tokens[1], Integer
						.parseInt(tokens[2]), tokens[0], hasFile));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return peerInfoList;
	}
	
}
