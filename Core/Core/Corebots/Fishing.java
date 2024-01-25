package Core.Corebots;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class Fishing extends Skeleton {
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
		log("Fishing Has started");
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
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true,200,5000);
		}
	}

	private void Checkup() throws InterruptedException {
		FishLevel = skills.getStatic(Skill.FISHING);
		Sleep.waitCondition(() -> !myPlayer().isAnimating() || getDialogues().isPendingContinuation() || !myPlayer().isAnimating(), 3000, 50000);
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		if (!inventory.contains(Net)) {
			log("No Net");
			Banking();
		}

		if (inventory.contains(Net)) {
			// under 20
			if (getCombat().getCombatLevel() <= 20) {
				if (inventory.isFull()) {
					log("Dropping fishes");
					inventory.dropAll("Raw anchovies");
					sleep(random(200,600));
					inventory.dropAll("Raw shrimps");
				} else if (!Areas.ShrimpfishingBIGarea.contains(myPlayer())) {
					log("Walking shrimp spot");
					Walkspot();
				} else if (!inventory.isFull() && Areas.ShrimpfishingBIGarea.contains(myPlayer())) {
					log("Fishing shrimp");
					FishShrimp();
				}

			}
			// over 20
			if (FishLevel >= 15 && getCombat().getCombatLevel() >= 20) {
				if (inventory.isFull()) {
					log("Banking anovy");
					Banking();
				} else if (!Areas.AnovyBigarea.contains(myPlayer())) {
					log("Walking anovy");
					Walkspotanovy();
				} else if (!inventory.isFull() && Areas.AnovyBigarea.contains(myPlayer())) {
					log("Fishing anovy");
					FishAnovy();
				}
			}
		}
	}

	private void FishShrimp() throws InterruptedException {
		NPC shrimp = getNpcs().closest(Fishingspotname);
		if (shrimp == null) {
			log("Checking spots this one is null");
			Checkspots();
		}
		if (!myPlayer().isAnimating() && shrimp != null && !myPlayer().isMoving()
				&& Areas.ShrimpfishingBIGarea.contains(myPlayer()) && !inventory.isFull()) {
			log("Start Fishing");
			camera.toEntity(shrimp);
			Sleep.waitCondition(() -> shrimp.isOnScreen(),200, 5000);
			shrimp.interact("Small Net");
			sleep(random(1500,2000));
			Sleep.waitCondition(() -> myPlayer().isAnimating(), 200,80000);
			moveoutornot = random(0, 6);
			if (moveoutornot == 2 || moveoutornot == 6) {
				log("Moving out");
				mouse.moveOutsideScreen();
			}
			Sleep.waitCondition(() -> inventory.isFull() || getDialogues().isPendingContinuation() || !myPlayer().isAnimating(),
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


	private void Walkspot() {
		if (!Areas.AnovyBigarea.contains(myPlayer())) {
		log("Walking shrimps");
		walking.webWalk(areas.Shrimpspot);
		Sleep.waitCondition(() -> !myPlayer().isMoving(),200,5000);
		}
	}

	private void FishAnovy() throws InterruptedException {
		NPC shrimp = getNpcs().closest(Fishingspotname);
		if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Start Fishing");
			camera.toEntity(shrimp);
			Sleep.waitCondition(() -> shrimp.isOnScreen(),200, 5000);
			shrimp.interact("Small Net");
			sleep(random(1500,2000));
			Sleep.waitCondition(() -> myPlayer().isAnimating(), 200,80000);
			moveoutornot = random(0, 6);
			if (moveoutornot == 4 || moveoutornot == 5) {
				log("Moving out");
				mouse.moveOutsideScreen();
			}
			Sleep.waitCondition(() -> inventory.isFull() || getDialogues().isPendingContinuation() || !myPlayer().isAnimating(), 1000, 50000);
		}
	}

	private void Walkspotanovy() throws InterruptedException {
		if (!Areas.AnovyBigarea.contains(myPlayer())) {
			log("Walking Anovy");
			walking.webWalk(areas.Anovyspot());
			Sleep.waitCondition(() -> !myPlayer().isMoving(),200, 5000);
		}
	}

	private void Banking() throws InterruptedException {
			if (!Areas.DraynorBank.contains(myPlayer())) {
				log("Walking draynor");
				walking.webWalk(areas.DraynorBank);
				Sleep.waitCondition(() -> !myPlayer().isMoving(),200, 5000);
			}
			if (!bank.isOpen()) {
				log("Open bank for Bank for start");
				bank.open();
				sleep(random(500,1000));
				Sleep.waitCondition(() -> bank.isOpen(),200,3000);
			}
			if (bank.isOpen()) {
				if (!inventory.isEmpty()) {
					bank.depositAll();
					sleep(random(1500,2200));
					Sleep.waitCondition(() -> inventory.isEmpty(), 200,3000);
				}
				Shrimp = (int) getBank().getAmount("Raw shrimps");
				Anovy = (int) getBank().getAmount("Raw anchovies");
			}
			if (!inventory.contains(Net)) {
				bank.withdraw(Net, 1);
				sleep(random(200,800));
				Sleep.waitCondition(() -> inventory.contains(Net),200, 3000);
			}
			bank.close();
		}
	

	@Override
	public int onLoop(){
		time.Timerun();
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); }

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 0, 11);
		g.drawString("Shrimps " + Shrimp, 430, 305);
		g.drawString("Anovy " + Anovy, 430, 295);
		g.drawString("Fishing level " + FishLevel, 430, 285);

	}
	}
