package Core.Corebots;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

public class Potatopicker extends Skeleton {

	Areas areas = new Areas();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Potato Picker Has started");
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

	private void Dropeverything() throws InterruptedException {
		log("Inventory not empty");
		if (!Areas.DraynorBank.contains(myPlayer())) {
			walking.webWalk(areas.DraynorBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		Banking();
	}

	private void Banking() throws InterruptedException {
		if (inventory.isEmpty()) {
			log("Inventory empty");
		} else {
			if (!Areas.DraynorBank.contains(myPlayer())) {
				log("Walking Draynor bank");
				walking.webWalk(areas.DraynorBank());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
				bank.depositAll();
				sleep(random(1500, 2200));

				Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
				bank.close();
				Sleep.waitCondition(() -> bank.close(), 500, 3000);
			}
		}
	}

	private void CheckUp() throws InterruptedException {
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}
		if (!inventory.isEmptyExcept("Potato")) {
			log("Not in Draynor");
			Dropeverything();
		} else if (!inventory.isFull() && Areas.PotatofieldBig.contains(myPlayer()) && !myPlayer().isAnimating()
				&& !myPlayer().isMoving()) {
			log("Picking potatos");
			Pickpotatos();
		} else if (inventory.isEmpty() && !Areas.Potatofield.contains(myPlayer())) {
			log("Walking field");
			Walkfield();
		} else if (inventory.isFull()) {
			log("Walking bank");
			Banking();
		}

	}

	private void Pickpotatos() throws InterruptedException {
		int Space = (int) getInventory().getAmount("Potato");
		RS2Object Potatos = getObjects().closest("Potato");
		if (Potatos != null) {
			Potatos.interact("Pick");
			while (true) {
				Sleep.waitCondition(() -> !myPlayer().isMoving() && !myPlayer().isAnimating(), 500, 5000);
				int spacenow = (int) getInventory().getAmount("Potato");
				if (Space != spacenow) {
					break;
				}
			}
		}
	}

	private void Walkfield() throws InterruptedException {
		if (!Areas.PotatofieldBig.contains(myPlayer())) {
			walking.webWalk(Areas.Potatofield());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		}
	}

	@Override
	public int onLoop() {
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); }


}