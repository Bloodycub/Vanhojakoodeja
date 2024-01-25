package Trash;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Chickenkillerold extends Script {
Area Chickenspit = new Area( new int[][]{{ 3173, 3308 },{ 3180, 3308 },{ 3180, 3304 },{ 3182, 3304 },{ 3183, 3303 },{ 3184, 3303 },{ 3186, 3301 },
    { 3186, 3299 },{ 3187, 3298 },{ 3187, 3296 },{ 3186, 3295 },{ 3186, 3291 },{ 3185, 3290 },{ 3170, 3290 },{ 3169, 3291 },{ 3169, 3295 },{ 3170, 3296 },
    { 3170, 3298 },{ 3169, 3299 },{ 3169, 3300 },{ 3173, 3304 },{ 3173, 3304 }, { 3173, 3308 }});
Area ChickensBig = new Area(3166, 3309, 3189, 3286);
private int At; //Attack
private int Str; //Strenght
private int Def; // Defence
Area Upg = new Area(3233, 3233, 3232, 3234);
private long timeBegan;
private long timeRan;
final int attack1 = random(2,8);
final int attack2 = random(12,20);
final int Str1 = random(10,18);
final int Str2 = random(32,35);
final int Defns = random(20,45);
Area Trainer = new Area(3216, 3240, 3221, 3236);

Sleep.sleepUntil (() -> !myPlayer().isMoving(), 3000);


//add no wepon training wepon

    @Override
    	public void onStart() throws InterruptedException {
        timeBegan = System.currentTimeMillis();
        tabs.open(Tab.INVENTORY);
        sleep(random(1000,2000));
        log("Check equipments");
        ItemChecklist();
        sleep(200);
    	if(!inventory.isEmpty() && inventory.getEmptySlots() <= 13 && !Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !ChickensBig.contains(myPlayer())) {
    		log("Banking stuff for space");
    		Bankingforstart();
    		}else{
    			log("Startup");
    				CheckUp();
    	}
}

    private void Bankingforstart() throws InterruptedException {
    	log("Walking LB bank");
    	walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
    	log("Waiting for finish walking");
    		sleep(1000);
    		log("Opening bank");
    			bank.open();
    			log("Bank opend");
    				sleep(random(400,700));
    					bank.depositAll();
    						sleep(random(400,700));
    						bank.close();		
    }
	
    
    private void CheckUp() throws InterruptedException {
        Str = skills.getDynamic(Skill.STRENGTH);
        Def = skills.getDynamic(Skill.DEFENCE);
        At = skills.getDynamic(Skill.ATTACK);

		
	/* if(inventory.isFull() && ChickensBig.contains(myPlayer())) {
    	log("Inventory full");
    	Bury(); 	*/
    if(settings.getRunEnergy() >= 20 && !getSettings().isRunning()) {
    	log("Setrun");
    	settings.setRunning(true);	
    }else if(!tabs.isOpen(Tab.INVENTORY)) {
    	log("Wrong tab");
    	tabs.open(Tab.INVENTORY);
    }else if(getEquipment().isWearingItem(EquipmentSlot.WEAPON) == false){
    	log("Cheking items");
    	ItemChecklist();
    }else if(inventory.isFull() || inventory.getEmptySlots() < 5) {
    	walking.webWalk(Banks.LUMBRIDGE_UPPER);
    }else if(!Chickenspit.contains(myPlayer())) {	
    	Upgrade();
    	log("Walking to spot");
    	Walking();
    }else if(Chickenspit.contains(myPlayer()) && !myPlayer().isMoving() && !myPlayer().isUnderAttack() && !myPlayer().isAnimating()) {
    	Kill();
    	Loot();
    	
	}
    	
    	
	
}


	/* private void Bury() throws InterruptedException {
		if(inventory.contains("Raw chicken")){
			log("Droping chicken");
			inventory.interact("Drop", "Raw chicken");
			sleep(random(634,892));
		}else while(inventory.contains("Bones")) {
			log("Burying bones");
			inventory.interact("Bury", "Bones");
			sleep(random(850,1050));
		}
	}
*/
	private void Loot() throws InterruptedException {
		//GroundItem Bones = getGroundItems().closest("Bones");
    	GroundItem Feather = getGroundItems().closest("Feather");
	 if((Feather !=null) &&!inventory.isFull() && !myPlayer().isUnderAttack() && !myPlayer().isMoving() && ChickensBig.contains(myPlayer())) {
    		log("Looting");
    		Feather.interact("Take");
			sleep(random(650,900));
		}
	}				
	
	private void Kill() throws InterruptedException {
		NPC Ch = getNpcs().closest("Chicken");
		log("Killing chicken");
		if(Ch !=null && Chickenspit.contains(Ch) && !myPlayer().isInteracting(Ch) && Chickenspit.contains(myPlayer())&& Ch.isAttackable() && Ch.getHealthPercent() == 100 &&!Ch.isHitBarVisible() && !Ch.isUnderAttack()) {
			log("Attacking");
			sleep(1000);
			Ch.interact("Attack");
			sleep(random(2800,3469));
			log("Cheking level");
			Levelcheck();
			ItemChecklist();
				}
		}

	private void Walking() throws InterruptedException {
		walking.webWalk(Chickenspit.getRandomPosition());
		sleep(1000);
	}
	
	private void Levelcheck() throws InterruptedException {	
		
		Upgrade();
		if(At <= attack1){
			if(!configs.isSet(43, 0)) {
		log("Chanking style attack 1");
		openTab();
		Attack();
			}
		}else if(At >= 20 && Str >= Str2 && Def <= Defns){
			if(!configs.isSet(43, 3)) {
	    		log("Chanking style Defence 1");
	    		openTab();
	        	Defence();	
		}else if(Str <= Str1) {
			if(!configs.isSet(43, 1)) {
			log("Chanking style Strenght 1");
			openTab();
	    	Strenght();
			}
		}else if(At <= 20 && Str >= Str1) {
	    	if(!configs.isSet(43, 0)) {
			log("Chanking style attack 2 ");
	    		openTab();
	    		Attack();
	    	}
	    }else if(Str < Str2 ) {
	    			if(!configs.isSet(43, 1)) {
	    			log("Chanking style Strenght 2");
	    			openTab();
	    	    	Strenght();
		
			}
		}
	    }
	 }
	private void openTab() throws InterruptedException {
		
		
		
		if(tabs.getTabs().isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.ATTACK);
			sleep(random(600,900));
		}
	}

	private void Defence() throws InterruptedException {
		
		if(tabs.isOpen(Tab.ATTACK)) {
			widgets.interact(593, 18, "Block");
				sleep(random(600,850));
				tabs.open(Tab.INVENTORY);
				sleep(200);
		}		
		}
	private void Strenght() throws InterruptedException {
		if(tabs.isOpen(Tab.ATTACK))
			sleep(random(600,850));
		if(getEquipment().isWieldingWeapon("Bronze longsword")){
			widgets.interact(593, 10, "Lunge");
		}else{
			widgets.interact(593, 10, "Slash");
				sleep(random(600,850));
				tabs.open(Tab.INVENTORY);
				sleep(200);
		}
	}

	private void Attack() throws InterruptedException {
		
		if(tabs.isOpen(Tab.ATTACK))
			sleep(random(600,850));
		if(getEquipment().isWieldingWeapon("Steel longsword")) {
			widgets.interact(593, 6, "Chop");
		}else if(getEquipment().isWieldingWeapon("Bronze longsword")){
			widgets.interact(593, 6, "Stab");
		}else if(getEquipment().isWieldingWeapon("Black longsword")){
			widgets.interact(593, 6, "Chop");
		}else if(getEquipment().isWieldingWeapon("Mithril longsword")){
			widgets.interact(593, 6, "Chop");
		}
				sleep(random(500,700));
				tabs.open(Tab.INVENTORY);
				sleep(200);
		}
		
	
		private void Upgrade() throws InterruptedException {
			NPC Upgrade = getNpcs().closest("Adventurer Jon");
			if(At >= 5 && At < 10 && !inventory.contains("Steel longsword") &&!getEquipment().isWieldingWeapon("Steel longsword")) {
				log("Under lvl 10");
				log("Getting upgrade");
				walking.webWalk(Upg);
				sleep(random(1898,2821));
				Upgrade.interact("Claim");
				sleep(random(1200,1800));
				if (dialogues.inDialogue()) {
					getDialogues().clickContinue();
					sleep(random(400,842));
				}
				sleep(random(400,800));
				log("Cheking upgrades");
				ItemChecklist();
			}else if(At >= 10 && At < 20 && !inventory.contains("Black longsword")&& !getEquipment().isWieldingWeapon("Black longsword")){
				log("Under lvl 20");
				log("Under lvl 10");
				log("Getting upgrade");
				walking.webWalk(Upg);
				sleep(random(1898,2821));
				Upgrade.interact("Claim");
				sleep(random(1200,1800));
				if (dialogues.inDialogue()) {
					getDialogues().clickContinue();
					sleep(random(400,842));
				}
				sleep(random(400,800));
				log("Cheking upgrades");
				ItemChecklist();
			}else if(At >= 20 && !inventory.contains("Mithril longsword")&&  !getEquipment().isWieldingWeapon("Mithril longsword")) {
				log("+ 20lvl");
			log("Getting upgrade");
			walking.webWalk(Upg);
			sleep(random(1009,1821));
			Upgrade.interact("Claim");
			sleep(random(1200,1800));
			if (dialogues.inDialogue()) {
				getDialogues().clickContinue();
				sleep(random(400,842));
			}
			sleep(random(400,800));
			log("Cheking upgrades");
			ItemChecklist();
			}
		}

		private void ItemChecklist() throws InterruptedException {
			log("Equips check");
			if(inventory.contains("Mithril longsword") && !inventory.contains("Black longsword") && getEquipment().isWieldingWeapon("Black longsword")){ //n.3
				log("Equiping Mithril sword");
				inventory.interact("Wield","Mithril longsword");
				sleep(400);
					}else if(inventory.contains("Black longsword") && !inventory.contains("Mithril longsword") && !inventory.contains("Steel longsword") && getEquipment().isWieldingWeapon("Steel longsword")){ //n.2
						log("Equiping Black sword");
						inventory.interact("Wield","Black longsword");
					sleep(400);
						}else if(inventory.contains("Steel longsword") && !inventory.contains("Mithril longsword")  && !inventory.contains("Black longsword") &&  getEquipment().isWieldingWeapon("Bronze sword")){ //n.1
							log("Equiping Steel sword");
							inventory.interact("Wield","Steel longsword");
							sleep(700);
							inventory.drop("Bronze sword");
						sleep(400);
							}else if(inventory.contains("Bronze sword") && !inventory.contains("Steel longsword") && !inventory.contains("Mithril longsword")  && !inventory.contains("Black longsword") &&  !equipment.isWearingItem(EquipmentSlot.WEAPON, "Steel longsword") && !equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze sword")){ //n.0
								log("Wielding Sword");
								inventory.interact("Wield", "Bronze sword");
								sleep(400);		
								}else if(inventory.contains("Wooden shield") && !equipment.isWearingItem(EquipmentSlot.SHIELD, "Wooden shield")) {
									log("Wielding Shield");
									inventory.interact("Wield", "Wooden shield");
									sleep(400);		
								}else if(inventory.contains("Training sword") && !inventory.contains("Steel longsword") && !inventory.contains("Mithril longsword")  && !inventory.contains("Black longsword") &&  !equipment.isWearingItem(EquipmentSlot.WEAPON, "Steel longsword") && !equipment.isWearingItem(EquipmentSlot.WEAPON, "Training sword")){ 
									log("Wielding Sword");
									inventory.interact("Wield", "Training sword");
									sleep(400);		
								}else if(inventory.contains("Training shield") && !equipment.isWearingItem(EquipmentSlot.SHIELD, "Training shield")) {
									log("Wielding Shield");
									inventory.interact("Wield", "Training shield");
									sleep(400);		
									}else if(!inventory.contains("Bronze sword") && !inventory.contains("Steel longsword") && !inventory.contains("Mithril longsword")  && !inventory.contains("Black longsword") &&  !equipment.isWearingItem(EquipmentSlot.WEAPON, "Steel longsword") && !equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze sword")){ 
										log("Your empty handed");
										Checkbankforsword();
										sleep(400);	
			}
			}

	
	private void Checkbankforsword() throws InterruptedException {
			if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			walking.webWalk(Banks.LUMBRIDGE_UPPER);
			sleep(random(1000,2000));}
			if(!bank.isOpen()) {
			bank.open();
			sleep(random(400,700));
			if(getBank().contains("Mithril longsword")) {
				log("Taking Mithril sword");
				bank.withdraw("Mithril longsword", 1);
				sleep(random(400,800));
				bank.close();
			}else if(getBank().contains("Black longsword")) {
				log("Taking Black sword");
				bank.withdraw("Black longsword", 1);
				sleep(random(400,800));
				bank.close();
			}else if(getBank().contains("Steel longsword")) {
				log("Taking Steel sword");
				bank.withdraw("Steel longsword", 1);
				sleep(random(400,800));
				bank.close();
			}else if(getBank().contains("Bronze sword")) {
				log("Taking Bronze sword");
				bank.withdraw("Bronze sword", 1);
				sleep(random(400,800));
				bank.close();
			}else if(getBank().contains("Training sword")) {
				log("Taking Bronze sword");
				bank.withdraw("Training sword", 1);
				sleep(random(400,800));
				bank.close();
			}else {
				log("Dont have swords go to trainer");
				GettingTrainingsword();
			}
			}
		}

	private void GettingTrainingsword() throws InterruptedException {
		NPC TrainerG = getNpcs().closest("Melee combat tutor");
		walking.webWalk(Trainer.getRandomPosition());
		sleep(random(1000,1500));
		TrainerG.interact("Talk-to");
		sleep(random(600,1000));
		if(dialogues.inDialogue()) {
		String[] SaS = {"I'd like a training sword and shield."};
		dialogues.completeDialogue(SaS);
		}
		if(inventory.contains("Training sword") && inventory.contains("Training shield")) {
			log("Doing check list");
			ItemChecklist();
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
    	 g.setColor(Color.decode("#00ff04")); //Color
         timeRan = System.currentTimeMillis() - this.timeBegan;
         g.drawString(ft(timeRan), 460, 335);
         g.drawString(attack1 + " Att phase 1", 405, 325);
         g.drawString(attack2 + " Att phase 2", 405, 315);
         g.drawString(Str1 + " Str phase 1", 405, 305);
         g.drawString(Str2 + " Str phase 2", 405, 295);
         g.drawString(Defns + " Def phase 1", 405, 285);
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