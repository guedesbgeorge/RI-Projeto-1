package crawler;

public class Main {
	public static void main(String[] args) {
		final String sitesFileName = "crawler-data/sites.txt";
		final String containsHeuristicWordsFileName = "crawler-data/heuristic_words/contains.txt";
		final String notContainsHeuristicWordsFileName = "crawler-data/heuristic_words/not_contains.txt";
		HeuristicCrawler crawler = new HeuristicCrawler(sitesFileName, containsHeuristicWordsFileName, notContainsHeuristicWordsFileName);
		crawler.crawl();
	}
}
