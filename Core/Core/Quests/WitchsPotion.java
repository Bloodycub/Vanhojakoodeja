package Core.Quests;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;
import java.awt.*;

public class WitchsPotion extends Skeleton {
	Areas areas = new Areas();
	Time timers;
	Timer Timerun;

	@Override
	public void onStart() throws InterruptedException {
		//Timerun = new Timer(0);
		log("Witch's Potion Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if(dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Wrong tab");
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 300, 5000);
		}
		while (settings.areRoofsEnabled() == false) {
			log("Roofs off");
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}
		if (getConfigs().get(67) == 3) {
			log("2");
			onLoop();
		}
	}

	private void Checkup() throws InterruptedException {
		log("On loop");
		if(quests.isComplete(Quest.WITCHS_POTION)) {
			log("Completed");
			onLoop();
		}
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		if (getConfigs().get(67) != 3 && !inventory.contains("Eye of newt") && !inventory.contains("Coins")) {
			log("Getting conis on the way");
			GettingCoins();
		} else if (inventory.getAmount("Coins") >= 3 && !inventory.contains("Eye of newt") && getConfigs().get(67) != 2) {
			log("Buying eye");
			Buyingeye();
		} else if (inventory.contains("Eye of newt") && getConfigs().get(67) < 1) {
			log("Starting quest");
			Startingquest();
		} else if (getConfigs().get(67) == 1 && !inventory.contains("Rat's tail")) {
			log("Walk to rats");
			GettingRattail();
		} else if (getConfigs().get(67) == 1 && inventory.contains("Rat's tail") && inventory.contains("Raw beef") && !inventory.contains("Burnt meat")) {
			log("Walk to cow get burnt meat");
			Burningmeat();
		} else if (getConfigs().get(67) == 1 && inventory.contains("Rat's tail") && !inventory.contains("Raw beef") && !inventory.contains("Burnt meat")) {
			log("Walk to cow");
			KillingCow();
		} else if (getConfigs().get(67) == 1 && inventory.contains("Rat's tail") && inventory.contains("Burnt meat") && !inventory.contains("Onion")) {
			log("Getting onion");
			Gettingonion();
		} else if (getConfigs().get(67) == 1 && inventory.contains("Rat's tail") && inventory.contains("Burnt meat")
				&& inventory.contains("Onion")) {
			log("Return quest");
			Returningtowizzard();
		} else if (getConfigs().get(67) == 2 && !inventory.contains("Rat's tail") && !inventory.contains("Burnt meat")
				&& !inventory.contains("Onion")) {
			log("Completing");
			Completing();
			// 67:1 start quest
			// 67:2 continuing Drinking from pot
			// 67:3 Completed
		}
	}

	private void KillingCow() throws InterruptedException {
		if(!inventory.contains("Burnt meat")){
		NPC cow = getNpcs().closest("Cow");
		GroundItem meat = getGroundItems().closest("Raw beef");
		if (meat != null) {
			meat.interact("Take");
			sleep(random(100,300));
			Sleep.waitCondition(() -> inventory.contains("Raw beef"), 500, 5000);
			Checkup();
		}
		if (!Areas.Questcowpitforburnmeat.contains(myPlayer())) {
			walking.webWalk(areas.Questcowpitforburnmeat);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (cow != null && !myPlayer().isHitBarVisible() && !myPlayer().isUnderAttack()) {
			cow.interact("Attack");
			sleep(random(1000,2000));
			Sleep.waitCondition(() -> myPlayer().isHitBarVisible(), 1000, 5000);
			Sleep.waitCondition(() -> !myPlayer().isHitBarVisible(), 1000, 200000);
			if (meat != null) {
				meat.interact("Take");
				sleep(random(100,300));
				Sleep.waitCondition(() -> inventory.contains("Raw beef"), 500, 5000);
			}
		}
		}
	}

	private void Completing() {
		RS2Object Pot = getObjects().closest("Cauldron");

		if (!Areas.Wichersquest.contains(myPlayer())) {
			walking.webWalk(areas.Wichersquest);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Pot != null) {
			Pot.interact("Drink From");
			Sleep.waitCondition(() -> getDialogues().isPendingContinuation(), 500, 5000);
			getDialogues().clickContinue();
			Sleep.waitCondition(() -> getConfigs().get(67) == 3, 500, 5000);
			log("Completed");
		}
	}

	private void Gettingonion() throws InterruptedException {
		RS2Object onion = getObjects().closest("Onion");

		if (!Areas.Onionfield.contains(myPlayer())) {
			walking.webWalk(areas.Onionfield);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (onion != null) {
			onion.interact("Pick");
			sleep(random(1000,2000));
			Sleep.waitCondition(() -> inventory.contains("Onion"), 500, 5000);
		}
	}

	private void Returningtowizzard() throws InterruptedException {
		NPC wizzard = getNpcs().closest("Hetty");

		String[] talk = { "" };
		if (!Areas.Wichersquest.contains(myPlayer())) {
			walking.webWalk(areas.Wichersquest);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (wizzard != null) {
			wizzard.interact("Talk-to");
			sleep(random(100,300));
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			dialogues.completeDialogue(talk);
			Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
		}
	}

	private void Burningmeat() throws InterruptedException {
		RS2Object Fire = getObjects().closest("Fireplace");

		if (!Areas.Cookpitformeat.contains(myPlayer())) {
			walking.webWalk(areas.Cookpitformeat);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Fire != null) {
			inventory.interact("Use", "Raw beef");
			sleep(random(100,300));
			Sleep.waitCondition(() -> getInventory().isItemSelected(), 500, 5000);
			Fire.interact("Use");
			sleep(random(100,300));
			Sleep.waitCondition(() -> inventory.contains("Burnt meat") || inventory.contains("Cooked meat"), 500, 5000);
			if (inventory.contains("Cooked meat")) {
				inventory.interact("Use", "Cooked meat");
				sleep(random(300,800));
				Sleep.waitCondition(() -> getInventory().isItemSelected(), 500, 5000);
				Fire.interact("Use");
				sleep(random(300,800));
				Sleep.waitCondition(() -> inventory.contains("Burnt meat"), 500, 5000);
				sleep(random(200,400));
			}
		}
	}

	private void GettingRattail() throws InterruptedException {
		NPC rat = getNpcs().closest("Rat");
		if (!Areas.RatTail.contains(myPlayer())) {
			walking.webWalk(areas.RatTail);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (rat != null) {
			rat.interact("Attack");
			sleep(random(1000,2000));
			Sleep.waitCondition(() -> myPlayer().isHitBarVisible(), 1000, 5000);
			Sleep.waitCondition(() -> !myPlayer().isHitBarVisible(), 1000, 10000);
			GroundItem tail = getGroundItems().closest("Rat's tail");
			if(tail != null) {
				tail.interact("Take");
				sleep(random(100,400));
				Sleep.waitCondition(() -> inventory.contains("Rat's tail"), 500, 5000);
			}}
		}

	private void Startingquest() throws InterruptedException {
		NPC wizzard = getNpcs().closest("Hetty");
		String[] talk = { "I am in search of a quest.", "Yes, help me become one with my darker side." };
		if (!Areas.Wichersquest.contains(myPlayer())) {
			walking.webWalk(areas.Wichersquest);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (wizzard != null) {
			wizzard.interact("Talk-to");
			sleep(random(100,300));
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			dialogues.completeDialogue(talk);
			Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
		}
	}

	private void Buyingeye() throws InterruptedException {
		NPC wizzard = getNpcs().closest("Betty");

		if (!Areas.PortMageShop.contains(myPlayer())) {
			walking.webWalk(areas.PortMageShop);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (wizzard != null) {
			wizzard.interact("Trade");
			sleep(random(100,300));
			Sleep.waitCondition(() -> getStore().isOpen(), 500, 5000);
			store.buy("Eye of newt", 1);
			sleep(random(100,300));
			Sleep.waitCondition(() -> inventory.contains("Eye of newt"), 500, 5000);
			store.close();
			Sleep.waitCondition(() -> !getStore().isOpen(), 500, 5000);
		}
	}

	private void GettingCoins() throws InterruptedException {
		if (!Areas.DraynorBank.contains(myPlayer()) && !bank.isOpen()) {
			log("Walking DraynorBank bank");
			walking.webWalk(areas.DraynorBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500,1000));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
			if(!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500,2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
			}
			if (getBank().getAmount("Coins") >= 18) {
				bank.withdraw("Coins", random(5,18));
				sleep(random(100,300));
			} else {
				onLoop();
			}
			Sleep.waitCondition(() -> inventory.contains("Coins"), 500, 3000);
			bank.close();
		}
	}

	@Override
	public int onLoop() {
		if (getConfigs().get(67) == 3) {
			log("You completed alredy");
			 return -10;
		}
		if (getBank().getAmount("Coins") >= 5) {
			return -1;
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); } // The amount of time in milliseconds before the loop starts over

	
	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString("Time runned " + timers, 0, 11);

	}

}
