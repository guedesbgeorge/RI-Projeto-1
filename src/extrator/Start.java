package extrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Start {

	public static void main(String[] args) {
		
		Extrator e = new ExtratorAmericanas(new File("sites/americanas.html"));
		
		try {
			e.extrair();
			
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}

	}
}
