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
		String domain = urlObj.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	public static ArrayList<HeuristicWord> getHeuristicFromFile(String fileName) {
		ArrayList<HeuristicWord> heuristicWords = new ArrayList<HeuristicWord>();
		
		ArrayList<String> lines = getLinesFromFile(fileName);
		for(String line : lines) {
			String[] split = line.split(" ");
			heuristicWords.add(new HeuristicWord(split[0], Integer.parseInt(split[1])));
		}
		
		return heuristicWords;
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
