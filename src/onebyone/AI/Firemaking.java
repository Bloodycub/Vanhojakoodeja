package onebyone.AI;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Firemaking extends Script {
	

    @Override
    	public void onStart() throws InterruptedException {
    	CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	if(inventory.contains("Logs")) {
    	Makefire();
    	}
    }


	private void Makefire() throws InterruptedException {
			RS2Object Fire = getObjects().closest("Fire");
			log("you got logs");
	        	inventory.interact("Use", "Tinderbox");
	        		sleep(random(542,823));
	        			inventory.interactWithNameThatContains("Use","logs");
	        				sleep(random(699,903));
	        					//fire
	        	while(myPlayer().isAnimating()) {
	        		log("making fire Animation");
	        		sleep(random(299,421));
	        }
	    			if(Fire.exists() == true){
	    			sleep(random (1000,2000));
	        	}
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