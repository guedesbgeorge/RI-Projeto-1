package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorCissMagazine extends Extrator {

	private final String CSV_NAME = "result/cissmagazine.csv";
	
	public ExtratorCissMagazine(File file) {
		super(file);
	}

	public ExtratorCissMagazine() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder saida = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select("h1[itemprop=name]").first();
		Element preco = doc.select("span.price").first();
		Elements dadosEspecificacao = doc.select("div.content-caracteristicas > div.caracteristicas-lista-corrida > dl > span > dt");
		Elements dadosDescricao = doc.select("div.content-caracteristicas > div.caracteristicas-lista-corrida > dl > span > dd");
		
		//colocando juntado informacoes
		saida.append("Nome Produto: ");
		saida.append(";");
		saida.append(nomeProduto.text());
		saida.append("\n");
		saida.append("Preco: ");
		saida.append(";");
		saida.append(preco.text());
		saida.append("\n");
				
				
		for (int i = 0; i < dadosEspecificacao.size(); i++) 
		{
			saida.append(dadosEspecificacao.get(i).text());
			saida.append(";");
			saida.append(dadosDescricao.get(i).text());
			saida.append("\n");
		}
		
		saida.append("\n\n\n");
		
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME), true));
		super.getCsvFile().write(saida.toString());
		super.getCsvFile().close();

	}

}
