package Onshelf;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.Sleep;
import Core.Support.Time;
import Support.*;

import java.awt.*;

public class BucketPickerandfiller extends Script {
	Areas areas = new Areas();
	Time time = new Time();

    @Override
    	public void onStart() throws InterruptedException {
    	log("Startup");
    	time.Starttime();
    	if (myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating() && myPlayer().isOnScreen()) {
			while(settings.areRoofsEnabled() == false) {
				getKeyboard().typeString("::toggleroofs");
				Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
			}
			}
    	if (!(getInventory().contains("Bucket") || getInventory().contains("Pot of flour"))){
    		Dropeveryhing();
    	}else {
    	CheckUp();
    		}
    }
	
    
    private void CheckUp() throws InterruptedException {
    	if(Areas.SellerFULL.contains(myPlayer()) && inventory.isEmpty()) {
    		log("Empty and in Seller");
    		Walkbuget();
    	}if(Areas.Seller.contains(myPlayer()) && inventory.isEmptyExcept("Bucket")){
    		log("Looting Buget");
    		Pickbuget();
    	}
    	Fillbuget();
    	Bank();
    	Dropeveryhing();   		
    	}

	private void Walkbuget() throws InterruptedException {
		if(!Areas.Cookroom.contains(myPlayer())) {
		walking.webWalk(Areas.Cookroom);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,3000);
		}
	if(Areas.Cookroom.contains(myPlayer())) {
		objects.closest("Trapdoor").interact("Climb-down");
 		Sleep.waitCondition(() -> Areas.SellerFULL.contains(myPlayer()), 500,5000);
	}if (Areas.SellerFULL.contains(myPlayer())) {
		if(!Areas.Seller.contains(myPlayer())) {
 		walking.webWalk(Areas.Seller);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,3000);
		}
 	}
 	}


	private void Dropeveryhing() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,3000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			bank.depositAll();
			Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
			
			bank.close();
		}
	}
	


	private void Bank() throws InterruptedException {
		if(!Areas.Cookroom.contains(myPlayer()) && Areas.SellerFULL.contains(myPlayer())){
		if(inventory.contains("Bucket of water") && inventory.isFull()){
			 walking.webWalk(areas.SellerS);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,3000);
			 objects.closest("Ladder").interact("Climb-up");
				Sleep.waitCondition(() -> Areas.Cookroom.contains(myPlayer()), 500,3000);
			 }
		}	
	}

	private void Fillbuget() throws InterruptedException {
		RS2Object Sink = getObjects().closest("Sink");
		if(!Areas.WaterspotLbSeller.contains(myPlayer())){	
		walking.webWalk(areas.WaterspotLbSeller);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,3000);}
		if(inventory.contains("Bucket")) {
			inventory.interact("Use","Bucket");
			Sleep.waitCondition(() -> getInventory().isItemSelected(),3000);
			Sink.interact("Use");
			Sleep.waitCondition(() -> inventory.contains("Bucket"), 500,30000);		
		}
	}

	private void Pickbuget() throws InterruptedException {
		GroundItem Bucket = getGroundItems().closest("Bucket");
	if (!inventory.isFull() && Areas.Seller.contains(myPlayer())) {
		 if(Bucket != null) {
		 log("Taking Bucket");
		 Bucket.interact("Take");	
		 sleep(random(1500,2000));
		 log("Walking back to staris");
		 }else if (Bucket == null){
			 worlds.hopToF2PWorld();
			 sleep(random(6000,8000));
		 	}
		}
	}
	


	@Override
    public int onLoop() throws InterruptedException {
		time.Timerun();
		CheckUp();
	   return random(1000) ; //The amount of time in milliseconds before the loop starts over
    }
   
    @Override
    public void onExit() {
    	log("Good Bye");
    	stop();
    }	

    @Override
    public void onPaint(Graphics2D g) {
    		g.setColor(Color.decode("#00ff04")); // Color
    		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);
    	}
    }
