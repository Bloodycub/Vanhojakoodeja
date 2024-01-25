package Trash;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;


public class Cowkillerold extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();
	public int Levelreq = 0;
	public String Mithril = "Mithril longsword";
	public String Black = "Black longsword";;
	public String Steel = "Steel longsword";
	public String Iron = "Iron longsword";
	public String Bronze = "Bronze sword";;
	public String TrainS = "Training sword";
	public String Woodenshield = "Wooden shield";
	public String Feather = "Feather";
	final String[] swordsAll = { Mithril, Black, Steel, Iron, Bronze, TrainS };

	private int Dice = random(1, 2);
	private int Runspeedrandomaiser = random(15, 50);
	private final int Firsttack = random(5, 8); // Steel Sword
	private int SecondRandomLevel = random(10, 13); // Black Sword
	private int Upgrade_BlackSword = random(SecondRandomLevel, 17); // 10-13 to 13-17
	private int Upgrade_MithrilSword = random(20, 50);
	public int At; // Attack
	private int moveoutornot;
	private int Checker = 0;
	private int Counter = random(5, 15);
	private int Bonefirst = 0;
	private int Boneslater = 0;
	private int Featherfirst = 0;
	private int Featherlater = 0;

	@Override
	public void onStart() throws InterruptedException {

		time.Starttime();
		log("Cowkiller Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating()
				&& myPlayer().isOnScreen(), 500, 5000);
		Levelreq = random(14, 18);
		log("My Level is " + myPlayer().getCombatLevel() + " Required level is " + Levelreq);
		if (myPlayer().getCombatLevel() <= Levelreq) {
			onLoop();
		}
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
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
		if (inventory.getEmptySlots() <= 13 && !Areas.CowPitInfalador.contains(myPlayer())) {
			log("Banking stuff for space");
			Bankingforstart();
		}
		Area Check = new Area(2919, 3523, 3065, 3321);
		Area Goto = new Area(3009, 3309, 2998, 3293);
		if (Check.contains(myPlayer())) {
			walking.webWalk(Goto);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	@SuppressWarnings("static-access")
	private void CheckUp() throws InterruptedException {
		At = skills.getStatic(Skill.ATTACK);
		log(Checker + " Checer" + " -> " + Counter + " Checker");
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}
		if (settings.getRunEnergy() >= Runspeedrandomaiser && !getSettings().isRunning()) {
			Runspeedrandomaiser = random(15, 50);
			log("Runspeed SET AT " + Runspeedrandomaiser);
			settings.setRunning(true);
			Sleep.waitCondition(() -> settings.isRunning(), 200, 3000);
		} else if (inventory.contains("Lamp of the gatherer")) {
			log("Wut you got lamp plz use it!");
			Usinglamp();
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Wrong tab");
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 200, 3000);
		} else if (getEquipment().isWearingItem(EquipmentSlot.WEAPON) == false) {
			log("Cheking items");
			ItemChecklist();
		} else if (!areas.CowPitInfalador.contains(myPlayer())) {
			log("Walking to Spot");
			Walkingpit();
		} else if (inventory.isFull() && inventory.contains("Bones")) {
			log("Bury");
			Buring();
		} else if (Areas.CowPitInfalador.contains(myPlayer()) && !myPlayer().isMoving() && !myPlayer().isUnderAttack()
				&& !myPlayer().isAnimating()) {
			Kill();
		}
	}

	private void Usinglamp() throws InterruptedException {
		if (inventory.contains("Lamp of the gatherer")) {
			inventory.interact("Rub", "Lamp of the gatherer");
			if (getWidgets().isVisible(219, 1)) {
				widgets.interact(219, 1, 2, "Continue");
				sleep(random(400, 800));
				widgets.interact(219, 1, 1, "Continue");
			}
			if (getDialogues().isPendingContinuation()) {
				getDialogues().clickContinue();
			}
		}
	}

	private void Buring() throws InterruptedException {
		while (inventory.contains("Raw beef")) {
			log("Droping Raw beef");
			inventory.dropAll("Raw beef");
			Sleep.waitCondition(() -> inventory.contains("Raw beef"), 3000);
			continue;
		}
		while (inventory.contains("Bones")) {
			log("Burying bones");
			inventory.interact("Bury", "Bones");
			sleep(random(850, 1050));
			continue;
		}
	}

	private void Kill() throws InterruptedException {
		if (Checker >= Counter) {
			Counter = random(5, 15);
			log("Cheking level and Items");
			// Upgrading();
			ItemChecklist();
			Levelcheck();
		}
		NPC Cow = getNpcs().closest("Cow");
		moveoutornot = random(0, 6);
		int Shouldloot = random(1, 10);
		int randomofrandom = random(1, 5);

		if (Cow != null && !Cow.isHitBarVisible() && Areas.CowPitInfalador.contains(Cow)) {
			log("Attacking");
			Cow.interact("Attack");
			if (moveoutornot == 4 || moveoutornot == 5) {
				log("Moving out");
				sleep(random(100, 230));
				mouse.moveOutsideScreen();
			}
			sleep(random(500, 1000));
			Checker++;
			Sleep.waitCondition(() -> !myPlayer().isInteracting(Cow), 1000, 50000);
			if (randomofrandom < 3) {
				if (Shouldloot <= 4) {
					Sleep.waitCondition(() -> !Cow.isHitBarVisible(), 100, 2000);
					log("Looting inside of random");
					Loot();
				}
			} else {
				log("Looting");
				Loot();
			}
		}
	}

	private void Loot() throws InterruptedException {
		GroundItem Bones = getGroundItems().closest("Bones");
		log("Looting");
		if (Bones != null) {
			if (myPlayer().getPosition().distance(Bones) < 1) {
				log("Looting Bones");
				Bones.interact("Take");
				sleep(random(500, 800));
				Bonefirst++;
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 7000);
				Sleep.waitCondition(() -> Boneslater < Bonefirst, 500, 5000);
				Boneslater++;
			}
		}
	}

	private void Levelcheck() throws InterruptedException {
		Checker = 0;
		if (At >= Firsttack && (!getEquipment().isWieldingWeapon(TrainS) || !getEquipment().isWieldingWeapon(Bronze))) {
			if (!configs.isSet(43, 2)) {
				log("Chanking style Lunge");
				Chankingtolunge();
			}
		}
	}

	private void Chankingtolunge() throws InterruptedException {
		if (tabs.getTabs().isOpen(Tab.INVENTORY)
				&& (!(getEquipment().isWieldingWeapon(TrainS) || getEquipment().isWieldingWeapon(Bronze)))) {
			tabs.open(Tab.ATTACK);
			Sleep.waitCondition(() -> tabs.getTabs().isOpen(Tab.ATTACK), 500, 3000);
			widgets.interact(593, 14, "Lunge");
			Sleep.waitCondition(() -> configs.isSet(43, 2), 500, 3000);
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.getTabs().isOpen(Tab.INVENTORY), 500, 3000);
		}
	}

	private void Walkingpit() throws InterruptedException {
		// Upgrading();
		log("Walking to pit");
		if (!Areas.CowPitInfalador.contains(myPlayer())) {
			walking.webWalk(areas.CowPitInfalador());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	private void Upgrading() throws InterruptedException {
		Boolean AdventurerJonExsist = true;
		At = skills.getStatic(Skill.ATTACK);
		String[] swords = { "Mithril longsword", "Black longsword", "Steel longsword" };
		int[] levels = { 20, 10, 5 };

		int AttackLevel = At;

		if (AdventurerJonExsist == true) {
			log("Cheking upgrades");
			if (inventory.getCapacity() < 10) {
				if (inventory.contains("Bones")) {
					log("Too much bones inventory bury");
					Buring();
				}
			}
			if (inventory.getCapacity() < 5 && !inventory.contains("Bones")) {
				log("No bones? but still full??");
				Bankingforstart();
			}

			for (int i = 0; i < swords.length; i++) {
				if ((AttackLevel >= levels[i]) && !getEquipment().isWieldingWeapon(swords[i])) {
					if (inventory.contains(swords[i])) {
						ItemChecklist();
						break;
					} else if (!getEquipment().isWieldingWeapon(swords[i]) && !inventory.contains(swords[i])) {
						NPC Upgrade = getNpcs().closest("Adventurer Jon");
						if (!Areas.SwordUpgrade.contains(myPlayer())) {
							log("Walking to Upgrade");
							walking.webWalk(areas.SwordUpgrade);
							sleep(random(2000, 3000));
						}
						if (Upgrade != null) {
							log("not null");
							Upgrade.interact("Claim");
							sleep(random(2000, 3000));
							log("Pending option");
							if (dialogues.isPendingContinuation()) {
								dialogues.clickContinue();
								sleep(random(800, 1200));
							}
						}
						if (inventory.contains(swords[i])) {
							log("Got sword");
							ItemChecklist();
							Walkingpit();
						}
						sleep(random(500, 1000));
						log("Cheking upgrades");
						ItemChecklist();
					} else {
						log("Jon null");
						AdventurerJonExsist = false;
					}
					break;
				}
			}
		}
	}
	// end

	private void Bankingforstart() throws InterruptedException {
		log("Doing For start banking");
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Bank for start");
			bank.open();
			sleep(random(1000, 1500));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				String[] swordsBank = { Mithril, Black, Steel, Iron, Bronze, TrainS };
				for (String Swordsbank : swordsBank) {
					if (bank.contains(Swordsbank)) {
						bank.withdraw(Swordsbank, 1);
						Sleep.waitCondition(() -> inventory.contains(Swordsbank), 500, 3000);
						inventory.interact("Wield", Swordsbank);
						sleep(random(1000, 1500));
						Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Swordsbank), 500, 5000);
						break;
					}
				}
				if (!inventory.isEmpty()) {
					bank.depositAll();
					sleep(random(1500, 2200));
					Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				}
			}
		}
		bank.close();
		Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
	}

	private void ItemChecklist() throws InterruptedException {
		Checker = 0;

		if (inventory.contains(Woodenshield)) {
			if (Dice == 2) {
				log("Equip shield");
				inventory.interact("Wield", Woodenshield);
			}
		}
		At = skills.getStatic(Skill.ATTACK);
		int[] levels = { 20, 10, 5, 1, 1, 1 };
		final String[] swordsCheck = { Mithril, Black, Steel, Iron, Bronze, TrainS };
		int AttackLevel = At;
		for (int i = 0; i < swordsCheck.length; i++) {
			log("looping swords in inventory");
			if (getEquipment().isWieldingWeapon(swordsCheck[i])) {
				sleep(random(1500, 2200));
				break;
			}
			if ((AttackLevel >= levels[i])) {
				if (inventory.contains(swordsCheck[i])) {
					inventory.interact("Wield", swordsCheck[i]);
					sleep(random(1500, 2200));
					final int A = i;
					Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(swordsCheck[A]), 500, 5000);
					break;
				}
			}
		}

		if (!(getEquipment().isWieldingWeapon(Black) || getEquipment().isWieldingWeapon(Mithril)
				|| getEquipment().isWieldingWeapon(Steel) || getEquipment().isWieldingWeapon(Iron)
				|| getEquipment().isWieldingWeapon(Bronze))) {
			log("Dont have swords");
			log("Checking bank");
			Checkbankforsword();
		}
	}

	private void Checkbankforsword() throws InterruptedException {
		log("Checking sword From bank");
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Bank for start");
			bank.open();
			sleep(random(1000, 1500));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				bank.depositWornItems();
				sleep(random(1500, 2200));
				String[] swordsBank = { Mithril, Black, Steel, Iron, Bronze, TrainS };
				for (String Swordsbank : swordsBank) {
					if (bank.contains(Swordsbank)) {
						bank.withdraw(Swordsbank, 1);
						Sleep.waitCondition(() -> inventory.contains(Swordsbank), 500, 3000);
						inventory.interact("Wield", Swordsbank);
						sleep(random(1000, 1500));
						Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Swordsbank), 500, 5000);
						break;
					}
				}
				

			}
		}
		bank.close();
		Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
	}

	private void GettingTrainingsword() {


	public int onLoop() {
		time.Timerun();
		if (myPlayer().getCombatLevel() <= Levelreq) {
			log("Level muste be under == " + Levelreq + " And my level is now " + myPlayer().getCombatLevel());
			stop(false);
			return -1;
		}
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 100 + random(100);
	}

	@Override
	public void onExit() {
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);
	}

}
