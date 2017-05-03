package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorExtra extends Extrator {

	private final String CSV_NAME = "result/extra.csv";
	
	public ExtratorExtra(File file) {
		super(file);
	}

	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder sb = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select("b[itemprop='name']").first();
		Element preco = doc.select("#ctl00_Conteudo_ctl01_precoPorValue > i.sale").first();
		//System.out.println(nomeProduto.text());
		Elements dadosNome = doc.select("dl > dt");
		Elements dadosValor = doc.select("dl > dd");
		
		for (int i = 0; i < dadosNome.size(); i++) {
			//ajustar espacos vazios
			sb.append(dadosNome.get(i).text());
			sb.append(";");
			sb.append(dadosValor.get(i).text());
			sb.append("\n");
		}
		
		super.setCsvFile(new FileWriter(this.CSV_NAME));
		super.getCsvFile().write(sb.toString());
		super.getCsvFile().close();
	}

}
