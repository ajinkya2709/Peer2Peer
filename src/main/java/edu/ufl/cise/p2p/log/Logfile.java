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
 * Receiving 'have' message
 * Receiving 'interested' message
 * Receiving 'not interested' message
 * Downloading a piece
 * Completion of download
 */
public class Logfile extends Log
{
	/******************* Class Attributes *******************/
	private String peerID;
	
	/******************* Class Methods *******************/
	public Logfile(String localPeerId) throws IOException 
	{
		super("log_peer_" + localPeerId + ".log");
		this.peerID = localPeerId;
	} /* end constructor */
	
	/**
	 * Returns a string of the form [Time]: Peer [peer_ID]
	 * @return	a string of the form [Time]: Peer [peer_ID]
	 */
	private String peerConnection()
	{
		return(this.getTime() + ": Peer " + this.peerID);
	} /* end peerconnection */
	
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
	} /* end logTCPConnection method */
	
	/**
	 * Logs the change of preferred neighbors
	 * @param preferredNeighbours	an array with the neighbors that became the	proffered 
	 * 					neighbors
	 */
	public void logChangePreferredNeighbors(ArrayList<RemotePeer> preferredNeighbours)
	{
		StringBuffer event = new StringBuffer();
		event.append(peerConnection() + " has the preferred neighbors ");
		
		/* Append the neighbors */
		for(int i = 0; i < (preferredNeighbours.size() - 1); i++)
		{
			event.append(preferredNeighbours.get(i).getPeerId() + ", ");
		} /* end for loop */
		
		/* Append the last neighbor */
		event.append(preferredNeighbours.get(preferredNeighbours.size() - 1).getPeerId() + ". \n");
		
		this.writeToFile(event.toString());
	} /* end logChangePreferredNeighbors method */
	
	/**
	 * Logs the change of the optimistically unchoked neighbor
	 * @param string	the peerID of the optimistically unchoked neighbor
	 */
	public void logChangeOptimisticallyUnchokedNeighbor(String string)
	{
		String event = peerConnection() + " has the optimistically-unchoked neighbor " + string + ". \n";
		this.writeToFile(event);
	} /* end logChangeOptimisticallyUnchokedNeighbor method */
	
	/**
	 * Log the unchoking of this Peer by peer
	 * @param string	the peer who has unchoked this Peer
	 */
	public void logUnchoking(String string)
	{
		String event = peerConnection() + " is unchoked by " + string + ". \n";
		this.writeToFile(event);
	} /* end logUnchoking method */
	
	/**
	 * Log the choking of this Peer by peer
	 * @param string	the peer who has choked this peer
	 */
	public void logChoking(String string)
	{
		String event = peerConnection() + " is choked by " + string + ". \n";
		this.writeToFile(event);
	} /* end logChoking method */
	
	/**
	 * Log the arrival of a 'have' message from peer with piece index of index
	 * @param string	the peer who sent the message
	 * @param index	the piece index
	 */
	public void logReceivedHave(String string, int index)
	{
		String event = peerConnection() + " received a 'have' message from " + string + " for the piece " + index + ". \n";
		this.writeToFile(event);
	} /* end logReceivedHave method */
	
	/**
	 * Log the arrival of a 'interested' message from peer
	 * @param string	the peer who sent the message
	 */
	public void logReceivedInterested(String string)
	{
		String event = peerConnection() + " received an 'interested' message from " + string + ". \n";
		this.writeToFile(event);
	} /* end logReceivedInterested method */
	
	/**
	 * Log the arrival of a 'not interested' message from peer
	 * @param string	the peer who sent the message
	 */
	public void logReceivedNotInterested(String string)
	{
		String event = peerConnection() + " received a 'not interested' message from " + string + ". \n";
		this.writeToFile(event);
	} /* end logReceivedNotInterested method */

	/**
	 * Logs the downloading of a piece of the file
	 * @param peer	the peer from whom the piece was obtained
	 * @param index	the index of the piece obtained
	 * @param has 	the number of pieces this peer has of the file
	 */
	public void logDownloadingPiece(int peer, int index, int has)
	{
		String event = peerConnection() + " has downloaded the piece " + index + " from " + peer + ". \nNow the number of pieces it has is " + has + ".\n";
		this.writeToFile(event);
	} /* end logDownloadingPiece method */
	
	/**
	 * Logs the completion of the file
	 */
	public void logCompletion()
	{
		String event = peerConnection() + " has downloaded the complete file.\n";
		this.writeToFile(event);
	} /* end logCompletion method */
	
} /* end LogFile */
