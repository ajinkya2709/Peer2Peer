package edu.ufl.cise.p2p.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import edu.ufl.cise.p2p.CommonPeerProperties;

public class CommonPropReader {

	private final static String fileName = "/Common.cfg";
	private final static String delimiter = "\\s+";
	
	public CommonPropReader() {
	}

	@SuppressWarnings("serial")
	public static CommonPeerProperties read() {
		Properties prop = null;
		CommonPeerProperties result = null;
		try {
			prop = new Properties() {
				@Override
				public synchronized void load(InputStream in)
						throws IOException {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "UTF-8"));
					String line = null;
					String[] tokens = null;
					while ((line = br.readLine()) != null) {
						tokens = line.split(delimiter);
						String key = tokens[0];
						String value = tokens[1];
						put(key, value);
					}

				}

			};
			InputStream in = CommonPropReader.class
					.getResourceAsStream(fileName);
			prop.load(in);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = mapProperties(prop);
		return result;

	}

	private static CommonPeerProperties mapProperties(Properties prop) {
		CommonPeerProperties common = new CommonPeerProperties();
		common.setFileName(String.valueOf(prop.get("FileName")));
		common.setFileSize(Integer.parseInt(String.valueOf(prop.get("FileSize"))));
		common.setNumberOfPreferredNeighbors(Integer.parseInt(String
				.valueOf(prop.get("NumberOfPreferredNeighbors"))));
		common.setOptimisticUnchokingInterval(Integer.parseInt(String
				.valueOf(prop.get("UnchokingInterval"))));
		common.setUnchokingInterval(Integer.parseInt(String.valueOf(prop
				.get("OptimisticUnchokingInterval"))));
		return common;
	}

}
