package edu.ufl.cise.p2p;
public class Handshake implements PeerMessage
{
	private String handShakeHeader;
	private String peerID;
	private static int instanceCounter;
	private int messageNumber;
	
	public void setPeerID(String peerID) 
	{
		this.peerID = peerID;
	}

	private Handshake()
	{
		
	}
	
	public static Handshake getInstance()
	{
		Handshake handshake = new Handshake();
		boolean isSuccessful = handshake.init();
		if(isSuccessful == false)
		{
			handshake.close();
			handshake = null;
		}
		return handshake;
	}

	private boolean init(){
		instanceCounter++;
		messageNumber = instanceCounter; 
		return true;
	}
	public void close()
	{
		
	}
	public byte[] getHandshakeMessage()
	{
		return null;
	}

	public int getType() 
	{
		return Constants.HANDSHAKE;
	}

	public int getMessageLength() 
	{
		return 0;
	}
	
	public String getPeerID()
	{
		return peerID;
	}

	public int getMessageNumber() 
	{
		return messageNumber;
	}
}