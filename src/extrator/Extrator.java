package extrator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class Extrator {
	private File file;
	private FileWriter csvFile;

	public Extrator(File file) {
		this.file = file;

	}

	abstract public void extrair() throws IOException;

	public void mostraHTML(Elements e)
	{
		try{
			FileWriter fw = new FileWriter("texto.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(e.outerHtml());
			bw.write("\n\n\n\n");
			bw.close();
			fw.close();
		}
		catch (Exception exception){}
		
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileWriter getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(FileWriter csvFile) {
		this.csvFile = csvFile;
	}
}
