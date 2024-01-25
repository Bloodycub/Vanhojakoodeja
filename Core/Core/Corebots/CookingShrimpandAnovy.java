package Core.Corebots;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class CookingShrimpandAnovy extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();
	int Shrimp = 0;
	int Anovy = 0;

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("CookingShrimpandAnovy Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating()
				&& myPlayer().isOnScreen(), 500, 5000);
		if (quests.isComplete(Quest.COOKS_ASSISTANT)) {
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
				Sleep.waitCondition(() -> settings.areRoofsEnabled() == true,200,5000);
			}
			if (!inventory.contains("Raw shrimps")) {
				log("Inventory not empty");
				Bankingforstart();
			}
		}

	}

	private void Bankingforstart() throws InterruptedException {
			if (Areas.LumbgeBank.contains(myPlayer()) && !bank.isOpen()) {
				log("Walking Lb bank");
				walking.webWalk(areas.LumbgeBank);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> bank.isOpen(),200,3000);
			}
			if (bank.isOpen()) {
				Sleep.waitCondition(() -> bank.isOpen(), 200,3000);
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(),200,3000);
				if (bank.isOpen() && bank.getAmount(Anovy) < 10) {
					log("No Anovys in check up");
					onLoop();
				}
				bank.close();
				Sleep.waitCondition(() -> !bank.isOpen(), 200, 5000);

			}
		}

	private void CheckUp() throws InterruptedException {
		log("Check up");
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));

		}
		if (bank.isOpen() && bank.getAmount(Anovy) < 10) {
			log("No Anovys in check up");
			onLoop();
		}
		if (inventory.isEmpty() && Areas.LumbgeBank.contains(myPlayer())) {
			log("In bank afking");
			Takingshrimp();
		} else if ((inventory.isFull() || !inventory.isEmpty())
				&& (!(inventory.contains("Raw shrimps") || inventory.contains("Raw anchovies")))) {
			log("Banking cooked Shrimp");
			Takingshrimp();
		} else if (!Areas.LumbgeBank.contains(myPlayer()) && !Areas.LbCookingStove.contains(myPlayer())) {
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
				Sleep.waitCondition(() -> Range.isVisible(), 200, 5000);
				inventory.interact("Use", "Raw shrimps");
				sleep(random(200,600));
				Sleep.waitCondition(() -> inventory.isItemSelected(),200,3000);
				Range.interact("Use");
				sleep(random(200,600));
				Sleep.waitCondition(() -> widgets.isVisible(270, 1),200,3000);
				if (widgets.isVisible(270, 1)) {
					log("Cooking widget visible");
					widgets.interact(270, 14, 38, "Cook");
					sleep(random(200,600));
					Sleep.waitCondition(() -> !getWidgets().isVisible(270, 1), 200, 5000);
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
				Sleep.waitCondition(() -> Range.isVisible(),200,3000);
				inventory.interact("Use", "Raw anchovies");
				sleep(random(200,600));
				Sleep.waitCondition(() -> inventory.isItemSelected(),200,3000);
				Range.interact("Use");
				sleep(random(200,600));
				Sleep.waitCondition(() -> widgets.isVisible(270, 1),200,3000);
				if (widgets.isVisible(270, 1)) {
					log("Cooking widget visible");
					widgets.interact(270, 14, 38, "Cook");
					sleep(random(200,600));
					Sleep.waitCondition(() -> !getWidgets().isVisible(270, 1), 200, 5000);
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
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
	}

	private void Takinganovy() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking Lb bank for anovy");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!bank.isOpen()) {
			log("Bank not open");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(),200,3000);
		}
		if (bank.isOpen()) {
			log("Taking anovy");
			Anovy = (int) getBank().getAmount("Raw anchovies");
			if (bank.isOpen() && bank.getAmount(Anovy) < 10) {
				log("No anowys");
				onLoop();
			} else {
				if (!inventory.isEmpty()) {
					log("Depositing");
					bank.depositAll();
					sleep(random(1500, 2200));
					Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000);
				}
				if (inventory.isEmpty()) {
					bank.withdrawAll("Raw anchovies");
					Sleep.waitCondition(() -> inventory.contains("Raw anchovies"),200,3000);
				}
			}
		}
	}

	private void Takingshrimp() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer()) && !bank.isOpen()) {
			log("Walking Lb bank for shrimp");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(),200,3000);
			Shrimp = (int) getBank().getAmount("Raw shrimps");
			Anovy = (int) getBank().getAmount("Raw anchovies");
		}
		if (bank.getAmount(Shrimp) < 10) {
			log("No shrimp");
			Takinganovy();
		} else {
			if (!inventory.isEmpty()) {
				log("Depositing");
				bank.depositAll();
				sleep(random(1500, 2200));
				Sleep.waitCondition(() -> inventory.isEmpty(), 200, 3000);
			}
			if (inventory.isEmpty()) {
				log("Getting Fish");
				bank.withdrawAll("Raw shrimps");
				Sleep.waitCondition(() -> inventory.contains("Raw shrimps"),200,3000);
			}
		}
		bank.close();
	}

	@Override
	public int onLoop() {
		time.Timerun();
		if (bank.getAmount(Anovy) < 10) {
			log("Out of shrimp/anovy");
			return -1;
		}
		if (!quests.isComplete(Quest.COOKS_ASSISTANT)) {
			log("Cant cook do CookAssist");
			return -1;
		}
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 400 + random(100);
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
