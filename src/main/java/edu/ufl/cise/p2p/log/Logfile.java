package edu.ufl.cise.p2p.log;

import java.io.IOException;

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
	private int peerID;
	
	/******************* Class Methods *******************/
	public Logfile(int peerID, String path) throws IOException 
	{
		super(path + "log_peer_" + peerID + ".log");
		this.peerID = peerID;
	} /* end constructor */
	
	/**
	 * Returns a string of the form [Time]: Peer [peer_ID]
	 * @return	a string of the form [Time]: Peer [peer_ID]
	 */
	private String peerconnection()
	{
		return(this.getTime() + ": Peer " + this.peerID);
	} /* end peerconnection */
	
	/**
	 * Logs the connection to peer
	 * @param peer				the peerID of the connected peer
	 * @param madeConnection	flag on whether or not this Peer established the
	 * 							connection
	 */
	public void logTCPConnection(int peer, boolean madeConnection)
	{
		
		String event;
		if(madeConnection)
		{/* This Peer made the connection */
			event = peerconnection() + " makes a connection to Peer " + peer + ". \n";
		} /* end if */
		else
		{ /* This Peer was connected to by peer */
			event = peerconnection() + " is connected from Peer " + peer + ". \n";
		} /* end else */
		
		this.writeToFile(event);
	} /* end logTCPConnection method */
	
	/**
	 * Logs the change of preferred neighbors
	 * @param integers	an array with the neighbors that became the	proffered 
	 * 					neighbors
	 */
	public void logChangePreferredNeighbors(Integer[] integers)
	{
		StringBuffer event = new StringBuffer();
		event.append(peerconnection() + " has the preferred neighbors ");
		
		/* Append the neighbors */
		for(int i = 0; i < (integers.length - 1); i++)
		{
			event.append(integers[i] + ", ");
		} /* end for loop */
		
		/* Append the last neighbor */
		event.append(integers[integers.length - 1] + ". \n");
		
		this.writeToFile(event.toString());
	} /* end logChangePreferredNeighbors method */
	
	/**
	 * Logs the change of the optimistically unchoked neighbor
	 * @param peer	the peerID of the optimistically unchoked neighbor
	 */
	public void logChangeOptimisticallyUnchokedNeighbor(int peer)
	{
		String event = peerconnection() + " has the optimistically-unchoked neighbor " + peer + ". \n";
		this.writeToFile(event);
	} /* end logChangeOptimisticallyUnchokedNeighbor method */
	
	/**
	 * Log the unchoking of this Peer by peer
	 * @param peer	the peer who has unchoked this Peer
	 */
	public void logUnchoking(int peer)
	{
		String event = peerconnection() + " is unchoked by " + peer + ". \n";
		this.writeToFile(event);
	} /* end logUnchoking method */
	
	/**
	 * Log the choking of this Peer by peer
	 * @param peer	the peer who has choked this peer
	 */
	public void logChoking(int peer)
	{
		String event = peerconnection() + " is choked by " + peer + ". \n";
		this.writeToFile(event);
	} /* end logChoking method */
	
	/**
	 * Log the arrival of a 'have' message from peer with piece index of index
	 * @param peer	the peer who sent the message
	 * @param index	the piece index
	 */
	public void logReceivedHave(int peer, int index)
	{
		String event = peerconnection() + " received a 'have' message from " + peer + " for the piece " + index + ". \n";
		this.writeToFile(event);
	} /* end logReceivedHave method */
	
	/**
	 * Log the arrival of a 'interested' message from peer
	 * @param peer	the peer who sent the message
	 */
	public void logReceivedInterested(int peer)
	{
		String event = peerconnection() + " received an 'interested' message from " + peer + ". \n";
		this.writeToFile(event);
	} /* end logReceivedInterested method */
	
	/**
	 * Log the arrival of a 'not interested' message from peer
	 * @param peer	the peer who sent the message
	 */
	public void logReceivedNotInterested(int peer)
	{
		String event = peerconnection() + " received a 'not interested' message from " + peer + ". \n";
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
		String event = peerconnection() + " has downloaded the piece " + index + " from " + peer + ". \nNow the number of pieces it has is " + has + ".\n";
		this.writeToFile(event);
	} /* end logDownloadingPiece method */
	
	/**
	 * Logs the completion of the file
	 */
	public void logCompletion()
	{
		String event = peerconnection() + " has downloaded the complete file.\n";
		this.writeToFile(event);
	} /* end logCompletion method */
	
} /* end LogFile */
