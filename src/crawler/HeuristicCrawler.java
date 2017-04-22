package crawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HeuristicCrawler extends Crawler {
	private ArrayList<HeuristicWord> heuristicWords;

	public HeuristicCrawler(String url, String heuristicWordsFile, int siteIndex) {
		super(url, siteIndex);
		this.heuristicWords = CrawlerUtil.getHeuristicFromFile(heuristicWordsFile);	
	}

	@Override
	public void addLinksFound(ArrayList<Link> linksFound) {
		for(Link link : linksFound) {
			int importance = 0;
			String url = link.getUrl();
			String anchor = link.getAnchor();
			if(!this.pagesVisited.contains(url) && url.length() > 0 && Robot.isAllowed(url) && url.contains(currentDomain)) {
				for(HeuristicWord heuristicWord : this.heuristicWords) {
					if((url.toLowerCase()).contains(heuristicWord.getWord().toLowerCase()) || anchor.toLowerCase().contains(heuristicWord.getWord().toLowerCase())) {
						importance += heuristicWord.getImportance();
					}
				}
				
				link.setImportance(importance);
				this.pagesToVisit.add(link);
			}
			
		}
		Collections.sort(this.pagesToVisit, Comparator.comparing(Link::getImportance).reversed());
	}
}
