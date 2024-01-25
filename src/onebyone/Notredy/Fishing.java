package onebyone.Notredy;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Fishing extends Script {
final String[] Fishingspotname = {"Fishing spot"};
Area ShrimpfishingBIGarea = new Area(new int[][]{{ 2984, 3176 },{ 2993, 3161 },{ 2995, 3154 },{ 2991, 3145 },{ 2992, 3141 },{ 2997, 3140 },{ 2996, 3148 },{ 3001, 3156 },{ 3000, 3164 },{ 2987, 3183 }, { 2981, 3181 } });
Area Shrimpspot = new Area(2998, 3159, 2999, 3156);
private int Fish; //fishing
Area AnovyBigarea = new Area(3082, 3232, 3090, 3223);
int Shrimp = 0;
int Anovy = 0;
private long timeBegan;
private long timeRan;
Area Anovyspot = new Area(3084, 3231, 3091, 3224);






    @Override
    	public void onStart() throws InterruptedException {
    	if(inventory.isEmptyExcept("Small fishing net")) {
    	  timeBegan = System.currentTimeMillis();
        Fish = skills.getDynamic(Skill.FISHING);
    	if(Fish >= 15) {
    		if(inventory.contains("Small fishing net")){
    		log("Over 15 level get anovy");
    		CheckupOverfifteen();
    	}else {
    		log("Getting net");
    		Getnet();
    	}
    	}
    	if(Fish < 15) {
    		if(inventory.contains("Small fishing net")){
    			log("Fish shrimp");
    		CheckUpUnderfifteen();
    	}else{
    		log("Got net and getting shrimp");
    		Getnet();
    	}
    	}
    	}else {
    		Getnet();
    	}
    }
	
    
    private void CheckupOverfifteen() throws InterruptedException {
    	if (!AnovyBigarea.contains(myPlayer())){
    			log("Walking anovy");
    			Walkspotanovy();
    	}else if(!inventory.isFull() && AnovyBigarea.contains(myPlayer())) {
    			FishAnovy();
    	}else if(inventory.isFull()) {
    			 log("Banking anovy");
    			 Getnet();
    		}
    	}		
	private void Walkspotanovy() throws InterruptedException {
		if(inventory.isEmptyExcept("Small fishing net")) {
		log("Walking Anovy");
		walking.webWalk(Anovyspot.getRandomPosition());
		sleep(random(1500,2604));		
	}else {
		Getnet();
	}
		}


	private void FishAnovy() throws InterruptedException {
		 NPC shrimp = getNpcs().closest(Fishingspotname);
		 while (Fishingspotname !=null && myPlayer().isAnimating()) {
			 log("Animating");
			 sleep(random(4500, 8500));
		 }
		 if (!myPlayer().isAnimating() && Fishingspotname !=null && !myPlayer().isMoving() && AnovyBigarea.contains(myPlayer())) {
			 log("Start Fishing");
			 camera.toEntity(shrimp);
			 sleep(random(311,501));
                shrimp.interact("Small Net");
                     sleep(random(2000, 4500));
		 }
	}		


	private void Getnet() throws InterruptedException {
		
		if(!Banks.DRAYNOR.contains(myPlayer())) {
		log("Walking draynor");
    	walking.webWalk(Banks.DRAYNOR);
    	sleep(random(1200,1800));
    	}
    	bank.open();
    	log("opening bank");
    	sleep(random(723,800));
    	if(bank.isOpen()) {
    		log("Bank open");
    	if(!inventory.isEmpty()) {
    		log("Inventory not empty");
    		bank.depositAll();
    		sleep(random(688,811));
    		Shrimp = (int)getBank().getAmount("Raw shrimps");
    		Anovy = (int)getBank().getAmount("Raw anchovies");
    	}
    	if(!inventory.contains("Small fishing net")) {
    		bank.withdraw("Small fishing net", 1);
    	sleep(random(500,800));
    	}
    	bank.close();
	}
	}

	public void CheckUpUnderfifteen() throws InterruptedException {
    	if (!ShrimpfishingBIGarea.contains(myPlayer())){
    		log("Walking shrimp spot");
    		Walkspot();
    	}else if(!inventory.isFull() && ShrimpfishingBIGarea.contains(myPlayer())) {
    			log("Fishing shrimp");
    			 FishShrimp();
    	}else if(inventory.isFull()) {
    			 inventory.dropAll("Raw shrimps");
    		}
    	}
    
	public void Walkspot() throws InterruptedException {	
		if(inventory.isEmptyExcept("Small fishing net")) {
		log("Walking shrimps");
		walking.webWalk(Shrimpspot.getRandomPosition());
		sleep(random(1500,2604));
	}else {
		Getnet();
	}
	}

	public void FishShrimp() throws InterruptedException {
		 NPC shrimp = getNpcs().closest(Fishingspotname);
		 while (Fishingspotname !=null && myPlayer().isAnimating()) {
			 sleep(random(4500, 8500));
		 }
		 if (!myPlayer().isAnimating() && Fishingspotname !=null && !myPlayer().isMoving()) {
			 camera.toEntity(shrimp);
			 sleep(random(311,501));   
			 shrimp.interact("Small Net");
                     sleep(random(2500, 5500));
		 }
	}

	@Override
    public int onLoop() throws InterruptedException {
		if(!tabs.isOpen(Tab.INVENTORY)) {
			log("Inv tab not open");
			tabs.open(Tab.INVENTORY);	}
		 Fish = skills.getDynamic(Skill.FISHING);
		if(Fish >= 15 && inventory.contains("Small fishing net")) {
    		CheckupOverfifteen();}
    	if(Fish <= 15 && inventory.contains("Small fishing net")) {
    		CheckUpUnderfifteen();}
    	
	   return random(700) ; }
   
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