package Trash;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class QuestCooksassistant extends Script {
	public static Area Cookroom = new Area(3211,3212,3205,3215);
	public static String[] Cook1 = { "What's wrong?", "I'm always happy to help a cook in distress."};
	public static Area Seller= new Area(3216,9622,3211,9624);
	public static Area SellerS= new Area(3210,9615,3208,9618);
	public static Area SellerFULL = new Area(3219, 9626, 3207, 9613);
	public static Area Eggs = new Area(3173, 3306, 3179, 3304);
	public static Area Wheat = new Area(3154, 3302, 3160, 3298);
	public static Area Mill = new Area(3168, 3308, 3164, 3305);
	public static Area Cow = new Area(3177, 3316, 3170, 3321);
	public static String[] Cook2 = { "Actually, I know where to find this stuff."};
	public static String[] Cook3 = { "I'll get right on it."};
	Area LBSpawnarea = new Area(3222, 3236, 3240, 3219); //Spawn area








    @Override
    	public void onStart() throws InterruptedException {
    	if (quests.isComplete(Quest.COOKS_ASSISTANT)== true) {
    		log("Quest Complete");
    		onExit();
    }
    }
    
    private void CheckUp() throws InterruptedException {
    	if (quests.isComplete(Quest.COOKS_ASSISTANT)== true) {
    		log("Quest Complete");
    		onExit();
    	}if(!inventory.contains("Bowl") && !Cookroom.contains(myPlayer())){
    		log("Walking Cook room");
    		walking.webWalk(Cookroom);
    	 }else if (getDialogues().isPendingContinuation()) {
    		 log("You are in dialog");
    		 Talktocoocktostart();
    	 }else if ( getDialogues().isPendingOption()) {
    		 log("You are in dialog option");
		 Talktocoocktostart();
    	 }else if (SellerFULL.contains(myPlayer())) {
    		 log("You are in seller take bucket");
    			Gathering();
    	 }else if((!inventory.contains("Bowl")) || ((!inventory.contains("Pot")) || ((!inventory.contains("Bucket"))))) {
    			log("Start Gathering");
    			Gathering();
    	}else if(inventory.contains("Bowl") && inventory.contains("Pot") && inventory.contains("Bucket")){
    		log("Walking for resourses");
    		WalkingForRes();
    	
    	}else if (!inventory.isEmpty() && !inventory.contains("Bowl")) {
    		DropInventory();
    	}else if ( inventory.isEmpty() && (quests.isStarted(Quest.COOKS_ASSISTANT) != true) && !Cookroom.contains(myPlayer())){
    		log("Walking to start quest");
    		walking.webWalk(Cookroom);	
    	}else if ( inventory.isEmpty() && (quests.isStarted(Quest.COOKS_ASSISTANT) !=true) && Cookroom.contains(myPlayer()) && !getDialogues().isPendingContinuation()){
    		StartingCookingquest();
    		}
    	}
    	
    
    	
    	//ADD tp to LB

    	
    		
 
	


	private void Gathering() throws InterruptedException {
		GroundItem Bowl = getGroundItems().closest("Bowl");
		GroundItem Bucket = getGroundItems().closest("Bucket");
		GroundItem Pot = getGroundItems().closest("Pot");
		 if(!inventory.contains("Bowl")) {
			 if (Bowl != null) {
			 log("Taking Bowl");
			 Bowl.interact("Take");
			 sleep(random(1500,2000));
		 } }
		 else if(!inventory.contains("Pot")) {
				if( Pot != null) {
				 log("Taking pot");
				 Pot.interact("Take");
				 sleep(random(1500,2000));
			 }}
		 else if(inventory.contains("Bowl") && inventory.contains("Pot") && !inventory.contains("Bucket") && !SellerFULL.contains(myPlayer())) {
		 		objects.closest("Trapdoor").interact("Climb-down");
		 		sleep(random(2000,3000));
		 }if (SellerFULL.contains(myPlayer())) {
		 		walking.webWalk(Seller);
		 		 sleep(random(800,1000));
		 		 if(Bucket != null) {
		 		sleep(200);
				 log("Taking Bucket");
				 Bucket.interact("Take");	
				 sleep(random(1000,2000));
				 log("Walking back to staris");
				 walking.webWalk(SellerS);
				 sleep(800);
				 objects.closest("Ladder").interact("Climb-up");
				 sleep(random(1000,2000));
				 log("Getting resourses");
				 if(Cookroom.contains(myPlayer())){
				 WalkingForRes();
				 }
		 	}}}

	private void WalkingForRes() throws InterruptedException {
		if(!inventory.contains("egg")) {
			walking.webWalk(Eggs);
		sleep(11800);
		GroundItem Egg = groundItems.closest("egg");
			Egg.interact("Take");
			sleep(random(1500,2000));
		}
		else if(!inventory.contains("Grain")) {
			walking.webWalk(Wheat);
		sleep(1800);
		RS2Object Wheat = getObjects().closest("Wheat");
			Wheat.interact("Pick");
				sleep(random(2500,3000));
		}else if (inventory.contains("Grain")){
				walking.webWalk(Mill);
				sleep(800);
				objects.closest("Ladder").interact("Climb-up");
				sleep(random(2500,3000));
				objects.closest("Ladder").interact("Climb-up");
				sleep(random(2500,3000));
				RS2Object Hopper = getObjects().closest("Hopper");
				Hopper.interact("Fill");
				sleep(random(5500,8000));
				RS2Object Lever = getObjects().closest("Hopper controls");
				Lever.interact("Operate");
				sleep(random(5500,8000));
				objects.closest("Ladder").interact("Climb-down");
				sleep(random(2500,3000));
				objects.closest("Ladder").interact("Climb-down");
				sleep(random(2500,3000));
				RS2Object Flour = getObjects().closest("Flour bin");
				Flour.interact("Empty");
				sleep(random(2500,3000));
				walking.webWalk(Cow);
				sleep(random(2500,3000));
				RS2Object Cow = getObjects().closest("Dairy cow");
				Cow.interact("Milk");
				sleep(random(7500,9000));
				walking.webWalk(Cookroom);
				sleep(random(2500,3000));
				Completecookingquest();
		}
	
	}

	private void Completecookingquest() throws InterruptedException {
		if(!Cookroom.contains(myPlayer())) {
			log("Walking to coock");
			walking.webWalk(Cookroom.getRandomPosition());}
			else {
			log("Starting quest");
			 NPC Coock = getNpcs().closest("Cook");
		        Coock.interact("Talk-to");
		        sleep(random(1000,1652));
		        log("Going to dialog");
		        if (getDialogues().isPendingContinuation()) {
		        	log("Continue");
					getDialogues().clickContinue();
					sleep(Script.random(800, 1200));
						onStart();
			}		
		}
   	
	}

	private void DropInventory() throws InterruptedException {
		log("Droping items to bank");
		walking.webWalk(Banks.LUMBRIDGE_UPPER);
		log("Opening bank");
		bank.open();
		sleep(random(600,1000));
		bank.depositAll();
		sleep(random(600,1000));
		bank.close();
		StartingCookingquest();
		
		}



	private void StartingCookingquest() throws InterruptedException {
		if(!Cookroom.contains(myPlayer())) {
		log("Walking to coock");
		walking.webWalk(Cookroom.getRandomPosition());}
		else {
		log("Starting quest");
		sleep(1200);
		 NPC Coock = getNpcs().closest("Cook");
		 	if(Coock != null) {
	        Coock.interact("Talk-to");
	        sleep(random(3000,4652));
	        log("Going to dialog");
	        Talktocoocktostart();
	        }
	}
	}

	private void Talktocoocktostart() throws InterruptedException {
		if(Cookroom.contains(myPlayer())) {
		 log("Pending");
	        if (getDialogues().isPendingContinuation()) {
	        	log("Continue");
				getDialogues().clickContinue();
				sleep(Script.random(800, 1200));
	        	} else if(getDialogues().completeDialogue(Cook1)) {
		log("Chosing dialog for Continue 1");
		
		sleep(Script.random(800, 1200));
	        }else if(getDialogues().completeDialogue(Cook2)){
			log("Chosing dialog for Continue 2");
			sleep(Script.random(800, 1200));
	        }else if(getDialogues().completeDialogue(Cook3)){
				log("Chosing dialog for Continue 3");
				sleep(Script.random(800, 1200));
		}		
	}
		}

	@Override
    public int onLoop() throws InterruptedException {
		CheckUp();

	   return random(2000) ; //The amount of time in milliseconds before the loop starts over


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