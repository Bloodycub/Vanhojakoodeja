package Simplescripts;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Woodcutting extends Script {
	
  	

	@Override
    	public void onStart() throws InterruptedException {
    	CheckUp();
    	
    }
	
   public void CheckUp() throws InterruptedException {
		if (!inventory.isFull()){
			Cutwood();
		}else {
			onExit();
		}
		
	}

	private void Cutwood() throws InterruptedException {
		log("Cut Wood");
		if(inventory.isFull() && inventory.drop("Raw shrimps")) {
			log("Droping Srimp for space");
		}else {
			if(!myPlayer().isAnimating() && !myPlayer().isMoving()){ 
            log("Im idle redy to chop");
                RS2Object tree = getObjects().closest("Tree");
                if (tree != null) {
                    if (tree.interact("Chop down")) {
                    	sleep(random(600,1234));
                        while (myPlayer().isMoving()) {
                        	sleep(1000);
                        }
                        while (myPlayer().isAnimating()) {
                            sleep(random(700, 950));
                        }
                    }
                }
			}}
	}
   
    @Override
    public int onLoop() throws InterruptedException {
    	CheckUp();

	   return random(700) ; //The amount of time in milliseconds before the loop starts over


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