package Onshelf;

import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.Tabs;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.GetLevels;
import Core.Support.Sleep;
import Core.Support.Time;
import Support.*;
import java.awt.*;

public class Xmarktospot extends Script {
	Areas areas = new Areas();
	GetLevels levels = new GetLevels();
	Time time = new Time();

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		if (myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating() && myPlayer().isOnScreen()) {
			while(settings.areRoofsEnabled() == false) {
				getKeyboard().typeString("::toggleroofs");
				Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
			}}
	}

	private void Checkup() throws InterruptedException {
		if (!Areas.Xmarkquestnpc.contains(myPlayer()) && getConfigs().get(2111) == 0) {
			log("Walk start area");
			walking.webWalk(areas.Xmarkquestnpc());
		} else if (Areas.Xmarkquestnpc.contains(myPlayer()) && getConfigs().get(2111) == 0) {
			log("Start quest");
			Startquest();
		} else if (configs.get(2111) == 2 && inventory.contains("Treasure scroll") && !inventory.contains("Spade")) {
			log("Going get spaced");
			Gettingspade();
		} else if (inventory.contains("Spade") && inventory.contains("Treasure scroll")
				&& getConfigs().get(2111) == 2) {
			log("Going to dig first spot");
			Digging();
		} else if (getConfigs().get(2111) == 3) {
			log("Going to dig second spot");
			Diggingspottwo();
		} else if (getConfigs().get(2111) == 4) {
			log("Going to dig tird spot");
			Diggingspotthree();
		} else if (getConfigs().get(2111) == 5) {
			log("Going to dig fourth spot");
			Diggingspotfour();
		} else if (getConfigs().get(2111) == 6) {
			log("Going to complete");
			Completing();
		}
	}

	private void Completing() throws InterruptedException {
		Area Completingarea = new Area(3055, 3247, 3051, 3245);
		String[] Xmark = { "" };

		if (!Completingarea.contains(myPlayer())) {
			log("Walk to completing area");
			walking.webWalk(Completingarea.getRandomPosition());
			sleep(random(2500, 3000));
		}
		if (!getDialogues().inDialogue()) {
			NPC xmark = getNpcs().closest("Veos");
			log("Talking to ");
			xmark.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 3000);
		}
		if (getDialogues().inDialogue()) {
			log("In dialogue");
			dialogues.completeDialogue(Xmark);
		}
		if (configs.get(2111) > 10) {
			log("X mark spot quest done");
			inventory.interact("Rub","Antique lamp");
			Sleep.waitCondition(() -> getWidgets().isVisible(134, 2),3000);
			getWidgets().interact(134, 31, "Advance Defence");
			sleep(random(700,1000));
			getWidgets().interact(134,26, "Ok");
			sleep(random(500,1000));
			stop();
		}
	}

	private void Diggingspotfour() throws InterruptedException {
		Position Digspot = new Position(3077, 3261, 0);
		Area Gate = new Area(3078,3257,3076,3258);


		if (!myPlayer().getPosition().equals(Digspot)) {
			RS2Object gate = getObjects().closest("Gate");
			walking.webWalk(Gate.getRandomPosition());
			sleep(random(1800, 2200));
			getObjects().closest("Gate");
			if (gate.hasAction("Open")) {
				gate.interact("Open");
				log("Opend gate");
				Sleep.waitCondition(() -> gate.hasAction("Close"), 3000);
			}
			sleep(random(1000, 2000));
			log("Walking spot");
			Digspot.interact(bot, "Walk here");
			sleep(random(1500, 2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			Sleep.waitCondition(() -> inventory.contains("Ancient casket"), 3000);
		}
	}

	private void Diggingspotthree() throws InterruptedException {
		Area Digspotbig = new Area(3103, 3270, 3119, 3254);
		Position Digspot = new Position(3110, 3261, 0);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Digspot);
			sleep(random(2500, 3000));
			Digspot.interact(bot, "Walk here");
			sleep(random(1000,2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			Sleep.waitCondition(() -> inventory.contains("Treasure scroll"), 3000);
		}
	}

	private void Diggingspottwo() throws InterruptedException {
		Area Digspotbig = new Area(3204, 3219, 3201, 3211);
		Position Digspot = new Position(3203, 3212, 0);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Digspot);
			sleep(random(2500, 3000));
			Digspot.interact(bot, "Walk here");
			sleep(random(1000,2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			Sleep.waitCondition(() -> inventory.contains("Mysterious orb"), 3000);
		}
	}

	private void Digging() throws InterruptedException {
		Area Digspotbig = new Area(3229, 3210, 3233, 3207);
		Position Digspot = new Position(3230, 3209, 0);

		if (!myPlayer().getPosition().equals(Digspot)) {
			walking.webWalk(Digspot);
			sleep(random(2500, 3000));
			Digspot.interact(bot, "Walk here");
			sleep(random(1000,2000));
		}
		if (myPlayer().getPosition().equals(Digspot)) {
			log("Digging");
			inventory.interact("Dig", "Spade");
			sleep(random(900, 1300));
			if (getDialogues().inDialogue()) {
				dialogues.clickContinue();
			}
		}
	}

	private void Gettingspade() throws InterruptedException {
		Area Frontbigdoors = new Area(3106, 3353, 3111, 3352);
		RS2Object Frontdoors = getObjects().closest("Large door");
		Position Spade = new Position(3121,3359, 0);
		Area Mansion = new Area(3078, 3388, 3132, 3327);
		Area Mansioninside = new Area(new int[][]{{ 3126, 3361 },{ 3120, 3361 },{ 3120, 3374 }, { 3103, 3374 }, { 3104, 3364 }, { 3105, 3354 },
			        { 3117, 3354 },{ 3118, 3353 },{ 3119, 3353 },{ 3120, 3354 },{ 3126, 3354 }});
		Area Spaderoom = new Area(3120, 3360, 3126, 3354);
		
		if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
		}
		if(!Mansion.contains(myPlayer())) {
		if (!Frontbigdoors.contains(myPlayer())) {
			log("Walking to draynor castle");
			log("Walking Front Doors");
			walking.webWalk(Frontbigdoors);
			sleep(random(1587, 2134));
		}

		if (Frontbigdoors.contains(myPlayer()) && Frontdoors != null) {
			Frontdoors.interact("Open");
			sleep(random(1552, 2300));
		}}
		if(!Mansioninside.contains(myPlayer())){
		if (!Spaderoom.contains(myPlayer())) {
			walking.webWalk(Spade);
			sleep(random(1587, 2134));
		}}
		if(Spaderoom.contains(myPlayer())) {
			GroundItem SpadeGround = getGroundItems().closest("Spade");
			camera.toEntity(SpadeGround);
			sleep(random(1000, 1300));
			if(SpadeGround !=null) {
				SpadeGround.interact("Take");
				Sleep.waitCondition(() -> inventory.contains("Spade"), 3000);
			}else {
				Sleep.waitCondition(() -> SpadeGround !=null, 1000,3000);
			}
			
		}
		log("Spade done");
	}

	private void Startquest() throws InterruptedException {
		String[] Xmark = { "I'm looking for a quest.", "Sounds good, what should I do", "Okay, thanks Veos." };
		NPC xmark = getNpcs().closest("Veos");
		if (!getDialogues().inDialogue()) {
			xmark.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 3000);
		}
		if (getDialogues().inDialogue()) {
			dialogues.completeDialogue(Xmark);
		}
		log("Start quest done");
	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		Checkup();

		return random(1000); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onExit() {
		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);

	}

}
