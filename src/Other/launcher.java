package Other;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class launcher {
	public static void main(String[] args) {
		System.out.println("Available arguments: \"java file path\" \"OSBot file path\" \"Accfile file path\"");
		try {
			String[] _args = new String[3];
			int i = 0;
			for (String arg : args) {
				_args[i++] = arg;
			}
			
			//Change true to randomize world from the __worlds[] list
			boolean __randomWorlds = true;
			String[] __worlds = {"301,308,316,326,335,379,380,382,383,384,393,394,397,398,399,417,418,425,426" +
					"430,431,433,434,435,436,437,438,439,440,451,452,453,454,455,456,457,458,459,469,470,471,472,473,474"
					+ "475,476,483,497,498,499,500,501,502,503,504"};
			
			String __default_script = "777Core";
			String __default_world = "454";
			String __cli_arguments = "norandoms,lowcpu";
			
			
			Random r = new Random();
			String strLine;
			if (_args[0] != null) {
				__java_location = args[0];
			}
			if (_args[1] != null) {
				__osbot_location = args[1];
			}
			
			String accfile = "accfile.txt";
			if (_args[2] != null) {
				accfile = args[2];
			}
			System.out.println(System.getProperty("user.dir"));
			FileInputStream fstream = new FileInputStream(accfile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				String random_world = __default_world;
				if (__randomWorlds) {
					random_world = __worlds[r.nextInt(__worlds.length)];
				}
				String[] split = strLine.split(":");
				String proxy_ip = split[0];
				String proxy_port = split[1];
				String bot_user = split[2];
				String bot_pass = split[3];
				//String username_simple = bot_user.split("@")[0];

				String formatted_command = String.format("\"%s\" -jar \"%s\" -proxy %s:%s -login %s:%s -bot %s:%s:0000 -allow %s -script %s:1 -world %s",
						__java_location, __osbot_location, proxy_ip, proxy_port, __osbot_user, __osbot_pass, bot_user, bot_pass, __cli_arguments, __default_script, random_world);


				System.out.println(formatted_command);
				new ProcessBuilder("cmd.exe", "/c", formatted_command).start();
				Thread.sleep(10000);
				
				
			}

			// Close the input stream
			fstream.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
