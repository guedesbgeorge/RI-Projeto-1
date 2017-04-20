package extrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Extrator {
	private File file;
	private FileWriter csvFile;

	public Extrator(File file) {
		this.file = file;

	}

	abstract public void extrair() throws IOException;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileWriter getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(FileWriter csvFile) {
		this.csvFile = csvFile;
	}
}
