package Copy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class asd {

	public static void main(String[] args) {  
		 List<String> lines = new ArrayList<String>();
		    String line = null;
		    
	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	            		String A = line.replace(line, line + " BANNED!!!");
	            		System.out.println(A);
	            		lines.add(A);
	            	}
	                System.out.println(line);
	            }   

	            // Always close files.
	            bufferedReader.close();      
	            
	            
	            FileWriter fileWriter =
	                    new FileWriter(fileName);

	                // Always wrap FileWriter in BufferedWriter.
	                BufferedWriter bufferedWriter =
	                    new BufferedWriter(fileWriter);

	                // Note that write() does not automatically
	                // append a newline character.
	                for(int i = 0; i < lines.size(); i++) {
	                bufferedWriter.write(lines.get(i));
	                }

	                // Always close files.
	                bufferedWriter.close();
	            }
	            catch(IOException ex) {
	                System.out.println(
	                    "Error writing to file '"
	                    + fileName + "'");
	                // Or we could just do this:
	                // ex.printStackTrace();
	            }
	        }
	    }
	        
	        


