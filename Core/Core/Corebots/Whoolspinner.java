package Core.Corebots;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class Whoolspinner extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Core Whool spinner Has started");
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
		if(!inventory.isEmpty()) {
			log("Too much stuff");
			Bankingforstart();
		}
	}

	private void CheckUp() throws InterruptedException {
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		if (!inventory.contains("Shears")) {
			log("Getting Sheers");
			Shears();
		} else 	if (!inventory.isEmptyExcept("Shears", "Wool", "Ball of wool") && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
				Bankingforstart();
		} else if (myPlayer().isHitBarVisible()) {
			log("You are underattack");
			Safespot();
		} else if (inventory.contains("Shears") && !Areas.LumbgeSheepareabig.contains(myPlayer()) && !inventory.isFull()
				&& !Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("Walking SheepPit");
			SheepPitWalk();
		} else if (inventory.contains("Shears") && Areas.LumbgeSheepareabig.contains(myPlayer())
				&& !inventory.isFull()) {
			log("Getting whools");
			GettingWhool();
		} else if(inventory.isFull() && inventory.contains("Wool") && !Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("Walking to spinning spot");
			Goingtospin();
		} else if (inventory.isFull() && inventory.contains("Wool") && Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
			log("SpinWhool");
			SpinWhool();
		} else if (inventory.isFull() && !inventory.contains("Wool")) {
			log("Banking whool");
			Bankingwhool();
		}

	}
	private void Goingtospin() {
	if (!Areas.LumbgeCastleWhoolSpinning.contains(myPlayer())) {
		log("Walking lb castle to spin");
		walking.webWalk(areas.LumbgeCastleWhoolSpinning());
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
	}
	}

	private void Bankingforstart() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			walking.webWalk(Banks.LUMBRIDGE_UPPER);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			bank.open();
			sleep(random(500,1000));
			Sleep.waitCondition(() -> bank.isOpen(), 500, 3000);
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500,2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
			}
			if (bank.contains("Shears")) {
				bank.withdraw("Shears", 1);
				Sleep.waitCondition(() -> inventory.contains("Shears"), 500, 3000);
			}if (bank.contains("Wool")) {
				bank.withdrawAll("Wool");
				Sleep.waitCondition(() -> inventory.contains("Wool"), 500, 3000);
			}
				log("Bank dont have Shears");
				bank.close();
				Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
			}
	}


	@SuppressWarnings("static-access")
	private void Safespot() {
		if (!Areas.Sheepsafespot.contains(myPlayer())) {
			walking.webWalk(areas.Sheepsafespot);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	@SuppressWarnings("static-access")
	private void Bankingwhool() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			bank.open();
			sleep(random(500,1000));
			Sleep.waitCondition(() -> bank.isOpen(), 500, 3000);
			bank.depositAllExcept("Shears");
			Sleep.waitCondition(() -> !inventory.contains("Ball of wool"), 500, 3000);
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
		}
	}

	private void SpinWhool() throws InterruptedException {
		RS2Object Spin = getObjects().closest("Spinning wheel");
		if (Spin != null) {
			log("Gona spin");
			Spin.interact("Spin");
			sleep(random(500,800));
			Sleep.waitCondition(() -> getWidgets().isVisible(270, 1) ,500,5000);
			if (getWidgets().isVisible(270, 1)) {
				widgets.interact(270, 14, 38, "Spin");
				sleep(random(900, 1500));
				mouse.moveOutsideScreen();
				Sleep.waitCondition(() -> !inventory.contains("Wool") || dialogues.isPendingContinuation(), 500, 100000);
				if (dialogues.isPendingContinuation()) {
					log("in dialoge");
					dialogues.clickContinue();
					sleep(random(500,800));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
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
		int woolold = (int) inventory.getAmount("Wool");
		if(sheepMob != null) {
		sheepMob.interact("Shear");
		sleep(random(1000,2000));
		}
		Sleep.waitCondition(() -> (!(myPlayer().isMoving() && myPlayer().isAnimating())),200,5000);
		int woolnew =(int) inventory.getAmount("Wool");
		Sleep.waitCondition(() -> woolold < woolnew,200,2000);
	}
	
	private void SheepPitWalk() throws InterruptedException {
		if (!Areas.LumbgeSheepPit.contains(myPlayer())) {
			walking.webWalk(areas.LumbgeSheepPit());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	private void Shears() throws InterruptedException {
		GroundItem Sakset = getGroundItems().closest("Shears");
		if (!Areas.LumbgechickenHut.contains(myPlayer())) {
			log("Walking Hut");
			walking.webWalk(areas.LumbgechickenHut());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.LumbgechickenHut.contains(myPlayer())) {
			if (Sakset != null) {
				Sakset.interact("Take");
				Sleep.waitCondition(() -> inventory.contains("Shears"), 500, 7000);
			}
		}
	}

	@Override
	public int onLoop() {
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

	@Override
	public void onPaint(Graphics2D g) {
	}

}