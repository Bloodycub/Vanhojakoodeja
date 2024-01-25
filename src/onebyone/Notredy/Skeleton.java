package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Skeleton extends Script {
	

	Area[] banks = {Banks.LUMBRIDGE_UPPER};


    @Override
    	public void onStart() throws InterruptedException {
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));
    	}
    }

@Override
    public int onLoop() {
		Checkup();

	   return random(700) ; //The amount of time in milliseconds before the loop starts over


    }
   
    private void Checkup() {
	// TODO Auto-generated method stub
	
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