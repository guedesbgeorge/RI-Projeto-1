package crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import extrator.*;
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
	private ClassificationHelper helper;

	public Spider() {
		this.helper = helper.getInstance();
	}

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
				if(!this.savePage(htmlDocument.toString(), Integer.toString(pageCount) + ".html", -1)) {
					return false;
				}
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

	private boolean savePage(String page, String pageName, int siteIndex) {
		BufferedWriter htmlWriter;
		try {
			File file = new File(this.baseDir);
			file.mkdir();
			htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath() + "/" + pageName), "UTF-8"));
			htmlWriter.write(page);
			htmlWriter.flush();
			htmlWriter.close();


			String fileContent = HTMLtoText.htmltoString(file.getPath() + "/" + pageName);

			if (helper.classify(fileContent)) {
				Extrator e;
				switch (siteIndex) {
					case 1:
						e = new ExtratorRicardoEletro(new File(file.getPath() + "/" + pageName));
						break;
					case 2:
						e = new ExtratorExtra(new File(file.getPath() + "/" + pageName));
						break;
					case 3:
						e = new ExtratorAmericanas(new File(file.getPath() + "/" + pageName));
						break;
					case 4:
						e = new ExtratorSubmarino(new File(file.getPath() + "/" + pageName));
						break;
					case 5:
						e = new ExtratorNagem(new File(file.getPath() + "/" + pageName));
						break;
					case 6:
						e = new ExtratorCissMagazine(new File(file.getPath() + "/" + pageName));
						break;
					case 7:
						e = new ExtratorSaraiva(new File(file.getPath() + "/" + pageName));
						break;
					case 8:
						e = new ExtratorCasasBahia(new File(file.getPath() + "/" + pageName));
						break;
					case 9:
						e = new ExtratorShopTime(new File(file.getPath() + "/" + pageName));
						break;
					case 10:
						e = new ExtratorPontoFrio(new File(file.getPath() + "/" + pageName));
						break;
					default:
						e = new ExtratorGlobal(new File(file.getPath() + "/" + pageName));
						break;
				}
				try {
					e.extrair();
					helper.addToDataSet(fileContent, "pos");
				} catch (Exception ex) {
					ex.printStackTrace();
					helper.addToDataSet(fileContent, "neg");
				}
			}

			try(FileWriter fw = new FileWriter(this.baseDir + "/" + "resume.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(Boolean.toString(helper.classify(fileContent)));
				} catch (IOException e) {
					return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
