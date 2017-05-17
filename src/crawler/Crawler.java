package crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Crawler {
	private static final int MAX_PAGES = 1000;
	private String initialUrl;
	private int siteIndex;
	protected ArrayList<Link> pagesToVisit = new ArrayList<Link>();
	protected Set<String> pagesVisited = new HashSet<String>();
	protected String currentDomain;

	public Crawler(String url, int siteIndex) {
		this.initialUrl = url;
		this.siteIndex = siteIndex;
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
			if(spider.visit(currentUrl, siteIndex, this.pagesVisited.size())) {
				this.pagesVisited.add(currentUrl);
				this.addLinksFound(spider.getLinksFound());
			}
		}

		System.out.println("Visited " + this.pagesVisited.size() + " web page(s)\n\n");
	}

	private String nextUrl() throws Exception {
		String nextUrl;
		if (this.pagesToVisit.isEmpty())
			throw new Exception("No more links!");
		
		do {
			nextUrl = this.pagesToVisit.remove(0).getUrl();
		} while(this.pagesVisited.contains(nextUrl) || nextUrl.length() <= 0 || !Robot.isAllowed(nextUrl) || !nextUrl.contains(currentDomain));

		
		return nextUrl;
	}
	
	public abstract void addLinksFound(ArrayList<Link> linksFound);
}
