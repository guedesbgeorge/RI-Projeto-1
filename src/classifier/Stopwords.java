package classifier;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

import weka.core.stopwords.StopwordsHandler;

public class Stopwords implements StopwordsHandler {
	HashSet<String> stopwords;
	FileReader file;
	
	public Stopwords(String path){
		this.stopwords = new HashSet<String>();
		try{
			this.file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
            String line;
            while((line = reader.readLine()) != null){
                stopwords.add(line);
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
	}
	
	@Override
	public boolean isStopword(String word) {
		return stopwords.contains(word);
	}
}
