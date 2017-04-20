package classifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.apache.commons.io.IOUtils;

public class HTMLtoText {
	private String htmltoString(String path){
		String output = null;
		
		try {
			FileInputStream html = new FileInputStream(path);
			String htmlstr = IOUtils.toString(html, "UTF-8");
			output = Jsoup.parse(htmlstr).text();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
	
	public void toText(final File folder, String newPath) {
		File dir = new File(newPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            toText(fileEntry, newPath);
	        } else if(!fileEntry.getName().equals(".DS_Store")) {
	            PrintWriter out = null;
				try {
					String filename = fileEntry.getName().replaceFirst("[.][^.]+$", "");
					String dirName = fileEntry.getParentFile().getName();
					String newFile = newPath + filename + "_" + dirName + ".txt";					
					out = new PrintWriter(newFile);
					out.println(htmltoString(fileEntry.getAbsolutePath()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					out.close();
				}   
	        }
	    }
	}
}
