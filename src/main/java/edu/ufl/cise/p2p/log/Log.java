package edu.ufl.cise.p2p.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Anirudh Rege 
 **/
public class Log 
{
	public final String path;
	private Timestamp time; 
	private File file;
	private FileWriter fw;
	private BufferedWriter bw;
	
	public Log(String path) throws IOException
	{
		this.path = path;
		Date date = new Date();
		this.time = new Timestamp(date.getTime());
		
		file = new File(path);
		//if(file.exists()) file.append();
		file.createNewFile();

		try 
		{
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
		} 

		catch (IOException e) 
		{

		} 
		
	}
	
	public final synchronized void writeToFile(String content)
	{
		try 
		{
			bw.write(content);
			bw.flush();
		}
		catch (IOException e) 
		{

		} 
		
	}

	public final synchronized void close()
	{
		try 
		{
			bw.close();
			fw.close();
		}
		catch (IOException e) 
		{

			e.printStackTrace();
		}
		
	}
	
	public final synchronized String getTime()
	{
		Date date = new Date();
		this.time.setTime(date.getTime());
		return time.toString();
	}
}