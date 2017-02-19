package edu.ufl.cise.p2p.reader;

import edu.ufl.cise.p2p.RemotePeer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeerConfigReader {
    static final String fileName = "classpath:PeerInfo.cfg";
    final static String delimiter = "\\s+";
    List<RemotePeer> peerInfoList;

    public PeerConfigReader(){
        peerInfoList = new ArrayList<RemotePeer>();
    }

    public List getPeerInfoList(){
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            String[] tokens = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                tokens = line.split(delimiter);
                Boolean hasFile = (tokens[3].trim().compareTo("1") == 0);
                peerInfoList.add(new RemotePeer(tokens[1],Integer.parseInt(tokens[2]),tokens[0],hasFile));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return peerInfoList;
    }
}
