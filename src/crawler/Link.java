package crawler;

public class Link {
	private String url;
	private String anchor;
	private int importance;
	
	public Link(String url, String anchor) {
		this.url = url;
		this.anchor = anchor;
		this.importance = 0;
	}
	
	public Link(String url, String anchor, int importance) {
		this.url = url;
		this.anchor = anchor;
		this.importance = importance;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}
}
