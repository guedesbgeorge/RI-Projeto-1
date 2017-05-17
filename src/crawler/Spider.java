package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import classifier.ClassificationHelper;
import classifier.HTMLtoText;

public class Spider {
	private ArrayList<Link> linksFound = new ArrayList<Link>();
	private String baseDir = "crawler-data/pages";

	public boolean visit(String url, int siteIndex, int pageCount) {
		this.baseDir += "/" + Integer.toString(siteIndex);
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();

			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("Ops! Document was not HTML.");
				return false;
			}
			if (connection.response().statusCode() == 200) {
				System.out.println("Visiting " + url);
				this.savePage(htmlDocument.toString(), Integer.toString(pageCount) + ".html");
			}

			Elements linksFoundOnPage = htmlDocument.select("a[href]");
			//System.out.println("Found " + linksFoundOnPage.size() + " links");
			for (Element link : linksFoundOnPage) {
				this.linksFound.add(new Link(link.absUrl("href"), link.text()));
			}
			System.out.println();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	public ArrayList<Link> getLinksFound() {
		return this.linksFound;
	}

	private void savePage(String page, String pageName) {
		BufferedWriter htmlWriter;
		try {
			File file = new File(this.baseDir);
			file.mkdir();
			htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath() + "/" + pageName), "UTF-8"));
			htmlWriter.write(page);
			htmlWriter.flush();
			htmlWriter.close();
			
			ClassificationHelper helper = new ClassificationHelper("sgd");
			String fileContent = HTMLtoText.htmltoString(file.getPath() + "/" + pageName);
			try(FileWriter fw = new FileWriter(this.baseDir + "/" + "resume.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(Boolean.toString(helper.classify(fileContent)));
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
