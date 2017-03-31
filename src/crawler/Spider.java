package crawler;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {
	private ArrayList<String> linksFound = new ArrayList<String>();

	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();
			
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("Ops! Document was not HTML.");
				return false;
			}
			if (connection.response().statusCode() == 200) {
				System.out.println("Visiting " + url);
			}
			
			Elements linksFoundOnPage = htmlDocument.select("a[href]");
			System.out.println("Found " + linksFoundOnPage.size() + " links");
			for (Element link : linksFoundOnPage) {
				this.linksFound.add(link.absUrl("href"));
			}
			System.out.println();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	public ArrayList<String> getLinksFound() {
		return this.linksFound;
	}
}
