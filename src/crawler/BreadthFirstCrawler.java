package crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BreadthFirstCrawler {
	private static final int MAX_PAGES = 100;
	private ArrayList<String> initialUrls;
	private Set<String> pagesVisited = new HashSet<String>();
	private ArrayList<String> pagesToVisit = new ArrayList<String>();

	public BreadthFirstCrawler(String sitesFileName) {
		this.initialUrls = this.getUrlsFromFile(sitesFileName);
	}

	public void crawl() {
		for (int i = 0; i < this.initialUrls.size(); i++) {
			this.crawl(this.initialUrls.get(i));
			pagesVisited.clear();
			pagesToVisit.clear();
		}
	}

	private void crawl(String url) {
		while (this.pagesVisited.size() < MAX_PAGES) {
			String currentUrl;
			if (this.pagesToVisit.isEmpty()) {
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
			spider.visit(currentUrl);
			this.pagesVisited.add(currentUrl);
			this.pagesToVisit.addAll(spider.getLinksFound());

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
		do {
			if (this.pagesToVisit.isEmpty())
				throw new Exception("No more links!");

			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl) || nextUrl.length() == 0 || !Robot.isAllowed(nextUrl));

		return nextUrl;
	}

	private ArrayList<String> getUrlsFromFile(String sitesFileName) {
		ArrayList<String> urls = new ArrayList<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(sitesFileName));
			String line;
			while ((line = in.readLine()) != null) {
				urls.add(line);
			}
			in.close();
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
