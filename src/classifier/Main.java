package classifier;

import java.io.File;

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
		
		String classifier_type = "bayes";
		//BatchClassifier classifier = new BatchClassifier(classifier_type);

		ClassificationHelper helper = new ClassificationHelper(classifier_type);
		String path = "/Users/ianmanor/IdeaProjects/RI-Projeto-1/classifier-data/pages/pos/americanas/21.html";
		String fileContent = HTMLtoText.htmltoString(path);
		System.out.println(fileContent);
		System.out.println(helper);
		try {
			System.out.println(helper.classify(fileContent));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}
}