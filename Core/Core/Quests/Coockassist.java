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

public class Coockassist extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();
	Timer Timerun;


	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
		log("Core Coockassist Quest Has started");
		if (getConfigs().get(29) == 2) {
			onLoop();
		}
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
		
	}

	private void Banking() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking LumbgeBank bank");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Bank for start");
			bank.open();
			sleep(random(500,1000));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500,2200));

				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
			}
			bank.close();
		}
	}

	private void Checkup() throws InterruptedException {
		int Inventorysize = inventory.getEmptySlots();
		if (getConfigs().get(29) == 2) {
			onLoop();
		}
		if (Inventorysize <= 7) {
			log("Dont have space");
			Banking();
		}
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		if (inventory.contains("Bucket of milk") && inventory.contains("Pot of flour") && inventory.contains("Egg")) {
			log("Going to complete");
			Completing();
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Inventory tab not open");
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 500, 5000);
		} else if (!inventory.contains("Pot") && !inventory.contains("Pot of flour")) {
			log("Getting pot");
			GettingPot();
		} else if (!inventory.contains("Bucket") && !inventory.contains("Bucket of milk")) {
			log("Getting Bucket");
			GettingBucket();
		} else if (!inventory.contains("Pot of flour") && inventory.contains("Pot")) {
			log("Getting pot Flour");
			GettingPotFlour();
		} else if (!inventory.contains("Egg")) {
			log("Getting Egg");
			GettingEgg();
		} else if (!inventory.contains("Bucket of milk") && inventory.contains("Bucket")) {
			log("Getting Bucket of milk");
			GettingBucketofmilk();
		}
	}

	private void Completing() throws InterruptedException {
		if (!Areas.Cookroom.contains(myPlayer())) {
			log("Walking to coock");
			walking.webWalk(areas.Cookroom());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
			NPC Coock = getNpcs().closest("Cook");
			if(Coock != null) {
			Coock.interact("Talk-to");
			sleep(random(500,1000));
			log("Going to dialog");
			Sleep.waitCondition(() -> getDialogues().isPendingContinuation(), 500,5000);
			if (getDialogues().completeDialogue("What's wrong?", "I'm always happy to help a cook in distress.","Actually, I know where to find this stuff." 
					, "I'll get right on it.")) {
				Sleep.waitCondition(() -> getConfigs().get(29) == 2,500,5000);
			}
			if(getConfigs().get(29) == 2) {
				onLoop();
			}
		}
	}


	private void GettingBucketofmilk() throws InterruptedException {
		RS2Object Cow = getObjects().closest("Dairy cow");

		if (!Areas.Cow.contains(myPlayer())) {
			log("Walking Milking cow");
			walking.webWalk(areas.Cow());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!inventory.contains("Bucket of milk")) {
			if (Cow != null) {
				log("Milking");
				Cow.interact("Milk");
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> inventory.contains("Bucket of milk"), 500, 5000);
			}
		}
	}

	private void GettingBucket() throws InterruptedException {
		GroundItem Bucket = getGroundItems().closest("Bucket");

		if (!Areas.Seller.contains(myPlayer())) {
			log("Walking Seller");
			walking.webWalk(areas.Seller());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!inventory.contains("Bucket")) {
			if (Bucket != null) {
				log("Taking Bucket");
				Bucket.interact("Take");
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> inventory.contains("Bucket"), 500, 5000);
			}
		}
	}

	private void GettingEgg() throws InterruptedException {
		GroundItem Egg = getGroundItems().closest("Egg");
		GroundItem egg = getGroundItems().closest("egg");
		if (!Areas.Chickenpit.contains(myPlayer()) && !Areas.Eggs.contains(myPlayer())) {
			log("Running to Egg");
			walking.webWalk(areas.Eggs());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Egg != null) {
			log("Taking Egg");
			Egg.interact("Take");
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> inventory.contains("Egg"), 500, 5000);
		} else if (egg != null) {
			egg.interact("Take");
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> inventory.contains("egg"), 500, 5000);
		}
	}

	private void GettingPotFlour() throws InterruptedException {

		if (!Areas.Wheatbig.contains(myPlayer()) &&  !Areas.Mill.contains(myPlayer())&& !Areas.UpperflourBig.contains(myPlayer()) &&  !inventory.contains("Grain") && !inventory.contains("Pot of flour")) {
			walking.webWalk(areas.Wheat());
			log("Walking wheat");
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		RS2Object WheatObj = getObjects().closest("Wheat");
		if (!inventory.contains("Grain") && !inventory.contains("Pot of flour") && Areas.Wheatbig.contains(myPlayer())) {
			WheatObj.interact("Pick");
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> inventory.contains("Grain"), 500, 5000);
		}
		if (!inventory.contains("Grain") && !inventory.contains("Pot of flour") && Areas.UpperflourBig.contains(myPlayer())) {
			log("Out of Grain");
			walking.webWalk(areas.Mill());
			Sleep.waitCondition(() -> (!myPlayer().isMoving()), 500, 5000);
			RS2Object Flour = getObjects().closest("Flour bin");
			Flour.interact("Empty");
			sleep(random(500,1000));
			Sleep.waitCondition(() -> inventory.contains("Pot of flour"), 500, 5000);

		}
		if(!Areas.UpperflourBig.contains(myPlayer()) && inventory.contains("Grain"))  {
			walking.webWalk(areas.Upperflour());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		while (inventory.contains("Grain") && Areas.UpperflourBig.contains(myPlayer())) {
			RS2Object Hopper = getObjects().closest("Hopper");
			Hopper.interact("Fill");
			sleep(random(3800, 4100));
			RS2Object Lever = getObjects().closest("Hopper controls");
			Lever.interact("Operate");
			sleep(random(3800, 4100));
		}


	}

	private void GettingPot() throws InterruptedException {
		GroundItem Pot = getGroundItems().closest("Pot");

		if (!Areas.Cookroom.contains(myPlayer())) {
			walking.webWalk(areas.Cookroom());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!inventory.contains("Pot") && Areas.Cookroom.contains(myPlayer())) {
			if (Pot != null) {
				log("Taking pot");
				Pot.interact("Take");
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> inventory.contains("Pot"), 500, 5000);
			}
		}
	}

	@Override
	public int onLoop() {
		if (getConfigs().get(29) == 2) {
			log("Completed");
			return -10;
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 400+random(100); }
	

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString("Time runned " + Timerun, 0, 11);

	}

}