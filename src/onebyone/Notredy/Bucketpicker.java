package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Bucketpicker extends Script {
	public static Area Seller= new Area(3216,9624,3215,9624); //bugets
	public static Area SellerS= new Area(3210,9615,3208,9618);
	public static Area SellerFULL = new Area(3219, 9626, 3207, 9613);
	public static Area Cookroom = new Area(3205, 3217, 3212, 3212);
	Area Waterspot = new Area(3212,9624, 3212,9624);

    @Override
    	public void onStart() throws InterruptedException {
    	log("Startup");
    	if (!(getInventory().contains("Bucket") || getInventory().contains("Pot of flour"))){
    		Dropeveryhing();
    		}else {
    	CheckUp();
    		}
    }
	
    
    private void CheckUp() throws InterruptedException {
    	if(SellerFULL.contains(myPlayer()) && inventory.isEmpty()) {
    		log("Empty and in Seller");
    		Walkbuget();
    	}if(Seller.contains(myPlayer()) && inventory.isEmptyExcept("Bucket")){
    		log("Looting Buget");
    		Pickbuget();
    	}
    	
    	
    	Fillbuget();
    	Bank();
    	Dropeveryhing();
    	
    		
    	}
	


	private void Walkbuget() throws InterruptedException {
		walking.webWalk(Cookroom);
		sleep(1500);
	if(Cookroom.contains(myPlayer())) {
		objects.closest("Trapdoor").interact("Climb-down");
 		sleep(random(5000,6000));
	}if (SellerFULL.contains(myPlayer())) {
 		walking.webWalk(Seller);
 		 sleep(random(1500,2500));
	
 	}
 	}


	private void Dropeveryhing() throws InterruptedException {
		log("Walking Bank");
		walking.webWalk(Banks.LUMBRIDGE_UPPER);	
		sleep(1500);
		log("Opening bank");
		bank.open();
		sleep(random(600,1000));
		bank.depositAll();
		sleep(random(600,1000));
		bank.close();
	}


	private void Bank() throws InterruptedException {
		if(!Cookroom.contains(myPlayer()) && SellerFULL.contains(myPlayer())){
		if(inventory.contains("Bucket of water") && inventory.isFull()){
			 walking.webWalk(SellerS);
			 sleep(800);
			 objects.closest("Ladder").interact("Climb-up");
			 sleep(random(1000,2000));}}	
	}


	private void Fillbuget() throws InterruptedException {
		RS2Object Sink = getObjects().closest("Sink");
		walking.webWalk(Waterspot);
		sleep(random(1000,1500));
		while(inventory.contains("Bucket")) {
			inventory.interact("Use","Bucket");
			sleep(300);
			Sink.interact("Use");
			sleep(random(1000,1200));
			
			
		}
		
	}


	private void Pickbuget() throws InterruptedException {
		GroundItem Bucket = getGroundItems().closest("Bucket");
	while (!inventory.isFull() && Seller.contains(myPlayer())) {
		 if(Bucket != null) {
		sleep(200);
		 log("Taking Bucket");
		 Bucket.interact("Take");	
		 sleep(random(1000,2000));
		 log("Walking back to staris");
		 }else if (Bucket == null){
			 worlds.hopToF2PWorld();
			 sleep(random(6000,8000));
		 }}}
	


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