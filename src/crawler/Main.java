package crawler;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> urls = CrawlerUtil.getLinesFromFile("crawler-data/sites.txt");
		for(int i = 0; i < urls.size(); i++) {
			String url = urls.get(i);
			final int siteIndex = i + 1;
			new Thread(() -> {
				final String heuristicWordsFileName = "crawler-data/heuristic_words.txt";
				HeuristicCrawler crawler = new HeuristicCrawler(url, heuristicWordsFileName, siteIndex);
				crawler.crawl();
			}).start();
		}			
	}
}
