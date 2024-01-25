package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class PotofFlour extends Script {
	public static Area Mill = new Area(3168, 3308, 3165, 3305);
	//public static Area Wheat = new Area(3154, 3302, 3160, 3298);
	Area Upperflour = new Area(3163, 3310, 3170, 3303).setPlane(2);
	Area Wheatbig = new Area(3152, 3308, 3165, 3289);
	Area Wheat = new Area(
		    new int[][]{{ 3161, 3291 },{ 3162, 3291 },{ 3163, 3292 },{ 3163, 3295 },{ 3163, 3296 }, { 3164, 3297 },{ 3164, 3298 }, { 3164, 3299 },{ 3162, 3302 },{ 3161, 3303 },{ 3160, 3305 },{ 3159, 3306 },{ 3157, 3307 },{ 3154, 3307 },{ 3153, 3306 },{ 3153, 3297 },
		    	{ 3155, 3295 },{ 3157, 3295 },{ 3161, 3291 }});
	Area Millbig = new Area(3160, 3312, 3173, 3301);



	
	
    @Override
    	public void onStart() throws InterruptedException {
    	log("Startup");
    	if (!(getInventory().contains("Pot") || getInventory().contains("Pot of flour"))){
    		Banking();
    		}else {
    	CheckUp();
    }
    }
	
    
    private void CheckUp() throws InterruptedException {
    	
    	if(Millbig.contains(myPlayer()) && inventory.getAmount("Pot of flour") != 14 && !inventory.contains("Grain") && inventory.contains("Pot")) {
    		log("in mill");
    		gettingFlour();
    	}else if(Upperflour.contains(myPlayer()) && !inventory.contains("Grain")) {
    		log("Out of grain claiming down");
    			ClaimDown();
    	}else if(!inventory.contains("Grain") && !inventory.contains("Pot") && inventory.contains("Pot of flour")) {
    		log("You got All Flour ");
    		BankingTakingpots();
    	}else if (Upperflour.contains(myPlayer()) && inventory.contains("Grain")) {
    		log("You got grain and milling");
    		Milling();
    	}else if ( !Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && inventory.getAmount("Pot") != 14 && !inventory.contains("Pot of flour")){
    		log("You are stuck go pick wheat");
    		BankingTakingpots();
    	}else if (!inventory.contains("Pot") && !inventory.contains("Pot of flour")) {
    		log("Dont have pots or Flour");
    		BankingTakingpots();
    	}else if (inventory.getAmount("Grain") == 14 && !Upperflour.contains(myPlayer())) {
    		log("Walking mill");
    		WalktoMill();
    	}else if (inventory.getAmount("Grain") == 14 && Upperflour.contains(myPlayer())) {
    		log("Milling");
        	Milling();
    	}else if (inventory.contains("Pot") && (inventory.getAmount("Grains") != 14) && Wheatbig.contains(myPlayer())){
    		log("Picking wheat");
        	Pickingwheat();
    	}else if (inventory.getAmount("Pot of flour") == 14){
    		log("Got flour bank it");
    		BankingTakingpots();
    	}else if(!inventory.contains("Grain") && !Millbig.contains(myPlayer()) &&!inventory.contains("Pot of flour") && inventory.getAmount("Pot") == 14) {
        		log("Walking field");
        		WalkingField();
    	}	
    	}
	


	private void WalktoMill() throws InterruptedException {
		log("Walking mill");
		walking.webWalk(Mill);
		sleep(2000);
		if(Mill.contains(myPlayer())) {
			log("Climpup");
		ClaimUp();}
	}


	private void ClaimUp() throws InterruptedException {
		objects.closest("Ladder").interact("Climb-up");
		sleep(random(3500,4000));
		objects.closest("Ladder").interact("Climb-up");
		sleep(random(2500,3000));
	}

	private void gettingFlour() throws InterruptedException {
		RS2Object Flour = getObjects().closest("Flour bin");
		log("Eptying Bin");
		if(Flour !=null && Millbig.contains(myPlayer())) {
			Flour.interact("Empty");
			sleep(random(1500,2000));	
		}
	}

	private void ClaimDown() throws InterruptedException {
		objects.closest("Ladder").interact("Climb-down");
		sleep(random(2500,3000));
		objects.closest("Ladder").interact("Climb-down");
		sleep(random(2500,3000));
		gettingFlour();
	}


	private void BankingTakingpots() throws InterruptedException {
		if(!Banks.DRAYNOR.contains(myPlayer())) {
			log("Droping items to bank");
				walking.webWalk(Banks.DRAYNOR);
		}else {
			log("Opening bank");
			bank.open();
			if(bank.isOpen()) {
				if(bank.getAmount("Pot") <=14) {
					log("Dont have pots");
					onExit();
				}else {
			sleep(random(600,1000));
			if(!inventory.isEmpty()) {
			bank.depositAll();
			sleep(random(600,1000));
			}
			bank.withdraw("Pot", 14);
			sleep(random(600,1000));
			bank.close();				
	}
			}
		}
	}
	private void WalkingField() throws InterruptedException {
			walking.webWalk(Wheat);
		sleep(1800);
	}


	private void Banking() throws InterruptedException {
		log("Droping Trash to bank");
		walking.webWalk(Banks.DRAYNOR);
		sleep(random(3000,5000));
		bank.open();
		log("Opening bank");
		if(bank.isOpen()) {
			if(bank.getAmount("Pot") <=14) {
				log("Dont have pots");
				onExit();
			}else {
		sleep(random(600,1000));
		if(!inventory.isEmpty()) {
			bank.depositAll();
			sleep(random(600,1000));
			}
		sleep(random(600,1000));
		bank.close();		
}
		}
	}
	private void Milling() throws InterruptedException {
		if(!inventory.contains("Grain")) {
			log("Out of Grain");
			ClaimDown();
		}
		while (inventory.contains("Grain")){
			RS2Object Hopper = getObjects().closest("Hopper");
			Hopper.interact("Fill");
			sleep(random(4200,4400));
			RS2Object Lever = getObjects().closest("Hopper controls");
			Lever.interact("Operate");
			sleep(random(4200,4400));
		}
	}


	private void Pickingwheat() throws InterruptedException {
			RS2Object Wheat = getObjects().closest("Wheat");
				Wheat.interact("Pick");
					sleep(random(2500,3000));
					}
	


	@Override
    public int onLoop() throws InterruptedException {
		CheckUp();

	   return random(1700) ; //The amount of time in milliseconds before the loop starts over


    }
   
    @Override
    public void onExit() throws InterruptedException {
    	log("Good Bye");
    	sleep(5000);
    	stop();

        //Code here will execute after the script ends


    }	

    @Override


    public void onPaint(Graphics2D g) {


        //This is where you will put your code for paint(s)


    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}