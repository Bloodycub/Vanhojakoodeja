package bots;
import org.osbot.rs07.api.map.constants.Banks;
import bots.BotSkeleton;
public class Freshspawn extends BotSkeleton {

    	public void onStart() throws InterruptedException {
    		log("Fresh");
    	//CheckUp();
    	}
	
    
   private void CheckUp() throws InterruptedException {
		if(inventory.contains("Shrimps") && inventory.contains("Bread")) {
			log("Go to bank");
			gotobank();
		}else {
			log("Exiting");
		}
		
	}


	private void gotobank() throws InterruptedException {
		if(Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			bank.open();
			log("Bank opend");
			sleep(random(500,1000));
			log("Depositing all");
			bank.depositAll();
			sleep(random(500,1000));
			if(inventory.isEmpty() == true) {
				log("Close bank");
				bank.close();
				sleep(random(500,1000));
			}else {
				//onExit();
			}
		}else{
			log("Walking to bank");
			walking.webWalk(Banks.LUMBRIDGE_UPPER);
		}
	}


    public int onLoop() throws InterruptedException {
		CheckUp();
	
		return 7;

    }
   
}