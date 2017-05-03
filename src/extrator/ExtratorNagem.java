package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorNagem extends Extrator {

	private final String CSV_NAME = "result/nagem.csv";
	
	public ExtratorNagem(File file) {
		super(file);
	}

	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder saida = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select("span.tituloProduto > strong").first();
		Element preco = doc.select("span.precoDescricao > strong").first();
		Elements dadosEspecificacao = doc.select("td.coluna-nome-especificacao");
		Elements dadosDescricao = doc.select("td.coluna-descricao");
		
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
						
		for (int i = 0; i < dadosEspecificacao.size(); i++) 
		{
			saida.append(dadosEspecificacao.get(i).text());
			saida.append(";");
			saida.append(dadosDescricao.get(i).text());
			saida.append("\n");
		}
		
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME)));
		super.getCsvFile().write(saida.toString());
		super.getCsvFile().close();

	}

}
