package crawler;

import java.util.ArrayList;

public class SimpleHeuristicCrawler extends Crawler {
	private ArrayList<HeuristicWord> goodWords;
	private ArrayList<HeuristicWord> badWords;

	public SimpleHeuristicCrawler(String url, String heuristicWordsFile, int siteIndex) {
		super(url, siteIndex);
		this.goodWords = getWords(true, heuristicWordsFile);
		this.badWords = getWords(false, heuristicWordsFile);
	}

	@Override
	public void addLinksFound(ArrayList<Link> linksFound) {
		for (Link link : linksFound) {
			String url = link.getUrl();
			String anchor = link.getAnchor();
			boolean relevant = false;
			for (HeuristicWord goodWord : this.goodWords) {
				if ((url.toLowerCase()).contains(goodWord.getWord().toLowerCase())
						|| anchor.toLowerCase().contains(goodWord.getWord().toLowerCase())) {
					relevant = true;
					break;
				}
			}

			for (HeuristicWord badWord : this.badWords) {
				if ((url.toLowerCase()).contains(badWord.getWord().toLowerCase())
						|| anchor.toLowerCase().contains(badWord.getWord().toLowerCase())) {
					relevant = false;
					break;
				}
			}

			if (relevant) {
				this.pagesToVisit.add(0, link);
			} else {
				this.pagesToVisit.add(link);
			}
		}
	}

	public static ArrayList<HeuristicWord> getWords(boolean good, String filename) {
		ArrayList<HeuristicWord> heuristicWords = CrawlerUtil.getHeuristicFromFile(filename);
		ArrayList<HeuristicWord> words = new ArrayList<HeuristicWord>();
		for (HeuristicWord word : heuristicWords) {
			if (word.getImportance() > 0 == good) {
				words.add(word);
			}
		}
		return words;
	}
}
