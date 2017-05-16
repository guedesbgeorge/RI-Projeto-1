package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorGlobal extends Extrator {

	private String siteName;
	private final static String CSV_NAME = "result/global.csv"; 
	private final String raisPai[] = {
			".table-striped",
			"#caracteristicas",
			".caracteristicas-do-produto",
			".area-especificacao",
			"#aba-caracteristicas",
			".product-characteristics"
	};
	
	public ExtratorGlobal(File file) {
		super(file);
	}

	public String nomeRaizPai(Document doc)
	{
		for (String string : raisPai) {
			if(doc.select(string).first() != null)
				return string;
			//System.out.println(string);
		}
		return null;
	}

	public String it(Element e)
	{
		StringBuilder sb = new StringBuilder();
		Elements element = e.children();
		boolean flag = true;
		for (Element el : element) {
			if (el.childNodeSize() == 1) 
			{
				//System.out.println(el.text());
				sb.append(el.text());
				if (flag) sb.append(";");
				else sb.append("\n");
				flag = !flag;
			}
			sb.append(it(el));
		}
		
		return sb.toString();
	}
	
	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
	
		String raizPai = nomeRaizPai(doc);
		
		Element e = doc.select(raizPai).first();		
		
		String saida = it(e);
		saida += "\n\n\n";	
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME), true));
		super.getCsvFile().write(saida);
		super.getCsvFile().close();
	}

}
