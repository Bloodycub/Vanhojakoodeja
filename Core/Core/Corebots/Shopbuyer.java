package Core.Corebots;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;


public class Shopbuyer extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		log("Shopbuyer Has started");
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

	@Override
	public int onLoop() {
		if (bank.isOpen() && bank.getAmount("Coins") < 100 || inventory.getAmount("Coins") < 100)  {
			return -1;
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); }

	

	private void Checkup() throws InterruptedException {
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}
		if (inventory.isFull()) {
			log("Inventory full go bank");
			Banking();
		} else if (Areas.PortShop.contains(myPlayer()) && inventory.getAmount("Coins") > 100) {
			log("Buy shop");
			Buying();
		} else if (!inventory.contains("Coins")) {
			log("Getting Coins");
			Getcoins();
		} else if (!Areas.PortShop.contains(myPlayer())) {
			log("Walk shop");
			Walking();
		} else if (inventory.getAmount("Coins") < 100) {
			onLoop();
		}
	}

	private void Checkinventory() throws InterruptedException {
		if (inventory.isFull()) {
			Checkup();
		}
	}

	private void Buying() throws InterruptedException {
		NPC shopper = getNpcs().closest("Wydin");
		shopper.interact("Trade");
		sleep(random(300,600));
		Sleep.waitCondition(() -> store.isOpen(), 3000);
		if (store.getAmount("Pot of flour") >= 1) {
			store.buy("Pot of flour", 5);
			Sleep.waitCondition(() -> store.getAmount("Pot of flour") == 0, 3000);
			Checkinventory();
		}
		if (store.getAmount("Redberries") >= 1) {
			store.buy("Redberries", 5);
			Sleep.waitCondition(() -> store.getAmount("Redberries") == 0, 3000);
			Checkinventory();
		}
		if (store.getAmount("Chocolate bar") >= 1) {
			store.buy("Chocolate bar", 5);
			Sleep.waitCondition(() -> store.getAmount("Chocolate bar") == 0, 3000);
			Checkinventory();
		}
		if (store.getAmount("Cheese") >= 1) {
			store.buy("Cheese", 5);
			Sleep.waitCondition(() -> store.getAmount("Cheese") == 0, 3000);
			Checkinventory();
		}
		if (store.getAmount("Tomato") >= 1) {
			store.buy("Tomato", 5);
			Sleep.waitCondition(() -> store.getAmount("Tomato") == 0, 3000);
			Checkinventory();
		}
		store.close();
		Sleep.waitCondition(() -> !store.isOpen(), 3000);
		int Currentworldold;
		int Currentworldnew;
		Currentworldold = getWorlds().getCurrentWorld();
		if (getDialogues().isPendingContinuation()) {
			dialogues.clickContinue();
		} else {
			log("Hopping world");
			worlds.hopToF2PWorld();
			Currentworldnew = getWorlds().getCurrentWorld();
			Sleep.waitCondition(() -> Currentworldold != Currentworldnew, 300, 15000);
		}
	}

	private void Walking() throws InterruptedException {
		if (!Areas.PortShop.contains(myPlayer()))
			walking.webWalk(areas.PortShop());
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
	}

	private void Banking() throws InterruptedException {
		if (!Areas.PortBank.contains(myPlayer())) {
			walking.webWalk(areas.PortBankSpot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!depositBox.isOpen()) {
			depositBox.open();
			sleep(random(1500,2200));
			Sleep.waitCondition(() -> depositBox.isOpen(), 3000);
			depositBox.depositAllExcept("Coins");
			sleep(random(700,1200));
			Sleep.waitCondition(() -> inventory.isEmptyExcept("Coins"), 500, 5000);
			depositBox.close();
			Sleep.waitCondition(() -> !depositBox.isOpen(), 3000);
		}
	}

	private void Getcoins() throws InterruptedException {
		if (!Areas.DraynorBank.contains(myPlayer())) {
			log("Not in bank");
			walking.webWalk(areas.DraynorBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 500, 3000);
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
			}
			if (bank.getAmount("Coins") < 100) {
				onLoop();
			}
			bank.withdrawAll("Coins");
			Sleep.waitCondition(() -> inventory.contains("Coins"), 3000);
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 3000);
		}
	}
}
