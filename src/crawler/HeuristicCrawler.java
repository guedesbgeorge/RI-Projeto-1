package crawler;

import java.util.ArrayList;

public class HeuristicCrawler extends Crawler {
	private ArrayList<String> heuristicWords;
	private ArrayList<String> bestPagesToVisit = new ArrayList<String>();
	private ArrayList<String> otherPagesToVisit = new ArrayList<String>();

	public HeuristicCrawler(String sitesFileName, String heuristicWordsFileName) {
		super(sitesFileName);
		this.heuristicWords = CrawlerUtil.getLinesFromFile(heuristicWordsFileName);
		this.bestPagesToVisit = new ArrayList<String>();
		this.otherPagesToVisit = new ArrayList<String>();
;	}

	@Override
	public String nextUrl() throws Exception {
		String nextUrl;
		do {
			if (this.bestPagesToVisit.isEmpty() && this.otherPagesToVisit.isEmpty())
				throw new Exception("No more links!");
			
			if(!this.bestPagesToVisit.isEmpty()) {
				nextUrl = this.bestPagesToVisit.remove(0);
			} else {
				nextUrl = this.otherPagesToVisit.remove(0);
			}
			
		} while (this.pagesVisited.contains(nextUrl) || nextUrl.length() == 0 || !Robot.isAllowed(nextUrl) || !nextUrl.contains(currentDomain));

		return nextUrl;
	}

	@Override
	public void addLinksFound(ArrayList<String> linksFound) {
		for(String url : linksFound) {
			boolean bestLink = false;
			for(String word : this.heuristicWords) {
				if(url.contains(word)) {
					bestLink = true;
					break;
				}
			}
			if(bestLink) {
				this.bestPagesToVisit.add(url);
			} else {
				this.otherPagesToVisit.add(url);
			}
		}
	}

	@Override
	public void clearPagesQueue() {
		this.bestPagesToVisit.clear();
		this.otherPagesToVisit.clear();
		
	}

	@Override
	public int pagesToVisit() {
		return bestPagesToVisit.size() + otherPagesToVisit.size();
	}

}
