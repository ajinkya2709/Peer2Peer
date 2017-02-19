package edu.ufl.cise.p2p.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class CommonPropReader {

	private final static String fileName = "classpath:Common.cfg";
	private final static String delimiter = "\\s+";

	public CommonPropReader() {
	}

	public static Properties read() {
		Properties prop = null;
		try {
			FileReader reader = new FileReader(fileName);
			prop = new Properties() {
				@Override
				public synchronized void load(Reader reader) throws IOException {
					BufferedReader bufferedReader = new BufferedReader(reader);
					String line = null;
					String[] tokens = null;
					while ((line = bufferedReader.readLine()) != null) {
						tokens = line.split(delimiter);
						String key = tokens[0];
						String value = tokens[1];
						put(key, value);
					}

				}

			};

			prop.load(reader);
			return prop;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}

}
