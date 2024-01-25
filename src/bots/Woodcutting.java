package bots;
import org.osbot.rs07.api.model.RS2Object;



	public class Woodcutting extends BotSkeleton {
	
    public void onStart() throws InterruptedException {
    	
    }
	
   public void CheckUp() throws InterruptedException {
		if (!inventory.contains("Logs")){
			Cutwood();
		}else {
		}
		
	}

	public void Cutwood() throws InterruptedException { 
            if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
                log("Im idle redy to chop");
                    RS2Object tree = getObjects().closest("Tree");
                    if (tree != null) {
                        if (tree.interact("Chop down")) {
                            sleep(random(3500, 5932));
                            if (myPlayer().isAnimating()) {
                                sleep(random(700, 950));
                            }
                        }
                    }
                }
            }
   
    public int onLoop() throws InterruptedException {
    	CheckUp();
    	return 0;
    }
}