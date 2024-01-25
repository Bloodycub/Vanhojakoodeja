package onebyone.Notredy;
import org.osbot.S;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class SheepShrederquest extends Script {

Area Sheeppit = new Area(3194, 3273, 3209, 3259);
Area Hut = new Area(3188, 3274, 3191, 3271);
Area PlankArea = new Area(3197, 3277, 3197, 3277);
Area PlankArea2 = new Area(3197,3275, 3197,3275);
Area Sheepareabig = new Area(3193, 3278, 3212, 3257);
Area Spinning = new Area(3208, 3216, 3211, 3212).setPlane(1);
public static String[] Farmer1 = {"I'm looking for a quest.","Yes okay. I can do that."};



    @Override
    public void onStart() throws InterruptedException {
    	if(quests.isComplete(Quest.SHEEP_SHEARER)) {
    		log("You completed alredy");
    		onExit();
    	}
    	if(!inventory.isEmptyExcept("Shears","Wool","Ball of wool") && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
    		Bankingforstart();
    		}else{
    			log("Startup");
    				CheckUp();
    	}
}

    private void Bankingforstart() throws InterruptedException {
    	walking.webWalk(Banks.LUMBRIDGE_UPPER);
    		sleep(1000);
    			bank.open();
    				sleep(random(400,700));
    					bank.depositAll();
    						sleep(random(400,700));
    						bank.close();	
    						CheckUp();
    }
	
    private void CheckUp() throws InterruptedException {
    	if(!inventory.contains("Shears")) {
    		log("Getting Sheers");
    		Shears();
    	}else if(inventory.getAmount("Ball of wool") >= 20) {
    		log("Completing quest");
    		Completing();
    	}else if (inventory.contains("Shears")&& !PlankArea.contains(myPlayer()) && !Sheepareabig.contains(myPlayer()) && !Spinning.contains(myPlayer()) && inventory.getAmount("Whool") <= 20) {	
    		log("Walking SheepPit");
    		SheepPitWalk();
    	}else if(inventory.contains("Shears") && Sheepareabig.contains(myPlayer()) && inventory.getAmount("Wool") <= 20){
    		log("Getting whools");
    		GettingWhool();
    	}else if(inventory.getAmount("Wool") >= 20) {
    		log("SpinWhool");
    		SpinWhool();
    	
	
    	}
    		
    	}

	private void Completing() throws InterruptedException {
		NPC Farmer = getNpcs().closest("Fred the Farmer");
		walking.webWalk(Hut.getRandomPosition());
		sleep(1500);
		Farmer.interact("Talk-to");
		sleep(random(800,1200));
		log("Starting quest");
	        log("Going to dialog");
	       if(getDialogues().completeDialogue(Farmer1)) {
	        log("Chosing dialog for Continue 1");
			sleep(Script.random(800, 1200));
			}
		
		
	}

	private void SpinWhool() throws InterruptedException {
		RS2Object Spin = getObjects().closest("Spinning wheel");
		if(!Spinning.contains(myPlayer())) {
		walking.webWalk(Spinning.getRandomPosition());
		sleep(1000);
		}else {
		if(Spin !=null) {
		Spin.interact("Spin");
		sleep(random(800,1400));
		getWidgets().isVisible(270, 1);
		widgets.interact(270, 12, 0, "All");
		sleep(random(600,900));
		widgets.interact(270, 14, 38, "Spin");
		sleep(random(15000,25000));
		}
	}
}
	private void GettingWhool() throws InterruptedException {
		NPC Sheep = getNpcs().closestThatContains("Sheep");
		if(Sheep !=null && Sheep.hasAction(("Shear")) && !Sheep.hasAction("Talk-to") && !myPlayer().isAnimating() && !myPlayer().isMoving()){
		camera.toEntity(Sheep);
		Sheep.interact("Shear");
		sleep(random(3550,4111));
		}else {
			log("Chaniging spot");
			walking.webWalk(Sheeppit.getRandomPosition());
			sleep(random(1200,1600));
		}
	}

	private void SheepPitWalk() throws InterruptedException {
		RS2Object Plank = getObjects().closest("Stile");
		log("Walking to jump soit");
		if(!PlankArea2.contains(myPlayer())) {
		walking.webWalk(PlankArea.getCentralPosition());
		sleep(1500);
		Plank.interact("Climb-over");
		sleep(7000);	}
		if(PlankArea2.contains(myPlayer())) {
		walking.webWalk(Sheeppit);
		sleep(1000);
		}
	}

	private void Shears() throws InterruptedException {
		GroundItem Sakset = getGroundItems().closest("Shears");
		if(!Hut.contains(myPlayer())) {
			log("Walking Hut");
		walking.webWalk(Hut.getRandomPosition());}
		sleep(random(1300,1800));
		if(Sakset !=null) {
		Sakset.interact("Take");
		sleep(random(1500,2500));		
	}
	}
	@Override
    public int onLoop() throws InterruptedException {
		CheckUp();

	   return random(1000) ; //The amount of time in milliseconds before the loop starts over


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