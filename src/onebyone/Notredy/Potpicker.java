package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Potpicker extends Script {
public static Area Cookroom = new Area(3211,3212,3205,3215);
	int Inventoryspace;

// add pending request
	

    @Override
    	public void onStart() throws InterruptedException {
    	log("Startup");
    	Bankeverything();
    	CheckUp();
    }
  

	private void CheckUp() throws InterruptedException {
    	if (Cookroom.contains(myPlayer()) && inventory.isEmptyExcept("Pot") && Inventoryspace != 0){
    		log("looting pot");
    		Lootingpot();
    	}else if(Cookroom.contains(myPlayer()) && Inventoryspace == 0) {
    		log("Banking");
    		Bankeverything();
    	}else if(!Cookroom.contains(myPlayer()) && inventory.isEmptyExcept("Pot")) {
    		log("Walking coockroom");
    		Walkingpot();
    	}else if(Cookroom.contains(myPlayer()) && !inventory.isFull()) {
    		log("Hopping");
    		Worldhop();
    	
		}
	}

	private void Worldhop() throws InterruptedException {
		worlds.hopToF2PWorld();
		sleep(random(6012,9033));		
	}


	private void Walkingpot() throws InterruptedException {
		walking.webWalk(Cookroom);
		sleep(1000);
		
	}


	private void Lootingpot() throws InterruptedException {
		GroundItem Pot = getGroundItems().closest("Pot");
		if(Pot == null && inventory.isEmptyExcept("Pot")) {
			log("Pot Null");
			Worldhop();
		}
				if( Pot != null) {
				 log("Taking pot");
				 Pot.interact("Take");
				 sleep(2000);
				 }
	}

 private void Bankeverything() throws InterruptedException {
			log("Walking bank");
	    	walking.webWalk(Banks.LUMBRIDGE_UPPER);
	    	sleep(1000);
	    	if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {	
	    		bank.open();
	    		log("Bank opend");
	    		sleep(random(500,1000));
	    		bank.depositAll();
	    		sleep(random(500,1000));
	    			bank.close();
	    	}else {
	    		CheckUp();
	    	}
	}

	@Override
    public int onLoop() throws InterruptedException {
		CheckUp();

	   return random(1700) ; //The amount of time in milliseconds before the loop starts over


    }
   
    @Override
    public void onExit() {
    	log("Good Bye");
    	stop();

        //Code here will execute after the script ends


    }	

    @Override


    public void onPaint(Graphics2D g) {
    	 g.drawString("Inventory size left  " + Inventoryspace, 5, 330);
         g.setColor(Color.decode("#00ff04")); //Color
    	 Inventoryspace = getInventory().getEmptySlots();
        //This is where you will put your code for paint(s)


    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}