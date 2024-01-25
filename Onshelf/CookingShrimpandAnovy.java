package Onshelf;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.Sleep;
import Core.Support.Time;

import java.awt.*;
import Support.*;

public class CookingShrimpandAnovy extends Script {
	Areas areas = new Areas();
	Time time = new Time();
	int Shrimp = 0;
	int Anovy = 0;

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		if (quests.isComplete(Quest.COOKS_ASSISTANT)) {
			if (myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating()
					&& myPlayer().isOnScreen()) {
				while (settings.areRoofsEnabled() == false) {
					getKeyboard().typeString("::toggleroofs");
					Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
				}
			}
		}else {
			log("Cant cook");
			stop();
		}
		Bankingforstart();
	}

	private void Bankingforstart() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !bank.isOpen()) {
			log("Walking Lb bank");
			walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (bank.isOpen()) {
			bank.depositAll();
			Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
			bank.close();
		} else {
			log("Open bank");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
	}

	private void CheckUp() throws InterruptedException {
		log("Check up");
		if (inventory.isEmpty() && Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			log("In bank afking");
			Takingshrimp();
		} else if ((inventory.isFull() || !inventory.isEmpty())
				&& (!(inventory.contains("Raw shrimps") || inventory.contains("Raw anchovies")))) {
			log("Banking cooked Shrimp");
			Takingshrimp();
		} else if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !Areas.LbCookingStove.contains(myPlayer())) {
			log("Taking shrimp");
			Takingshrimp();
		} else if ((inventory.contains("Raw shrimps") || inventory.contains("Raw anchovies"))
				&& !Areas.LbCoookroomstovebig.contains(myPlayer())) {
			log("Walking to cookspot");
			Walktocookspot();
		} else if (Areas.LbCoookroomstovebig.contains(myPlayer()) && inventory.contains("Raw shrimps")) {
			log("Cooking shrimp");
			CookingShrimp();
		} else if (Areas.LbCoookroomstovebig.contains(myPlayer()) && inventory.contains("Raw anchovies")) {
			log("Cooking Anovy");
			CookingAnovy();
		}
	}

	private void CookingShrimp() throws InterruptedException {
		RS2Object Range = getObjects().closest("Cooking range");
		if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
			if (Range != null) {
				camera.toEntity(Range);
				Sleep.waitCondition(() -> Range.isVisible(), 500, 5000);
				inventory.interact("Use", "Raw shrimps");
				Sleep.waitCondition(() -> inventory.isItemSelected(), 3000);
				Range.interact("Use");
				Sleep.waitCondition(() -> widgets.isVisible(270, 1), 3000);
				if (widgets.isVisible(270, 1)) {
					log("Cooking widget visible");
					widgets.interact(270, 14, 38, "Cook");
					Sleep.waitCondition(() -> !getWidgets().isVisible(270, 1), 500, 5000);
				}
				Sleep.waitCondition(() -> !inventory.contains("Raw shrimps") || getDialogues().isPendingContinuation(),
						500, 30000);
			}
		}
	}

	private void CookingAnovy() throws InterruptedException {
		RS2Object Range = getObjects().closest("Cooking range");
		if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
			if (Range != null) {
				camera.toEntity(Range);
				Sleep.waitCondition(() -> Range.isVisible(), 3000);
				inventory.interact("Use", "Raw anchovies");
				Sleep.waitCondition(() -> inventory.isItemSelected(), 3000);
				Range.interact("Use");
				Sleep.waitCondition(() -> widgets.isVisible(270, 1), 3000);
				if (widgets.isVisible(270, 1)) {
					log("Cooking widget visible");
					widgets.interact(270, 14, 38, "Cook");
					Sleep.waitCondition(() -> !getWidgets().isVisible(270, 1), 500, 5000);
				}
				Sleep.waitCondition(
						() -> !inventory.contains("Raw anchovies") || getDialogues().isPendingContinuation(), 500,
						30000);
			}
		}
	}

	@SuppressWarnings("static-access")
	private void Walktocookspot() throws InterruptedException {
		if (!Areas.LbCookingStove.contains(myPlayer())) {
			walking.webWalk(areas.LbCookingStove());
			log("Walking Cook spot");
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	private void Takinganovy() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			log("Walking Lb bank for anovy");
			walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (bank.isOpen() && bank.getAmount("Raw anchovies") < 10) {
			log("Out of shrimp/anovy");
			stop();
		}
		if (bank.isOpen()) {
			log("Taking anovy");
			Anovy = (int) getBank().getAmount("Raw anchovies");
			if (!inventory.isEmpty()) {
				log("Depositing");
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 200,3000);
			}
			if (inventory.isEmpty()) {
				bank.withdrawAll("Raw anchovies");
				Sleep.waitCondition(() -> inventory.contains("Raw anchovies"), 3000);
			}
		} else if (!bank.isOpen()) {
			log("Bank not open");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
	}

	private void Takingshrimp() throws InterruptedException {
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && !bank.isOpen()) {
			log("Walking Lb bank for shrimp");
			walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500, 700));
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
		}
		if (bank.getAmount("Raw shrimps") < 10) {
			log("No shrimp");
			Takinganovy();
		} else {
			Shrimp = (int) getBank().getAmount("Raw shrimps");
			Anovy = (int) getBank().getAmount("Raw anchovies");
			if (!inventory.isEmpty()) {
				log("Depositing");
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 200,3000);
			}
			if (inventory.isEmpty()) {
				log("Getting Fish");
				bank.withdrawAll("Raw shrimps");
				Sleep.waitCondition(() -> inventory.contains("Raw shrimps"), 3000);
			}
		}
		bank.close();
	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		CheckUp();
		return 1000;
	}

	@Override
	public void onExit() {
		log("Good Bye");
		stop();
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 430, 320);
		g.drawString("Shrimps " + Shrimp, 430, 305);
		g.drawString("Anovy " + Anovy, 430, 295);
		g.drawString("Anovy " + Anovy, 430, 295);

	}
}
