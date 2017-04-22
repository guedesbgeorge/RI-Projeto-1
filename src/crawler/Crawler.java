package crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Crawler {
	private static final int MAX_PAGES = 1000;
	private String initialUrl;
	protected ArrayList<Link> pagesToVisit = new ArrayList<Link>();
	protected Set<String> pagesVisited = new HashSet<String>();
	protected String currentDomain;

	public Crawler(String url) {
		this.initialUrl = url;
		this.currentDomain = CrawlerUtil.getDomain(url);
	}

	public void crawl() {
		while (this.pagesVisited.size() < MAX_PAGES) {
			String currentUrl;
			if (this.pagesToVisit.size() == 0) {
				currentUrl = initialUrl;
			} else {
				try {
					currentUrl = this.nextUrl();
				} catch (Exception e) {
					System.out.println("Ops! " + e.getMessage());
					break;
				}
			}

			Spider spider = new Spider();
			spider.visit(currentUrl, this.pagesVisited.size() + 1);
			this.pagesVisited.add(currentUrl);
			this.addLinksFound(spider.getLinksFound());

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("Visited " + this.pagesVisited.size() + " web page(s)\n\n");
	}

	private String nextUrl() throws Exception {
		String nextUrl;
		if (this.pagesToVisit.isEmpty())
			throw new Exception("No more links!");
			
		nextUrl = this.pagesToVisit.remove(0).getUrl();
		return nextUrl;
	}
	
	public abstract void addLinksFound(ArrayList<Link> linksFound);
}
