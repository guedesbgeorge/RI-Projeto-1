package crawler;

public class Main {
	public static void main(String[] args) {
		String[] urls = {"http://www.globo.com", "http://www.cin.ufpe.br/~gbg/test/test.html"};
		BreadthFirstCrawler crawler = new BreadthFirstCrawler();
		crawler.search(urls);
	}
}
