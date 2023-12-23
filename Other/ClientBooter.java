package Other;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class ClientBooter {
	public static void main(String[] args) {
		try {
			System.out.println(System.getProperty("user.dir"));
			FileInputStream fstream = new FileInputStream("accfile.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			//Change true to randomize world from the __worlds[] list
			boolean __randomWorlds = false;
			String[] __worlds = {"123","234"};

			String __java_location = "C:\\Program Files\\Java\\jre1.8.0_241\\bin\\java.exe";

			String __default_script = "777Core";
			String __default_world = "454";
			String __cli_arguments = "norandoms,lowcpu";
			
			
			Random r = new Random();
			String strLine;
			
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
				Process p = new ProcessBuilder("cmd.exe", "/c", formatted_command).start();
				Thread.sleep(3000);
				
				
			}

			// Close the input stream
			fstream.close();
			
		} catch (Exception e) {
			System.out.println("Vituiks meni");
			System.out.println(e);
		}
	}

}

//Runtime.getRuntime().exec("cmd /c \"start somefile.bat && start other.bat && cd C:\\test && test.exe\"");

