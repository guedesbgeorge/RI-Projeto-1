package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorSaraiva extends Extrator {

	private final String CSV_NAME = "result/saraiva.csv";
	
	public ExtratorSaraiva(File file) {
		super(file);
	}

	@Override
	public void extrair() throws IOException {
		Document doc = Jsoup.parse(super.getFile(), "UTF-8", "");
		StringBuilder saida = new StringBuilder();
		
		//consultas
		Element nomeProduto = doc.select(".product-info > h1").first();
		System.out.println(nomeProduto.text());
		Element preco = doc.select(".main-price-infos > p > .price-val").first();
		System.out.println(preco.text());
		
		Elements nomeDescricao = doc.select("table > tbody > tr > th");
		Elements dadosDescricao = doc.select("table > tbody > tr > td");
		System.out.println(nomeDescricao.size() + " " + dadosDescricao.size());
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
				
		for (Iterator<Element> itNome = nomeDescricao.iterator(), itDados = dadosDescricao.iterator();
				(itDados.hasNext() && itNome.hasNext());) 
		{
			String label = itNome.next().text();
			String descricao = itDados.next().text();
			saida.append(label);
			saida.append(";");
			saida.append(descricao);
			saida.append("\n");
		}
		
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME), true));
		super.getCsvFile().write(saida.toString());
		super.getCsvFile().close();
	
	}

}
