package edu.ufl.cise.p2p.log;

import java.io.IOException;
import java.util.ArrayList;

import edu.ufl.cise.p2p.RemotePeer;

/**
 * The Log that will be used by the program to log the following events:
 * TCP Connection
 * Change of Preferred Neighbors
 * Change of Optimistically Unchoked Neighbors
 * Unchoking
 * Choking
 * Receiving 'have' Message
 * Receiving 'interested' Message
 * Receiving 'not interested' Message
 * Downloading a Piece
 * Completion of Download
 **/
public class Logfile extends Log
{
	private String peerID;
	
	public Logfile(String localPeerId) throws IOException 
	{
		super("log_peer_" + localPeerId + ".log");
		this.peerID = localPeerId;
	}

	private String peerConnection()
	{
		return(this.getTime() + ": Peer " + this.peerID);
	}
	
	/**
	 * Logs the connection to peer
	 * @param localPeerId				the peerID of the connected peer
	 * @param remotePeerId	flag on whether or not this Peer established the
	 * 							connection
	 */
	public void logTCPConnection(String localPeerId, String remotePeerId)
	{
		
		String event;
		//if(remotePeerId)
	//	{/* This Peer made the connection */
			event = peerConnection() + " makes a connection to Peer " + remotePeerId + ". \n";
		//} /* end if */
		//else
		//{ //* This Peer was connected to by peer */
		  //  event = peerConnection() + " is connected from Peer " + localPeerId + ". \n";
		//} /* end else */
		
		this.writeToFile(event);
	}
	
	public void logChangePreferredNeighbors(ArrayList<RemotePeer> preferredNeighbours)
	{
		StringBuffer event = new StringBuffer();
		event.append(peerConnection() + " has the preferred neighbors ");
		
		for(int i = 0; i < (preferredNeighbours.size() - 1); i++)
		{
			event.append(preferredNeighbours.get(i).getPeerId() + ", ");
		}

		event.append(preferredNeighbours.get(preferredNeighbours.size() - 1).getPeerId() + ". \n");
		
		this.writeToFile(event.toString());
	}
	
	public void logChangeOptimisticallyUnchokedNeighbor(String optimisticallyUnchokedNeighbour)
	{
		String event = peerConnection() + " has the optimistically unchoked neighbor " + optimisticallyUnchokedNeighbour + ". \n";
		this.writeToFile(event);
	}
	
	public void logUnchoking(String rPeer)
	{
		String event = peerConnection() + " is unchoked by " + rPeer + ". \n";
		this.writeToFile(event);
	}
	
	public void logChoking(String rPeer)
	{
		String event = peerConnection() + " is choked by " + rPeer + ". \n";
		this.writeToFile(event);
	}
	
	public void logReceivedHave(String rPeer, int index)
	{
		String event = peerConnection() + " received a 'have' message from " + rPeer + " for the piece " + index + ". \n";
		this.writeToFile(event);
	}
	
	public void logReceivedInterested(String rPeer)
	{
		String event = peerConnection() + " received an 'interested' message from " + rPeer + ". \n";
		this.writeToFile(event);
	}
	
	public void logReceivedNotInterested(String rPeer)
	{
		String event = peerConnection() + " received a 'not interested' message from " + rPeer + ". \n";
		this.writeToFile(event);
	}

	public void logDownloadingPiece(String rPeer, int pieceIndex, int totalPiecesDownloaded)
	{
		String event = peerConnection() + " has downloaded the piece " + pieceIndex + " from " + rPeer + ". \nNow the number of pieces it has is " + totalPiecesDownloaded + ".\n";
		this.writeToFile(event);
	}

	public void logCompletion()
	{
		String event = peerConnection() + " has downloaded the complete file.\n";
		this.writeToFile(event);
	}
	
}