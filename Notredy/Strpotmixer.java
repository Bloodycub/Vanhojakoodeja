package Notredy;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Support.Areas;

import java.awt.*;

public class Strpotmixer extends Script {
	public Areas area = new Areas();

	@Override
	public void onStart() throws InterruptedException {
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		Checkup();

		return random(690); // The amount of time in milliseconds before the loop starts over

	}

	private void Checkup() throws InterruptedException {
		if (!Areas.LBChest.contains(myPlayer())) {
			log("Walking bank");
			WalkingBank();
		} else if (!(inventory.contains("Strength potion(1)") || inventory.contains("Strength potion(2)")
				|| inventory.contains("Strength potion(3)"))) {
			log("Banking");
			Banking();
		} else if (inventory.contains("Strength potion(1)") || inventory.contains("Strength potion(2)")
				|| inventory.contains("Strength potion(3)")) {
			log("Mixing");
			Mixing();
		}
		log("Loop");
	}

	private void Mixing() throws InterruptedException {
		if(inventory.contains("Strength potion(1)") && inventory.getAmount("Strength potion(1)" ) >= 2) {
		log("Mixing One's");
		sleep(random(500,550));
		getInventory().interact("Use", "Strength potion(1)");
		sleep(random(100, 250));
		int str1 = inventory.getSelectedItemIndex() + 1;
		getInventory().interact(str1, "Use");

		}else if (!inventory.contains("Strength potion(1)") && inventory.contains("Strength potion(2)") && inventory.getAmount("Strength potion(2)") >= 2) {
			log("Mixing Two's");
			sleep(random(500,550));
			getInventory().interact("Use", "Strength potion(2)");
			sleep(random(100, 250));
			int str2 = inventory.getSelectedItemIndex() + 2;
			getInventory().interact(str2, "Use");
		} else if (!inventory.contains("Strength potion(2)") && inventory.contains("Strength potion(3)") && inventory.getAmount("Strength potion(3)" ) >= 2) {
			log("Mixing Three's");
			sleep(random(500,550));
			getInventory().interact("Use", "Strength potion(3)");
			sleep(random(100, 250));
			int str3 = inventory.getSelectedItemIndex() + 2;
			getInventory().interact(str3, "Use");
		}

	}

	private void Banking() throws InterruptedException {
		if (!bank.isOpen()) {
			bank.open();
		}
		if (bank.isOpen()) {
			if (inventory.isEmpty()) {
				sleep(random(300, 830));
				Withdraw();
			} else {
				sleep(random(300, 800));
				bank.depositAll();
				sleep(random(300, 800));
				Withdraw();
			}
		}

	}

	private void Withdraw() throws InterruptedException {
		if (bank.contains("Strength potion(1)") && bank.getAmount("Strength potion(1)" ) > 2) {
			bank.withdrawAll("Strength potion(1)");
			sleep(random(300, 500));
		} else if (bank.contains("Strength potion(2)") && bank.getAmount("Strength potion(2)" ) > 2) {
			bank.withdrawAll("Strength potion(2)");
			sleep(random(300, 500));
		} else if (bank.contains("Strength potion(3)") && bank.getAmount("Strength potion(3)" ) > 2) {
			bank.withdrawAll("Strength potion(3)");
			sleep(random(300, 500));
		}
		bank.close();
	}

	private void WalkingBank() throws InterruptedException {
		if (!Areas.LBChest.contains(myPlayer())) {
			area.LBChest();
			sleep(1500);
		}

	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {

		// This is where you will put your code for paint(s)

	}

}