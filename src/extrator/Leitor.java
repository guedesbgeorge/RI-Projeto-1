package extrator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leitor 
{
	FileReader file;
	
	public Leitor(FileReader file) 
	{
		this.file = file;
	}
	public String getTexto() 
	{
		BufferedReader  br = new BufferedReader(file);
		StringBuilder sb = new StringBuilder();
		
		try 
		{
			String line = br.readLine();
			while (line != null) 
			{
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		return sb.toString();
	}
}
