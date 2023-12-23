package Onshelf;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;
import Support.*;

import java.awt.*;

public class Doricsquest extends Script {
	public int Min; // mining
	Areas areas = new Areas();
	String[] Dialogues = {"I wanted to use your anvils.","Yes, I will get you the materials."};
//Fix null check on ore

	@Override
	public void onStart() throws InterruptedException {
		if (myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating() && myPlayer().isOnScreen()) {
			while(settings.areRoofsEnabled() == false) {
				getKeyboard().typeString("::toggleroofs");
				Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
			}}
		if (quests.isComplete(Quest.DORICS_QUEST) == true) {
			log("Quest Complete");
			onExit();
		}
		CheckUp();
	}

	private void CheckUp() throws InterruptedException {
		int Inventorysize = Inventory.SIZE - getInventory().getEmptySlots();
		Min = skills.getDynamic(Skill.MINING);
		if(quests.isComplete(Quest.DORICS_QUEST)) {
			log("Quest completed");
			stop();
		}else if ((!inventory.contains("Bronze pickaxe")) || (Inventorysize >= 15) && !Areas.RockIron.contains(myPlayer())
				&& !Areas.RocksBig.contains(myPlayer()) && !Areas.ClayBig.contains(myPlayer())) {
			log("Banking");
			Bankall();
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 3000);

		} else if (inventory.getAmount("Copper ore") == 4 && Min >= 15 && inventory.getAmount("Iron ore") < 2) {
			log("Got copper walk to iron mine");
			Ironmine();
		} else if (inventory.getAmount("Iron ore") == 2 && inventory.getAmount("Copper ore") == 4 && Min >= 15 && inventory.getAmount("Clay") < 6) {
			log("Got iron and copper Walk to clay");
			Claymine();
		} else if (inventory.getAmount("Clay") == 6 && inventory.getAmount("Iron ore") == 2 && inventory.getAmount("Copper ore") == 4 &&  Min >= 15) {
			log("Got all lets go to hut");
			Completingquest();
		} else if (Min >= 15 && inventory.getAmount("Copper ore") < 4) {
			log("Mining level 15 and mining ores for quest");
			MineCopper();
		} else if (Min < 15 && inventory.contains("Bronze pickaxe")) {
			log("Farming levels");
			Gettinglevels();
		} else if (Min >= 15) {
			log("Checking space");
			Checkquestitems();
		}
	}

	private void Checkquestitems() throws InterruptedException {
		if (!inventory.isItemSelected()) {
			if (inventory.getAmount("Copper ore") > 4) {
				log("Dropping copper");
				inventory.drop("Copper ore");
				sleep(random(500, 800));
			}
			if (inventory.getAmount("Iron ore") > 2) {
				log("Dropping Iron");
				inventory.drop("Iron ore");
				sleep(random(500,800));
			}if (inventory.getAmount("Clay") > 6) {
				log("Dropping Clay");
				inventory.drop("Clay");
				sleep(random(500, 800));
			}
				
			if(inventory.getAmount("Copper ore") < 4) {
				MineCopper();
			}
			if(inventory.getAmount("Iron ore") == 2 &&  inventory.getAmount("Copper ore") == 4) {
				Ironmine();
			}
			if(inventory.getAmount("Clay") < 6 && inventory.getAmount("Iron ore") == 2 &&  inventory.getAmount("Copper ore") == 4) {
				Claymine();
		}} else {
			inventory.deselectItem();
		}
	}

	private void Completingquest() throws InterruptedException {
		if (!Areas.Doricsquesthut.contains(myPlayer())) {
			walking.webWalk(areas.Doricsquesthut());
			sleep(random(1500, 2000));
		}
		NPC Doric = getNpcs().closest("Doric");
		if (getDialogues().inDialogue()) {
			dialogues.completeDialogue(Dialogues);
		} else {
			Doric.interact("Talk-to");
			Sleep.waitCondition(() -> dialogues.inDialogue(), 5000);
		}

	}

	private void MineCopper() throws InterruptedException {
		if (Areas.RocksBig.contains(myPlayer())) {
			log("Dont have Ore");
			Gettinglevels();
		} else {
			log("Not in area go to main");
			Walking();
			
		}
	}

	private void Claymine() throws InterruptedException {
		if (Areas.ClayBig.contains(myPlayer())) {
			log("Mining Clay");
			MineClay();
		} else {
			log("Walking to Clay");
			walkingtoClay();
		}
	}

	private void MineClay() throws InterruptedException {
		if (!Areas.ClayBig.contains(myPlayer())) {
			log("Walking Clay one");
			walking.webWalk(areas.Clay);
		}
		if (Areas.ClayBig.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Redy to mine");
			RS2Object Ore = getObjects().closest("Rocks");
			if (Ore != null) {;
			log("Mining");
			Ore.interact("Mine");
			sleep(random(2500, 3000));}
			while (myPlayer().isAnimating()) {
				log("Animating");
				sleep(random(1002, 2019));
			}
		}
	}

	private void walkingtoClay() throws InterruptedException {
		log("Walking clay");
		walking.webWalk(areas.Clay);
		sleep(random(1500, 2000));
	}

	private void Ironmine() throws InterruptedException {
		if (!Areas.RockIron.contains(myPlayer())) {
			log("Walking irone one");
			walkingtoiron();
		}
		if (Areas.RockIron.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Redy to mine");
			RS2Object Ore = getObjects().closest("Rocks");
			if (Ore != null) {
				
			log("Mining Ironone");
			Ore.interact("Mine");
			sleep(random(2500, 3000));}
			while (myPlayer().isAnimating()) {
				log("Animating");
				sleep(random(1002, 2019));
			}
		}
	}

	private void Inventoryfull() throws InterruptedException {
		log("Droping tin");
		sleep(1000);
		inventory.dropAll("Tin ore");
		sleep(300);
		log("Droping Copper ore");
		inventory.dropAll("Copper ore");
		sleep(1300);
	}

	private void walkingtoiron() throws InterruptedException {
		walking.webWalk(areas.Ironore);
		sleep(random(1300, 2000));
	}

	private void Walking() throws InterruptedException {
		if (!Areas.Rocks.contains(myPlayer())) {
			walking.webWalk(areas.Rocks);
			sleep(1300);
		}
	}

	private void Gettinglevels() throws InterruptedException {
		if (inventory.getAmount("Copper Ore") == 4 && Min > 15) {
			log("Got 4 copper and mining 15");
			CheckUp();
		} else if (inventory.isFull()) {
			log("Inventory full true");
			Inventoryfull();
		}
		if (Areas.RocksBig.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Redy to mine");
			RS2Object Ore = getObjects().closest("Rocks");
			if (Ore != null) {
			log("Mining");
			Ore.interact("Mine");
			sleep(random(2500, 3000));}
			while (myPlayer().isAnimating()) {
				log("Animating");
				sleep(random(2002, 4019));
			}
		}else {
			walking.webWalk(areas.Rocks);
			sleep(random(1500,2000));
		}
	}

	public void Bankall() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !bank.isOpen()) {
			log("Walking Lb bank");
			walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
			Sleep.waitCondition(() -> Banks.LUMBRIDGE_UPPER.contains(myPlayer()), 3000);
		}
		if (bank.isOpen()) {
			bank.depositAll();
			Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
			bank.withdraw("Bronze pickaxe", 1);
			Sleep.waitCondition(() -> inventory.contains("Bronze pickaxe"), 3000);
			bank.close();
		} else {
			log("Open bank");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		CheckUp();

		return random(1700); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onExit() {
		log("Good Bye");
		stop();

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.drawString(Min + " == 15", 700, 112);

		// This is where you will put your code for paint(s)

	}

}