package crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BreadthFirstCrawler {
	private static final int MAX_PAGES = 8;
	private Set<String> pagesVisited = new HashSet<String>();
	private ArrayList<String> pagesToVisit = new ArrayList<String>();
	
	public void search(String[] urls) {
		for(int i = 0; i < urls.length; i++) {
			this.search(urls[i]);
			pagesVisited.clear();
			pagesToVisit.clear();
		}
	}

	private void search(String url) {
		while (this.pagesVisited.size() < MAX_PAGES) {
			String currentUrl;
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
			} else {
				try {
					currentUrl = this.nextUrl();
				} catch (Exception e) {
					System.out.println("Ops! "+ e.getMessage());
					break;
				}
			}
			
			Spider spider = new Spider();
			spider.crawl(currentUrl);
			this.pagesVisited.add(currentUrl);
			this.pagesToVisit.addAll(spider.getLinksFound());
		}
		
		System.out.println("Visited " + this.pagesVisited.size() + " web page(s)\n\n");
	}

	private String nextUrl() throws Exception {
		String nextUrl;
		do {
			if(this.pagesToVisit.isEmpty())
				throw new Exception("No more links!");
			
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));
		
		return nextUrl;
	}
}
