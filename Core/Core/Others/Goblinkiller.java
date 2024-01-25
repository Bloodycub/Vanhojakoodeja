package Core.Corebots;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;


public class Goblinkiller extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();
	int randomlevel = random(15, 20);
	public String Mithril = "Mithril longsword";
	public String Black = "Black longsword";;
	public String Steel = "Steel longsword";
	public String Iron = "Iron longsword";
	public String Bronze = "Bronze sword";;
	public String TrainS = "Training sword";
	public String Woodenshield = "Wooden shield";
	public String Feather = "Feather";
	final String[] swordsAll = { Mithril, Black, Steel, Iron, Bronze, TrainS };

	int Dice = random(1, 2);
	int Runspeedrandomaiser = random(15, 50);
	final int Firsttack = random(5, 8); // Steel Sword
	int SecondRandomLevel = random(10, 13); // Black Sword
	int Upgrade_BlackSword = random(SecondRandomLevel, 17); // 10-13 to 13-17
	int Upgrade_MithrilSword = random(20, 50);
	public int At; // Attack
	private int moveoutornot;
	int Checker = 0;
	int Counter = random(15, 30);
	int Bonefirst = 0;
	int Boneslater = 0;
	int Featherfirst = 0;
	int Featherlater = 0;

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Chickenkiller Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating()
				&& myPlayer().isOnScreen(), 500, 5000);
		if (myPlayer().getCombatLevel() <= randomlevel) {
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
			ItemChecklist();
			if (inventory.getEmptySlots() <= 13 && !Areas.GoblinArea.contains(myPlayer())) {
				log("Banking stuff for space");
				Bankingforstart();
			}
		} else {
			log("Go cows dude");
			onLoop();
		}
	}

	@SuppressWarnings("static-access")
	private void CheckUp() throws InterruptedException {
		At = skills.getStatic(Skill.ATTACK);
		// log(Checker + " Checer" + " -> " + Counter + " Checker");
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
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
		} else if (!areas.GoblinArea.contains(myPlayer())) {
			log("Walking to Spot");
			GoblinArea();
		} else if (inventory.isFull() && inventory.contains("Bones")) {
			log("Bury");
			Buring();
		} else if (Areas.GoblinArea.contains(myPlayer()) && !myPlayer().isMoving() && !myPlayer().isUnderAttack()
				&& !myPlayer().isAnimating()) {
			Kill();
		}
	}

	private void Usinglamp() throws InterruptedException {
		if (inventory.contains("Lamp of the gatherer")) {
			inventory.interact("Rub", "Lamp of the gatherer");
			Sleep.waitCondition(() -> widgets.isVisible(219, 1), 200, 5000);
			if (getWidgets().isVisible(219, 1)) {
				widgets.interact(219, 1, 2, "Continue");
				sleep(random(400, 800));
				widgets.interact(219, 1, 1, "Continue");
				sleep(random(400, 800));
			}
			if (getDialogues().isPendingContinuation()) {
				getDialogues().clickContinue();
				sleep(random(850, 1050));
			}
		}
	}

	private void Buring() throws InterruptedException {
		if (inventory.contains("Raw chicken")) {
			log("Droping chicken");
			inventory.dropAll("Raw chicken");
			Sleep.waitCondition(() -> !inventory.contains("Raw chicken"), 200, 3000);
		}
		while (inventory.contains("Bones")) {
			log("Burying bones");
			inventory.interact("Bury", "Bones");
			sleep(random(850, 1050));
			continue;
		}
	}

	private void KillCheck() throws InterruptedException {
		if(Checker == 10 || Checker == 20 || Checker == 30 || Checker == 40 || Checker == 50) {
		log(Checker + "Check amount");
		}
		if (Checker >= Counter) {
			Counter = random(20, 30);
			log("Cheking level and Items");
			boolean Upg = false;
			int Atttack = skills.getStatic(Skill.ATTACK);
			String[] swords = { "Mithril longsword", "Black longsword", "Steel longsword" };
			int[] levels = { 20, 10, 5 };
			int AttackLevel = Atttack;

			for (int i = 0; i < swords.length; i++) {
				log("Looping Swords");
				log(i);
				if (AttackLevel >= levels[i]) {

					if (getEquipment().isWieldingWeapon(swords[i])) {
						log("Is wielding Weapon");
						// Everything is good, do nothing
					} else if (!inventory.contains(swords[i])) {
						log("Upg true");
						Upg = true;
					} else {
						log("Got Sword");
						ItemChecklist();
					}
					break;
				}
			}

			if (Upg == true) {
				log("Doing Upgrades");
				Upgrading();
				ItemChecklist();
				Levelcheck();
			}
		}
	}

	private void Kill() throws InterruptedException {
		KillCheck();
		NPC Goblin = getNpcs().closest("Goblin");
		moveoutornot = random(0, 16);
		int Shouldloot = random(1, 10);
		int randomofrandom = random(1, 5);
		int Doloot = random(0, 5);

		if (Goblin != null && !Goblin.isHitBarVisible() && Areas.GoblinArea.contains(Goblin)) {
			Goblin.interact("Attack");
			if (moveoutornot == 4 || moveoutornot == 5) {
				log("Moving out");
				sleep(random(100, 230));
				mouse.moveOutsideScreen();
			}
			sleep(random(1500, 2123));
			Checker++;
			Sleep.waitCondition(() -> !myPlayer().isInteracting(Goblin), 1000, 50000);
			if (Doloot == 2 || Doloot == 3) {
				if (randomofrandom < 3) {
					if (Shouldloot <= 4) {
						Sleep.waitCondition(() -> !Goblin.isHitBarVisible(), 100, 2000);
						log("Looting inside of random");
						Loot();
					}
				} else {
					log("Looting");
					Loot();
				}
			}
		}
	}

	private void Loot() throws InterruptedException {
		GroundItem Feather = getGroundItems().closest("Feather");
		GroundItem Bones = getGroundItems().closest("Bones");
		log("Looting");
		if (Feather != null) {
			if (myPlayer().getPosition().distance(Feather) < 3) {
				log("Looting feather");
				Feather.interact("Take");
				sleep(random(500, 800));
				Featherfirst++;
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 7000);
				Sleep.waitCondition(() -> Featherlater < Featherfirst, 500, 5000);
				Featherlater++;
			}

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
			sleep(random(400, 1000));
			Sleep.waitCondition(() -> tabs.getTabs().isOpen(Tab.ATTACK), 500, 3000);
			widgets.interact(593, 14, "Lunge");
			sleep(random(400, 1000));
			Sleep.waitCondition(() -> configs.isSet(43, 2), 500, 3000);
			tabs.open(Tab.INVENTORY);
			sleep(random(400, 1000));
			Sleep.waitCondition(() -> tabs.getTabs().isOpen(Tab.INVENTORY), 500, 3000);
		}
	}

	private void GoblinArea() throws InterruptedException {
		log("Walking to pit");
		if (!Areas.GoblinArea.contains(myPlayer())) {
			walking.webWalk(areas.GoblinArea());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	@SuppressWarnings("static-access")
	private void Upgrading() throws InterruptedException {
		Boolean AdventurerJonExsist = true;

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
			if (!Areas.SwordUpgrade.contains(myPlayer())) {
				log("Walking to Upgrade");
				walking.webWalk(areas.SwordUpgrade);
				sleep(random(2000, 3000));
			}
			NPC Upgrade = getNpcs().closest("Adventurer Jon");
			if (Upgrade != null) {
				log("not null");
				Upgrade.interact("Claim");
				sleep(random(2000, 3000));
				log("Pending option");
				while (dialogues.isPendingContinuation()) {
					dialogues.clickContinue();
					sleep(random(400, 600));
				}
			} else {
				log("Jon null");
				AdventurerJonExsist = false;
			}
			sleep(random(500, 1000));
			log("Cheking upgrades");
			ItemChecklist();
		}
	}

	// end

	@SuppressWarnings("static-access")
	private void Bankingforstart() throws InterruptedException {
		if(client.isLoggedIn()) {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Bank for start");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			bank.depositWornItems();
			sleep(random(500, 1000));
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
			}
			final String[] swordsBank = { Mithril, Black, Steel, Iron, Bronze, TrainS };

			for (String Swordsbank : swordsBank) {
				if (bank.contains(Swordsbank) && !inventory.contains(swordsBank)
						&& !getEquipment().isWieldingWeapon(Swordsbank)) {
					bank.withdraw(Swordsbank, 1);
					Sleep.waitCondition(() -> inventory.contains(Swordsbank), 500, 3000);
					inventory.interact("Wield", Swordsbank);
					sleep(random(200, 600));
					Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Swordsbank), 500, 5000);
					break;
				}
			}
		}
		}
		if (!inventory.isEmpty()) {
			bank.depositAll();
			sleep(random(1500, 2200));
			Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000);
		}
		bank.close();
		Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000);
		ItemChecklist();
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
			log("looping swords");

			if (getEquipment().isWieldingWeapon(swordsCheck[i])) {
				break;
			}
			if ((AttackLevel >= levels[i])) {
				if (inventory.contains(swordsCheck[i])) {
					log("Equiping");
					inventory.interact("Wield", swordsCheck[i]);
					sleep(random(200, 600));
					final int A = i;
					Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(swordsCheck[A]), 200, 5000);
					break;
				}
			}
		}
		if (!(getEquipment().isWieldingWeapon(Black) || getEquipment().isWieldingWeapon(Mithril)
				|| getEquipment().isWieldingWeapon(Steel) || getEquipment().isWieldingWeapon(Iron)
				|| getEquipment().isWieldingWeapon(Bronze)) || getEquipment().isWieldingWeapon(TrainS)) {
			log("Dont have swords");
			log("Checking bank");
			Checkbankforsword();
		}
	}

	@SuppressWarnings("static-access")
	private void Checkbankforsword() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank for Checkign Sword");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000);
				bank.depositWornItems();
				sleep(random(1500, 2200));
			}
			for (String Swordsbank : swordsAll) {
				if (bank.contains(Swordsbank) && !inventory.contains(Swordsbank)
						&& !getEquipment().isWieldingWeapon(Swordsbank)) {
					bank.withdraw(Swordsbank, 1);
					sleep(random(300, 600));
					Sleep.waitCondition(() -> inventory.contains(Swordsbank), 200, 3000);
					inventory.interact("Wield", Swordsbank);
					sleep(random(300, 600));
					if (getEquipment().isWieldingWeapon(Swordsbank)) {
						Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Swordsbank), 200, 5000);
						break;
					}
				}
			}
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000);
			}
			if (!equipment.isWieldingWeaponThatContains(swordsAll)) {
				log("You wielding a sword");
				log("Getting sword");
				GettingTrainingsword();
			}
		
		}
		bank.close();
		Sleep.waitCondition(() -> !bank.isOpen(), 200, 3000);
		ItemChecklist();
	}

	@SuppressWarnings("static-access")
	private void GettingTrainingsword() throws InterruptedException {
		log("Getting Training Sword");
		walking.webWalk(areas.SwordTrainer);
		sleep(random(3000, 4500));
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);

		NPC ST = getNpcs().closest("Melee combat tutor");
		if (ST != null) {
			ST.interact("Talk-to");
			sleep(random(600, 1000));
			Sleep.waitCondition(() -> dialogues.inDialogue(), 200, 5000);
			if (dialogues.inDialogue()) {
				String[] SaS = { "I'd like a training sword and shield." };
				dialogues.completeDialogue(SaS);
				sleep(random(200, 500));
			}
			if (inventory.contains("Training sword") && inventory.contains("Training shield")) {
				log("Doing check list");
				ItemChecklist();
			}
		}
	}

	public int onLoop() {
		time.Timerun();
		if (myPlayer().getCombatLevel() >= randomlevel) {
			log("Level muste be under == " + randomlevel + "Yours now " + myPlayer().getCombatLevel());
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
