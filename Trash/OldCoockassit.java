package Trash;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Support.Areas;

import java.awt.*;

public class OldCoockassit extends Script {
	public static String[] Cook1 = { "What's wrong?", "I'm always happy to help a cook in distress." };
	public static String[] Cook2 = { "Actually, I know where to find this stuff." };
	public static String[] Cook3 = { "I'll get right on it." };
	Areas area = new Areas();

	@Override
	public void onStart() throws InterruptedException {
		sleep(2000);
		log("Starting Cooks assistant");
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
		if (quests.isComplete(Quest.COOKS_ASSISTANT) == true) {
			log("Quest Complete");
			// Return 7;
		} else if (Inventorysize <= 7) {
			log("Dont have space");
			DropInventory();
		}
	}

	private void CheckUp() throws InterruptedException {
		log("Checkup");
		if (quests.isComplete(Quest.COOKS_ASSISTANT) == true) {
			log("Quest Complete");
			onExit();
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Inventory tab not open");
			tabs.open(Tab.INVENTORY);
			sleep(random(400, 800));
		} else if (!Areas.SellerFULL.contains(myPlayer()) && !Areas.LB.contains(myPlayer())
				&& !inventory.contains("Pot") && !inventory.contains("Bucket") && !Areas.Cookroom.contains(myPlayer())) {
			log("Your not near LB");
			walking.webWalk(area.Cookroom());
		} else if (getDialogues().isPendingContinuation()) {
			log("You are in dialog");
			Completing();
		} else if (getDialogues().isPendingOption()) {
			log("You are in dialog option");
			Completing();
		} else if (!inventory.contains("Pot") && !inventory.contains("Pot of flour")) {
			log("Pot");
			TakingPot();
		} else if (!inventory.contains("Bucket of milk") && !inventory.contains("Bucket")) {
			log("Getting Bucket");
			TakingBucket();
		} else if (inventory.contains("Pot") && inventory.contains("Bucket") && !inventory.contains("Egg")
				&& !Areas.SellerFULL.contains(myPlayer())) {
			log("Gathering Egg");
			Egg();
		} else if (!inventory.contains("Pot of flour") && inventory.contains("Egg")) {
			log("Gathering Taking Wheat");
			Wheat();
		} else if (!inventory.contains("Bucket of milk") && inventory.contains("Pot of flour")
				&& inventory.contains("Bucket")) {
			log("Gathering Milk");
			Milk();
		} else if (inventory.contains("Egg") && inventory.contains("Bucket of milk")
				&& inventory.contains("Pot of flour")) {
			log("Compleat quest");
			Completecookingquest();

		} else if (Areas.SellerFULL.contains(myPlayer()) && inventory.contains("Bucket")) {
			log("You are in Seller");
			walking.webWalk(area.SellerS());
			sleep(800);
			objects.closest("Ladder").interact("Climb-up");
			sleep(1500);
		}
	}

	private void Wheat() throws InterruptedException {
		RS2Object WheatObj = getObjects().closest("Wheat");
		if (!inventory.contains("Grain") && !inventory.contains("Pot of flour")) {
			walking.webWalk(area.Wheat());
			sleep(1800);
			WheatObj.interact("Pick");
			sleep(random(2500, 3000));
		} else if (inventory.contains("Grain") && !inventory.contains("Pot of flour")) {
			walking.webWalk(area.Mill());
			sleep(800);
			objects.closest("Ladder").interact("Climb-up");
			sleep(random(3500, 4000));
			objects.closest("Ladder").interact("Climb-up");
			sleep(random(2500, 3000));
			RS2Object Hopper = getObjects().closest("Hopper");
			Hopper.interact("Fill");
			sleep(random(5500, 8000));
			RS2Object Lever = getObjects().closest("Hopper controls");
			Lever.interact("Operate");
			sleep(random(5500, 8000));
			objects.closest("Ladder").interact("Climb-down");
			sleep(random(2500, 3000));
			objects.closest("Ladder").interact("Climb-down");
			sleep(random(2500, 3000));
			RS2Object Flour = getObjects().closest("Flour bin");
			Flour.interact("Empty");
			sleep(random(2500, 3000));
		}
	}

	private void Egg() throws InterruptedException {
		GroundItem Egg = getGroundItems().closest("Egg");
		GroundItem egg = getGroundItems().closest("egg");
		log("1");
		if (!Areas.Chickenpit.contains(myPlayer()) && !Areas.Eggs.contains(myPlayer())) {
			log("Running to Egg");
			walking.webWalk(area.Eggs());
			sleep(random(1800, 2500));
		} else if ((Egg != null || egg != null) && Areas.Eggs.contains(myPlayer())) {
			log("Taking Egg");
			Egg.interact("Take");
			egg.interact("Take");
			sleep(random(1500, 2000));
		} else if ((Egg == null || egg == null) && Areas.Eggs.contains(myPlayer())) {
			log("No egg hopping");
			worlds.hopToF2PWorld();
			sleep(random(4000, 7000));
			tabs.open(Tab.INVENTORY);
			sleep(300);
		}
	}

