package crawler;

public class Main {
	public static void main(String[] args) {
		HeuristicCrawler crawler = new HeuristicCrawler("crawler-data/sites.txt", "crawler-data/heuristic_words.txt");
		crawler.crawl();
	}
}
