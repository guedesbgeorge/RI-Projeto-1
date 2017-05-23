package extrator;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class ReadFiles {

	public List<File> listFilesForFolder(File folder) {
		List<File> f = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				f.addAll(listFilesForFolder(fileEntry));
				System.out.println(fileEntry.getName());
			} else {
				f.add(fileEntry);
			}
		}
		
		return f;
	}

	public List<File> listFilesSpecificFolder(File folder) {
		List<File> f = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				f.add(fileEntry);
				//System.out.println(fileEntry.getName());
			}
		}
		
		return f;
	}


}
