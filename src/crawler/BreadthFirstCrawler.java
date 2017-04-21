package crawler;

import java.util.ArrayList;

public class BreadthFirstCrawler extends Crawler {
	private ArrayList<String> pagesToVisit;

	public BreadthFirstCrawler(String sitesFileName) {
		super(sitesFileName);
		pagesToVisit = new ArrayList<String>();
	}
	
	@Override
	public String nextUrl() throws Exception {
		String nextUrl;
		do {
			if (this.pagesToVisit.isEmpty())
				throw new Exception("No more links!");

			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl) || nextUrl.length() == 0 || !Robot.isAllowed(nextUrl) || !nextUrl.contains(currentDomain));

		return nextUrl;
	}

	@Override
	public void addLinksFound(ArrayList<String> linksFound) {
		this.pagesToVisit.addAll(linksFound);
	}

	@Override
	public void clearPagesQueue() {
		this.pagesToVisit.clear();
	}

	@Override
	public int pagesToVisit() {
		return this.pagesToVisit.size();
	}
	
	

}
