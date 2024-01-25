package Core.Corebots;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class Potpicker extends Skeleton {
	int Inventoryspace;
	int Pots;
	Areas areas = new Areas();
	Time time = new Time();
// add pending request

	@Override
	public void onStart() throws InterruptedException {
		log("Pot picker Starting");
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		while (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(400,800));
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
		if (!inventory.contains("Pot")) {
			Bankeverything();
		}
	}

	private void CheckUp() throws InterruptedException {
		Inventoryspace = getInventory().getEmptySlots();
		GroundItem Pot = getGroundItems().closest("Pot");
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}
		if (Areas.Cookroom.contains(myPlayer()) && inventory.isEmptyExcept("Pot") && Inventoryspace != 0) {
			log("looting pot");
			Lootingpot();
		} else if (Areas.Cookroom.contains(myPlayer()) && Inventoryspace == 0) {
			tabs.open(Tab.INVENTORY);
			log("Banking");
			Bankeverything();
		} else if (!Areas.Cookroom.contains(myPlayer()) && inventory.isEmptyExcept("Pot")) {
			log("Walking coockroom");
			Walkingpot();
		} else if (Areas.Cookroom.contains(myPlayer()) && !inventory.isFull() && Pot == null) {
			log("Hopping");
			Worldhop();
		} else if (!inventory.isEmptyExcept("Pot")) {
			log("Banking all");
			Bankeverything();
		}
	}

	private void Worldhop() throws InterruptedException {
		int Currentworldold;
		int Currentworldnew;
		Currentworldold = getWorlds().getCurrentWorld();
		if (getDialogues().isPendingContinuation()) {
			dialogues.clickContinue();
		} else {
			worlds.hopToF2PWorld();
			Currentworldnew = getWorlds().getCurrentWorld();
			Sleep.waitCondition(() -> Currentworldold != Currentworldnew, 500, 6000);
		}
	}

	private void Walkingpot() throws InterruptedException {
		if (!Areas.Cookroom.contains(myPlayer())) {
			walking.webWalk(areas.Cookroom);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	private void Lootingpot() throws InterruptedException {
		GroundItem Pot = getGroundItems().closest("Pot");
		if (Pot != null) {
			log("Taking pot");
			Pot.interact("Take");
			Sleep.waitCondition(() -> Pot == null, 200, 3000);
			if (inventory.isFull()) {
				Bankeverything();
			}
			log("Pot Null");
			Worldhop();
		} else {
			log("Pot Null");
			Worldhop();
		}
	}

	private void Bankeverything() throws InterruptedException {
		if (inventory.isEmpty()) {
			log("Inventory empty");
		} else {
			if (!Areas.LumbgeBank.contains(myPlayer())) {
				log("Walking Lb bank");
				walking.webWalk(areas.LumbgeBank);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			if (!bank.isOpen()) {
				log("Open bank for Bank for start");
				bank.open();
				sleep(random(500, 1000));
				Pots = (int) bank.getAmount("Pot");
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
				bank.depositAll();
				sleep(random(1500, 2200));

				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
			}
			bank.close();
		}
	}

	@Override
	public int onLoop() {
		try {
			CheckUp();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 400+random(100); }
	

	@Override
	public void onExit() {
		log("Good Bye");
		stop();

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {
		g.drawString("Inventory size left  " + Inventoryspace, 0, 11);
		g.drawString("Total amount " + Pots, 0, 21);
		g.setColor(Color.decode("#00ff04")); // Color
	}

}