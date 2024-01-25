package Core.Quests;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

public class SheepShrederquest extends Skeleton {
	Areas areas = new Areas();
	int Whoolamount = random(20,23);

	@Override
	public void onStart() throws InterruptedException {
		log("SheepShreder Quest Has started");
		if(getConfigs().get(179) == 21) {
			log("Going to loop completed");
			onLoop();
		}
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
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
		
	}

	@SuppressWarnings("static-access")
	private void Bankingforstart() throws InterruptedException {
		if (inventory.isEmpty()) {
			log("inventory epty");
		} else {
			if (!Areas.LumbgeBank.contains(myPlayer())) {
				walking.webWalk(areas.LumbgeBank);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				sleep(random(500,1000));
				Sleep.waitCondition(() -> bank.isOpen(), 300, 3000);
				if (!inventory.isEmpty()) {
					bank.depositAll();
					sleep(random(1500,2200));

					Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
				}
				bank.withdraw("Shears", 1);
				Sleep.waitCondition(() -> inventory.contains("Shears"), 500, 3000);
				if (bank.contains("Wool")) {
					bank.withdraw("Wool", 20);
					Sleep.waitCondition(() -> inventory.contains("Wool"), 500, 3000);
				}
				if (bank.contains("Ball of wool")) {
					bank.withdraw("Ball of wool", 20);
					Sleep.waitCondition(() -> inventory.contains("Ball of wool"), 500, 3000);
				}
				bank.close();
				Sleep.waitCondition(() -> !bank.isOpen(), 3000);
			}
		}
	}

	private void CheckUp() throws InterruptedException {
		if(getConfigs().get(179) == 21) {
			onLoop();
		}
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
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
				&& !Areas.LumbgeCastleWhoolSpinning.contains(myPlayer()) && inventory.getAmount("Whool") <= Whoolamount) {
			log("Walking SheepPit");
			SheepPitWalk();
		} else if (inventory.contains("Shears") && Areas.LumbgeSheepareabig.contains(myPlayer())
				&& inventory.getAmount("Wool") <= Whoolamount) {
			log("Getting whools");
			GettingWhool();
		} else if (inventory.getAmount("Wool") >= 20) {
			log("SpinWhool");
			SpinWhool();
		}
		if (!(inventory.contains("Ball of wool") || inventory.contains("Shears"))) {
			log("Banking");
			Bankingforstart();
		}
	}

	private void Completing() throws InterruptedException {
		if (quests.isComplete(Quest.SHEEP_SHEARER)) {
			log("Completed");
			onLoop();
		}
		if (!Areas.LumbgechickenHut.contains(myPlayer())) {
			walking.webWalk(areas.LumbgechickenHut());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		
		NPC Farmer = getNpcs().closest("Fred the Farmer");
		if (Farmer != null) {
			Farmer.interact("Talk-to");
			sleep(random(500,1000));
			Sleep.waitCondition(() -> getDialogues().isPendingContinuation(), 1000, 5000);
		}
		String[] Farmer1 = { "I'm looking for a quest.", "Yes, okay. I can do that." };
		log("Starting quest");
		log("Going to dialog");
		if (getDialogues().completeDialogue(Farmer1)) {
			log("Chosing dialog for Continue 1");
			Sleep.waitCondition(() -> (!(getDialogues().isPendingOption() || getDialogues().isPendingContinuation())),
					5000);
		}
	}

	private void SpinWhool() throws InterruptedException {
		RS2Object Spin = getObjects().closest("Spinning wheel");
		if (dialogues.isPendingContinuation()) {
			log("in dialoge");
			dialogues.clickContinue();
			sleep(random(800,1200));
		} else if (inventory.contains("Wool") && Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())
				&& myPlayer().isAnimating()) {
			log("Spinning");
			Sleep.waitCondition(() -> !inventory.contains("Wool") || dialogues.isPendingContinuation(), 500, 50000);
		} else if (!Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("Walking lb castle to spin");
			walking.webWalk(areas.LumbgeCastleWhoolSpinning());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		} else if (Spin != null) {
			log("Gona spin");
			Spin.interact("Spin");
			sleep(random(200,400));
			Sleep.waitCondition(() -> getWidgets().isVisible(270, 1), 500, 10000);
			if (getWidgets().isVisible(270, 1)) {
				widgets.interact(270, 14, 38, "Spin");
				sleep(random(900, 1500));
			}
		}
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

	@SuppressWarnings("static-access")
	private void SheepPitWalk() throws InterruptedException {
		if (!Areas.LumbgeSheepPit.contains(myPlayer())) {
			walking.webWalk(areas.LumbgeSheepPit);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		}
	}

	@SuppressWarnings("static-access")
	private void Shears() throws InterruptedException {
		GroundItem Sakset = getGroundItems().closest("Shears");
		if (!Areas.SheepshrederHut.contains(myPlayer())) {
			log("Walking Hut");
			walking.webWalk(areas.Sheartable());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		} else if (Areas.SheepshrederHut.contains(myPlayer())) {
			if (Sakset != null) {
				Sakset.interact("Take");
				Sleep.waitCondition(() -> inventory.contains("Shears"), 500, 10000);
			}
		}
	}

	@Override
	public int onLoop() {
		if(getConfigs().get(179) == 21) {
			log("You completed Sheep_Shearer");
			return -10;
		}
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); }
	

	@Override
	public void onExit() {
		log("Good Bye");

	}

}