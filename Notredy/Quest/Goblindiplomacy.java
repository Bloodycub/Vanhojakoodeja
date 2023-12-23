package Notredy.Quest;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;
import java.awt.*;

public class Goblindiplomacy extends Script {
	Areas areas = new Areas();
	Timer Timerun;
	boolean Spot1 = true;
	boolean Spot2 = true;
	boolean Empty = false;
	boolean NoBerrys = false;
	int loopcheck =random(5,10);

	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
		log("XXXXXX BOT NAME XXXXXXXXXXX Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		
		if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 300, 5000);
		}
		while (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}
	}

	private void Checkup() throws InterruptedException {
		if (getConfigs().get(62) <= 2) {
			log("Walk start area");
			StartQuest();
		} else if(getConfigs().get(62) !=0 && inventory.getAmount("Goblin mail") <= 3) {
			log("Getting Mails");
			GettingArmor();		
		} else if(getConfigs().get(62) !=0 && inventory.getAmount("Goblin mail") >= 3) {
			log("Getting Blue Leafs");
			BlueLeafs();		
		} else if(getConfigs().get(62) !=0 && inventory.getAmount("Redberries") == 3 && inventory.getAmount("Redberries") != 2) {
			log("Getting Berrys");
			Gettingberrys();	
		} else if(getConfigs().get(62) !=0 && inventory.getAmount("Redberries") != 3 && inventory.getAmount("Redberries") <= 2) {
			log("Getting Berrys");
			GettingOnions();	
			
			
	}/*
		 * Goblin diplomacy 62:3 start
		 * 
		 */
	}
	private void GettingOnions() throws InterruptedException {
		if(Areas.LBOnionField.contains(myPlayer())) {
			log("Walking onions");
			walking.webWalk(areas.LBOnionField());
		}
		RS2Object onion = getObjects().closest("Onion");
		while(inventory.getAmount("Onion") < 2) {
		onion.interact("Pick");
		sleep(random(500,1000));
		Sleep.waitCondition(() -> (!(myPlayer().isMoving() && myPlayer().isAnimating())),500,5000);

	}
	}
	private void Gettingberrys() throws InterruptedException {
		RS2Object berrys = getObjects().closest("Redberry bush");
		if(!Areas.Redberrys.contains(myPlayer())) {
			log("Walking bank");
			walking.webWalk(areas.Redberrys());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if(Areas.Berryone.contains(myPlayer())) {
			RS2Object berries = getObjects().closest("Redberry bush");
			if(berries.getModelIds().length == 2) {
				berries.interact("Pick-from");
				sleep(random(1500,2200));
				Sleep.waitCondition(() -> !myPlayer().isMoving() && !myPlayer().isAnimating(), 500, 5000);
		}
	}
	}
		
	private void BlueLeafs() throws InterruptedException {
		int coins = random(35,40);
		if(!Areas.FaladorWestBank.contains(myPlayer())) {
			log("Walking bank");
			walking.webWalk(areas.FaladorWestBank());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if(!bank.isOpen()) {
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 500, 5000);
			if(inventory.SIZE < 4){
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 5000);
			}
			if(bank.getAmount("Coins") <= 40) {
				bank.withdraw("Coins", coins);
				Sleep.waitCondition(() -> inventory.contains("Coins"), 500, 5000);
			}else {
				onLoop();
			}
			if(inventory.contains("Coins")){
				bank.close();
			}
			if(!Areas.BlueDyeGuy.contains(myPlayer())) {
				NPC Farmer = getNpcs().closest("Wyson the gardener");
				log("Walking bank");
				walking.webWalk(areas.BlueDyeGuy());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
				if(Farmer != null) {
					Farmer.interact("Talk-to");
					sleep(random(500,1000));
					Sleep.waitCondition(() -> dialogues.inDialogue(), 500,1000);
					if(dialogues.inDialogue()) {
						String[] T = {"Yes please, I need woad leaves.","How about 20 coins?"};
						dialogues.completeDialogue(T);
					}
				}
			}
		}	
	}

	private void GettingArmor() throws InterruptedException{
		RS2Object Box = getObjects().closest("Crate");
		if (inventory.getAmount("Goblin mail") <= 1 && !inventory.isFull()) {
			log("Getting first armor");
			walking.webWalk(areas.box1);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			Box.interact("Search");
			sleep(random(500,1000));
			Sleep.waitCondition(() -> inventory.getAmount("Goblin mail") <= 1, 500, 5000);
		} else if (inventory.getAmount("Goblin mail") <= 2 && !inventory.isFull()) {
			log("Getting first armor");
			walking.webWalk(areas.box2);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			Box.interact("Search");
			sleep(random(500,1000));
			Sleep.waitCondition(() -> inventory.getAmount("Goblin mail") <= 2, 500, 5000);
		} else if (inventory.getAmount("Goblin mail") <= 3 && !inventory.isFull()) {
			log("Getting first armor");
			walking.webWalk(areas.box3);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			Box.interact("Search");
			sleep(random(500,1000));
			Sleep.waitCondition(() -> inventory.getAmount("Goblin mail") <= 3, 500, 5000);
		}else if(inventory.isFull()) {
			log("You full");
			BlueLeafs();
		}
	}

	private void StartQuest() throws InterruptedException {
		if (!Areas.Diplomacygoblinhut.contains(myPlayer())) {
			log("Walk to Gobling to start quest");
			walking.webWalk(areas.Diplomacygoblinhut);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		NPC Leader = getNpcs().closest("General Bentnoze");
		String Start[] = { "Do you want me to pick an armour colour for you?", "What about a different colour?" };
		if (Leader != null) {
			Leader.interact("Talk-to");
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> dialogues.inDialogue(), 500, 5000);
		}
		if(dialogues.completeDialogue(Start)) {
		Sleep.waitCondition(() ->getConfigs().get(63) == 3,500,5000);
		}
		

	}

	@Override
	public int onLoop() throws InterruptedException {
		int Counter = 0;
		if(Counter == loopcheck){
			int loopcheck =random(5,10);
			if(dialogues.isPendingContinuation()) {
				log("Stuck");
				dialogues.clickContinue();
			}
			Counter = 0;
		}
		Checkup();

		return 100 + random(100);

	}

	@Override
	public void onExit() {
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString("Time runned " + Timerun, 0, 11);

	}

}