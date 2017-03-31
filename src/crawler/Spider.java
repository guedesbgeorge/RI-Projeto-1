package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {
	private ArrayList<String> linksFound = new ArrayList<String>();
	private static String dir = "crawlerPages";
	private static File file = new File(dir);

	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();

			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("Ops! Document was not HTML.");
				return false;
			}
			if (connection.response().statusCode() == 200) {
				this.savePage(htmlDocument.toString(), dir +"/"+ htmlDocument.title()+".html");
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

	private void savePage(String page, String pageName) {
		BufferedWriter htmlWriter;
		try {
			file.mkdir();
			htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pageName), "UTF-8"));
			htmlWriter.write(page);
			htmlWriter.flush();
			htmlWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
