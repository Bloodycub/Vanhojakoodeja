package Simple;


import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;



public class Test extends Script {

    @Override
    public void onStart() {
       
    }

    @Override
    public int onLoop() throws InterruptedException {
      CheckUp();
        return 110;
    }

	private void CheckUp() throws InterruptedException {
		log("Loop");
		int b = 7813;
		int a[] = {7813,7816};
		RS2Object berrys = getObjects().closest("Redberry bush");
			log("1");
		if(berrys.getId() == b) {	
			log("Picking");
		berrys.interact("Pick-from");
		sleep(random(3000,5000));
		}
	}

    
}