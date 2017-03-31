package crawler;

public class Main {
	public static void main(String[] args) {
		String[] urls = {"http://cin.ufpe.br/~gbg/test/test.html", "http://globo.com"};
		BreadthFirstCrawler crawler = new BreadthFirstCrawler();
		crawler.search(urls);
	}
}
