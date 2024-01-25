package Notredy;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;

import java.awt.*;

public class JugFiiller extends Script {
	Areas areas = new Areas();
	GetLevels levels = new GetLevels();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		while (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
		}
	}

	private void Checkup() throws InterruptedException {
		if (!inventory.contains("Jug of water") && !inventory.contains("Jug")) {
			log("Going flador and getting jug");
			BankingJugs();
		} else if (inventory.contains("Jug")) {
			log("Filling");
			FillingJugs();
		} else if (inventory.contains("Jug of water") && !inventory.contains("Jug")) {
			log("inventory is full");
			BankingJugs();
		}

	}

	private void BankingJugs() throws InterruptedException {
		int jugs = 0;
		if (!Areas.FaladorWestBank.contains(myPlayer())) {
			walking.webWalk(areas.FaladorWestBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,5000);
		}
		if (!bank.isOpen()) {
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
			jugs = (int) bank.getAmount("Jug");
			if (!inventory.isEmpty()) {
				bank.depositAll();
			}
			if (inventory.isEmpty() && jugs > 28) {
				bank.withdrawAll("Jug");
				Sleep.waitCondition(() -> inventory.isFull(), 3000);
			} else {
				stop();
			}
			bank.close();

		}
	}

	@SuppressWarnings("static-access")
	private void FillingJugs() throws InterruptedException {
		if (!Areas.FaladorWestbankWaterspot.contains(myPlayer())) {
			walking.webWalk(areas.FaladorWestbankWaterspot);
			sleep(random(1500, 2000));
		}
		RS2Object Sink = getObjects().closest("Waterpump");
		if (inventory.contains("Jug")) {
			inventory.interact("Use", "Jug");
			Sleep.waitCondition(() -> inventory.isItemSelected(), 3000);
			Sink.interact("Use");
			Sleep.waitCondition(() -> !inventory.contains("Jug"), 700, 30000);
		}

	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		Checkup();

		return random(1000); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);

	}

}