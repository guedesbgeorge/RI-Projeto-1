package crawler;

public class Main {
	public static void main(String[] args) {
		BreadthFirstCrawler crawler = new BreadthFirstCrawler("crawler-data/sites.txt");
		crawler.crawl();
	}
}
