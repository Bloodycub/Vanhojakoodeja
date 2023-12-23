package Onshelf;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;
import Core.Support.Time;
import Support.*;

import java.awt.*;

public class Potpicker extends Script {
	int Inventoryspace;
	int Pots;
	Areas areas = new Areas();
	Time time = new Time();
// add pending request

	@Override
	public void onStart() throws InterruptedException {
		log("Pot picker Starting");
		if (!inventory.contains("Pot")) {
			Bankeverything();
		}
	}

	private void CheckUp() throws InterruptedException {
		GroundItem Pot = getGroundItems().closest("Pot");
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
		walking.webWalk(areas.Cookroom);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
	}

	private void Lootingpot() throws InterruptedException {
		GroundItem Pot = getGroundItems().closest("Pot");
		if (Pot != null) {
			log("Taking pot");
			Pot.interact("Take");
			Sleep.waitCondition(() -> Pot == null, 200,3000);
			if(inventory.isFull()) {
				Bankeverything();
			}
			log("Pot Null");
			Worldhop();
		}else {
			log("Pot Null");
			Worldhop();
		}
	}

	private void Bankeverything() throws InterruptedException {
			if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
				log("Walking Lb bank");
				walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			if (!bank.isOpen()) {
				log("Open bank for Bank for start");
				bank.open();
				Pots = (int) bank.getAmount("Pot");
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
					bank.depositAll();
					Sleep.waitCondition(() -> inventory.isEmpty(), 500,3000);
				}
			bank.close();
		}

	@Override
	public int onLoop() throws InterruptedException {
		CheckUp();

		return random(1000); // The amount of time in milliseconds before the loop starts over

	}

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
		Inventoryspace = getInventory().getEmptySlots();
	}

}