package onebyone.Notredy;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Doricsquest extends Script {
Area Rocks = new Area(3229, 3147, 3230, 3146);	
Area RocksBig = new Area(3220, 3152, 3236, 3140);
public int Min;  //mining
Area RockIron = new Area(3294, 3373, 3275, 3356);
Position Ironore = new Position(3286,3368,0);
Area Clay = new Area(3072, 3425, 3089, 3415);


//Fix null check on ore



//open tab get open tab


    @Override
    public void onStart() throws InterruptedException {
    	if (quests.isComplete(Quest.DORICS_QUEST)== true) {
    		log("Quest Complete");
    		onExit();
    	}
    	CheckUp();
    }
  
    private void CheckUp() throws InterruptedException {
    	int Inventorysize = Inventory.SIZE-getInventory().getEmptySlots();
    	Min = skills.getDynamic(Skill.MINING);
    	log("Error");
    	if((!inventory.contains("Bronze pickaxe")) || (Inventorysize >= 15) && !RockIron.contains(myPlayer()) &&  !RocksBig.contains(myPlayer()) && !Clay.contains(myPlayer())){
    		log("Banking");
    		Bankall();
    		}else if (inventory.getAmount("Copper ore") == 4 && Min > 15){
    			log("Got copper walk to iron mine");
    			Ironmine();
    		}else if (inventory.getAmount("Iron ore") == 2 && inventory.getAmount("Copper ore") == 4 && Min > 15) {
    			log("Got iron and copper Walk to clay");
    	    		Claymine();
    		}else if (inventory.getAmount("Clay") == 6 && Min > 15) {
    			
    			
    		}else if (Min > 15 && !inventory.contains("Copper ore")) {
    		log("Mining level 15 and mining ores for quest");
    		MineOres(); 
    		}else if(( Min < 15) && RocksBig.contains(myPlayer()) &&inventory.contains("Bronze pickaxe")){
    		log("Farming levels");
    		Gettinglevels();
    	}else if(inventory.contains("Bronze pickaxe") && !RocksBig.contains(myPlayer()) ){
    		log("Walking");
    		Walking();
    	}
    }
    	    
    private void MineOres() throws InterruptedException {
    	
    	if(RocksBig.contains(myPlayer())){ 
        		log("Dont have Ore");
        		Gettinglevels();
    	}else {
    		log("Not in area go to main");
        		Walking();
    	}	
	}   

    private void Claymine() throws InterruptedException {
    	if(Clay.contains(myPlayer())){
    		log("Mining iron");
    		MineClay();	
    	}else {
    		log("Walking to iron");
    		walkingtoClay();
	}
    }

	private void MineClay() throws InterruptedException {
		if(Clay.contains(myPlayer())) {
			log("Walking irone one");
			walking.webWalk(Clay); 
			}if(Clay.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()){
	    		log("Redy to mine");
	    		RS2Object Ore = getObjects().closest("Rocks");
	    		if(Rocks != null);
	    		log("Mining");
	    		Ore.interact("Mine");
	    			sleep(random(2500,3000));
	    			while(myPlayer().isAnimating()){
	    				log("Animating");
	    				sleep(random(1002,2019));
	    			}
	    	}		
	}

	private void walkingtoClay() throws InterruptedException {	
    	log("Walking clay");
    	walking.webWalk(Clay);
    	sleep(1500);
	}

	private void Ironmine() throws InterruptedException {
		if(!RockIron.contains(myPlayer())) {
		log("Walking irone one");
		walkingtoiron();
		}if(RockIron.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()){
    		log("Redy to mine");
    		RS2Object Ore = getObjects().closest("Rocks");
    		if(Rocks != null);
    		log("Mining");
    		Ore.interact("Mine");
    			sleep(random(2500,3000));
    			while(myPlayer().isAnimating()){
    				log("Animating");
    				sleep(random(1002,2019));
    			}
    	}
	}

	private void Inventoryfull() throws InterruptedException {
    		log("Droping tin");
    		// open tab get open
    		//  mouse.click(640, 186, true);
    		sleep(1000);
    		inventory.dropAll("Tin ore");
    		sleep(300);
    		log("Droping Copper ore");
    		inventory.dropAll("Copper ore");
    		sleep(1300);
	}

	private void walkingtoiron() throws InterruptedException {
		walking.webWalk(Ironore);
		sleep(1300);		
	}

	private void Walking() throws InterruptedException {
    	walking.webWalk(Rocks.getRandomPosition());
		sleep(1300);		
	}

	private void Gettinglevels() throws InterruptedException {
		if (inventory.getAmount("Copper Ore") == 4 && Min > 15) {
			log("Got 4 copper and mining 15");
    		CheckUp();
		}else if(inventory.isFull()) {
			log("Inventory full true");
			Inventoryfull();
		}
		if(RocksBig.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()){
		log("Redy to mine");
		RS2Object Ore = getObjects().closest("Rocks");
		if(Rocks != null);
		log("Mining");
		Ore.interact("Mine");
			sleep(random(2500,3000));
			while(myPlayer().isAnimating()){
				log("Animating");
				sleep(random(2002,4019));
			}
			
		}
	}
   

	public void Bankall() throws InterruptedException {
    	log("Walking bank");
    	walking.webWalk(Banks.LUMBRIDGE_UPPER);
    	sleep(1000);
    	if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {	
    		bank.open();
    		log("Bank opend");
    		sleep(random(500,1000));
    		bank.depositAll();
    		sleep(random(500,1000));
    		bank.withdraw("Bronze pickaxe", 1);
    		sleep(random(500,1000));
    			bank.close();
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
        g.drawString("==15  " + Min, 715, 112);


        //This is where you will put your code for paint(s)


    }


	
}