package Core.Corebots;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;
import java.awt.*;

public class MonymMakingwoodcutter extends Skeleton {

	Areas areas = new Areas();
	Timer Timerun;
	private int runspeed = random(20, 37);
	private int moveoutornot;
	private String[] axe = { "Rune axe", "Adamant axe", "Mithril axe", "Steel axe", "Iron axe", "Bronze axe" };
	private String[] AxeForUpgrade = { "Mithril axe", "Adamant axe", "Rune axe" };
	private boolean[] Gotaxes = { false, false, false };
	private int[] AxeLevels = { 41, 31, 6, 1, 1, 1 };
	int logsamount = 0;
	private int WC;// Woodcutting
	private MouseTrail trail = new MouseTrail(0, 255, 255, 6000, this);
	private MouseCursor cursor = new MouseCursor(52, 2, Color.RED, this);

	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
		log("Woodcutting monymaker Has started");
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
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 200, 5000);
		}
	}

	private void Checkup() throws InterruptedException {
		WC = skills.getStatic(Skill.WOODCUTTING);
		Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}if (!tabs.isOpen(Tab.INVENTORY)) {
		log("Wrong tab");
		tabs.open(Tab.INVENTORY);
		Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 200, 3000);
		}if (!inventory.contains(axe)) {
			log("Banking In check up");
			Banking();
		} else if (settings.getRunEnergy() >= runspeed && getSettings().isRunning() == false) {
			runspeed = random(20, 37);
			log("My runspeed SET AT " + runspeed);
			settings.setRunning(true);
			Sleep.waitCondition(() -> settings.isRunning(), 200, 3000);
		} else if (logsamount >= 350) {
			log("Woo upgreading");
			Upgradeing();
		} else if (inventory.contains(axe)) {
			log("Cutting method start");
			CutWood();
		}
	}

	@SuppressWarnings("static-access")
	private void Upgradeing() throws InterruptedException {
		if (!Areas.Ge.contains(myPlayer())) {
			log("Walking Ge bank");
			walking.webWalk(areas.Ge);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!bank.isOpen()) {
			log("Upgrading?");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 200, 3000);
		}
		if (bank.isOpen()) {
			logsamount = (int) bank.getAmount("Logs");
			widgets.interact(12, 23, 4, "Note");
			sleep(random(600, 1000));
			bank.withdrawAll("Logs");
			sleep(random(200, 400));
			Sleep.waitCondition(() -> inventory.contains("Logs"), 200, 3000);
			bank.withdrawAll("Coins");
			sleep(random(200, 400));
			Sleep.waitCondition(() -> inventory.contains("Coins"), 200, 3000);
			bank.close();
			sleep(random(400, 800));
			Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000);
		}
		if (inventory.contains("Logs")) {
			NPC Ex = getNpcs().closest("Grand Exchange Clerk");
			if (Ex != null) {
				Ex.interact("Exchange");
				sleep(random(200, 400));
				Sleep.waitCondition(() -> grandExchange.isOpen(), 200, 5000);
				if (grandExchange.isOpen()) {
					grandExchange.sellItem(1512, 20, logsamount);
					sleep(random(250, 700));
					grandExchange.confirm();
					sleep(random(1050, 3000));
					grandExchange.collect();
					sleep(random(450, 800));
					logsamount = 0;
					for (int i = 0; i < Gotaxes.length; i++) {
						if (Gotaxes[i] == false) {
					BuyAxe();
						}
					}
				}
			}

		}
	}

	private void BuyAxe() throws InterruptedException {
		if (inventory.contains("Coins")) {
			log("Buying");
			log("If status Flase meaning dont have item");
			log(Gotaxes + "Status");
			sleep(random(600, 1000));
			if (widgets.isVisible(465, 2, 5)) {
				for (int i = 0; i < Gotaxes.length; i++) {
					int Coins = (int) inventory.getAmount("Coins");
					if (Gotaxes[i] == false) {
						widgets.interact(465, 7, 26, "Create Buy offer");
						sleep(random(200, 400));
						Sleep.waitCondition(() -> widgets.isVisible(465, 24), 200, 5000);
						sleep(random(600, 1000));
						log("Typing");
						getKeyboard().typeString(AxeForUpgrade[i], false);
						sleep(random(1000, 2080));
						log("Enter");
						getKeyboard().typeEnter();
						sleep(random(200, 580));
						widgets.interact(465, 24, 52, "Enter price");
						sleep(random(50, 150));
						Sleep.waitCondition(() -> widgets.isVisible(162, 40), 200, 5000);
						getKeyboard().typeString("" + Coins, false);
						sleep(random(1000, 2080));
						log("Enter");
						getKeyboard().typeEnter();
						sleep(random(400, 600));
						log("Confirming");
						grandExchange.confirm();
						sleep(random(1200, 2000));
						log("Colecting");
						grandExchange.collect();
						sleep(random(200, 400));
						final int u = i;
						log("Sleeping");
						Sleep.waitCondition(() -> inventory.contains(AxeForUpgrade[u]), 200, 3000);
						log("Done sleeping");
					}
						
					}
					
				}
			}
			Banking();
		}
	

	@SuppressWarnings("static-access")
	private void CutWood() throws InterruptedException {
		RS2Object tree = getObjects().closest("Tree");
		if (inventory.isFull()) {
			log("Banking Logs");
			BankingWood();
		}
		if (!Areas.Varrockwoodcutting.contains(myPlayer())) {
			log("Walking Tree");
			walking.webWalk(areas.Varrockwoodcutting());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!Areas.Varrockwoodcutting.contains(tree)) {
			log("Getting random spot");
			walking.webWalk(areas.Varrockwoodcutting());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null
				&& Areas.Varrockwoodcutting.contains(tree)) {
			tree.interact("Chop down");
			sleep(random(300, 732));
			Sleep.waitCondition(() -> !myPlayer().isMoving() && myPlayer().isAnimating(), 150, 5000);
			if (dialogues.inDialogue()) {
				log("In dialog interact");
				tree.interact("Chop down");
				sleep(random(600, 932));
				Sleep.waitCondition(() -> !myPlayer().isMoving() && myPlayer().isAnimating(), 150, 5000);
			}
			Antiban();
			Sleep.waitCondition(() -> !myPlayer().isAnimating() || dialogues.inDialogue() || tree == null, 150, 50000);
		}
	}

	private void Antiban() throws InterruptedException {
		moveoutornot = random(0, 15);
		if (moveoutornot == 4 || moveoutornot == 5) {
			log("Moving out");
			mouse.moveOutsideScreen();
		}
	}

	@SuppressWarnings("static-access")

	private void BankingWood() throws InterruptedException {
		if (!Areas.Ge.contains(myPlayer())) {
			log("Walking Ge bank");
			walking.webWalk(areas.Ge);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Bank for start");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 200, 3000);
		}
		if (bank.isOpen()) {
			if (bank.contains("Mithril axe")) {
				Gotaxes[0] = true;
			}
			if (bank.contains("Adamant axe")) {
				Gotaxes[1] = true;
			}
			if (bank.contains("Rune axe")) {
				Gotaxes[2] = true;
			}
			logsamount = (int) bank.getAmount("Logs");
			bank.depositAll();
			sleep(random(1500, 2200));
			if (logsamount >= 250) {
				log("Woo upgreading");
				Upgradeing();
			}
			Sleep.waitCondition(() -> !inventory.contains("Logs"), 200, 3000);
			WC = skills.getStatic(Skill.WOODCUTTING);
			for (int i = 0; i < axe.length; i++) {
				log("Looping axes");
				if (WC >= AxeLevels[i]) {
					if (inventory.contains(axe[i])) {
						break;
					}
					if (bank.contains(axe[i])) {
						if(!inventory.isEmpty()) {
						bank.depositAll();
						sleep(random(1500, 2200));
						}
						log("Withdrawing axe");
						bank.withdraw(axe[i], 1);
						sleep(random(400, 800));
						Sleep.waitCondition(() -> inventory.contains(axe), 200, 3000);
						bank.close();
						Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000);
						break;
					}
				}
			}
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000);
		}
	}

	/*
	 * private void Camera() { log("Moving camera angle"); int targetPitch =
	 * random(50, 67); log(targetPitch); camera.movePitch(targetPitch); }
	 */
	@SuppressWarnings("static-access")

	public void Banking() throws InterruptedException {
		if (!Areas.Ge.contains(myPlayer())) {
			log("Walking Ge bank");
			walking.webWalk(areas.Ge);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Bank open");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (bank.contains("Mithril axe")) {
				Gotaxes[0] = true;
			}
			if (bank.contains("Adamant axe")) {
				Gotaxes[1] = true;
			}
			if (bank.contains("Rune axe")) {
				Gotaxes[2] = true;
			}
			logsamount = (int) bank.getAmount("Logs");
			if (!inventory.isEmpty()) {
				log("Deposite all");
				bank.depositAll();
				sleep(random(800, 1200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				bank.depositWornItems();
				sleep(random(800, 1200));
			}
			if (!equipment.isWieldingWeaponThatContains("axe")) {
				WC = skills.getStatic(Skill.WOODCUTTING);
				for (int i = 0; i < axe.length; i++) {
					log("Looping axes");
					if (WC >= AxeLevels[i]) {
						if (bank.contains(axe[i])) {
							log("Withdrawing axe");
							bank.withdraw(axe[i], 1);
							sleep(random(400, 800));
							Sleep.waitCondition(() -> inventory.contains(axe), 200, 3000);
							bank.close();
							Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public int onLoop() {
		if (dialogues.isPendingContinuation()) {
			log("Stuck");
			dialogues.clickContinue();
			try {
				sleep(random(400, 800));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				sleep(random(200, 400));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 400 + random(100);
	}

	@Override
	public void onExit() {
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString("Time runned " + Timerun, 0, 11);
		trail.paint(g);
		cursor.paint(g);

	}

}
