package classifier;

import javax.swing.text.html.HTML;
import java.io.File;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		HTMLtoText htt = new HTMLtoText();
		
		final String pos = "classifier-data/pages/pos/";
		final String neg = "classifier-data/pages/neg/";
		
		final String postxt = "classifier-data/pages-txt/pos/";
		final String negtxt = "classifier-data/pages-txt/neg/";

		File pos_folder = new File(pos);
		htt.toText(pos_folder, postxt);
		
		File neg_folder = new File(neg);
		htt.toText(neg_folder, negtxt);
		
		String classifier_type = "tree";
		ClassificationHelper helper = new ClassificationHelper(classifier_type);

		try {
			int posCorrect = 0;
			for(File dir : pos_folder.listFiles()) {
				for(File htmlFile : dir.listFiles()) {
					String fileContent = HTMLtoText.htmltoString(htmlFile.getPath());
					if(helper.classify(fileContent)) {
						posCorrect++;
					}
				}
			}

			int negCorrect = 0;
			for(File dir : neg_folder.listFiles()) {
				for(File htmlFile : dir.listFiles()) {
					String fileContent = HTMLtoText.htmltoString(htmlFile.getPath());
					if(!helper.classify(fileContent)) {
						negCorrect++;
					}
				}
			}

			System.out.println("PosCorrect: " + posCorrect);
			System.out.println("NegCorrect: " + negCorrect);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}