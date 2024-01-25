package Trash;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import Support.*;
public class TRASHHHHCookingshrimp extends Script {

Areas areas = new Areas();
private long timeBegan;
private long timeRan;
int Shrimp = 0;
int Anovy = 0;

    @Override
    	public void onStart() throws InterruptedException {
    	sleep(3000);
    	timeBegan = System.currentTimeMillis();
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));
    	}
    	if(!inventory.isEmpty() && !Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !inventory.contains("Raw shrimps")) {
    		Bankingforstart();
    		}else{
    			log("Startup");
    				CheckUp();
    	}
}

    private void Bankingforstart() throws InterruptedException {
    	walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
    		sleep(1000);
    			bank.open();
    				sleep(random(400,700));
    					bank.depositAll();
    						sleep(random(400,700));
    						bank.close();	
    						CheckUp();
    }
	
    
    private void CheckUp() throws InterruptedException {
    		if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !inventory.isEmpty() && !Areas.LbCookingStove.contains(myPlayer())){
    			log("Taking shrimp");
    			
    		}else if(bank.getAmount("Raw shrimps") < 28 && !inventory.contains("Raw anchovies")) {
    			log("Dont have shrimps");
    			Takinganovy();
    		}else if(inventory.isEmpty()) {
    			log("Getting shrimp");
    			Takingshrimp();
    		}else if(inventory.isFull() && (inventory.contains("Raw shrimps") || inventory.contains("Raw anchovies")) && !Areas.LbCookingStove.contains(myPlayer())) {
    			log("Walking to cookspot");
    			Walktocookspot();
    		}else if(Areas.LbCookingStove.contains(myPlayer()) && inventory.contains("Raw anchovies")) {
    			log("Cooking Anovy");
    			CookingAnovy();
    		}else if(Areas.LbCookingStove.contains(myPlayer()) && inventory.contains("Raw shrimps")) {
    			log("Cooking shrimp");
    			Cooking();
    		}
    		else if(inventory.isFull() && !inventory.contains("Raw shrimps") && Areas.LbCookingStove.contains(myPlayer())){
    			log("Banking cooked Shrimp");
    			Takingshrimp();
    		}
    	}
	


	private void CookingAnovy() throws InterruptedException {
		RS2Object Range = getObjects().closest("Cooking range");
		if(!myPlayer().isAnimating() && !myPlayer().isMoving()) {
		if(Range != null) {
		camera.toEntity(Range);
		sleep(random(400,800));
		inventory.interact("Use", "Raw anchovies");
		sleep(random(400,702));
		Range.interact("Use");
		sleep(random(2200,3111));
		}if(widgets.isVisible(270, 1)) {
			log("Cooking widget visible");
			widgets.interact(270, 14, 38, "Cook");
			sleep(random(1000,2000));
		}while(true) {
				if(myPlayer().isAnimating()) {
					sleep(random(1000,2000));
				}else {
					break;
				}
		}
	}
	}		
	

	private void Takinganovy() throws InterruptedException {
		if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			log("Walking LUMBRIDGE_UPPER");
	    	walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
	    	sleep(random(1200,1800));
	    	}
	    	bank.open();
	    	log("opening bank");
	    	Shrimp = (int)getBank().getAmount("Raw shrimps");
	    	Anovy = (int)getBank().getAmount("Raw anchovies");
	    	sleep(random(723,800));
	    	if(bank.isOpen()) {
	    		log("Bank open");
	    	if(!inventory.isEmpty() && !inventory.contains("Raw anchovies") && !inventory.contains("Raw anchovies")) {
	    		log("Inventory not empty");
	    		bank.depositAll();
	    		sleep(random(688,811));
	    	}
	    	if(bank.isOpen() && bank.getAmount("Raw shrimps") < 28 && bank.getAmount("Raw anchovies") < 28) {
    			log("Out of shrimp/anovy");
    			onExit();
	    	}if(!inventory.contains("Raw anchovies")) {
	    		sleep(random(311,731));
	    		bank.withdrawAll("Raw anchovies");
	    		sleep(random(500,800));
	    	}
	    	bank.close();
		}		
		}		
	

	private void Walktocookspot() throws InterruptedException {
		walking.webWalk(areas.LbCookingStove());
		log("Walking Cook spot");
		sleep(random(2100,2990));
	}

	private void Cooking() throws InterruptedException {
		RS2Object Range = getObjects().closest("Cooking range");
		if(!myPlayer().isAnimating() && !myPlayer().isMoving()) {
		if(Range != null) {
		camera.toEntity(Range);
		sleep(random(400,800));
		inventory.interact("Use", "Raw shrimps");
		sleep(random(400,702));
		Range.interact("Use");
		sleep(random(2200,3111));
		}if(widgets.isVisible(270, 1)) {
			log("Cooking widget visible");
			widgets.interact(270, 14, 38, "Cook");
			sleep(random(1000,2000));
		}while(true) {
				if(myPlayer().isAnimating()) {
					sleep(random(1000,2000));
				}else {
					break;
				}
		}
	}
	}

	private void Takingshrimp() throws InterruptedException {

		if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
		log("Walking LUMBRIDGE_UPPER");
    	walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
    	sleep(random(1200,1800));
    	}
    	bank.open();
    	log("opening bank");
    	Shrimp = (int)getBank().getAmount("Raw shrimps");
    	Anovy = (int)getBank().getAmount("Raw anchovies");
    	sleep(random(723,800));
    	if(bank.isOpen()) {
    		log("Bank open");
    	if(!inventory.isEmpty() && !inventory.contains("Raw shrimps") && !inventory.contains("Raw anchovies")) {
    		log("Inventory not empty");
    		bank.depositAll();
    		sleep(random(688,811));
    	}
    	if(!inventory.contains("Raw shrimps")) {
    		sleep(random(311,731));
    		bank.withdrawAll("Raw shrimps");
    		sleep(random(500,800));
    	}
    	bank.close();
	}		
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
    	 g.setColor(Color.decode("#00ff04")); //Color
         timeRan = System.currentTimeMillis() - this.timeBegan;
         g.drawString(ft(timeRan), 430, 320);
     	 g.drawString("Shrimps " + Shrimp, 430, 305);
         g.drawString("Anovy " + Anovy, 430, 295);
        //This is where you will put your code for paint(s)

       
    }
    public String ft(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) -
            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}