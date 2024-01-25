package Onshelf;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;
import Support.Areas;

import java.awt.*;

public class SheepShrederquest extends Script {
	Areas area = new Areas();

	public static String[] Farmer1 = { "I'm looking for a quest.", "Yes okay. I can do that." };

	@Override
	public void onStart() throws InterruptedException {
		if (quests.isComplete(Quest.SHEEP_SHEARER)) {
			log("You completed alredy");
			onExit();
		}
		if (myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating() && myPlayer().isOnScreen()) {
			while (settings.areRoofsEnabled() == false) {
				getKeyboard().typeString("::toggleroofs");
				Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
			}
		}
		if (!inventory.isEmptyExcept("Shears", "Wool", "Ball of wool") && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			Bankingforstart();
		}
	}

	private void Bankingforstart() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			walking.webWalk(Banks.LUMBRIDGE_UPPER);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 300, 3000);
			bank.depositAll();
			Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 3000);
		}
	}

	private void CheckUp() throws InterruptedException {
		if (!inventory.contains("Shears")) {
			log("Getting Sheers");
			Shears();
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 3000);
		} else if (inventory.getAmount("Ball of wool") >= 20) {
			log("Completing quest");
			Completing();
		} else if (inventory.contains("Shears") && !Areas.LumbgeSheepareabig.contains(myPlayer())
				&& !Areas.LumbgeCastleWhoolSpinning.contains(myPlayer()) && inventory.getAmount("Whool") <= 20) {
			log("Walking SheepPit");
			SheepPitWalk();
		} else if (inventory.contains("Shears") && Areas.LumbgeSheepareabig.contains(myPlayer())
				&& inventory.getAmount("Wool") <= 20) {
			log("Getting whools");
			GettingWhool();
		} else if (inventory.getAmount("Wool") >= 20) {
			log("SpinWhool");
			SpinWhool();
		}
	}

	private void Completing() throws InterruptedException {
		NPC Farmer = getNpcs().closest("Fred the Farmer");
		if (!Areas.LumbgechickenHut.contains(myPlayer())) {
			walking.webWalk(area.LumbgechickenHut());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		Farmer.interact("Talk-to");
		Sleep.waitCondition(() -> getDialogues().isPendingContinuation(), 1000, 5000);
		log("Starting quest");
		log("Going to dialog");
		if (getDialogues().completeDialogue(Farmer1)) {
			log("Chosing dialog for Continue 1");
			Sleep.waitCondition(() -> (!(getDialogues().isPendingOption() || getDialogues().isPendingContinuation())),
					5000);
		}
		if(quests.isComplete(Quest.SHEEP_SHEARER)) {
			log("Completed");
			stop();
		}
	}

	private void SpinWhool() throws InterruptedException {
		if (dialogues.isPendingContinuation()) {
			log("in dialoge");
			dialogues.completeDialogue("");
			Sleep.waitCondition(() -> !getDialogues().isPendingContinuation(), 3000);
		}
		if (!Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("Walking lb castle to spin");
			walking.webWalk(area.LumbgeCastleWhoolSpinning());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		}
		RS2Object Spin = getObjects().closest("Spinning wheel");

		if (Spin != null) {
			log("Gona spin");
			Spin.interact("Spin");
			Sleep.waitCondition(() -> getWidgets().isVisible(270, 1), 1000, 5000);
			widgets.interact(270, 14, 38, "Spin");
			Sleep.waitCondition(() -> !getWidgets().isVisible(270, 1), 5000);
		}
		Sleep.waitCondition(() -> !inventory.contains("Wool"), 1000, 30000);
	}

	private void GettingWhool() throws InterruptedException {
		if (camera.getPitchAngle() < 50) {
			log("Moving camera angle");
			int targetPitch = random(50, 67);
			log(targetPitch);
			camera.movePitch(targetPitch);
		}
		Filter<NPC> sheepFilter = new Filter<NPC>() {
			@Override
			public boolean match(NPC n) {
				return n.getName().equals("Sheep") && !n.hasAction("Talk-to") && n.hasAction("Shear")
						&& Areas.LumbgeSheepareabig.contains(n);
			}
		};
		@SuppressWarnings("unchecked")
		NPC sheepMob = getNpcs().closest(sheepFilter);
		sheepMob.interact("Shear");
		sleep(random(3550, 4111));

	}

	private void SheepPitWalk() throws InterruptedException {
		walking.webWalk(Areas.LumbgeSheepPit);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
	}

	@SuppressWarnings("static-access")
	private void Shears() throws InterruptedException {
		GroundItem Sakset = getGroundItems().closest("Shears");
		if (!Areas.SheepshrederHut.contains(myPlayer())) {
			log("Walking Hut");
			walking.webWalk(area.Sheartable());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		} else if (Areas.SheepshrederHut.contains(myPlayer())) {
			if (Sakset != null) {
				Sakset.interact("Take");
				Sleep.waitCondition(() -> inventory.contains("Shears"), 500, 10000);
			}
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		CheckUp();

		return random(1000); // The amount of time in milliseconds before the loop starts over

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