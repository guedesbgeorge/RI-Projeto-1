package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorPontoFrio extends Extrator {

	private final String CSV_NAME = "result/pontoFrio.csv";
	
	public ExtratorPontoFrio(File file) {
		super(file);
	}

	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder saida = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select("b[itemprop='name']").first();
		Element preco = doc.select("#ctl00_Conteudo_ctl01_precoPorValue > i.sale").first();
		//System.out.println(nomeProduto.text());
		Elements dadosNome = doc.select("dl > dt");
		Elements dadosValor = doc.select("dl > dd");
		
		//colocando juntado informacoes
		saida.append("Nome Produto: ");
		saida.append(";");
		saida.append(nomeProduto.text());
		saida.append("\n");
		saida.append("Preco: ");
		saida.append(";");
		saida.append(preco.text());
		saida.append("\n");
		
		for (int i = 0; i < dadosNome.size(); i++) {
			//ajustar espacos vazios
			saida.append(dadosNome.get(i).text());
			saida.append(";");
			saida.append(dadosValor.get(i).text());
			saida.append("\n");
		}
		
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME), true));
		super.getCsvFile().write(saida.toString());
		super.getCsvFile().close();
	}

}
