package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorRicardoEletro extends Extrator {
	
	private final String CSV_NAME = "result/ricardoEletro.csv";
	
	public ExtratorRicardoEletro(File file) {
		super(file);
	}

	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder saida = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select("#ProdutoDetalhesNomeProduto > h1").first();
		Element preco = doc.select("[itemprop=lowPrice]").first();
		Elements dados = doc.select(".produto-descricao-caracteristicas > table > tbody > tr > td");
		
		//colocando juntado informacoes
		saida.append("Nome Produto: ");
		saida.append(";");
		saida.append(nomeProduto.text());
		saida.append("\n");
		saida.append("Preco: ");
		saida.append(";");
		saida.append(preco.text());
		saida.append("\n");	
				
		boolean flag = true;
				
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
		
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME), true));
		super.getCsvFile().write(saida.toString());
		super.getCsvFile().close();
	}

}
