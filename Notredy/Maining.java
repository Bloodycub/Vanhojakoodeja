package Notredy;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Maining extends Script {
	//Cameraup NAMEHERE = new Cameraup();	
	

    @Override
    	public void onStart() throws InterruptedException {
    	log("ASD");
    	CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	if(AREA.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()){
    		log("Redy to mine");
    		RS2Object Ore = getObjects().closest("Rocks");
    		if(AREA != null);
    		log("Mining");
    		Ore.interact("Mine");
    			sleep(random(2500,3000));
    			while(myPlayer().isAnimating()){
    				log("Animating");
    				sleep(random(1002,2019));
    			}
    			
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