package onebyone.Notredy;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Login extends Script {
	//Cameraup NAMEHERE = new Cameraup();	
	

    @Override
    	public void onStart() throws InterruptedException {
    	log("ASD");
    	CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    	
    		//NAMEHERE.onStart();
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