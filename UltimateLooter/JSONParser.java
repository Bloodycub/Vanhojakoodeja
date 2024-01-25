package UltimateLooter;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONParser {
	
	public HashMap<Integer, ArrayList<Integer>> parseData(String data, boolean skipMemberItems) {
		HashMap<Integer, ArrayList<Integer>> ret = new HashMap<>();
		String[] parts = data.split(":\\{");
		String skipMemb = skipMemberItems == true ? "true" : "false";
		for (int i = 1; i < parts.length; i++) {
			String[] subParts = parts[i].split(":");
			String ID = subParts[1].split(",")[0];
			String BUY = subParts[5].split(",")[0];
			String SELL = subParts[7].split(",")[0];
			
			if (subParts[3].split(",")[0].equals(skipMemb)) {
				continue;
			}
			ArrayList<Integer> values = new ArrayList<>();
			values.add(Integer.valueOf(BUY));
			values.add(Integer.valueOf(SELL));
			ret.put(Integer.valueOf(ID), values);
		}
		return ret;
	}
}
