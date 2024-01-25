package Trash;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class oldFishing extends Skeleton {
	final String[] Fishingspotname = { "Fishing spot" };
	final String Net = "Small fishing net";
	private int FishLevel; // fishing
	int Shrimp = 0;
	int Anovy = 0;
	int totalfishes = 0;
	public Areas areas = new Areas();
	public Time time = new Time();
	private int moveoutornot;

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Fishing is started");
		FishLevel = skills.getStatic(Skill.FISHING);
		Sleep.waitCondition(() -> myPlayer().isVisible() && !myPlayer().isMoving() && !myPlayer().isAnimating() && myPlayer().isOnScreen(),500,5000);
			while (settings.areRoofsEnabled() == false) {
				log("Turningg off roof");
				getKeyboard().typeString("::toggleroofs");
				Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
			}
			if(!getTabs().isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 200,3000);
		}
			
			
			
			
			
			
			if() {
				Getnet();
			}
	}

	
	
	
	
	private void CheckupOverfifteen() throws InterruptedException {
		if (inventory.isFull()) {
			log("Banking anovy");
			Getnet();
		} else if (!Areas.AnovyBigarea.contains(myPlayer())) {
			log("Walking anovy");
			Walkspotanovy();
		} else if (!inventory.isFull() && Areas.AnovyBigarea.contains(myPlayer())) {
			FishAnovy();
		}
	}
	
	

	private void Walkspotanovy() throws InterruptedException {
		log("Walking Anovy");
		walking.webWalk(areas.Anovyspot());
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
	}

	private void FishAnovy() throws InterruptedException {
		NPC shrimp = getNpcs().closest(Fishingspotname);
		if (!myPlayer().isAnimating() && !myPlayer().isMoving()
				&& Areas.AnovyBigarea.contains(myPlayer()) && !inventory.isFull()) {
			log("Start Fishing");
			camera.toEntity(shrimp);
			Sleep.waitCondition(() -> shrimp.isOnScreen(), 500, 5000);
			shrimp.interact("Small Net");
			moveoutornot = random(0, 6);
			if (moveoutornot == 4 || moveoutornot == 5) {
				log("Moving out");
				mouse.moveOutsideScreen();
			}
			Sleep.waitCondition(() -> inventory.isFull() || getDialogues().isPendingContinuation(),
					1000, 50000);
		}
	}

	

	public void CheckUpUnderfifteen() throws InterruptedException {
		if (inventory.isFull()) {
			inventory.dropAll("Raw anchovies");
			inventory.dropAll("Raw shrimps");
		} else if (!Areas.ShrimpfishingBIGarea.contains(myPlayer())) {
			log("Walking shrimp spot");
			Walkspot();
		} else if (!inventory.isFull() && Areas.ShrimpfishingBIGarea.contains(myPlayer())) {
			log("Fishing shrimp");
			FishShrimp();
		}
	}

	public void Walkspot() throws InterruptedException {
		log("Walking shrimps");
		walking.webWalk(areas.Shrimpspot);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,5000);
	}

	public void FishShrimp() throws InterruptedException {
		NPC shrimp = getNpcs().closest(Fishingspotname);
		if (shrimp == null) {
			log("Checking spots this one is null");
			Checkspots();
		}
		if (!myPlayer().isAnimating() && shrimp != null && !myPlayer().isMoving()
				&& Areas.ShrimpfishingBIGarea.contains(myPlayer()) && !inventory.isFull()) {
			log("Start Fishing");
			camera.toEntity(shrimp);
			Sleep.waitCondition(() -> shrimp.isOnScreen(), 500, 5000);
			shrimp.interact("Small Net");
			moveoutornot = random(0, 6);
			if (moveoutornot == 4 || moveoutornot == 5) {
				log("Moving out");
				mouse.moveOutsideScreen();
			}
			Sleep.waitCondition(() -> inventory.isFull() || getDialogues().isPendingContinuation(),
					1000, 50000);
		}
	}

	private void Checkspots() {
		NPC shrimp = getNpcs().closest(Fishingspotname);

		Area Spot1 = new Area(2994, 3147, 2993, 3145);
		Area Spot2 = new Area(2998, 3159, 2997, 3157);
		Area Spot3 = new Area(2991, 3170, 2987, 3177);
		int random = random(1, 3);

		if (random == 1) {
			log("Spot 1");
			walking.webWalk(Spot1);
			if (shrimp == null) {
				random = random(1, 3);
			}
		} else if (random == 2) {
			log("Spot 2");
			walking.webWalk(Spot2);
			if (shrimp == null) {
				random = random(1, 3);
			}
		} else if (random == 3) {
			log("Spot 3");
			walking.webWalk(Spot3);
			if (shrimp == null) {
				random = random(1, 3);
			}
		}
	}

	@Override
	public int onLoop() {
		time.Timerun();
		if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Inv tab not open");
			tabs.open(Tab.INVENTORY);
		}
		if (FishLevel >= 15 && inventory.contains(Net) && myPlayer().getCombatLevel() >= 20) {
			try {
				CheckupOverfifteen();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {
		if (inventory.contains(Net) && myPlayer().getCombatLevel() < 20) {
			try {
				CheckUpUnderfifteen();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}}
		return random(111);
	}

	@Override
	public void onExit() {
		log("Good Bye");
		stop();

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 430, 320);
		g.drawString("Shrimps " + Shrimp, 430, 305);
		g.drawString("Anovy " + Anovy, 430, 295);
		g.drawString("Fishing level " + FishLevel, 430, 285);

	}


}