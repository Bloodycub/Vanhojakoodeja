package Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Lootingbotvildernes extends Script {
private long timeBegan; private long timeRan;
Area Lootingareabig = new Area(new int[][]{{ 3126, 3523 },{ 3108, 3552 },{ 3125, 3570 },{ 3034, 3564 },{ 3027, 3543 },{ 3041, 3523 }});
Area Lootingarea = new Area(3100, 3525, 3038, 3543);
Area BankArea = new Area(3097, 3497, 3092, 3496);
Area Edgeville = new Area(new int[][]{{ 3098, 3485 },{ 3085, 3485 }, { 3082, 3499 }, { 3070, 3506 },{ 3065, 3520 }, { 3045, 3522 }, { 3031, 3542 },{ 3047, 3556 },
	        { 3070, 3561 },{ 3090, 3559 },{ 3107, 3552 },{ 3124, 3523 },{ 3131, 3512 },{ 3102, 3515 }});
int Adamantarrowrandom = random(50,80);
int Adamantarrow = 0;
int runspeed = random(20,37);
int Advalue = 70;
int dead = 0;
Area Safespot = new Area(3097, 3523, 3078, 3523);
Area Edgevillesafearea = new Area(3065, 3521, 3112, 3486);
Area Crossing = new Area(3086, 3521, 3102, 3521);




    @Override
    	public void onStart() throws InterruptedException {
    	log("Hellow");
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));
    	}
        timeBegan = System.currentTimeMillis();
    	}


    
	@Override
    public int onLoop() throws InterruptedException {
		if(myPlayer().getHealthPercent() == 0) {
    		log("Dead +1");
    		dead++;
    		sleep(3000);
    	}
		if(widgets.isVisible(475, 11, 1)) {
			log("Widget visible");
			widgets.interact(475, 11, 1, "Enter Wilderness");
			sleep(random(1500,2000));
		}
		Checkup();
	   return random(100) ; //The amount of time in milliseconds before the loop starts over
    }
  

	private void Checkup() throws InterruptedException {
		if(!tabs.isOpen(Tab.INVENTORY)) {
			log("Inv tab not open");
			tabs.open(Tab.INVENTORY);
		}
		if(!Lootingarea.contains(myPlayer()) && inventory.isEmpty()){
			log("Walking looting");
			crosstoloot();
		}
		if(myPlayer().getHealthPercent()< 95) {
			log("Eating");
			Eating();
			}
		if((myPlayer().isHitBarVisible() || myPlayer().isUnderAttack()) && !inventory.isEmpty())  {
			log("Underattack");
			Safespotting();}
		if(!Lootingarea.contains(myPlayer()) && !Edgeville.contains(myPlayer())) {
			log("Not in Edgeville area");
			Walkingbank();}
		if(inventory.isFull()) {
			log("Walking bank");
			Walkingbank();
		}else {
			if(Lootingarea.contains(myPlayer())) {
			log("Looting");
			Loot();
			}
		}
		if(Edgevillesafearea.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("Doing nothing move");
			walking.webWalk(Lootingarea.getRandomPosition());
		}
	}

	private void crosstoloot() throws InterruptedException {		// DO CROSSING PART
		if(!Crossing.contains(myPlayer())){
			walking.webWalk(Crossing.getRandomPosition());
		sleep(random(800,1200));
		}
		RS2Object Cross = getObjects().closest("Crossing"); // FIX
		if(Cross !=null && Cross.isVisible()) {
			log("Jumping over");
			Cross.interact("");
			walking.webWalk(Lootingarea.getRandomPosition());
			Loot();
		}
	}


	private void Eating() throws InterruptedException {
		log("Eating");
		if (inventory.contains("Salmon")){ 
			inventory.interact("Eat", "Salmon");	
		}else if (inventory.contains("Lobster")){ 
			inventory.interact("Eat", "Lobster");
		}else if(inventory.contains("Swordfish")){ 
			inventory.interact("Eat", "Swordfish");
		}else if(inventory.contains("Trout")){ 
			inventory.interact("Eat", "Trout");
		}else if(myPlayer().getHealthPercent() < 95) {
			Walkingbank();
		}
	}


	private void Safespotting() throws InterruptedException {   // DO CROSSING PART
		log("Walking safe spot");
		walking.webWalk(Safespot.getRandomPosition());
		sleep(random(300,500));
		RS2Object Cross = getObjects().closest("Crossing"); // FIX
		if(Cross !=null && Cross.isVisible()) {
			log("Jumping over");
			Cross.interact("");}
		if(myPlayer().getHealthPercent() < 95) {
			Eating();
		}
	}

	private void Loot() throws InterruptedException {	
    	GroundItem O = getGroundItems().closest(new String[]
    			{"Adamant arrow","Swordfish","Bronze arrow","Iron arrow","Mithril arrow","Maple shortbow","Maple longbow"
    					,"Bronze full helm","Iron full helm","Iron platebody","Iron chainbody","Green cape",
    					"Hardleather body","Iron med helm","Iron platelegs","Trout","Meat pizza",
    					"Amulet of magic","Iron full helm","Iron scimitar","Steel arrow","Bronze bolts","Pineapple pizza",	
    					"Strength potion(1)","Strength potion(2)","Strength potion(3)","Strength potion(4)","Anchovy pizza","Wizard hat",
    					"Studded body","Chaos rune","Earth rune","Water rune","Air rune","Fire rune"
    					,"Coins","Salmon","Lobster","Monk's robe top","Monk's robe","Black cape","Mind rune",
    					"Pizza","Bronze med helm","Amulet of strength","Rune platebody","Green d'hide vamb","Mithril chainbody"
    					,"Adamant scimitar","Green d'hide body","Steel sq shield","Rune scimitar","Mithril med helm","Mithril med helm","Ruby amulet"
    					,"Green d'hide chaps","Mithril scimitar","Team-8 cape","Team-9 cape","Team-10 cape","Team-11 cape","Team-12 cape","Team-13 cape",	"Team-14 cape",	"Team-15 cape",	
    				    "Team-16 cape",	"Team-17 cape",	"Team-18 cape",	"Team-19 cape",	"Team-20 cape",	"Team-21 cape",	"Team-22 cape",	
    				    "Team-23 cape",	"Team-24 cape","Team-25 cape","Team-6 cape","Team-26 cape","Team-27 cape","Team-28 cape",	
    				    "Team-29 cape","Team-30 cape","Team-31 cape","Team-32 cape","Team-33 cape","Team-7 cape","Team-5 cape",			
    				    "Team-34 cape","Team-35 cape","Team-36 cape","Team-37 cape","Team-38 cape","Team-39 cape","Team-4 cape",	
    				    "Team-40 cape","Team-41 cape","Team-42 cape","Team-43 cape","Team-44 cape","Team-45 cape","Team-3 cape",		
    				    "Team-46 cape","Team-47 cape","Team-48 cape","Team-49 cape","Team-50 cape","Team-2 cape","Team-1 cape"});
    	if(O == null) {
    		log("Chanking spot");
    		walking.webWalk(Lootingarea.getRandomPosition());
    	}
    	if(settings.getRunEnergy() <= runspeed) {
    		log("Too low energy");
    		settings.setRunning(false);
    		sleep(random(400,600));
    	}
    	if(inventory.getAmount("Adamant arrow") >= Adamantarrowrandom) {
    		log("Too much arrows");
    		Walkingbank();
    	}
    	if (O !=null  && !myPlayer().isMoving() && Lootingarea.contains(O) && !myPlayer().isUnderAttack() ) {
    		O.interact("Take");
			sleep(random(400,700));
    	}
    }
 

	private void Walkingbank() throws InterruptedException {
		if(!Banks.EDGEVILLE.contains(myPlayer())) {
			log("Walking edge bank");
		walking.webWalk(BankArea.getRandomPosition());
		sleep(random(1200,1800));}
		if(Banks.EDGEVILLE.contains(myPlayer()) && !bank.isOpen()){
		log("Opening bank");
		bank.open();
		sleep(random(1000,1650));}
		bank.depositAll();
		Adamantarrow = (int)getBank().getAmount("Adamant arrow");
		runspeed = random(20,37);
		sleep(random(300,600));
		bank.close();
		sleep(random(200,500));
		}
		


	@Override
    public void onExit() throws InterruptedException {
    	log("Good Bye");
    	Walkingbank();
    	stop();
    }	

    @Override
    public void onPaint(Graphics2D g) {
        g.setColor(Color.decode("#00ff04")); //Color
        timeRan = System.currentTimeMillis() - this.timeBegan;
        g.drawString(ft(timeRan), 450, 335);
        g.drawString("Ad arrows " + Adamantarrow, 410, 320);
        g.drawString("Value" + Adamantarrow * Advalue, 410, 305);
        g.drawString("Deads "+ dead, 410, 295);
    }
    
    public String ft(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) -
            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }
	
}