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

	public static void main(String[] args) {
				
		try {
			File folder = new File("classifier-data/pages/pos/submarino");

			ReadFiles read = new ReadFiles();
			List<File> files = read.listFilesSpecificFolder(folder);
	
			for (File file : files) {
				try {
					System.out.println(file.getName());
					Extrator e = new ExtratorSubmarino(file);
					e.extrair();	
				}
				catch (NullPointerException n)
				{
					System.out.println(n.getMessage());
				}
			}
			
		}
		catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}

	}
}
