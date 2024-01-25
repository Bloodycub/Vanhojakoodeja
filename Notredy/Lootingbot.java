package Notredy;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import Core.Support.RSExchange;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Lootingbot extends Script {
	private long timeBegan;
	private long timeRan;
	Area Lootingarea = new Area(new int[][] { { 3217, 3220 }, { 3217, 3227 }, { 3215, 3229 }, { 3215, 3241 },
			{ 3226, 3243 }, { 3235, 3243 }, { 3242, 3227 }, { 3250, 3227 }, { 3250, 3224 }, { 3251, 3202 },
			{ 3237, 3199 }, { 3230, 3199 }, { 3228, 3201 }, { 3228, 3202 }, { 3227, 3202 }, { 3227, 3204 },
			{ 3228, 3205 }, { 3228, 3206 }, { 3226, 3208 }, { 3221, 3203 }, { 3206, 3203 }, { 3207, 3209 },
			{ 3215, 3209 }, { 3217, 3211 }, { 3217, 3218 } });
	Area BankArea = new Area(3213, 3234, 3226, 3204);
	Area LBChest = new Area(3221, 3217, 3221, 3217); // Lumbgre
//Area Gate = new Area(3230, 3220, 3232, 3217); //Lumbgre
	Area Plain2 = new Area(3203, 3250, 3254, 3194).setPlane(2);
	Area Plain1 = new Area(3201, 3252, 3270, 3186).setPlane(1);
	Area Cappel = new Area(new int[][] { { 3240, 3216 }, { 3248, 3216 }, { 3248, 3204 }, { 3240, 3204 }, { 3240, 3209 },
			{ 3238, 3209 }, { 3238, 3212 }, { 3240, 3212 }, { 3240, 3216 } });
	Area Shops3 = new Area(3233, 3208, 3229, 3206);
	Area Shops2 = new Area(3237, 3198, 3230, 3195);
	Area Shops = new Area(3233, 3205, 3227, 3201);
	Area Midlooting = new Area(3232, 3222, 3237, 3216);
	Area Castle1 = new Area(new int[][] { { 3227, 3225 }, { 3228, 3226 }, { 3230, 3226 }, { 3231, 3225 },
			{ 3231, 3221 }, { 3227, 3221 }, { 3227, 3222 }, { 3227, 3225 } });
	int Adamantarrow = 0;
	int Advalue = 70;
	Area Castle2 = new Area(new int[][] { { 3228, 3217 }, { 3230, 3217 }, { 3231, 3216 }, { 3231, 3213 },
			{ 3230, 3212 }, { 3228, 3212 }, { 3227, 3213 }, { 3227, 3216 }, { 3228, 3217 } });
	Area Gate = new Area(3230, 3220, 3232, 3217); // Lumbgre
	int dead = 0;
	int runspeed = random(20, 37);
	final RSExchange rsExchange = new RSExchange();
	ConditionalSleep conditionalSleep;
	
	@Override
	public void onStart() throws InterruptedException {
		log("Hellow");
		sleep(5000);
		
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
		timeBegan = System.currentTimeMillis();

	}

	
	private void Loot() throws InterruptedException {
		// final RSExchange rsExchange = new RSExchange();
		// Optional<ExchangeItem> addyarrows = rsExchange.getExchangeItem("Adamant
		// arrow");

		if (camera.getPitchAngle() < 50) {
			log("Moving camera angle");
			int targetPitch = random(50, 67);
			log(targetPitch);
			camera.movePitch(targetPitch);
		}
		log("addyarrows");
		java.util.List<GroundItem> groundItems = getGroundItems().get(myPlayer().getX(), myPlayer().getY());

		/*
		 * GroundItem O = getGroundItems().closest(new String[]
		 * {"Swordfish","Adamant arrow","Bronze arrow","Iron arrow","Mithril arrow"
		 * ,"Maple shortbow","Maple longbow"
		 * ,"Bronze full helm","Iron full helm","Iron platebody","Iron chainbody"
		 * ,"Green cape",
		 * "Hardleather body","Iron med helm","Iron platelegs","Trout","Meat pizza",
		 * "Amulet of magic","Iron full helm","Iron scimitar","Steel arrow"
		 * ,"Bronze bolts","Pineapple pizza",
		 * "Strength potion(1)","Strength potion(2)","Strength potion(3)"
		 * ,"Strength potion(4)","Anchovy pizza","Wizard hat",
		 * "Studded body","Chaos rune","Earth rune","Water rune","Air rune","Fire rune"
		 * ,"Coins","Salmon","Lobster","Monk's robe top","Monk's robe","Black cape"
		 * ,"Mind rune", "Pizza","Bronze med helm","Amulet of strength","Rune platebody"
		 * ,"Green d'hide vamb","Mithril chainbody"
		 * ,"Adamant scimitar","Green d'hide body","Steel sq shield","Rune scimitar"
		 * ,"Mithril med helm","Mithril med helm","Ruby amulet"
		 * ,"Green d'hide chaps","Mithril scimitar","Team-8 cape","Team-9 cape"
		 * ,"Team-10 cape","Team-11 cape","Team-12 cape","Team-13 cape", "Team-14 cape",
		 * "Team-15 cape", "Team-16 cape", "Team-17 cape", "Team-18 cape",
		 * "Team-19 cape", "Team-20 cape", "Team-21 cape", "Team-22 cape",
		 * "Team-23 cape",
		 * "Team-24 cape","Team-25 cape","Team-6 cape","Team-26 cape","Team-27 cape"
		 * ,"Team-28 cape",
		 * "Team-29 cape","Team-30 cape","Team-31 cape","Team-32 cape","Team-33 cape"
		 * ,"Team-7 cape","Team-5 cape",
		 * "Team-34 cape","Team-35 cape","Team-36 cape","Team-37 cape","Team-38 cape"
		 * ,"Team-39 cape","Team-4 cape",
		 * "Team-40 cape","Team-41 cape","Team-42 cape","Team-43 cape","Team-44 cape"
		 * ,"Team-45 cape","Team-3 cape",
		 * "Team-46 cape","Team-47 cape","Team-48 cape","Team-49 cape","Team-50 cape"
		 * ,"Team-2 cape","Team-1 cape", });
		 */

		if (groundItems == null) {
			walking.webWalk(Lootingarea.getRandomPosition());
		}

		if (settings.getRunEnergy() <= runspeed && myPlayer().isHitBarVisible() == false) {
			log("Too low energy");
			settings.setRunning(false);
			sleep(random(400, 600));
		}

		if (Cappel.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("You in cappel");
			Opencappeldoors();
		}

		if (inventory.getAmount("Adamant arrow") >= 35 || inventory.getAmount("Mithril arrow") >= 35
				|| inventory.isFull()) {
			log("Too much arrows");
			Walkingbank();
		}

		if (groundItems != null && !myPlayer().isMoving() && !myPlayer().isUnderAttack()) {
			for (GroundItem item : groundItems) {
				if (item.exists()) {
					log("Looting");
					item.interact("Take");
					conditionalSleep = new ConditionalSleep(3000, 600) {
						@Override
						public boolean condition() throws InterruptedException {
							log("sleeping");
							return !item.exists();
						}};
						
					} else {
						log("Sleeping");
						conditionalSleep = new ConditionalSleep(3000, 600) {
							@Override
							public boolean condition() throws InterruptedException {
								return !item.exists();
							}};
					}
				}
			}
		}


	@Override
	public int onLoop() throws InterruptedException {
		if (myPlayer().getHealthPercent() == 0) {
			dead++;
			sleep(3000);
		}
		Checkup();
		return random(100); // The amount of time in milliseconds before the loop starts over
	}

	private void Checkup() throws InterruptedException {
		if (Castle2.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("You in Castle 1");
			OpenShopdoors5();
		}
		if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Inv tab not open");
			tabs.open(Tab.INVENTORY);
		}

		if (Castle1.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("You in Castle 1");
			OpenShopdoors4();
		}
		if (Shops3.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("You in Shop 3");
			OpenShopdoors3();
		}
		if (Shops.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("You in Shop 2");
			OpenShopdoors();
		}
		if (Shops2.contains(myPlayer()) && !myPlayer().isMoving()) {
			log("You in Shop 1");
			OpenShopdoors2();
		}
		if (BankArea.contains(myPlayer()) && !myPlayer().isMoving() && !bank.isOpen()) {
			log("Afking at bank");
			walking.webWalk(Gate.getRandomPosition());
		}
		if (Plain1.contains(myPlayer()) || Plain2.contains(myPlayer())) {
			log("You are very stuck!!!");
			walking.webWalk(LBChest);
		}
		if (BankArea.contains(myPlayer()) && inventory.isEmpty()) {
			log("Inventory empty in bank area");
			walking.webWalk(Gate.getRandomPosition());
		}
		if (myPlayer().getHealthPercent() > 95) {
			log("Eating");
			if (inventory.contains("Salmon")) {
				inventory.interact("Eat", "Salmon");
			}
			if (inventory.contains("Lobster")) {
				inventory.interact("Eat", "Lobster");
			}
			if (inventory.contains("Sword fish")) {
				inventory.interact("Eat", "Sword fish");
			}
			if (inventory.contains("Trout")) {
				inventory.interact("Eat", "Trout");
			}
		}
		if ((myPlayer().isHitBarVisible() || myPlayer().isUnderAttack()) && !inventory.isEmpty()) {
			log("Underattack");
			Walkingbank();
		}
		if (Plain1.contains(myPlayer()) || Plain2.contains(myPlayer())) {
			log("You are very stuck!!!");
			walking.webWalk(LBChest);
		}
		if (!Lootingarea.contains(myPlayer())) {
			log("Dont go there");
			Walkingbank();
		}
		if (inventory.isFull()) {
			log("Walking bank");
			Walkingbank();
		} else {
			log("Looting");
			Loot();
		}
	}

	private void OpenShopdoors5() {
		RS2Object SmallDoors = getObjects().closest("Door");
		if (SmallDoors.hasAction("Open")) {
			log("Opening Small Door 3");
			SmallDoors.interact("Open");
		}
	}

	private void OpenShopdoors4() {
		RS2Object SmallDoors = getObjects().closest("Door");
		if (SmallDoors.hasAction("Open")) {
			log("Opening Small Door 3");
			SmallDoors.interact("Open");
		}
	}

	private void OpenShopdoors3() {
		RS2Object SmallDoors = getObjects().closest("Door");
		if (SmallDoors.hasAction("Open")) {
			log("Opening Small Door 3");
			SmallDoors.interact("Open");
		}
	}

	private void OpenShopdoors2() {
		RS2Object SmallDoors = getObjects().closest("Door");
		if (SmallDoors.hasAction("Open")) {
			log("Opening Small Door 2");
			SmallDoors.interact("Open");
		}
	}

	private void OpenShopdoors() {
		RS2Object SmallDoors = getObjects().closest("Door");
		if (SmallDoors.hasAction("Open")) {
			log("Opening Small Door 1");
			SmallDoors.interact("Open");
		}
	}

	private void Opencappeldoors() {
		RS2Object BigDoors = getObjects().closest("Large door");
		RS2Object SmallDoors = getObjects().closest("Door");
		if (BigDoors.hasAction("Open")) {
			log("Opening Big Door");
			BigDoors.interact("Open");
		}
		if (SmallDoors.hasAction("Open")) {
			log("Opening small Door");
			SmallDoors.interact("Open");
		}

	}

	private void Walkingbank() throws InterruptedException {
		settings.setRunning(true);
		walking.webWalk(LBChest);
		sleep(random(1200, 1800));
		bank.open();
		sleep(random(1000, 1650));
		if (bank.isOpen()) {
			bank.depositAll();
			Adamantarrow = (int) getBank().getAmount("Adamant arrow");
			runspeed = random(20, 37);
			sleep(random(300, 600));
			bank.close();
			sleep(random(200, 500));
		} else {
			bank.open();
			sleep(500);
		}
	}

	@Override
	public void onExit() throws InterruptedException {
		log("Good Bye");
		Walkingbank();
		stop();

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		timeRan = System.currentTimeMillis() - this.timeBegan;
		g.drawString(ft(timeRan), 450, 335);
		g.drawString("Ad arrows " + Adamantarrow, 410, 320);
		g.drawString("Value" + Adamantarrow * Advalue, 410, 305);
		g.drawString("Deads " + dead, 410, 295);

		// This is where you will put your code for paint(s)

	}

	public String ft(long duration) {
		String res = "";
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		if (days == 0) {
			res = (hours + ":" + minutes + ":" + seconds);
		} else {
			res = (days + ":" + hours + ":" + minutes + ":" + seconds);
		}
		return res;
	}

}