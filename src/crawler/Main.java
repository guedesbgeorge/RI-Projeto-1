package crawler;

public class Main {
	public static void main(String[] args) {
		String url = "https://www.cissamagazine.com.br/smartphones";
		final String heuristicWordsFileName = "crawler-data/heuristic_words.txt";
		HeuristicCrawler crawler = new HeuristicCrawler(url, heuristicWordsFileName);
		crawler.crawl();
	}
}
