package util;

import java.net.URL;
import java.util.Scanner;

public class Readingfromweb {

	public String returnstring() {
		try {
			// Instantiating the URL class
			URL url = new URL(
					"https://api.genr8rs.com/Content/OldSchoolRunescape/NameGenerator?genr8rsUserId=1586090033.39645e89d03160c89&_sAccountType=standard");
			// Retrieving the contents of the specified page
			Scanner sc = new Scanner(url.openStream());
			// Instantiating the StringBuffer class to hold the result
			StringBuffer sb = new StringBuffer();
			while (sc.hasNext()) {
				sb.append(sc.next());
				// System.out.println(sc.next());
			}
			// Retrieving the String from the String Buffer object
			String result = sb.toString();
			System.out.println(result);
			// Removing the HTML tags
			result = result.replaceAll("<[^>]*>_:", "");
			String vanha = result;
			int pituus = vanha.length() - 2;
			CharSequence uusi = vanha.subSequence(13, pituus);
			return (String) uusi;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
