package crawler;

import java.util.ArrayList;

public class BreadthFirstCrawler extends Crawler {

	public BreadthFirstCrawler(String url) {
		super(url);
	}

	@Override
	public void addLinksFound(ArrayList<Link> linksFound) {
		for(Link link : linksFound) {
			String url = link.getUrl();
			if(!this.pagesVisited.contains(url) && url.length() > 0 && Robot.isAllowed(url) && url.contains(currentDomain))
				this.pagesToVisit.add(link);
		}
		
	}
}
