package onebyone.Notredy;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Makingdoe extends Script {
	
	

    @Override
    	public void onStart() throws InterruptedException {
    	log("Startup");
    	if(!inventory.isEmpty() && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
    		WalkingBank();
    	}
    	CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	if(bank.isOpen()) {
    	if(!(getBank().contains("Bucket of water") || getBank().contains("Pot of flour"))) {
    		log("Exiting no water no pot");
    		onExit();
    	}}else if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
    		log("Walking bank");
    		WalkingBank();
    	}else if (Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !inventory.contains("Bucket of water") && !inventory.contains("Pot of flour")) {
    		log("Taking Bank");
    		Banking();
    	}else if(inventory.contains("Bucket of water") && inventory.contains("Pot of flour")) {
    		log("Making doe");
    		Makingdoes();
    	}else if (inventory.contains("Pizza base")) {
    	log("Depositing all");
    		Banking();
    	}
    }
    	

	private void Makingdoes() throws InterruptedException {
		if (inventory.contains("Pot of flour")) {
			log("Interacting Pot");
		inventory.interact("Use", "Pot of flour");
		sleep(random(100,250));	
		log("Interacting Water");
		inventory.interactWithNameThatContains("Use", "Bucket of water");
		sleep(random(400,750));
		DoeMaking();
		
			}
		}

	private void DoeMaking() throws InterruptedException {
		RS2Widget Pizzadoeing = getWidgets().get(270,1); 
		RS2Widget Doe = getWidgets().get(270,16,38); 
		log("Pizza options on?");
		if(Pizzadoeing !=null) {
			log("Selecting Pizza doe");
			if(Doe !=null) {
				log("Doe selected");
				Doe.interact("Make");
				sleep(random(8000,11000));
			}
		}
	}


	private void WalkingBank() throws InterruptedException {
		log("Walking Bank Lb");
		walking.webWalk(Banks.LUMBRIDGE_UPPER);
			sleep(1000);
			Depositingall();
	}


	private void Depositingall() throws InterruptedException {
		bank.open();
		sleep(400);
		bank.depositAll();
		sleep(random(400,600));
		bank.close();	
		sleep(random(450,650));
	}


	private void Banking() throws InterruptedException {
	if(Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			if(inventory.contains("Pizza base")) {
				bank.open();
				bank.depositAll();
				sleep(random(100,250));	
			}else{
			bank.open();
				bank.withdraw("Bucket of water", 9);
				sleep(random(100,250));	
					bank.withdraw("Pot of flour", 9);
						sleep(random(400,800));
						bank.close();
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