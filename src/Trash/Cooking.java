package Trash;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Cooking extends Script {
	

    @Override
    	public void onStart() throws InterruptedException {
    	CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	
    	}
	private void Cooking() throws InterruptedException {
	//	RS2Object Fire = getObjects().closest(AREAIN, "Fire");//In area fire is
		if (inventory.contains("Raw shrimps")){
			log("Cooking");
			inventory.interact("Use", "Raw shrimps");
				sleep(random(200, 350));
					Fire.interact("Use");
					sleep(random(1089,1252));
					RS2Widget cookiconbutton = getWidgets().get(270, 14);
					cookiconbutton.interact();
					sleep(random(1234,2341));
						while (inventory.contains("Raw shrimps") && myPlayer().isAnimating() && Fire != null){
							log("Animaiting Cooking");
								sleep(random(1800, 2350));
					sleep(2000);
					}
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