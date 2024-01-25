package Notscripts;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Skeleton extends Script {
	
	

    @Override
    	public void onStart() throws InterruptedException {
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));
    	}
    	if(!inventory.isEmpty() && !Banks.DRAYNOR.contains(myPlayer())) {
    		Bankingforstart();
    		}else{
    			log("Startup");
    				CheckUp();
    	}
}

    private void Bankingforstart() throws InterruptedException {
    	walking.webWalk(Banks.DRAYNOR);
    		sleep(1000);
    			bank.open();
    				sleep(random(400,700));
    					bank.depositAll();
    						sleep(random(400,700));
    						bank.close();	
    						CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	
    		
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