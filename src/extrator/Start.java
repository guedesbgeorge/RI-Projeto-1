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
		
		String string[] = {
				"sites/shoptime.html",
				"sites/americanas.html",
				"sites/bahia.html",
				"sites/cissmagazine.html",
				"sites/saraiva.html",
				"sites/extra.html",
				"sites/nagem.html",
				"sites/pontofrio.html",
				"sites/ricardoEletro.html",
				"sites/submarino.html",
		};
		
		try {
			for (String s : string)  
			{
				Extrator e = new ExtratorGlobal(new File(s));
				e.extrair();
			}
			/*
			Extrator e = new ExtratorGlobal(new File("sites/ricardoEletro.html"));
			e.extrair();
			*/
		}
		catch (NullPointerException n)
		{
			n.getMessage();
		}
		catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}

	}
}
