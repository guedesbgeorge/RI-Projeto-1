package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExtratorGlobal extends Extrator {

	private final static String CSV_NAME = "result/global.csv"; 
	private final String raisPai[] = {
			".table-striped",
			"#caracteristicas",
			".caracteristicas-do-produto",
			".area-especificacao",
			"table",
			"#aba-caracteristicas"
	};
	
	private final String nomeProduto[] = 
	{
			".card-product-name",
			"b[itemprop='name']",
			".tituloProduto > strong",
			"#ProdutoDetalhesNomeProduto > h1",
			".product-info > h1",
			"h1[itemprop=name]"			
	};
	
	private final String preco[] = {
			".sales-price",
			"#ctl00_Conteudo_ctl01_precoPorValue > i.sale",
			"span.price",
			"[itemprop=lowPrice]",
			"span.precoDescricao > strong",
			".main-price-infos > p > .price-val",
	};
	
	
	public ExtratorGlobal(File file) {
		super(file);
	}

	/**
	 * @param doc
	 * @param tipo caso tipo 1 = raizPais, tipo 2 = nomeProduto, tipo 3 = preco
	 * @return retorna o Element ideal pra aquele site ou null caso nao encontre um
	 */
	private Element escolheRaiz(Document doc, int tipo)
	{
		String texto[] = null;
		switch (tipo) {
		case 1:
			texto = this.raisPai;
			break;
		case 2:
			texto = this.nomeProduto;
			break;
		case 3:
			texto = this.preco;
			break;
		}
		for (String string : texto) {
			Element e = doc.select(string).first() ;
	
			if(e != null) 
			{
				System.out.println(string);
				return e;
			}
		}
		return null;
	}
	
	
	private String it(Element e)
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
		String saida = "";
		Element nomeProduto = this.escolheRaiz(doc, 2);
		Element precoProduto = this.escolheRaiz(doc, 3);
		Element dados = this.escolheRaiz(doc, 1);	
		
		//se elemento vazio
		if (dados == null || nomeProduto == null) throw new NullPointerException();
		
		saida += "Nome;";
		saida += nomeProduto.text();
		saida += "\n";	
		saida += "Preco;";
		saida += precoProduto.text();
		saida += "\n";
		
		//metodo principal
		saida += it(dados);
		saida += "\n\n\n";	
		super.setCsvFile(new FileWriter(new File(this.CSV_NAME), true));
		super.getCsvFile().write(saida);
		super.getCsvFile().close();
	}

}