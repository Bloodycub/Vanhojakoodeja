package Notredy;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.ArrayList;

public class Muiling extends Script {
	Area LBChest = new Area(3221, 3217, 3221, 3217); // Lumbgre
	String[] Items = {"Adamant arrow","Swordfish","Bronze arrow","Iron arrow","Mithril arrow","Maple shortbow","Maple longbow"
					,"Bronze full helm","Iron full helm","Iron platebody","Iron chainbody","Green cape",
					"Hardleather body","Iron med helm","Iron platelegs","Trout","Meat pizza",
					"Amulet of magic","Iron full helm","Iron scimitar","Steel arrow","Bronze bolts","Pineapple pizza",	
					"Strength potion(1)","Strength potion(2)","Strength potion(3)","Strength potion(4)","Anchovy pizza","Wizard hat",
					"Studded body","Chaos rune","Earth rune","Water rune","Air rune","Fire rune"
					,"Coins","Salmon","Lobster","Monk's robe top","Monk's robe","Black cape","Mind rune",
					"Pizza","Bronze med helm","Amulet of strength","Rune platebody","Green d'hide vamb","Mithril chainbody"
					,"Adamant scimitar","Green d'hide body","Steel sq shield","Rune scimitar","Mithril med helm","Mithril med helm","Ruby amulet"
					,"Green d'hide chaps","Mithril scimitar","Team-8 cape","Team-9 cape","Team-10 cape","Team-11 cape","Team-12 cape","Team-13 cape","Team-14 cape","Team-15 cape",	
				    "Team-16 cape",	"Team-17 cape",	"Team-18 cape",	"Team-19 cape",	"Team-20 cape",	"Team-21 cape",	"Team-22 cape",	
				    "Team-23 cape",	"Team-24 cape","Team-25 cape","Team-6 cape","Team-26 cape","Team-27 cape","Team-28 cape",	
				    "Team-29 cape","Team-30 cape","Team-31 cape","Team-32 cape","Team-33 cape","Team-7 cape","Team-5 cape",			
				    "Team-34 cape","Team-35 cape","Team-36 cape","Team-37 cape","Team-38 cape","Team-39 cape","Team-4 cape",	
				    "Team-40 cape","Team-41 cape","Team-42 cape","Team-43 cape","Team-44 cape","Team-45 cape","Team-3 cape",		
				    "Team-46 cape","Team-47 cape","Team-48 cape","Team-49 cape","Team-50 cape","Team-2 cape","Team-1 cape"};

	@Override
	public void onStart() throws InterruptedException {
		log("Starting");
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("");
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		Checkup();

		return random(1500); // The amount of time in milliseconds before the loop starts over

	}

	private void Checkup() throws InterruptedException {
		if (!LBChest.contains(myPlayer())) {
			log("Walking to chest");
			Walktotradespot();
		} else if (LBChest.contains(myPlayer()) && inventory.isEmpty()) {
			log("Getting bank to inventory");
			Gettingbank();
		} else if (!inventory.isEmpty()) {
			log("Trading mule");
			Trading();
		}

	}

	private void Gettingbank() throws InterruptedException {
		bank.open();
		sleep(random(1000, 1800));
		if (bank.isOpen()) {
			bank.depositAll();
			sleep(random(300, 600));

		}
	}

	private void Walktotradespot() throws InterruptedException {
		walking.webWalk(LBChest);
		sleep(random(500, 1500));
		log("We are at spot");
	}

	private void Trading() {

	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {

		// This is where you will put your code for paint(s)

	}

}