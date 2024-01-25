package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Potatopicker extends Script {
Area Potatofield = new Area(3142, 3288, 3153, 3277);		
Area PotatofieldBig = new Area(3133, 3295, 3163, 3262);		
Area Draynor = new Area(3068, 3392, 3167, 3143); //Drayner area





    @Override
    	public void onStart() throws InterruptedException {
    	log("Start up");
    	CheckUp();
    }
	
    
    private void Dropeverything() throws InterruptedException {
    		log("Inventory not empty");
    		walking.webWalk(Banks.DRAYNOR);
    		sleep(1000);
    		if(Banks.DRAYNOR.contains(myPlayer())) {
    		Banking();
    	}
	}


	private void Banking() throws InterruptedException {
		if(!Banks.DRAYNOR.contains(myPlayer())) {
			log("Walking bank");
			walking.webWalk(Banks.DRAYNOR);
			sleep(random(1000,1100));}
			log("opening bank");
			bank.open();
			sleep(300);
			log("Deposite all");
			bank.depositAll();
			sleep(random(500,1000));
			log("Bank Close");
			bank.close();
			sleep(random(800,900));
	}


	private void CheckUp() throws InterruptedException {
    		if(!Draynor.contains(myPlayer()) && !inventory.isEmpty() ) {
    			log("Not in Draynor");
    			Dropeverything();
    		}else if(!inventory.isFull() && Potatofield.contains(myPlayer())&& !myPlayer().isAnimating() && !myPlayer().isMoving()){
    				log("Picking potatos");
    				Pickpotatos();
    		}else if(inventory.isEmpty() && !Potatofield.contains(myPlayer())) {
    			log("Walking field");
    			Walkfield();	
    		}else if(inventory.isFull() && PotatofieldBig.contains(myPlayer())){
    			log("Walking bank");
    			Banking();
    		}
    			
    	}

	private void Pickpotatos() throws InterruptedException {
		RS2Object Potatos = getObjects().closest("Potato");
		if(Potatos !=null) {
			Potatos.interact("Pick");
			sleep(random(1876,2123));
			}
	}
//add better sleep if picked potato i++ sleep for 1000. Animation check dosent work

	private void Walkfield() throws InterruptedException {
		walking.webWalk(Potatofield.getRandomPosition());
		sleep(random(1000,1200));
		
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