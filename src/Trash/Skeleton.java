package Trash;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Skeleton extends Script {
	

	Area[] banks = {Banks.LUMBRIDGE_UPPER};


    @Override
    	public void onStart() throws InterruptedException {
    	if (inventory.contains("Pot"));
    		walking.webWalk(banks);
    			if (Banks.LUMBRIDGE_UPPER.contains(myPlayer()));
    				bank.open();
    					if (getBank().isOpen());
    						bank.depositAll();
    						
    				
    	
    	
    	
    	//Code here will execute before the loop is started
    }


		
	

@Override
    public int onLoop() {


	   return random(700) ; //The amount of time in milliseconds before the loop starts over


    }
   
    @Override
    public void onExit() {


        //Code here will execute after the script ends


    }	

    @Override


    public void onPaint(Graphics2D g) {


        //This is where you will put your code for paint(s)


    }


}