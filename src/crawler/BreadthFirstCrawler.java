package crawler;

import java.util.ArrayList;

public class BreadthFirstCrawler extends Crawler {

	public BreadthFirstCrawler(String url, int siteIndex) {
		super(url, siteIndex);
	}

	@Override
	public void addLinksFound(ArrayList<Link> linksFound) {
		this.pagesToVisit.addAll(linksFound);

	}
}
