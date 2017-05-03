package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorMagazineLuiza extends Extrator {
	
	private final String CSV_NAME = "resul/magazineLuiza.csv";
	
	public ExtratorMagazineLuiza(File file) {
		super(file);
	}

	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder saida = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select("h1[itemprop='name']").first();
		Element preco = doc.select(".js-to-price-line > .js-price-value").first();
		Elements dados = doc.select(".fs-content-block > .fs-row > .fs-right");
	
		
		
		//colocando juntado informacoes
		saida.append("Nome Produto: ");
		saida.append(";");
		saida.append(nomeProduto.text());
		
		
		/*Element preco = doc.select(".sales-price").first();
		Elements dados = doc.select("table.table-striped > tbody > tr > td");
		
		
		saida.append("\n");
		saida.append("Preco: ");
		saida.append(";");
		saida.append(preco.text());
		saida.append("\n");
		
		
		boolean flag = true;
		
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME)));
		
		for (Element element : dados) 
		{
			String descricao = element.text();
			saida.append(descricao);
			if (flag) 
			{
				saida.append(": ");
				saida.append(";");
			}
			else saida.append("\n");
			flag = !flag;
		}
		super.getCsvFile().write(saida.toString());
		this.getCsvFile().close();
		*/
	}

}
