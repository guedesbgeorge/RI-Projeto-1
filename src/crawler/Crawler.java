package crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Crawler {
	private static final int MAX_PAGES = 1000;
	private ArrayList<String> initialUrls;
	protected Set<String> pagesVisited = new HashSet<String>();
	protected static String currentDomain;

	public Crawler(String sitesFileName) {
		this.initialUrls = CrawlerUtil.getLinesFromFile(sitesFileName);
	}

	public void crawl() {
		for (int i = 0; i < this.initialUrls.size(); i++) {
			currentDomain = CrawlerUtil.getDomain(this.initialUrls.get(i));
			this.crawl(this.initialUrls.get(i));
			pagesVisited.clear();
			clearPagesQueue();
		}
	}

	private void crawl(String url) {
		while (this.pagesVisited.size() < MAX_PAGES) {
			String currentUrl;
			if (this.pagesToVisit() == 0) {
				currentUrl = url;
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

	public abstract String nextUrl() throws Exception;
	
	public abstract void addLinksFound(ArrayList<String> linksFound);
	
	public abstract void clearPagesQueue();
	
	public abstract int pagesToVisit();
}
