package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.ArrayList;

public class Muiling extends Script {
	Area LBChest = new Area(3221,3217, 3221,3217); //Lumbgre
	String [] Items2 = {,,"Iron arrow",","Leather boots","Maple shortbow","Maple longbow"
					,,"","Iron platebody","Coif","Iron chainbody","Green cape","Leather chaps",
					"Hardleather body","Iron med helm","Iron platelegs","Leather gloves","Trout","Meat pizza",
					"Amulet of magic","Iron full helm","Iron scimitar","Steel arrow","Bronze bolts","Pineapple pizza",	
					"Strength potion(1)","Strength potion(2)","Strength potion(3)","Strength potion(4)","Anchovy pizza",
					
					,,,,,,
					,,,,",,,,
					,,,,
					,,,"Steel sq shield","Rune scimitar","Mithril med helm","Mithril med helm",
					,"Green d'hide chaps","Mithril scimitar","Team-8 cape","Team-9 cape","Team-10 cape","Team-11 cape","Team-12 cape","Team-13 cape",	"Team-14 cape",	"Team-15 cape",	
				  ,	"Team-17 cape",	"Team-18 cape",	"Team-19 cape",	"Team-20 cape",	,	
				    "Team-23 cape",	"Team-24 cape","Team-25 cape","Team-6 cape","Team-26 cape",,,	
				    "Team-29 cape","Team-30 cape","Team-31 cape","Team-32 cape","Team-33 cape",,		
				    ,,,,,,	
				    ,,,,,,		
				    ,,
	ArrayList<String> Items = new ArrayList<String> (10);
	Items.add()
	
    @Override
    	public void onStart() throws InterruptedException {
		Items.add("Adamant arrow");	Items.add("Bronze arrow");	Items.add("Bronze arrow");	Items.add("Mithril arrow");	Items.add("Team-22 cape");
		Items.add("Bronze full helm");	Items.add("Iron full helm");	Items.add("Studded body");	Items.add("Team-1 cape");
		Items.add("Team-16 cape");	Items.add("Team-35 cape");	Items.add("Team-49 cape");	Items.add("Team-2 cape");	Items.add("Team-3 cape");
		Items.add("Team-50 cape");	Items.add("Team-48 cape");	Items.add("Iron full helm");	Items.add("Team-45 cape");	
		Items.add("Iron dagger");	Items.add("Adamant scimitar");	Items.add("Team-47 cape");	Items.add("Team-44 cape");	Items.add("Team-4 cape");	
		Items.add("Team-43 cape");	Items.add("Wizard hat");	Items.add("Chaos rune");	Items.add("Iron dagger");	Items.add("Team-5 cape");
		Items.add("Team-40 cape");	Items.add("Team-41 cape");	Items.add("Team-34 cape");	Items.add("Team-39 cape");	Items.add("Team-7 cape");
		Items.add("Team-34 cape");	Items.add("Team-42 cape");	Items.add("Team-7 cape");	Items.add("Team-28 cape");
		Items.add("Team-38 cape");	Items.add("Team-37 cape");	Items.add("Team-36 cape");	Items.add("Team-46 cape");	
		Items.add("Earth rune");	Items.add("Water rune");	Items.add("Swordfish");		Items.add("Monk's robe");	Items.add("Mithril chainbody");		
		Items.add("Coins");	Items.add("Air rune");	Items.add("Fire rune");	Items.add("Salmon");	Items.add("Mind rune");
		Items.add("Black cape");	Items.add("Monk's robe top");	Items.add("Pizza");	Items.add("Bronze med helm");	Items.add("Team-27 cape");
		Items.add("Lobster");	Items.add("Bronze med helm");	Items.add("Rune platebody");	Items.add("Team-21 cape");
		Items.add("Amulet of strength");	Items.add("Green d'hide vamb");	Items.add("Green d'hide body");	Items.add("Ruby amulet");
		log("Starting");
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));
    	}
    }

@Override
    public int onLoop() throws InterruptedException {
		Checkup();
		

	   return random(1500) ; //The amount of time in milliseconds before the loop starts over


    }
   
    private void Checkup() throws InterruptedException {
    	if(!LBChest.contains(myPlayer())) {
    		log("Walking to chest");
    		Walktotradespot();
    	}else if(LBChest.contains(myPlayer()) && inventory.isEmpty()) {
    		log("Getting bank to inventory");
    		Gettingbank();
    	}else if(!inventory.isEmpty()) {
    		log("Trading mule");
    		Trading();
    	}
    	
		
}

	private void Gettingbank() throws InterruptedException {
		bank.open();
		sleep(random(1000,1800));
		if(bank.isOpen()) {
			bank.depositAll();
			sleep(random(300,600));
			
		}
	}

	private void Walktotradespot() throws InterruptedException {
    	walking.webWalk(LBChest);
    	sleep(random(500,1500));
    	log("We are at spot");
}

	private void Trading() {
		
	
}

	@Override
    public void onExit() {


        //Code here will execute after the script ends


    }	

    @Override


    public void onPaint(Graphics2D g) {


        //This is where you will put your code for paint(s)


    }


}