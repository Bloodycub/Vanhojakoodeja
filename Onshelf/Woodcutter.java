package Onshelf;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;
import Core.Support.Time;
import Core.Support.dropper;
import Support.Areas;

public class Woodcutter extends Script {
	private String[] axe = { "Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe",
			"Dragon axe" };
	final String[] typesOfLogs = { "Logs", "Oak logs", "Willow logs", "Maple logs", "Mahogany logs", "Yew logs",
			"Magic logs" };
	final String[] treetype = { "tree", "Willow", "Oak", "Maple", "Mahogany", "Yew", "Magic" };

	private int runspeed = random(20, 37);
	private final int Logslevel = random(17, 20);
	private final int Oaklevel = random(35, 38);
	private final int Willowlevel = random(61, 65);
	Areas areas = new Areas();
	Time time = new Time();
	dropper drop;
	private int WC;// Woodcutting
	private int moveoutornot;

	// value tracker
	// Ge buying rune axe

	@Override
	public void onStart() throws InterruptedException {
		log("Start up");
		time.Starttime();
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		while (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}
		if(getInventory().isEmptyExcept(axe) || getInventory().isEmptyExcept(typesOfLogs)) {
			log("Banking");
			Banking();
		}
	}

	public void Checkup() throws InterruptedException {
		if (inventory.isFull() && (inventory.contains("Logs") || inventory.contains("Oak logs"))) {
			log("Dropping wood");
			DropingWood();
		} else if (inventory.isFull() && inventory.contains("Willow logs")) {
			log("Banking willow");
			bankingWillow();
		} else if (settings.getRunEnergy() >= runspeed && getSettings().isRunning() == false) {
			log("Too High energy");
			settings.setRunning(true);
			Sleep.waitCondition(() -> settings.isRunning(), 200, 3000);
		} else if (!inventory.contains(axe) && !inventory.contains(typesOfLogs)
				&& !equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")) {
			log("Walking to bank with inventory not empty");
			Banking();
		} else if (inventory.contains(axe) && !equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")) {
			log("Equping");
			Equipping();
		} else if (inventory.contains(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")) {
			log("Checking level");
			levelcheck();
		} else if (camera.getPitchAngle() < 50) {
			log("Doing camera");
			Camera();
		}
	}

	private void Camera() {
		log("Moving camera angle");
		int targetPitch = random(50, 67);
		log(targetPitch);
		camera.movePitch(targetPitch);
	}

	private void Antiban() throws InterruptedException {
		moveoutornot = random(0, 6);
		if (moveoutornot == 4 || moveoutornot == 5) {
			log("Moving out");
			mouse.moveOutsideScreen();
		}

	}

	private void levelcheck() throws InterruptedException {
		if (WC < Logslevel) {
			CutWood();
		} else if (WC >= Logslevel && WC < Oaklevel) {
			CuttingOak();
		} else if (WC < 60 && WC >= Oaklevel) {
			CutWillow();
		} else if (WC >= Willowlevel) {
			Upgrade();
		} else if (WC >= Willowlevel) {
			CutYew();
		}
	}

	private void Upgrade() throws InterruptedException {
		if (inventory.isEmptyExcept(axe)
				|| equipment.isWearingItem(EquipmentSlot.WEAPON, "Rune axe") && WC >= Willowlevel) {
			CutYew();
		} else {
			if (!Areas.VarrocGrandextange.contains(myPlayer())) {
				WalkingGe();
			} else if (Areas.VarrocGrandextange.contains(myPlayer())) {
				Withdrawwillows();
			} else if (inventory.contains("Willow logs") && Areas.VarrocGrandextange.contains(myPlayer())) {
				Buyeruneaxe();
			}
		}

	}

	private void Buyeruneaxe() {
		log("Buing rune axe");

	}

	private void Withdrawwillows() throws InterruptedException {
		log("Withdrawwillows");
		if (!bank.isOpen()) {
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 200, 3000);
		}
		bank.depositAll();
		Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000);
		bank.notifyAll();
		sleep(random(500, 1000));
		bank.withdrawAll("Willow logs");
		Sleep.waitCondition(() -> inventory.contains("Willow logs"), 200, 3000);
		bank.close();
		Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000);
	}

	private void WalkingGe() throws InterruptedException {
		walking.webWalk(areas.VarrocGrandextange());
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
	}

	private void CutYew() throws InterruptedException {
		RS2Object tree = getObjects().closest("Yew");
		if (inventory.isFull() && Areas.RimmingtonYewSpot.contains(myPlayer())) {
			log("Banking yew");
			bankingYew();
		}
		if (!Areas.RimmingtonYewSpot.contains(myPlayer())) {
			log("Walking Yew Spot");
			walking.webWalk(areas.RimmingtonYewSpot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);

		}
		if (Areas.RimmingtonYewSpot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting yew");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
				Antiban();
				Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
			}
		}
	}

	private void bankingYew() throws InterruptedException {
		if (!Areas.PortBank.contains(myPlayer()) && inventory.isFull()) {
			walking.webWalk(areas.PortBankSpot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!depositBox.isOpen()) {
			depositBox.open();
			Sleep.waitCondition(() -> getDepositBox().isOpen(), 500, 5000);

		}
		if (depositBox.isOpen()) {
			getDepositBox().depositAll("Yew logs");
			Sleep.waitCondition(() -> !inventory.contains("Yew logs"), 500, 5000);
			depositBox.close();
			Sleep.waitCondition(() -> !getDepositBox().isOpen(), 500, 5000);
		}
		if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
			walking.webWalk(areas.RimmingtonYewSpot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}

	}

	private void CutWillow() throws InterruptedException {
		RS2Object tree = getObjects().closest("Willow");
		if (inventory.contains("Oak logs") || inventory.contains("Logs")) {
			DropingWood();
		}
		if (inventory.isFull() && Areas.PortWillowspot.contains(myPlayer())) {
			log("Banking Willow");
			bankingWillow();
		}
		if (!Areas.PortWillowspot.contains(myPlayer())) {
			log("Walking Willowspot");
			walking.webWalk(areas.PortWillowspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.PortWillowspot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting Willow");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
				Antiban();
				Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
			}
		}
	}

	private void bankingWillow() throws InterruptedException {
		if (!Areas.PortBank.contains(myPlayer()) && inventory.isFull()) {
			log("Walking bankspot");
			walking.webWalk(areas.PortBankSpot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!depositBox.isOpen()) {
			log("Opening deposit box");
			depositBox.open();
			Sleep.waitCondition(() -> getDepositBox().isOpen(), 500, 5000);
		}
		if (depositBox.isOpen()) {
			log("Box is open");
			getDepositBox().depositAllExcept(axe);
			Sleep.waitCondition(() -> !inventory.isEmptyExcept(axe), 500, 5000);
			depositBox.close();
			Sleep.waitCondition(() -> !getDepositBox().isOpen(), 500, 5000);
		}
		if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
			log("Walking willowspot");
			walking.webWalk(areas.PortWillowspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}

	}

	private void CuttingOak() throws InterruptedException {
		RS2Object tree = getObjects().closest("Oak");
		if (inventory.isFull() && Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Dropping Oak");
			DropingWood();
		}
		if (!Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Walking Oak");
			walking.webWalk(areas.FaladorWallcuttingspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.FaladorWallcuttingspot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting Oak");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
				Antiban();
				Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
			}
		}
	}

	private void DropingWood() throws InterruptedException {
		log("Droping Wood");
		String[] logs = { "Logs", "Oak logs" };
		while (inventory.contains(logs)) {
			log("Droping log");
			inventory.interact("Drop", logs);
			sleep(random(600, 769));
			if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
				break;
			}
		}
	}

	private void CutWood() throws InterruptedException {
		RS2Object tree = getObjects().closest("Tree");
		if (inventory.isFull() && Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Droping Logs");
			DropingWood();
		}
		if (!Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Walking Tree");
			walking.webWalk(areas.FaladorWallcuttingspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!Areas.FaladorWallcuttingspot.contains(tree)) {
			log("Getting random spot");
			walking.webWalk(areas.FaladorWallcuttingspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		} else if (Areas.FaladorWallcuttingspot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting Tree");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null
					&& Areas.FaladorWallcuttingspot.contains(tree)) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
				Antiban();
				Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
			}
		}
	}

	private void Equipping() throws InterruptedException {
		inventory.interact("Wield", "Bronze axe");
		Sleep.waitCondition(() -> getEquipment().isWieldingWeapon("Bronze axe"), 1000, 5000);
	}

	public void Banking() throws InterruptedException {
		if (!Banks.DRAYNOR.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(Banks.DRAYNOR.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Bank for start");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (inventory.isEmpty()) {
				getBank().withdraw("Bronze axe", 1);
				Sleep.waitCondition(() -> inventory.contains("Bronze axe"), 500, 3000);
				bank.close();
				Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
			} else {
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				getBank().withdraw("Bronze axe", 1);
				Sleep.waitCondition(() -> inventory.contains("Bronze axe"), 500, 3000);
				bank.close();
			}
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		Checkup();

		return 1000;
	}

	@Override
	public void onExit() {
		log("GOOD BYE");
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);
		WC = skills.getStatic(Skill.WOODCUTTING);
	}
}
