package Core.Corebots;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class BucketPickerandfiller extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		log("BucketPickerandfiller Has started");
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
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 200, 5000);
		}
		while (settings.areRoofsEnabled() == false) {
			log("Roofs off");
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true,200, 5000);
		}
		if (!(getInventory().contains("Bucket") || getInventory().contains("Bucket of water"))) {
			BankinEvrything();
		} else {
			CheckUp();
		}
	}

	private void CheckUp() throws InterruptedException {
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		if (!Areas.SellerFULL.contains(myPlayer()) && inventory.isEmpty()) {
			log("Empty and in Seller");
			Walkbuget();
		} else if ((!(Areas.SellerFULL.contains(myPlayer()) || Areas.Cookroom.contains(myPlayer())))
				&& !inventory.isEmpty()) {
			log("Your not in LB and your full");
			BankinEvrything();
		} else if (Areas.Seller.contains(myPlayer()) && inventory.isEmptyExcept("Bucket") && !inventory.isFull()) {
			log("Looting Buget");
			Pickbuget();
		} else if (Areas.SellerFULL.contains(myPlayer()) && inventory.isFull() && inventory.contains("Bucket")) {
			log("Filling Buget");
			Fillbuget();
		} else if (inventory.isFull() && !inventory.contains("Bucket")) {
			log("Banking Buget");
			BankinEvrything();
		}
	}

	private void Walkbuget() throws InterruptedException {
		if (!Areas.Cookroom.contains(myPlayer())) {
			walking.webWalk(Areas.Cookroom);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 3000);
		}
		if (Areas.Cookroom.contains(myPlayer())) {
			objects.closest("Trapdoor").interact("Climb-down");
			sleep(random(500,1000));
			Sleep.waitCondition(() -> Areas.SellerFULL.contains(myPlayer()), 200, 5000);
		}
		if (Areas.SellerFULL.contains(myPlayer())) {
			if (!Areas.Seller.contains(myPlayer())) {
				walking.webWalk(Areas.Seller);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 3000);
			}
		}
	}

	private void BankinEvrything() throws InterruptedException {
		if (inventory.isEmpty()) {
			log("Inventory empty");
		} else {
			if (!Areas.LumbgeBank.contains(myPlayer())) {
				log("Walking Lb bank");
				walking.webWalk(areas.LumbgeBank);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 3000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				sleep(random(500,1000));
				Sleep.waitCondition(() -> bank.isOpen(),200, 3000);
			}
			if (bank.isOpen()) {
				bank.depositAll();
				sleep(random(1500,2200));
				Sleep.waitCondition(() -> inventory.isEmpty(),200, 3000);
				bank.close();
				Sleep.waitCondition(() -> bank.close(), 200, 3000);
			}
		}
	}

	private void Fillbuget() throws InterruptedException {
		RS2Object Sink = getObjects().closest("Sink");
		if (!Areas.WaterspotLbSeller.contains(myPlayer())) {
			walking.webWalk(areas.WaterspotLbSeller);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 3000);
		}
		if (inventory.contains("Bucket")) {
			inventory.interact("Use", "Bucket");
			sleep(random(300,700));
			Sleep.waitCondition(() -> getInventory().isItemSelected(), 200,3000);
			Sink.interact("Use");
			sleep(random(300,700));
			Sleep.waitCondition(() -> !inventory.contains("Bucket"), 200, 50000);
		}
	}

	private void Pickbuget() throws InterruptedException {
		GroundItem Bucket = getGroundItems().closest("Bucket");
		if (!inventory.isFull() && Areas.Seller.contains(myPlayer())) {
			if (Bucket != null) {
				log("Taking Bucket");
				Bucket.interact("Take");
				sleep(random(600, 1000));
			} else if (Bucket == null) {
				int Currentworldold;
				int Currentworldnew;
				Currentworldold = getWorlds().getCurrentWorld();
				if (getDialogues().isPendingContinuation()) {
					dialogues.clickContinue();
				} else {
					worlds.hopToF2PWorld();
					Currentworldnew = getWorlds().getCurrentWorld();
					Sleep.waitCondition(() -> Currentworldold != Currentworldnew, 200, 6000);
				}
			}
		}
	}

	@Override
	public int onLoop() {
		time.Timerun();
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 400+random(100);
	}
	@Override
	public void onExit() {
		log("Good Bye");
		stop();
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);
	}
}
