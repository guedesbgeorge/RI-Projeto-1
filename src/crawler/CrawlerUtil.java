package crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CrawlerUtil {
	public static String getDomain(String url) {
		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hostId = urlObj.getProtocol() + "://" + urlObj.getAuthority();
		return hostId;
	}
	
	public static ArrayList<String> getLinesFromFile(String fileName) {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			in.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
