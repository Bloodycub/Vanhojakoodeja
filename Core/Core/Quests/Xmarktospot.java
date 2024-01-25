package Core.Quests;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class Xmarktospot extends Skeleton {
	Areas areas = new Areas();
	GetLevels levels = new GetLevels();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("X mark the spot Has started");
		if (configs.get(2111) > 10) {
			log("X mark spot completed");
			onLoop();
		}
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
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
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true,200, 5000);
		}
		if (inventory.getEmptySlots() <= 5) {
			log("Banking");
			Bankingforstart();
		}
	}

	@SuppressWarnings("static-access")
	private void Bankingforstart() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(), 300, 3000);
			if (!inventory.isEmpty()) {
				bank.depositAll();
				sleep(random(1500, 2200));

				Sleep.waitCondition(() -> inventory.isEmpty(),200,3000);
			}
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(),200, 3000);
		}
	}

	@SuppressWarnings("static-access")
	private void Checkup() throws InterruptedException {
		if (configs.get(2111) > 10) {
			log("X mark spot completed");
			onLoop();
		}
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
		}
		if (!Areas.Xmarkquestnpc.contains(myPlayer()) && getConfigs().get(2111) == 0) {
			log("Walk start area");
			walking.webWalk(areas.Xmarkquestnpc());
		} else if (Areas.Xmarkquestnpc.contains(myPlayer()) && getConfigs().get(2111) == 0) {
			log("Start quest");
			Startquest();

		} else if (configs.get(2111) == 2 && inventory.contains("Treasure scroll") && !inventory.contains("Spade")) {
			log("Going get spaced");
			Gettingspade();
		} else if (getConfigs().get(2111) == 2 && inventory.contains("Spade")
				&& inventory.contains("Treasure scroll")) {
			log("Going to dig first spot");
			Digging();
		} else if (getConfigs().get(2111) == 3 && inventory.contains("Spade")
				&& inventory.contains("Treasure scroll")) {
			log("Going to dig second spot");
			Diggingspottwo();
		} else if (getConfigs().get(2111) == 4 && inventory.contains("Spade") && inventory.contains("Mysterious orb")) {
			log("Going to dig tird spot");
			Diggingspotthree();
		} else if (getConfigs().get(2111) == 5 && inventory.contains("Spade")) {
			log("Going to dig fourth spot");
			Diggingspotfour();
		} else if (getConfigs().get(2111) == 6 && inventory.contains("Ancient casket")) {
			log("Going to complete");
			Completing();
		} else if (getConfigs().get(2111) >= 2) {
			if (!(inventory.contains("Spade") || inventory.contains("Treasure scroll"))
					&& getConfigs().get(2111) == 2) {
				log("Dont have spade go bank get  one");
				GetStuffFromLb();
			} else if (!(inventory.contains("Spade") || inventory.contains("Treasure scroll"))
					&& getConfigs().get(2111) == 3) {
				log("Dont have spade go bank get  one");
				GetStuffFromLb();
			} else if (!(inventory.contains("Spade")
					|| inventory.contains("Mysterious orb") && getConfigs().get(2111) == 4)) {
				log("Dont have spade go bank get  one");
				GetStuffFromDraynor();
			} else if (!inventory.contains("Spade") && getConfigs().get(2111) == 5) {
				log("Dont have spade go bank get  one");
				GetStuffFromDraynor();
			} else if (!(inventory.contains("Spade") || inventory.contains("Ancient casket"))
					&& getConfigs().get(2111) == 6) {
				log("Dont have spade go bank get  one");
				GetStuffFromDraynor();
			}
		}
	}

	/*
	 * X Mark spot 2111:0 not started 2111:1 Given task 2111:2 first dig 2111:3
	 * second dig 2111:4 mysterious orb needed 2111:5 dig 2111:6 Ancient casket
	 * digged 2111: > 10 completed
	 */
	@SuppressWarnings("static-access")
	private void GetStuffFromLb() throws InterruptedException {
		if (!Areas.LumbgeBank.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(areas.LumbgeBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 3000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(),200, 3000);
		}
		if (bank.isOpen()) {
			bank.depositAll();
			sleep(random(1500, 2200));

			Sleep.waitCondition(() -> inventory.isEmpty(),200, 3000);
			if (getBank().contains("Treasure scroll")) {
				bank.withdraw("Treasure scroll", 1);
				Sleep.waitCondition(() -> inventory.contains("Treasure scroll"), 200, 3000);
			}
			if (getBank().contains("Spade") && !getInventory().contains("Spade")) {
				bank.withdraw("Spade", 1);
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> inventory.contains("Spade"), 200, 3000);
			}
			bank.close();
			Sleep.waitCondition(() -> bank.close(), 200, 3000);
		}
	}

	@SuppressWarnings("static-access")
	private void GetStuffFromDraynor() throws InterruptedException {
		if (!Areas.DraynorBank.contains(myPlayer())) {
			log("Walking DRAYNOR bank");
			walking.webWalk(areas.DraynorBank.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 3000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500, 1000));
			Sleep.waitCondition(() -> bank.isOpen(),200, 3000);
		}
		if (bank.isOpen()) {
			bank.depositAll();
			sleep(random(1500, 2200));

			Sleep.waitCondition(() -> inventory.isEmpty(),200, 3000);
			if (getBank().contains("Treasure scroll")) {
				bank.withdraw("Treasure scroll", 1);
				sleep(random(200,400));
				Sleep.waitCondition(() -> inventory.contains("Treasure scroll"), 500, 3000);
			}
			if (getBank().contains("Spade") && !getInventory().contains("Spade")) {
				bank.withdraw("Spade", 1);
				sleep(random(200,400));
				Sleep.waitCondition(() -> inventory.contains("Spade"), 500, 3000);
			}
			if (getBank().contains("Ancient casket") && !getInventory().contains("Ancient casket")) {
				bank.withdraw("Ancient casket", 1);
				sleep(random(200,400));
				Sleep.waitCondition(() -> inventory.contains("Ancient casket"), 500, 3000);
			}
			if (getBank().contains("Mysterious orb") && !getInventory().contains("Mysterious orb")) {
				bank.withdraw("Mysterious orb", 1);
				sleep(random(200,400));
				Sleep.waitCondition(() -> inventory.contains("Mysterious orb"), 500, 3000);
			}
			bank.close();
			Sleep.waitCondition(() -> bank.close(), 500, 3000);
		}
	}

	private void Completing() throws InterruptedException {
		Area Completingarea = new Area(3055, 3247, 3051, 3245);
		String[] Xmark = { "" };

		if (!Completingarea.contains(myPlayer())) {
			log("Walk to completing area");
			walking.webWalk(Completingarea.getRandomPosition());
			sleep(random(1500, 2000));
		}
		if (!getDialogues().inDialogue()) {
			NPC xmark = getNpcs().closest("Veos");
			log("Talking to ");
			xmark.interact("Talk-to");
			sleep(random(500,800));
			Sleep.waitCondition(() -> getDialogues().inDialogue(),200, 3000);
		}
		if (getDialogues().inDialogue()) {
			log("In dialogue");
			dialogues.completeDialogue(Xmark);
		}
		if (configs.get(2111) > 10) {
			log("X mark spot quest done");
			inventory.interact("Rub", "Antique lamp");
			Sleep.waitCondition(() -> getWidgets().isVisible(134, 2),200, 3000);
			getWidgets().interact(134, 31, "Advance Defence");
			sleep(random(700, 1000));
			getWidgets().interact(134, 26, "Ok");
			sleep(random(500, 1000));
		}
	}

	private void Diggingspotfour() throws InterruptedException {
		Position Digspot = new Position(3077, 3261, 0);
		Area Gate = new Area(3078, 3257, 3076, 3258);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Gate.getRandomPosition());
			sleep(random(1000, 1500));
			RS2Object gate = getObjects().closest("Gate");
			if (gate.hasAction("Open")) {
				gate.interact("Open");
				sleep(random(1000,2000));
				log("Opend gate");
				Sleep.waitCondition(() -> gate.hasAction("Close"), 200, 3000);
			} else {
				sleep(random(1000, 2000));
				log("Walking spot");
				Digspot.interact(bot, "Walk here");
				sleep(random(1500, 2000));
			}
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			Sleep.waitCondition(() -> inventory.contains("Ancient casket"), 200, 3000);
		}
	}

	private void Diggingspotthree() throws InterruptedException {
		@SuppressWarnings("unused")
		Area Digspotbig = new Area(3103, 3270, 3119, 3254);
		Position Digspot = new Position(3110, 3261, 0);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Digspot);
			sleep(random(1500, 2000));
			Digspot.interact(bot, "Walk here");
			sleep(random(1000, 2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			Sleep.waitCondition(() -> inventory.contains("Treasure scroll"), 200,3000);
		}
	}

	private void Diggingspottwo() throws InterruptedException {
		@SuppressWarnings("unused")
		Area Digspotbig = new Area(3204, 3219, 3201, 3211);
		Position Digspot = new Position(3203, 3212, 0);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Digspot);
			sleep(random(1500, 2000));
			Digspot.interact(bot, "Walk here");
			sleep(random(1000, 2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			Sleep.waitCondition(() -> inventory.contains("Mysterious orb"),200, 3000);
		}
	}

	private void Digging() throws InterruptedException {
		@SuppressWarnings("unused")
		Area Digspotbig = new Area(3229, 3210, 3233, 3207);
		Position Digspot = new Position(3230, 3209, 0);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Digspot);
			sleep(random(1500, 2000));
			Digspot.interact(bot, "Walk here");
			sleep(random(1000, 2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			if (getDialogues().inDialogue()) {
				dialogues.clickContinue();
				sleep(random(500,800));
			}
		}
	}

	private void Gettingspade() throws InterruptedException {
		Area Frontbigdoors = new Area(3106, 3353, 3111, 3352);
		RS2Object Frontdoors = getObjects().closest("Large door");
		Position Spade = new Position(3121, 3359, 0);
		Area Mansion = new Area(3078, 3388, 3132, 3327);
		Area Mansioninside = new Area(new int[][] { { 3126, 3361 }, { 3120, 3361 }, { 3120, 3374 }, { 3103, 3374 },
				{ 3104, 3364 }, { 3105, 3354 }, { 3117, 3354 }, { 3118, 3353 }, { 3119, 3353 }, { 3120, 3354 },
				{ 3126, 3354 } });
		Area Spaderoom = new Area(3120, 3360, 3126, 3354);

		if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			sleep(random(200,400));
		}
		if (!Mansion.contains(myPlayer())) {
			if (!Frontbigdoors.contains(myPlayer())) {
				log("Walking to draynor castle");
				log("Walking Front Doors");
				walking.webWalk(Frontbigdoors);
				sleep(random(1587, 2134));
			}

			if (Frontbigdoors.contains(myPlayer()) && Frontdoors != null) {
				Frontdoors.interact("Open");
				sleep(random(1552, 2300));
			}
		}
		if (!Mansioninside.contains(myPlayer())) {
			if (!Spaderoom.contains(myPlayer())) {
				walking.webWalk(Spade);
				sleep(random(1587, 2134));
			}
		}
		if (Spaderoom.contains(myPlayer())) {
			GroundItem SpadeGround = getGroundItems().closest("Spade");
			camera.toEntity(SpadeGround);
			sleep(random(1000, 1300));
			if (SpadeGround != null) {
				SpadeGround.interact("Take");
				sleep(random(500,800));
				Sleep.waitCondition(() -> inventory.contains("Spade"),200, 3000);
			} else {
				Sleep.waitCondition(() -> SpadeGround != null, 1000, 3000);
			}
		}
		log("Spade done");
	}

	private void Startquest() throws InterruptedException {
		String[] Xmark = { "I'm looking for a quest.", "Sounds good, what should I do", "Okay, thanks Veos." };
		NPC xmark = getNpcs().closest("Veos");
		if (!getDialogues().inDialogue()) {
			xmark.interact("Talk-to");
			sleep(random(200,400));
			Sleep.waitCondition(() -> getDialogues().inDialogue(),200, 3000);
		}
		if (getDialogues().inDialogue()) {
			dialogues.completeDialogue(Xmark);
		}
		log("Start quest done");
	}

	@Override
	public int onLoop() {
		time.Timerun();
		if (configs.get(2111) > 10) {
			log("X mark spot completed");
			return -10;
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 400 + random(100);
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
