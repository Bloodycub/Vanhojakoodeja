package Core.Corebots;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

public class Woodcutter extends Skeleton {
	private String[] axe = { "Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe",
			"Dragon axe" };
	final String[] typesOfLogs = { "Logs", "Oak logs", "Willow logs", "Maple logs", "Mahogany logs", "Yew logs",
			"Magic logs" };
	final String[] treetype = { "tree", "Willow", "Oak", "Maple", "Mahogany", "Yew", "Magic" };

	private int runspeed = random(19, 53);
	Areas areas = new Areas();
	Time time = new Time();
	private int WC;// Woodcutting
	private int moveoutornot;
	int Logslevel = random(17, 20);

	// value tracker
	// Ge buying rune axe

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Woodcutter Has started");
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
		if (inventory.isEmpty() && (!(equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")
				|| equipment.isWearingItem(EquipmentSlot.WEAPON, "Iron axe")))) {
			log("Banking On start");
			Banking();
		}
	}

	public void Checkup() throws InterruptedException {
		Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
		WC = skills.getStatic(Skill.WOODCUTTING);
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
		}
		if (!equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")
				&& !equipment.isWearingItem(EquipmentSlot.WEAPON, "Iron axe")) {
			log("Banking In check up");
			Banking();
		} else if (!inventory.contains(typesOfLogs) && !inventory.isEmpty()) {
			log("Your Carrying somthing but dont have logs");
			Banking();
		} else if (settings.getRunEnergy() >= runspeed && getSettings().isRunning() == false) {
			runspeed = random(20, 37);
			log("My runspeed SET AT " + runspeed);
			settings.setRunning(true);
			Sleep.waitCondition(() -> settings.isRunning(), 200, 3000);
		} else if (camera.getPitchAngle() < 50) {
			log("Doing camera");
			Camera();
		} else if (equipment.isWearingItemThatContains(EquipmentSlot.WEAPON, "Bronze axe")
				|| equipment.isWearingItemThatContains(EquipmentSlot.WEAPON, "Iron axe")) {
			log("Checking level");
			levelcheck();
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
		WC = skills.getStatic(Skill.WOODCUTTING);
		log(WC + " my level " + Logslevel + " Requared");

	/*	if (WC >= Logslevel && (!(inventory.contains("Iron axe")
				|| equipment.isWearingItemThatContains(EquipmentSlot.WEAPON, "Iron axe")))) {
			log("Upgrading");
			Axeupgrade();
		} */
		if (WC >= Logslevel) {
			CuttingOak();
		} else if (WC < Logslevel) {
			CutWood();
		}
	}

	private void Axeupgrade() throws InterruptedException {
		Boolean AdventurerJonExsist = true;

		if (AdventurerJonExsist == true) {
			log("Cheking upgrades");
			NPC Upgrade = getNpcs().closest("Adventurer Jon");
			if (!Areas.SwordUpgrade.contains(myPlayer())) {
				getTabs().open(Tab.SKILLS);
				if (tabs.isOpen(Tab.SKILLS)) {
					getMagic().castSpell(Spells.NormalSpells.LUMBRIDGE_TELEPORT, "Cast");
					sleep(1500);
					Sleep.waitCondition(() -> Areas.LumbgeSpawn.contains(myPlayer()), 1000, 25000);
				}
				log("Walking to Upgrade");
				walking.webWalk(areas.SwordUpgrade);
				sleep(random(2000, 3000));
			}
			if (Upgrade != null) {
				log("not null");
				Upgrade.interact("Claim");
				sleep(random(2000, 4000));
				if (dialogues.isPendingContinuation()) {
					dialogues.completeDialogue("");
				}
				log("Pending option");
				// dialogues.completeDialogue("I'd like to claim my Adventure Path rewards.");
				log("Cheking upgrades");
			} else {
				log("Jon null");
				AdventurerJonExsist = false;
			}

		}
	}

	/*
	 * private void Upgrade() throws InterruptedException { if
	 * (inventory.isEmptyExcept(axe) ||
	 * equipment.isWearingItem(EquipmentSlot.WEAPON, "Rune axe")) { CutYew(); } else
	 * { if (!Areas.VarrocGrandextange.contains(myPlayer())) { WalkingGe(); } else
	 * if (Areas.VarrocGrandextange.contains(myPlayer())) { Withdrawwillows(); }
	 * else if (inventory.contains("Willow logs") &&
	 * Areas.VarrocGrandextange.contains(myPlayer())) { Buyeruneaxe(); } }
	 * 
	 * }
	 * 
	 * private void Buyeruneaxe() { log("Buing rune axe"); }
	 * 
	 * private void Withdrawwillows() throws InterruptedException {
	 * log("Withdrawwillows"); if (!bank.isOpen()) { bank.open();
	 * Sleep.waitCondition(() -> bank.isOpen(), 200, 3000); } bank.depositAll();
	 * Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000); bank.notifyAll();
	 * sleep(random(500, 1000)); bank.withdrawAll("Willow logs");
	 * Sleep.waitCondition(() -> inventory.contains("Willow logs"), 200, 3000);
	 * bank.close(); Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000); }
	 * 
	 * private void WalkingGe() throws InterruptedException {
	 * walking.webWalk(areas.VarrocGrandextange()); Sleep.waitCondition(() ->
	 * !myPlayer().isMoving(), 500, 5000); }
	 * 
	 * private void CutYew() throws InterruptedException { RS2Object tree =
	 * getObjects().closest("Yew"); if (inventory.isFull() &&
	 * Areas.RimmingtonYewSpot.contains(myPlayer())) { log("Banking yew");
	 * bankingYew(); } if (!Areas.RimmingtonYewSpot.contains(myPlayer())) {
	 * log("Walking Yew Spot"); walking.webWalk(areas.RimmingtonYewSpot());
	 * Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
	 * 
	 * } if (Areas.RimmingtonYewSpot.contains(myPlayer()) &&
	 * (inventory.isEmptyExcept(axe) ||
	 * equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
	 * log("Cutting yew"); if (!myPlayer().isAnimating() && !myPlayer().isMoving()
	 * && tree != null) { tree.interact("Chop down"); sleep(random(2500, 2932));
	 * Antiban(); Sleep.waitCondition(() -> !myPlayer().isAnimating(), 1000, 50000);
	 * } } }
	 * 
	 * private void bankingYew() throws InterruptedException { if
	 * (!Areas.PortBank.contains(myPlayer()) && inventory.isFull()) {
	 * walking.webWalk(areas.PortBankSpot()); Sleep.waitCondition(() ->
	 * !myPlayer().isMoving(), 500, 5000); } if (!depositBox.isOpen()) {
	 * depositBox.open(); Sleep.waitCondition(() -> getDepositBox().isOpen(), 500,
	 * 5000);
	 * 
	 * } if (depositBox.isOpen()) { getDepositBox().depositAll("Yew logs");
	 * Sleep.waitCondition(() -> !inventory.contains("Yew logs"), 500, 5000);
	 * depositBox.close(); Sleep.waitCondition(() -> !getDepositBox().isOpen(), 500,
	 * 5000); } if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
	 * walking.webWalk(areas.RimmingtonYewSpot()); Sleep.waitCondition(() ->
	 * !myPlayer().isMoving(), 500, 5000); }
	 * 
	 * } private void CutWillow() throws InterruptedException { RS2Object tree =
	 * getObjects().closest("Willow"); if (inventory.contains("Oak logs") ||
	 * inventory.contains("Logs")) { DropingWood(); } if (inventory.isFull() &&
	 * Areas.PortWillowspot.contains(myPlayer())) { log("Banking Willow");
	 * bankingWillow(); } if (!Areas.PortWillowspot.contains(myPlayer())) {
	 * log("Walking Willowspot"); walking.webWalk(areas.PortWillowspot());
	 * Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000); } if
	 * (Areas.PortWillowspot.contains(myPlayer()) && (inventory.isEmptyExcept(axe)
	 * || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
	 * log("Cutting Willow"); if (!myPlayer().isAnimating() &&
	 * !myPlayer().isMoving() && tree != null) { tree.interact("Chop down");
	 * sleep(random(2500, 2932)); Antiban(); Sleep.waitCondition(() ->
	 * !myPlayer().isAnimating(), 1000, 50000); } } }
	 */
	private void BankingOak() throws InterruptedException {
		if (!Areas.PortBank.contains(myPlayer()) && inventory.isFull()) {
			log("Walking bankspot");
			walking.webWalk(areas.PortBankSpot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!depositBox.isOpen()) {
			log("Opening deposit box");
			depositBox.open();
			sleep(random(100,300));
			Sleep.waitCondition(() -> getDepositBox().isOpen(), 500, 5000);
		}
		if (depositBox.isOpen()) {
			log("Box is open");
			getDepositBox().interact("Deposit-All", "Oak logs");
			sleep(random(1000,1500));
			Sleep.waitCondition(() -> inventory.isEmptyExcept(axe) || inventory.isEmpty(), 500, 5000);
			depositBox.close();
			Sleep.waitCondition(() -> !getDepositBox().isOpen(), 500, 5000);
		}
		if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
			log("Walking Oak spot");
			walking.webWalk(areas.PortOakspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}

	}

	private void CuttingOak() throws InterruptedException {
		RS2Object tree = getObjects().closest("Oak");
		if (inventory.isFull()) {
			log("Banking Oak");
			BankingOak();
		}
		if (!Areas.PortOakspotBig.contains(myPlayer())) {
			log("Walking Oak");
			walking.webWalk(areas.PortOakspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		log("Cutting Oak");
		if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null && Areas.PortOakspotBig.contains(myPlayer()) ) {
			tree.interact("Chop down");
			sleep(random(2500, 2932));
			Antiban();
			Sleep.waitCondition(() -> !myPlayer().isAnimating() || dialogues.inDialogue(), 1000, 50000);
		}
	}

	private void DropingWood() throws InterruptedException {
		log("Droping Wood");
		String[] logs = { "Logs" };
		log("Droping log");
		inventory.dropAll(logs);
		Sleep.waitCondition(() -> inventory.isEmpty(), 500, 5000);
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
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")
						|| equipment.isWearingItemThatContains(EquipmentSlot.WEAPON, "Iron axe"))) {
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



	public void Banking() throws InterruptedException {
		if (!Areas.DraynorBank.contains(myPlayer())) {
			log("Walking Draynor bank");
			walking.webWalk(areas.DraynorBank);
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
			if (inventory.isEmpty() && (!(equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")
					|| equipment.isWearingItem(EquipmentSlot.WEAPON, "Iron axe")))) {
				if (bank.contains("Iron axe")) {
					getBank().withdraw("Iron axe", 1);
					Sleep.waitCondition(() -> inventory.contains("Iron axe"), 500, 3000);
					inventory.interact("Wield", "Iron axe");
					Sleep.waitCondition(() -> getEquipment().isWieldingWeapon("Iron axe"), 1000, 5000);
				} else if (bank.contains("Bronze axe")) {
					getBank().withdraw("Bronze axe", 1);
					Sleep.waitCondition(() -> inventory.contains("Bronze axe"), 500, 3000);
					inventory.interact("Wield", "Bronze axe");
					Sleep.waitCondition(() -> getEquipment().isWieldingWeapon("Bronze axe"), 1000, 5000);
				} else {
					stop();
				}
			}
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500,2200));

				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				bank.close();
				Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);

			}
			bank.close();
		}
	}

	@Override
	public int onLoop() {
		time.Timerun();
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); }
	

	@Override
	public void onExit() {
		log("GOOD BYE");
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);
	}
}
