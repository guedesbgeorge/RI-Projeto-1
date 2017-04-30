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

		BatchClassifier classifier = new BatchClassifier();
		System.out.println("done");
	}
}