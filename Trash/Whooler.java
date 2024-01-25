package Trash;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Support.Areas;

import java.awt.*;

public class Whooler extends Skelor {
	Areas area = new Areas();

	@Override
	public void onStart() throws InterruptedException {
		sleep(2000);
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
		if (!inventory.isEmptyExcept("Shears", "Wool", "Ball of wool") && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			Bankingforstart();
		} else {
			log("Startup");
			CheckUp();
		}
	}

	private void Bankingforstart() throws InterruptedException {
		walking.webWalk(Banks.LUMBRIDGE_UPPER);
		sleep(1000);
		bank.open();
		sleep(random(400, 700));
		bank.depositAll();
		sleep(random(400, 700));
		bank.close();
		CheckUp();
	}

	private void CheckUp() throws InterruptedException {
		if (!inventory.contains("Shears")) {
			log("Getting Sheers");
			Shears();
		} else if (inventory.contains("Shears") && !Areas.LumbgeSheepareabig.contains(myPlayer()) && !inventory.isFull()
				&& !Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("Walking SheepPit");
			SheepPitWalk();
		} else if (inventory.contains("Shears") && Areas.LumbgeSheepareabig.contains(myPlayer())
				&& !inventory.isFull()) {
			log("Getting whools");
			GettingWhool();
		} else if (inventory.isFull() && inventory.contains("Wool")) {
			log("SpinWhool");
			SpinWhool();
		} else if (inventory.isFull() && !inventory.contains("Wool")) {
			log("Banking whool");
			Bankingwhool();
		}

	}

	private void Bankingwhool() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
		}
		if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			if (!bank.isOpen()) {
				bank.open();
				sleep(random(600, 1000));
			}
			bank.depositAll("Ball of wool");
			sleep(random(400, 700));
			bank.close();
			sleep(random(400, 700));
		}
	}

	private void SpinWhool() throws InterruptedException {
		RS2Object Spin = getObjects().closest("Spinning wheel");
		if (dialogues.isPendingContinuation()) {
			log("in dialoge");
			dialogues.clickContinue();
		} else if (inventory.contains("Wool") && myPlayer().isAnimating()) {
			log("Spinning");
			sleep(random(1500, 3531));
		} else if (!Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("Walking lb castle to spin");
			walking.webWalk(area.LumbgeCastleWhoolSpinning());
			sleep(1000);
		} else if (Spin != null) {
			log("Gona spin");
			Spin.interact("Spin");
			sleep(random(3000, 5800));
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
		NPC sheepMob = getNpcs().closest(sheepFilter);
		sheepMob.interact("Shear");
		sleep(random(3550, 4111));

	}

	private void SheepPitWalk() throws InterruptedException {
		if (!Areas.LumbgeSheepPit.contains(myPlayer())) {
			walking.webWalk(area.LumbgeSheepPit());
			sleep(1000);
		}
	}

	private void Shears() throws InterruptedException {
		GroundItem Sakset = getGroundItems().closest("Shears");
		if (!Areas.LumbgechickenHut.contains(myPlayer())) {
			log("Walking Hut");
			walking.webWalk(area.LumbgechickenHut());
		} else if (Areas.LumbgechickenHut.contains(myPlayer())) {
			sleep(random(1300, 1800));
			if (Sakset != null) {
				Sakset.interact("Take");
				sleep(random(1500, 2500));
			}
		}
	}

	@Override
	public int onLoop() {
		try {
			CheckUp();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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