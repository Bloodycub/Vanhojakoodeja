package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;


public class Karamajafishpicker extends Script {
Area Island = new Area(2882, 3198, 2966, 3128);
Area Mageshop = new Area(3011, 3261, 3016, 3256);
Area Ship = new Area(3026, 3220, 3029, 3214);
Area Dock = new Area(3020, 3227, 3036, 3209);
Area Insideship = new Area(2949, 3145, 2971, 3138).setPlane(1);
Area Bridge = new Area(2923, 3180, 2926, 3174);
public static String[] Fish = new String[] {"Raw lobster", "Raw tuna", "Tuna", "Lobster","Raw swordfish", "Swordfish"};
Area Seller = new Area(2903, 3150, 2908, 3146);
Area Shop = new Area(2900, 3153, 2911, 3143);
Area Bridgebigarea = new Area(2921, 3181, 2928, 3173);






	

    @Override
    	public void onStart() throws InterruptedException {
    	log("Start");
    	CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	
    	if(!inventory.isEmpty() && !Insideship.contains(myPlayer()) && !Island.contains(myPlayer()) && !Mageshop.contains(myPlayer()) && inventory.getAmount("Coins") < 30) {
    		log("Banking");
    		Banking();
    	}else if (Bridgebigarea.contains(myPlayer()) && !inventory.isFull()){
    		Looting();
    	}else if (Shop.contains(myPlayer()) && inventory.isEmptyExcept("Coins")) {
    		log("Sold Fish");
    		WalkBridge();
    	}else if(Bridge.contains(myPlayer()) && inventory.isFull()) {
    		log("You are full sell fish");
    		walktoseller();
    	}else if(Shop.contains(myPlayer()) && inventory.isFull()) {
    		log("selling fish");
    		sellfish();
    	}else if(Island.contains(myPlayer()) && inventory.isEmptyExcept("Coins") && !Bridgebigarea.contains(myPlayer()) && !Shop.contains(myPlayer())){
    		log("Getting fish");
    		WalkBridge();
    	}else if(Bridge.contains(myPlayer()) && inventory.isEmptyExcept("Coins")){
    		log("Looting fish");
    		Looting();
    	}else if(!Mageshop.contains(myPlayer()) && inventory.contains("Water rune")){	
    		log("Walking mage shop");
    		Walkingmageshop();
    	}else if(Mageshop.contains(myPlayer()) && inventory.contains("Water rune")){
    		log("Sell runes");
    		Sellingrunes();
    	}else if (Ship.contains(myPlayer())) {
    		log("Talk to owner");
    		TalktoOwner();
    	}else if(!Island.contains(myPlayer()) && !Bridgebigarea.contains(myPlayer()) && !Shop.contains(myPlayer()) && inventory.getAmount("Coins") >= 30 && !inventory.contains("Water rune") && !Dock.contains(myPlayer())) {
    		log("Walking dock");
    		WalktoDock();
    	}else if (Insideship.contains(myPlayer())){	
    		log("Steping out of ship");
    		stepoutofship();
    	
    	
    	
    	
    	
    	}else {stop();}
    	    
    		}
    		
    	private void Worldchange() throws InterruptedException {
    		log("Hopping");
    		worlds.hopToF2PWorld();
    		sleep(random(6000,9000));
		
	}


		private void walktoseller() throws InterruptedException {
    		walking.webWalk(Seller);
    		sleep(1000);
	}


		private void sellfish() throws InterruptedException {
			NPC Shopkeeper = getNpcs().closest("Shop keeper");
				Shopkeeper.interact("Trade");
				sleep(500);
				while (inventory.contains(Fish) && !inventory.isEmptyExcept("Coins")) {
				inventory.interact("Sell 50", Fish);
				sleep(random(500,800));
				}		
	}


		private void Looting() throws InterruptedException {
    		GroundItem Fishs = groundItems.closestThatContains(Fish);
			if (Fishs !=null){
				log("Taking fish");
				Fishs.interact("Take");
				sleep(random (100,300));
    		}else if(Fishs == null) {
    			log("Hopping world");
    			Worldchange();
    		}
    		
    		
    				
	}


		private void WalkBridge() throws InterruptedException {
    		walking.webWalk(Bridge);
    			sleep(1000);		
	}


		private void WalktoDock() throws InterruptedException {
    		log("Got coins lets go");
    		walking.webWalk(Ship);
    		sleep(1000);		
	}


		private void Sellingrunes() throws InterruptedException  {
		NPC Mage = getNpcs().closest("Betty");
			log("Interacting with mage");
				Mage.interact("Trade");
				sleep(random(500,1500));
				log("Selling runes");
				inventory.interact("Sell 50", "Water rune");
				WalktoShip();
				
	}


		private void WalktoShip() throws InterruptedException {
			walking.webWalk(Ship);
			sleep(1000);
			TalktoOwner();
		}


		private void TalktoOwner() throws InterruptedException {
			NPC Owner = getNpcs().closest("Seaman lorris");
			log("Paying fare");
			Owner.interact("Pay-fare");
			sleep(3000);
			getDialogues().clickContinue();
			sleep(1000);
			getDialogues().completeDialogue("Yes please.");
			sleep(10000);
			stepoutofship();

		}


		private void stepoutofship() throws InterruptedException {
			RS2Object Plank = getObjects().closest("Gangplank");
				Plank.interact("Cross");
				sleep(3000);
		}


		private void Walkingmageshop() throws InterruptedException {
    		log("Walking mage shop");
    		walking.webWalk(Mageshop.getRandomPosition());
    		sleep(1000);		
	}


		private void Banking() throws InterruptedException {
    		log("Walking bank");
    		walking.webWalk(Banks.LUMBRIDGE_UPPER);
    			sleep(1000);
    	    	if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {	
    	    		bank.open();
    	    		log("Bank opend");
    	    		sleep(random(500,1000));
    	    		bank.depositAll();
    	    		sleep(random(500,1000));
    	    		bank.withdrawAll("Water rune");
    	    		sleep(random(500,1000));
    	    		bank.withdrawAll("Coins");
    	    			bank.close();
    				
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


        //This is where you will put your code for paint(s)


    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}