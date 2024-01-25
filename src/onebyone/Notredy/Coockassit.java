package onebyone.Notredy;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Coockassit extends Script {
	public static Area Cookroom = new Area(3205, 3217, 3212, 3212);
	public static Area Seller= new Area(3216,9624,3215,9624);
	public static Area SellerS= new Area(3210,9615,3208,9618);
	public static Area SellerFULL = new Area(3219, 9626, 3207, 9613);
	public static Area Eggs = new Area(3173, 3306, 3179, 3304);
	Area Wheat = new Area(
		    new int[][]{{ 3161, 3291 },{ 3162, 3291 },{ 3163, 3292 },{ 3163, 3295 },{ 3163, 3296 }, { 3164, 3297 },{ 3164, 3298 }, { 3164, 3299 },{ 3162, 3302 },{ 3161, 3303 },{ 3160, 3305 },{ 3159, 3306 },{ 3157, 3307 },{ 3154, 3307 },{ 3153, 3306 },{ 3153, 3297 },
		    	{ 3155, 3295 },{ 3157, 3295 },{ 3161, 3291 }});
	public static Area Mill = new Area(3168, 3308, 3165, 3305);
	public static Area Cow = new Area(3173,3318, 3173,3318);
	public static String[] Cook1 = { "What's wrong?", "I'm always happy to help a cook in distress."};
	public static String[] Cook2 = { "Actually, I know where to find this stuff."};
	public static String[] Cook3 = { "I'll get right on it."};
	Area LB = new Area(3268, 3190, 3132, 3317); //Lumbgre
	Area Pickarea = new Area(3146, 3325, 3198, 3275);
	Area Chickenspit = new Area( new int[][]{{ 3173, 3308 },{ 3180, 3308 },{ 3180, 3304 },{ 3182, 3304 },{ 3183, 3303 },{ 3184, 3303 },{ 3186, 3301 },{ 3186, 3299 },{ 3187, 3298 },{ 3187, 3296 },{ 3186, 3295 },{ 3186, 3291 },{ 3184, 3289 },
		{ 3178, 3289 },{ 3177, 3288 },{ 3175, 3288 },{ 3174, 3289 }, { 3171, 3289 },{ 3169, 3291 }, { 3169, 3295 }, { 3170, 3296 }, { 3170, 3298 },{ 3169, 3299 }, { 3169, 3300 },{ 3173, 3304 },{ 3173, 3307 }});










    @Override
    	public void onStart() throws InterruptedException {
    	sleep(2000);
    	if(quests.isComplete(Quest.COOKS_ASSISTANT)) {
    		onExit();
    	}
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));}
    	int Inventorysize = inventory.getEmptySlots();
    	if (quests.isComplete(Quest.COOKS_ASSISTANT)== true) {
    		log("Quest Complete");
    		// Return 7;
    	}else
    	if (Inventorysize <= 7) {
    		log("Dont have space");
    		DropInventory();
    }}
    
    private void CheckUp() throws InterruptedException {
    	log("Checkup");
    	if (quests.isComplete(Quest.COOKS_ASSISTANT)== true) {
    		log("Quest Complete");
    		onExit();
    	}else if(!tabs.isOpen(Tab.INVENTORY)) {
    		log("Inventory tab not open");
    		tabs.open(Tab.INVENTORY);
    		sleep(random(400,800));		
    	}else if (!SellerFULL.contains(myPlayer()) && !LB.contains(myPlayer()) && !inventory.contains("Pot") && !inventory.contains("Bucket") && !Cookroom.contains(myPlayer())){
    		log("Your not near LB");
    		walking.webWalk(Cookroom.getRandomPosition());
    	}else if (getDialogues().isPendingContinuation()) {
    		 log("You are in dialog");
    		 Completing();
    	}else if ( getDialogues().isPendingOption()) {
    		 log("You are in dialog option");
    		 Completing();
    	}else if(!inventory.contains("Pot") && !inventory.contains("Pot of flour")){ 	
    		log("Pot");
    		TakingPot();
    	}else if(!inventory.contains("Bucket of milk") && !inventory.contains("Bucket")){
    		log("Getting Bucket");
    		TakingBucket();
    	}else if(inventory.contains("Pot") && inventory.contains("Bucket") && !inventory.contains("Egg") && !SellerFULL.contains(myPlayer())){
    		log("Gathering stuff");
    		Egg();
    	}else if(!inventory.contains("Pot of flour") && inventory.contains("Egg")){
    		log("Gathering Taking Wheat");
    		 Wheat();	
    	}else if(!inventory.contains("Bucket of milk") && inventory.contains("Pot of flour") && inventory.contains("Bucket")){
    		log("Gathering Milk");
    		 Milk(); 
    	}else if(inventory.contains("Egg") && inventory.contains("Bucket of milk") && inventory.contains("Pot of flour")) {
    			log("Compleat quest");
    			Completecookingquest();
    			
    	}else if(SellerFULL.contains(myPlayer()) && inventory.contains("Bucket")){
				 log("You are in Seller");
				 	walking.webWalk(SellerS);
				 		sleep(800);
				 			objects.closest("Ladder").interact("Climb-up");
				 				sleep(1500);	 
    	}
     }
    	private void Wheat() throws InterruptedException {
    		RS2Object WheatObj = getObjects().closest("Wheat");
    		if(!inventory.contains("Grain") && !inventory.contains("Pot of flour")) {
				walking.webWalk(Wheat.getRandomPosition());
					sleep(1800);
						WheatObj.interact("Pick");
							sleep(random(2500,3000));
			}else if (inventory.contains("Grain") && !inventory.contains("Pot of flour")){
					walking.webWalk(Mill.getRandomPosition());
					sleep(800);
					objects.closest("Ladder").interact("Climb-up");
					sleep(random(3500,4000));
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
	}
   }
		
    	private void Egg() throws InterruptedException {
    	GroundItem Egg = groundItems.closest("egg");
			if(!inventory.contains("Egg") && !Chickenspit.contains(myPlayer())) {
				log("Running to Egg");
				walking.webWalk(Eggs.getRandomPosition());
					sleep(random(1800,2500));
				}if(Egg !=null && Eggs.contains(myPlayer())) {
						log("Taking Egg");
						Egg.interact("Take");
							sleep(random(1500,2000));	
					}else if(Egg == null && Eggs.contains(myPlayer())){
						log("No egg hopping");
						worlds.hopToF2PWorld();
						sleep(random(4000,7000));
						tabs.open(Tab.INVENTORY);
						sleep(300);
					}
	}
		
    	private void TakingBucket() throws InterruptedException {
    		GroundItem Bucket = getGroundItems().closest("Bucket");
    		if(!inventory.contains("Bucket") && !inventory.contains("Bucket of milk") && !SellerFULL.contains(myPlayer())){
    			walking.webWalk(Cookroom);
    				sleep(random(700,1200));
    				objects.closest("Trapdoor").interact("Climb-down");
    		 		sleep(random(5000,6000));
    		}else if(SellerFULL.contains(myPlayer()) && !inventory.contains("Bucket")) {
	 		walking.webWalk(Seller);
	 		 sleep(random(1500,2500));
	 		 if(Bucket != null) {
	 		sleep(200);
			 log("Taking Bucket");
			 Bucket.interact("Take");	
			 sleep(random(2200,2900));
			 log("Walking back to staris");
	 		 }}else if (inventory.contains("Bucket")){
			 walking.webWalk(SellerS);
			 sleep(1600);
			 objects.closest("Ladder").interact("Climb-up");
			 sleep(random(1000,2000));
			 log("Getting resourses");
			 }
	}

		private void TakingPot() throws InterruptedException {
    		GroundItem Pot = getGroundItems().closest("Pot");
    		 log("Taking pot?");
    		 if(!inventory.contains("Pot") && !Cookroom.contains(myPlayer())) {
    			 walking.webWalk(Cookroom);
    			 sleep(random(1000,1500));
    		 }
    		 if(!inventory.contains("Pot")&& Cookroom.contains(myPlayer())) {
    				if( Pot != null) {
    				 log("Taking pot");
    				 	Pot.interact("Take");
    				 		sleep(random(1500,2000));
    			 }}
	}

		//ADD tp to LB
		
		private void Milk() throws InterruptedException {
			if (!inventory.contains("Bucket of milk")){
				walking.webWalk(Cow);
					log("Sleeping");
						sleep(random(8500,9000));
							log("Milking");
								RS2Object Cow = getObjects().closest("Dairy cow");
									Cow.interact("Milk");
										sleep(random(7500,9000));
											Completecookingquest();
			}
	
	}

	private void Completecookingquest() throws InterruptedException {
		if(Cow.contains(myPlayer())) {
			getTabs().open(Tab.SKILLS);
			if(tabs.isOpen(Tab.SKILLS)) {
				getMagic().castSpell(Spells.NormalSpells.LUMBRIDGE_TELEPORT, "Cast");
				sleep(1500);}
				while(myPlayer().isAnimating()) {
					sleep(random(1000,2000));}}
					if(LB.contains(myPlayer())) {
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
	        Completing();
	        }
	}
	}

	private void Completing() throws InterruptedException {
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