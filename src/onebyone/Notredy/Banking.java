package onebyone.Notredy;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Banking extends Script {
	//Cameraup NAMEHERE = new Cameraup();	
	

    @Override
    	public void onStart() throws InterruptedException {
    	CheckUp();
    }
	
    public void Atbank() throws InterruptedException {
    	log("Walking bank");
    	walking.webWalk(Banks.LUMBRIDGE_UPPER);
    	sleep(1000);
    	if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {	
    		bank.open();
    		log("Bank opend");
    		sleep(random(500,1000));
    		bank.depositAll();
    		sleep(random(500,1000));
    			bank.close();
	
    	}
    	}

    	
    
    
    
    
    private void CheckUp() throws InterruptedException {
    	
    		//NAMEHERE.onStart();
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