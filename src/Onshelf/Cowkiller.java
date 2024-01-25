package Onshelf;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;
import Core.Support.Time;
import Support.*;
import java.awt.*;

public class Cowkiller extends Script {
	int randomlevel = random(14, 18);
	Areas areas = new Areas();
	Time time = new Time();
	public String Mithril = "Mithril longsword";
	public String Black = "Black longsword";;
	public String Steel = "Steel longsword";
	public String Iron = "Iron longsword";
	public String Bronze = "Bronze sword";;
	public String TrainS = "Training sword";
	public String Woodenshield = "Wooden shield";
	int Dice = random(1, 2);
	int Runspeedrandomaiser = random(15, 50);
	final int Firsttack = random(5, 8); // Steel Sword
	int SecondRandomLevel = random(10, 13); // Black Sword
	int Upgrade_BlackSword = random(SecondRandomLevel, 17); // 10-13 to 13-17
	int Upgrade_MithrilSword = random(20, 26);
	public int At; // Attack
	private int moveoutornot;
	int Checker = 0;
	int Counter = random(50, 200);
	int Bonefirst = 0;
	int Boneslater = 0;
	int Featherfirst = 0;
	int Featherlater = 0;

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		if (myPlayer().getCombatLevel() >= randomlevel) {
			if (myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating()
					&& myPlayer().isOnScreen()) {
				while (settings.areRoofsEnabled() == false) {
					getKeyboard().typeString("::toggleroofs");
					Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
				}
			}
			if(!inventory.isEmpty()) {
				log("Inventory not empty");
				Bankingforstart();
			}
		} else {
			stop();
		}
	}

	private void Checkup() throws InterruptedException {
		if (camera.getPitchAngle() < 44) {
			log("Moving camera angle");
			movecamera();
		} else if (settings.getRunEnergy() >= Runspeedrandomaiser && !getSettings().isRunning()) {
			log("Setrun");
			settings.setRunning(true);
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Wrong tab");
			tabs.open(Tab.INVENTORY);
		} else if (getEquipment().isWearingItem(EquipmentSlot.WEAPON) == false) {
			log("Cheking items");
			ItemChecklist();
		} else if (!Areas.CowPitInfalador.contains(myPlayer())) {
			log("Walk area");
			Walkingpit();
		} else if (inventory.isFull() && inventory.contains("Bones")) {
			log("Bury");
			Buring();
		} else if (Areas.CowPitInfalador.contains(myPlayer())) {
			log("Killing");
			KillCows();
		}
	}

	@SuppressWarnings("static-access")
	private void KillCows() throws InterruptedException {
		@SuppressWarnings("unchecked")
		NPC Cow = getNpcs().closest(npc -> npc.getName().equals("Cow") && npc.isAttackable());

		if (Checker >= Counter) {
			Counter = random(25, 100);
			log("Cheking level and Items");
			Upgrading();
			ItemChecklist();
			Levelcheck();
		}
		if (Cow.getInteracting() != null) {
			log("Closest Cow underattack");
			walking.webWalk(areas.CowPitInfalador());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,5000);
		}
		if (Cow != null && Cow.interact("Attack")) {
			sleep(random(500, 1000));
			moveoutornot = random(0, 6);
			if (moveoutornot == 4) {
				log("Moving out");
				mouse.moveOutsideScreen();
			}
			Sleep.waitCondition(() -> myPlayer().getInteracting() == null && !myPlayer().isMoving()
					&& !myPlayer().isAnimating() && !myPlayer().isUnderAttack(), 500, 50000);
			log("Loot");
			sleep(random(1000, 1200));
			LootBones();
		}
	}

	private void LootBones() throws InterruptedException {
		GroundItem Bones = getGroundItems().closest("Bones");
		if (Bones != null && Areas.CowPitInfalador.contains(Bones)) {
			Bones.interact("Take");
			sleep(random(500, 1000));
			Bonefirst++;
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 7000);
			Sleep.waitCondition(() -> Boneslater < Bonefirst, 500, 5000);
			Boneslater++;
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
			Core.Support.Sleep.waitCondition(() -> tabs.getTabs().isOpen(Tab.ATTACK), 3000);
			widgets.interact(593, 14, "Lunge");
			Sleep.waitCondition(() -> configs.isSet(43, 2), 500, 3000);
		}
	}
	private void Buring() throws InterruptedException {
		if (inventory.contains("Raw beef")) {
			log("Droping Meat");
			inventory.interact("Drop", "Raw beef"); // Change for meat
			sleep(random(634, 892));
		} else
			while (inventory.contains("Bones")) {
				log("Burying bones");
				inventory.interact("Bury", "Bones");
				sleep(random(850, 1050));
			}
	}

	private void Bankingforstart() throws InterruptedException {
		if (!inventory.isEmptyExcept(Mithril, Black, Steel, Iron, Bronze, TrainS, Woodenshield)) {
			if (!Banks.DRAYNOR.contains(myPlayer())) {
				log("Walking Draynor bank");
				walking.webWalk(Banks.DRAYNOR.getRandomPosition());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			if (!bank.isOpen()) {
				log("Open bank for Bank for start");
				bank.open();
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
				if (!inventory.isEmptyExcept(Mithril, Black, Steel, Iron, Bronze, TrainS, Woodenshield)) {
					bank.depositAllExcept(Mithril, Black, Steel, Iron, Bronze, TrainS, Woodenshield);
					Sleep.waitCondition(() -> inventory.isEmpty(), 500,3000);
				}
				if (inventory.isEmptyExcept(Mithril, Black, Steel, Iron, Bronze, TrainS, Woodenshield)) {
					if (getBank().contains(Mithril)) {
						log("Taking mithrill");
						bank.withdraw(Mithril, 1);
					} else if (getBank().contains(Black)) {
						log("Taking Black");
						bank.withdraw(Black, 1);
					} else if (getBank().contains(Steel)) {
						log("Taking Steel");
						bank.withdraw(Steel, 1);
					} else if (getBank().contains(Iron)) {
						log("Taking Iron");
						bank.withdraw(Iron, 1);
					} else if (getBank().contains(Bronze)) {
						log("Taking Bronze");
						bank.withdraw(Bronze, 1);
					}
					sleep(random(300, 700));
					bank.withdraw(Woodenshield, 1);
					Sleep.waitCondition(() -> inventory.contains(Woodenshield), 3000);
				}
			}
			bank.close();
			if (!inventory.isEmptyExcept(Mithril, Black, Steel, Iron, Bronze, TrainS, Woodenshield)) {
				Bankingforstart();
			}
		}
	}

	private void Upgrading() throws InterruptedException {
		NPC Upgrade = getNpcs().closest("Adventurer Jon");
		log("Cheking upgrades");
		if (inventory.getCapacity() < 5) {
			if (inventory.contains("Bones")) {
				log("Too much bones inventory bury");
				Buring();
			}
		} else if (inventory.getCapacity() < 5 && !inventory.contains("Bones")) {
			log("No bones? but still full??");
			Bankingforstart();
		}

		if (At >= Firsttack && !inventory.contains(Steel)
				&& (!(getEquipment().isWieldingWeapon(Black) || getEquipment().isWieldingWeapon(Mithril)
						|| getEquipment().isWieldingWeapon(Steel) || getEquipment().isWieldingWeapon(Iron)))) {
			log("Under lvl 10");
			log("Getting upgrade");
			if (!Areas.SwordUpgrade.contains(myPlayer())) {
				walking.webWalk(areas.SwordUpgrade);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 7000);
			}
			getNpcs().closest("Adventurer Jon");
			Upgrade.interact("Claim");
			Sleep.waitCondition(() -> dialogues.inDialogue(), 500, 5000);
			if (dialogues.inDialogue()) {
				if (getDialogues().isPendingOption() || getDialogues().isPendingContinuation()) {
					log("Pending option");
					dialogues.completeDialogue("I'd like to claim my Adventure Path rewards.");
					ItemChecklist();
				}
			} else {
				log("In dalogue");
				getDialogues().completeDialogue("");
				Sleep.waitCondition(() -> !dialogues.inDialogue(), 500, 5000);
			}
			if (!inventory.contains(Steel) && !getEquipment().isWieldingWeapon(Steel)) {
				log("Dident get Sword Hmmmm...");
				Checkbankforsword();
			}
			sleep(random(500, 1000));
			log("Cheking upgrades");
			ItemChecklist();

		} else if (At >= SecondRandomLevel && At <= Upgrade_BlackSword && !inventory.contains("Black longsword")
				&& !getEquipment().isWieldingWeapon("Black longsword")) {
			log("Under lvl 20");
			log("over lvl 10");
			log("Getting upgrade");
			if (!Areas.SwordUpgrade.contains(myPlayer())) {
				walking.webWalk(areas.SwordUpgrade);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 7000);
			}
			getNpcs().closest("Adventurer Jon");
			Upgrade.interact("Claim");
			Sleep.waitCondition(() -> dialogues.inDialogue(), 500, 5000);
			if (dialogues.inDialogue()) {
				if (getDialogues().isPendingOption() || getDialogues().isPendingContinuation()) {
					log("Pending option");
					dialogues.completeDialogue("I'd like to claim my Adventure Path rewards.");
					ItemChecklist();
				}
			} else {
				log("In dalogue");
				getDialogues().completeDialogue("");
				Sleep.waitCondition(() -> !dialogues.inDialogue(), 500, 5000);

			}
			if (!inventory.contains(Black) && !getEquipment().isWieldingWeapon(Black)) {
				log("Dident get Sword Hmmmm...");
				Checkbankforsword();
			}
			sleep(random(500, 1000));
			log("Cheking upgrades");
			ItemChecklist();

		} else if ((At >= Upgrade_MithrilSword || At > 26) && !inventory.contains("Mithril longsword")
				&& !getEquipment().isWieldingWeapon("Mithril longsword")) {
			log("+ 20lvl");
			log("Getting upgrade");
			if (!Areas.SwordUpgrade.contains(myPlayer())) {
				walking.webWalk(areas.SwordUpgrade);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 7000);
			}
			getNpcs().closest("Adventurer Jon");
			Upgrade.interact("Claim");
			Sleep.waitCondition(() -> dialogues.inDialogue(), 500, 5000);
			if (dialogues.inDialogue()) {
				if (getDialogues().isPendingOption() || getDialogues().isPendingContinuation()) {
					log("Pending option");
					dialogues.completeDialogue("I'd like to claim my Adventure Path rewards.");
					ItemChecklist();
				}
			} else {
				log("In dalogue");
				getDialogues().completeDialogue("");
				Sleep.waitCondition(() -> !dialogues.inDialogue(), 500, 5000);

			}
			if (!inventory.contains(Mithril) && !getEquipment().isWieldingWeapon(Mithril)) {
				log("Dident get Sword Hmmmm...");
				Checkbankforsword();
			}
			sleep(random(500, 1000));
			log("Cheking upgrades");
			ItemChecklist();
		}
	}

	private void ItemChecklist() throws InterruptedException {
		Checker = 0;
		if (inventory.contains(Woodenshield)) {
			if (Dice == 2) {
				log("Equip shield");
				inventory.interact("Wield", Woodenshield);
			}
		}
		if (inventory.contains(Mithril)) {
			log("Equiping Mithril sword");
			inventory.interact("Wield", Mithril);
			Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Mithril), 500, 5000);

		} else if (inventory.contains(Black) && !getEquipment().isWieldingWeapon(Mithril)) {
			log("Equiping Black sword");
			inventory.interact("Wield", Black);
			Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Black), 500, 5000);

		} else if (inventory.contains(Steel) && !getEquipment().isWieldingWeapon(Black)
				&& !getEquipment().isWieldingWeapon(Mithril)) {
			log("Equiping Steel sword");
			inventory.interact("Wield", Steel);
			Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Steel), 500, 5000);

		} else if (inventory.contains(Iron) && !getEquipment().isWieldingWeapon(Black)
				&& !getEquipment().isWieldingWeapon(Mithril) && !getEquipment().isWieldingWeapon(Steel)) {
			log("Wielding Iron Sword");
			inventory.interact("Wield", Iron);
			Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Iron), 500, 5000);

		} else if (inventory.contains(Bronze) && !getEquipment().isWieldingWeapon(Black)
				&& !getEquipment().isWieldingWeapon(Mithril) && !getEquipment().isWieldingWeapon(Steel)
				&& !getEquipment().isWieldingWeapon(Iron)) {
			log("Wielding Sword");
			inventory.interact("Wield", Bronze);
			Sleep.waitCondition(() -> getEquipment().isWieldingWeapon(Bronze), 500, 5000);

		} else if (!getEquipment().isWieldingWeapon(Black) && !getEquipment().isWieldingWeapon(Mithril)
				&& !getEquipment().isWieldingWeapon(Steel) && !getEquipment().isWieldingWeapon(Iron)
				&& !getEquipment().isWieldingWeapon(Bronze)) {
			log("Dont have swords");
			log("Checking bank");
			Checkbankforsword();
		}
	}

	private void Checkbankforsword() throws InterruptedException {
		String[] Swords = { Mithril, Black, Steel, Iron, Bronze, TrainS };
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			walking.webWalk(Banks.LUMBRIDGE_UPPER);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank and checking sword");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.isOpen()) {
			if (getBank().contains(Mithril)) {
				log("Taking Mithril sword");
				bank.withdraw(Mithril, 1);
				Sleep.waitCondition(() -> inventory.contains(Mithril), 3000);
				bank.close();
			} else if (getBank().contains(Black)) {
				log("Taking Black sword");
				bank.withdraw(Black, 1);
				Sleep.waitCondition(() -> inventory.contains(Black), 3000);
				bank.close();
			} else if (getBank().contains(Steel)) {
				log("Taking Steel sword");
				bank.withdraw(Steel, 1);
				Sleep.waitCondition(() -> inventory.contains(Steel), 3000);
				bank.close();
			} else if (getBank().contains(Iron)) {
				log("Taking Iron sword");
				bank.withdraw(Iron, 1);
				Sleep.waitCondition(() -> inventory.contains(Iron), 3000);
				bank.close();
			} else if (getBank().contains(Bronze)) {
				log("Taking Bronze sword");
				bank.withdraw(Bronze, 1);
				Sleep.waitCondition(() -> inventory.contains(Bronze), 3000);
				bank.close();
			} else if (getBank().contains(TrainS)) {
				log("Taking Bronze sword");
				bank.withdraw(TrainS, 1);
				Sleep.waitCondition(() -> inventory.contains(TrainS), 3000);
			} else if (!getBank().contains(Swords)) {
				log("Dont have swords go to trainer");
				GettingTrainingsword();
			}
		}
		bank.close();
		if (inventory.isEmpty()) {
			log("Inventory still not empty");
			Bankingforstart();
		}

	}
	private void GettingTrainingsword() {
		log("Not done");
		stop();
	}

	private void Walkingpit() throws InterruptedException {
		walking.webWalk(areas.CowPitInfalador());
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,5000);
	}

	private void movecamera() {
		int targetPitch = random(44, 67);
		log(targetPitch);
		camera.movePitch(targetPitch);
	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		Checkup();

		return random(1000); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);

	}

}