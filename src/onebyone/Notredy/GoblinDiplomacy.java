package onebyone.Notredy;
import org.osbot.rs07.api.Magic;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class GoblinDiplomacy extends Script {
private int At; //Attack
private int Str; //Strenght
private int Def; // Defence
Area Goblinhut = new Area( new int[][]{{ 2956, 3515 },{ 2958, 3515 }, { 2959, 3515 },{ 2959, 3514 },{ 2962, 3514 },{ 2962, 3510 }, { 2954, 3510 },{ 2954, 3512 },
	        { 2954, 3513 },{ 2956, 3513 }});
Area Goblinvillage = new Area(2947, 3517, 2965, 3492);
public static String[] FirstGeneral = { "Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?","Do you want me to pick an armour colour for you?", "What about a different colour?"};
Area LB = new Area(3268, 3190, 3132, 3317); //Lumbgre

	

    @Override
    	public void onStart() throws InterruptedException {
    	if(quests.getQuests().isComplete(Quest.GOBLIN_DIPLOMACY)){
    		log("Quest Completed");
    	}else if(!inventory.isEmpty()) {
    		Bankingforstart();
    		}else{
    			log("Startup");
    				CheckUp();
    	}
}

    private void Bankingforstart() throws InterruptedException {
    	walking.webWalk(Banks.FALADOR_EAST);
    		sleep(1000);
    			bank.open();
    				sleep(random(400,700));
    					bank.depositAll();
    						sleep(random(400,700));
    						bank.close();		
    }
	
    private void CheckUp() throws InterruptedException {
    	GroundItem Bones = getGroundItems().closest("Bones");
		GroundItem Feather = getGroundItems().closest("Feather");
	if(Goblinhut.contains(myPlayer()) && inventory.isEmpty()) {
		log("Quest start");
		Startingquest();
	}else if(!Goblinhut.contains(myPlayer()) && Banks.FALADOR_EAST.contains(myPlayer())) {
		log("Walking Hut");
		Walkinghut();
	}else if(quests.isStarted(Quest.GOBLIN_DIPLOMACY) && Goblinhut.contains(myPlayer())) {
		log("Walking to Goblins spot");
		GoingtoGoblinsLB();
    }else if (inventory.isFull() && Goblinareabig.contains(myPlayer())) {
    	log("Inventory full");
    	Bury();
    }else if(settings.getRunEnergy() <= 20) {
    	log("Setrun");
    	settings.setRunning(true);	
    }else if((Bones !=null || Feather !=null) &&!inventory.isFull() && !myPlayer().isUnderAttack() && !myPlayer().isMoving() && ChickensBig.contains(myPlayer())) {
    	log("Looting");
   		Loot();
    }else if(!Goblinareabig.contains(myPlayer())) {	
    	log("Walking to spot");
    	Walking();
    }else if(Goblinareabig.contains(myPlayer()) && !myPlayer().isMoving() && !myPlayer().isUnderAttack() && !myPlayer().isAnimating()) {
    	log("Killing chicken");
    	Kill();
	}
    	
    	
	
}


	private void GoingtoGoblinsLB() throws InterruptedException {
		if(Goblinhut.contains(myPlayer())) {
			getTabs().open(Tab.SKILLS);
			if(tabs.isOpen(Tab.SKILLS)) {
				getMagic().castSpell(Spells.NormalSpells.LUMBRIDGE_TELEPORT, "Cast");
				sleep(1500);
				while(myPlayer().isAnimating()) {
					sleep(random(1000,2000));
					if(LB.contains(myPlayer())) {
						webwalking.
					}
				}
			}
		}
		
	}

	private void Walkinghut() throws InterruptedException {
		walking.webWalk(Goblinhut.getRandomPosition());
		sleep(1000);
		
	}

	private void Startingquest() throws InterruptedException {
		log("Starting quest");
		 NPC GeneralWar = getNpcs().closest("General Wartface");
		 GeneralWar.interact("Talk-to");
	        sleep(random(1000,1652));
	        log("Going to dialog");
	        if (getDialogues().isPendingContinuation()) {
	        	log("Continue");
				getDialogues().clickContinue();
				sleep(Script.random(800, 1200));
	        	} else if(getDialogues().completeDialogue(FirstGeneral)) {
	        log("Chosing dialog for Continue 1");
			sleep(Script.random(800, 1200));
	        }
	}

	private void Bury() throws InterruptedException {
		if(inventory.contains("Raw chicken")){
			log("Droping chicken");
			inventory.interact("Drop", "Raw chicken");
			sleep(random(634,892));
		}else while(inventory.contains("Bones")) {
			log("Burying bones");
			inventory.interact("Bury", "Bones");
			sleep(random(850,1050));
		}if(inventory.isEmptyExcept("Feather")) {
			Levelcheck();
		}
	}

	private void Loot() throws InterruptedException {
		GroundItem Bones = getGroundItems().closest("Bones");
		GroundItem Feather = getGroundItems().closest("Feather");
		while((Feather !=null || Bones !=null) && !myPlayer().isMoving() && !myPlayer().isAnimating()) {
			log("Take Feather");
			Feather.interact("Take");
				sleep(random(1000,2000));
					log("Take Bones");
						Bones.interact("Take");
							sleep(random(200,500));	
							return;
		//}else {
			//log("You are moving");
			//sleep(random(500,1500));
		}
	}				
	
	private void Kill() throws InterruptedException {
		NPC Ch = getNpcs().closest("Chicken");
		if(Ch !=null && !myPlayer().isInteracting(Ch) && Ch.isAttackable() && Ch.getHealthPercent() == 100 &&!Ch.isHitBarVisible() && !Ch.isUnderAttack()) {
			log("Attacking");
			Ch.interact("Attack");
			sleep(2500);
				
				}
		}

	private void Walking() throws InterruptedException {
		walking.webWalk(Goblinhut);
		sleep(1000);
	}

	private void Levelcheck() throws InterruptedException {
		
		if(At < Str || At < Def) {
			openTab();
		log("Chanking style attack");
		Attack();	
		}else if(Str < At || Str < Def) {
			openTab();
			log("Chanking style attack");
	    	Strenght();
		}else if(Def < At || Def < Str) {
			openTab();
	    		log("Chanking style attack");
	        	Defence();	
		}
	}
	private void openTab() throws InterruptedException {
		if(tabs.getTabs().isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.ATTACK);
			sleep(random(400,700));
		}
	}

	private void Defence() throws InterruptedException {
		if(tabs.isOpen(Tab.ATTACK)) {
			widgets.interact(593, 18, "Defence");
				sleep(random(300,6000));
				tabs.open(Tab.INVENTORY);
				sleep(200);
		}		
		}
	private void Strenght() throws InterruptedException {
		if(tabs.isOpen(Tab.ATTACK)) {
				widgets.interact(593, 10, "Lunge");
				sleep(random(300,6000));
				tabs.open(Tab.INVENTORY);
				sleep(200);
		}
		}

	private void Attack() throws InterruptedException {
		if(tabs.isOpen(Tab.ATTACK)) {
			widgets.interact(593, 6, "Stab");
				sleep(random(300,6000));
				tabs.open(Tab.INVENTORY);
				sleep(200);
		}
		}
	private void Equips() throws InterruptedException {
		if(equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze sword")){
			log("You got sword");
			CheckUp();
		}else if(equipment.isWearingItem(EquipmentSlot.SHIELD, "Wooden shield")) {
			log("Got shield");
			CheckUp();
		}else if(inventory.contains("Bronze sword")) {
			log("Wielding Sword");
			inventory.interact("Wield", "Bronze sword");
		}else if (inventory.contains("Wooden shield")) {
			log("Wielding Shield");
			inventory.interact("Wield", "Wooden shield");
		}
	}
	@Override
    public int onLoop() throws InterruptedException {
		NPC Ch = getNpcs().closest("Chicken");
		if(myPlayer().isUnderAttack() && Ch.isHitBarVisible() && Ch.isUnderAttack() && Ch.getHealthPercent() !=0 ){
			log("Sleeping");
			sleep(random(1500,2000));}

		CheckUp();

	   return random(1000) ; //The amount of time in milliseconds before the loop starts over


    }
   
    @Override
    public void onExit() {
    	log("Good Bye");
    	stop();
    }	

    @Override
    public void onPaint(Graphics2D g) {



    }
	
}