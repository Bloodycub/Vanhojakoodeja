package UltimateLooter;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public final class GrandExchangeFunctions extends Script {

	private HashMap<Integer, ArrayList<Integer>> database;
	
	private final boolean skipMemberItems = true;

	@Override
	public void onStart() {
		String FILE_URL = "https://rsbuddy.com/exchange/summary.json";
		String FILE_NAME = getDirectoryData() + "database.txt";
		downloadDatabase(FILE_URL, FILE_NAME);
		getDatabase(FILE_NAME);
	}

	public int getPrice(int id) {
		try {
			String sid = Integer.toString(id);
			ArrayList<Integer> item = database.get(id);
			int b = item.get(0);
			int s = item.get(1);
			return b > s ? b : s;
		} catch (java.lang.RuntimeException e) {
			return 0;
		}

	}

	private void downloadDatabase(String FILE_URL, String FILE_NAME) {
		File file = new File(FILE_NAME);
		if (file.lastModified() + 100000000 > System.currentTimeMillis()) {
			log("file needs no updatin");
			return;
		}
		
		try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
		} catch (IOException e) {
			log(e);
			// handle exception
		}
	}

	private void getDatabase(String filePath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			log(e);
		}
		JSONParser parser = new JSONParser();
		database = parser.parseData(content, skipMemberItems);

	}

	@Override
	public int onLoop() {
		log("working");
		return 1000;
	}
}