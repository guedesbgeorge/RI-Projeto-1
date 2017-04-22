package crawler;

public class HeuristicWord {
	private String word;
	private int importance;
	
	public HeuristicWord(String word, int importance) {
		this.word = word;
		this.importance = importance;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}
}
