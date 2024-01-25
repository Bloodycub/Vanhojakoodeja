package Core.Corebots;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class PotofFlour extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		log("Starting Pot of Flour");
		time.Starttime();
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
	}

	private void CheckUp() throws InterruptedException {
		int arvo = 0;
		arvo++;
		if (arvo == 5) {
			log("Looping check up");
			arvo = 0;
		}
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}
		if (!inventory.contains("Grain") && !Areas.Mill.contains(myPlayer()) && !inventory.contains("Pot of flour")
				&& inventory.getAmount("Pot") == 14 && !Areas.Wheat.contains(myPlayer())) {
			log("Walking field");
			WalkingField();
		} else if (Areas.Mill.contains(myPlayer()) && inventory.getAmount("Pot of flour") != 14
				&& !inventory.contains("Grain") && inventory.contains("Pot")) {
			log("in mill");
			gettingFlour();
		} else if (!inventory.contains("Grain") && !inventory.contains("Pot") && inventory.contains("Pot of flour")) {
			log("You got All Flour ");
			BankingTakingpots();
		} else if (inventory.getAmount("Grain") == 14 && Areas.Upperflour.contains(myPlayer())) {
			log("Milling");
			Milling();
		} else if (inventory.getAmount("Pot") != 14 && !inventory.contains("Pot of flour")) {
			log("You are stuck go pick wheat");
			BankingTakingpots();
		} else if (inventory.getAmount("Grain") == 14 && !Areas.Upperflour.contains(myPlayer())) {
			log("Walking mill");
			WalktoMill();
		} else if (inventory.contains("Pot") && inventory.getAmount("Grains") != 14) {
			log("Picking wheat");
			Pickingwheat();
		} else if (inventory.getAmount("Pot of flour") == 14) {
			log("Got flour bank it");
			BankingTakingpots();
		}
	}

	@SuppressWarnings("static-access")
	private void WalktoMill() throws InterruptedException {
		if (!Areas.Upperflour.contains(myPlayer())) {
			walking.webWalk(areas.Upperflour());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	private void ClaimUp() throws InterruptedException {
		objects.closest("Ladder").interact("Climb-up");
		sleep(random(3500, 4000));
		objects.closest("Ladder").interact("Climb-up");
		sleep(random(2500, 3000));
	}

	private void gettingFlour() throws InterruptedException {
		if (!Areas.Mill.contains(myPlayer())) {
			walking.webWalk(areas.Mill());
			Sleep.waitCondition(() -> Areas.Mill.contains(myPlayer()), 500, 5000);
		}
		RS2Object Flour = getObjects().closest("Flour bin");
		log("Eptying Bin");
		Flour.interact("Empty");
		sleep(random(500, 650));
	}

	private void ClaimDown() throws InterruptedException {
		objects.closest("Ladder").interact("Climb-down");
		sleep(random(2500, 3000));
		objects.closest("Ladder").interact("Climb-down");
		sleep(random(2500, 3000));
		gettingFlour();
	}

	private void BankingTakingpots() throws InterruptedException {
		if (inventory.isEmpty() && inventory.getAmount("Pot") == 14) {
			log("Got pots");
		} else {
			if (!Areas.DraynorBank.contains(myPlayer())) {
				log("Walking Draynor bank");
				walking.webWalk(areas.DraynorBank.getRandomPosition());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
				if (bank.isOpen() && bank.getAmount("Pot") < 14) {
					log("Dont got pots");
					onLoop();
				}
				if (!inventory.isEmpty()) {
					bank.depositAll();
					sleep(random(1500, 2200));

					Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
				}
				bank.withdraw("Pot", 14);
				Sleep.waitCondition(() -> inventory.getAmount("Pot") == 14, 500, 3000);
				bank.close();
				Sleep.waitCondition(() -> bank.close(), 500, 3000);
			}
		}
	}

	@SuppressWarnings("static-access")
	private void WalkingField() throws InterruptedException {
		walking.webWalk(areas.Wheat);
		Sleep.waitCondition(() -> (!myPlayer().isMoving()), 3000);
	}

	private void Milling() throws InterruptedException {
		if (!inventory.contains("Grain")) {
			log("Out of Grain");
			walking.webWalk(areas.Mill());
			Sleep.waitCondition(() -> (!myPlayer().isMoving()), 500, 5000);
		}
		while (inventory.contains("Grain")) {
			RS2Object Hopper = getObjects().closest("Hopper");
			Hopper.interact("Fill");
			sleep(random(3800, 4100));
			RS2Object Lever = getObjects().closest("Hopper controls");
			Lever.interact("Operate");
			sleep(random(3800, 4100));
		}
		gettingFlour();
	}

	private void Pickingwheat() throws InterruptedException {
		if (!Areas.Wheatbig.contains(myPlayer())) {
			walking.webWalk(areas.Wheat());
			Sleep.waitCondition(() -> (!myPlayer().isMoving()), 500, 5000);
		}
		RS2Object Wheat = getObjects().closest("Wheat");
		Wheat.interact("Pick");
		sleep(random(2500, 3000));
	}

	@Override
	public int onLoop() {
		if (bank.isOpen() && bank.getAmount("Pot") < 14) {
			return -1;
		}
		try {
			CheckUp();
		} catch (InterruptedException e) {
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

		// This is where you will put your code for paint(s)

	}

}