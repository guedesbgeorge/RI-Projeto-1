package extrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Start {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
				
		try {
			String path = "classifier-data/pages/pos/";
			
			String lojas[] = {
					"americanas",
					"casasbahia",
					"cissamagazine",
					"extra",
					"nagem",
					"pontofrio",
					"ricardoeletro",
					"saraiva",
					"shoptime",
					"submarino"
			};
			
			String classe[] = {
					"Americanas",
					"CasasBahia",
					"CissMagazine",
					"Extra",
					"Nagem",
					"PontoFrio",
					"RicardoEletro",
					"Saraiva",
					"ShopTime",
					"Submarino"
			};
			
			for  (int i = 0; i < lojas.length; i++)
			{
				File folder = new File(path + lojas[i]);

				ReadFiles read = new ReadFiles();
				List<File> files = read.listFilesSpecificFolder(folder);
				
				String nome = "extrator.Extrator" + classe[i];
				Extrator extrator = (Extrator) Class.forName(nome).newInstance();
					
				for (File file : files) {
					try {

						extrator.setFile(file);
						extrator.extrair();	
					}
					catch (NullPointerException n)
					{
						System.out.println(n.getMessage());
					}
				}

			}
		
		}
		catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}

	}
}
