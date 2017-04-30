package crawler;

import java.util.ArrayList;

public class SimpleHeuristicCrawler extends Crawler {
	private ArrayList<HeuristicWord> goodWords;
	private ArrayList<HeuristicWord> badWords;
	private ArrayList<Link> otherLinks = new ArrayList<Link>();

	public SimpleHeuristicCrawler(String url, String heuristicWordsFile, int siteIndex) {
		super(url, siteIndex);
		this.goodWords = getWords(true, heuristicWordsFile);
		this.badWords = getWords(false, heuristicWordsFile);
	}

	@Override
	public void addLinksFound(ArrayList<Link> linksFound) {
		for(Link link : linksFound) {
			String url = link.getUrl();
			String anchor = link.getAnchor();
			boolean relevant = false;
			if(!this.pagesVisited.contains(url) && url.length() > 0 && Robot.isAllowed(url) && url.contains(currentDomain)) {
				for(HeuristicWord goodWord : this.goodWords) {
					if((url.toLowerCase()).contains(goodWord.getWord().toLowerCase()) || anchor.toLowerCase().contains(goodWord.getWord().toLowerCase())) {
						relevant = true;
						break;
					}
				}
				
				for(HeuristicWord badWord : this.badWords) {
					if((url.toLowerCase()).contains(badWord.getWord().toLowerCase()) || anchor.toLowerCase().contains(badWord.getWord().toLowerCase())) {
						relevant = false;
						break;
					}
				}
				
				if(relevant) {
					this.pagesToVisit.add(link);
				} else {
					this.otherLinks.add(link);
				}
			}
			
		}
		
		if(this.pagesToVisit.isEmpty()) {
			Link link;
			do {
				link = this.otherLinks.remove(0);
			} while(this.pagesVisited.contains(link.getUrl()));
			this.pagesToVisit.add(link);
		}
	}
	
	public static ArrayList<HeuristicWord> getWords(boolean good, String filename) {
		ArrayList<HeuristicWord> heuristicWords = CrawlerUtil.getHeuristicFromFile(filename);	
		ArrayList<HeuristicWord> words = new ArrayList<HeuristicWord>();
		for(HeuristicWord word : heuristicWords) {
			if(word.getImportance() > 0 == good) {
				words.add(word);
			}
		}	
		return words;
	}
}
