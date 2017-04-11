package crawler;

public class Main {
	public static void main(String[] args) {
		String sitesFileName = "crawler-data/sites.txt";
		String heuristicWordsFileName = "crawler-data/heuristic_words.txt";
		HeuristicCrawler crawler = new HeuristicCrawler(sitesFileName, heuristicWordsFileName);
		crawler.crawl();
	}
}