	private void TakingBucket() throws InterruptedException {
		if (!inventory.contains("Bucket") && !inventory.contains("Bucket of milk")
				&& !Areas.SellerFULL.contains(myPlayer())) {
			walking.webWalk(area.Cookroom());
			sleep(random(700, 1200));
			objects.closest("Trapdoor").interact("Climb-down");
			sleep(random(5000, 6000));
		} else if (Areas.SellerFULL.contains(myPlayer()) && !inventory.contains("Bucket")) {
		
			sleep(random(1500, 2500));
			if (Bucket != null) {
				sleep(200);
				log("Taking Bucket");
				Bucket.interact("Take");
				sleep(random(2200, 2900));
				log("Walking back to staris");
			}
		} else if (inventory.contains("Bucket")) {
			walking.webWalk(area.SellerS());
			sleep(1600);
			objects.closest("Ladder").interact("Climb-up");
			sleep(random(1000, 2000));
			log("Getting resourses");
		}
	}

	private void TakingPot() throws InterruptedException {
		GroundItem Pot = getGroundItems().closest("Pot");
		log("Taking pot?");
		if (!inventory.contains("Pot") && !Areas.Cookroom.contains(myPlayer())) {
			walking.webWalk(area.Cookroom());
			sleep(random(1000, 1500));
		}
		if (!inventory.contains("Pot") && Areas.Cookroom.contains(myPlayer())) {
			if (Pot != null) {
				log("Taking pot");
				Pot.interact("Take");
				sleep(random(1500, 2000));
			}
		}
	}

	// ADD tp to LB

	private void Milk() throws InterruptedException {
		if (!inventory.contains("Bucket of milk")) {
			walking.webWalk(area.Cow());
			log("Sleeping");
			sleep(random(8500, 9000));
			log("Milking");
			RS2Object Cow = getObjects().closest("Dairy cow");
			Cow.interact("Milk");
			sleep(random(7500, 9000));
			Completecookingquest();
		}

	}

	private void Completecookingquest() throws InterruptedException {
		if (Areas.Cow.contains(myPlayer())) {
			getTabs().open(Tab.SKILLS);
			if (tabs.isOpen(Tab.SKILLS)) {
				getMagic().castSpell(Spells.NormalSpells.LUMBRIDGE_TELEPORT, "Cast");
				sleep(1500);
			}
			while (myPlayer().isAnimating()) {
				sleep(random(1000, 2000));
			}
		}
		if (Areas.LB.contains(myPlayer())) {
			if (!Areas.Cookroom.contains(myPlayer())) {
				log("Walking to coock");
				walking.webWalk(area.Cookroom());
			} else {
				log("Starting quest");
				NPC Coock = getNpcs().closest("Cook");
				Coock.interact("Talk-to");
				sleep(random(1000, 1652));
				log("Going to dialog");
				if (getDialogues().isPendingContinuation()) {
					log("Continue");
					getDialogues().clickContinue();
					sleep(Script.random(800, 1200));
					onStart();
				}
			}
		}
	}

	private void DropInventory() throws InterruptedException {
		log("Droping items to bank");
		walking.webWalk(Banks.LUMBRIDGE_UPPER);
		log("Opening bank");
		bank.open();
		sleep(random(600, 1000));
		bank.depositAll();
		sleep(random(600, 1000));
		bank.close();
		StartingCookingquest();

	}

	private void StartingCookingquest() throws InterruptedException {
		if (!Areas.Cookroom.contains(myPlayer())) {
			log("Walking to coock");
			walking.webWalk(area.Cookroom());
		} else {
			log("Starting quest");
			sleep(1200);
			NPC Coock = getNpcs().closest("Cook");
			if (Coock != null) {
				Coock.interact("Talk-to");
				sleep(random(3000, 4652));
				log("Going to dialog");
				Completing();
			}
		}
	}

	private void Completing() throws InterruptedException {
		if (Areas.Cookroom.contains(myPlayer())) {
			log("Pending");
			if (getDialogues().isPendingContinuation()) {
				log("Continue");
				getDialogues().clickContinue();
				sleep(Script.random(800, 1200));
			} else if (getDialogues().completeDialogue(Cook1)) {
				log("Chosing dialog for Continue 1");
				sleep(Script.random(800, 1200));
			} else if (getDialogues().completeDialogue(Cook2)) {
				log("Chosing dialog for Continue 2");
				sleep(Script.random(800, 1200));
			} else if (getDialogues().completeDialogue(Cook3)) {
				log("Chosing dialog for Continue 3");
				sleep(Script.random(800, 1200));
			}
		}
	}

	@Override
	public int onLoop() {
		if (quests.isComplete(Quest.COOKS_ASSISTANT)) {
			log("You have completed COOKS_ASSISTANT");
			return -1;
		}
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return random(2000); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onExit() {
		log("Good Bye");
		stop();

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {

		// This is where you will put your code for paint(s)

	}

}