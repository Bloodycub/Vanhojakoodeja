package Core.Support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.script.Script;

public class Filemanager extends Script{
	List<String> lines = new ArrayList<String>();
	
	
	public void WritetoFile(String name) {
			String line = "";
			String Banned = "Failed";
		File fileName = new File (getDirectoryData() + "Accfile.txt");
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter writer = new PrintWriter(bufferedWriter);
			
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(name)) {
            		String A = line.replace(line, line + " BANNED!!!");
            		lines.add(A);
				}
				System.out.println(line);
			}
            bufferedReader.close();      
            for(int i = 0; i < lines.size(); i++) {
    			writer.print(lines.get(i));
                }
			writer.flush();
			fileWriter.flush();
			fileWriter.close();
			fileReader.close();
			writer.close();
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}


	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}




}
