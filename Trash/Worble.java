package Trash;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Support.Areas;

public class Worble extends Skelor {
	private String[] axe = { "Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe",
			"Dragon axe" };
	final String[] typesOfLogs = { "Logs", "Oak logs", "Willow logs", "Maple logs", "Mahogany logs", "Yew logs",
			"Magic logs" };
	final String[] treetype = { "tree", "Willow", "Oak", "Maple", "Mahogany", "Yew", "Magic" };
	private int WC;// Woodcutting
	private int AG; // Agility
	private int AT; // Attack
	private int Con; // construction
	private int Cock; // cooking
	private int Craf;// crafting
	private int Def;// defence
	private int Farm;// farming
	private int Fire;// firemaking
	private int Fish;// fishing
	private int Flech;// fleching
	private int Herb;// Herblore
	private int Hit;// Hitpoints
	private int Hunt;// Hunting
	private int Mag;// mage
	private int Min;// mining
	private int Pray; // Prayer
	private int Rang;// range
	private int Rune;// Runecrafting
	private int Slay;// Slayer
	private int Smit;// Smithing
	private int Str;// Strenght
	private int Tif;// Tiefing
	private long timeBegan;
	private long timeRan;
	private int currentLevel;
	private int beginningLevel;
	private int levelsGained;
	private final Image backround = getImage("https://i.imgur.com/mzaMiac.png");
	private int runspeed = random(20, 37);
	private final int Logslevel = random(17, 20);
	private int Combat; // Combat level
	private final int Oaklevel = random(35, 38);
	private final int Willowlevel = random(61, 65);
	Areas area = new Areas();

	// value tracker
	// Ge buying rune axe

	@Override
	public void onStart() throws InterruptedException {
		startup();
		log("Start up");
		sleep(3000);
		timeBegan = System.currentTimeMillis();
		beginningLevel = skills.getStatic(Skill.WOODCUTTING);
		timeBegan = System.currentTimeMillis();
		if(inventory.contains("Shrimp")) {
			log("Fresh");
			Banking();
		}else {
		Checkup();
		}
	}

	public void Checkup() throws InterruptedException {
		WC = skills.getDynamic(Skill.WOODCUTTING);
		if (camera.getPitchAngle() < 50) {
			log("Moving camera angle");
			int targetPitch = random(50, 67);
			log(targetPitch);
			camera.movePitch(targetPitch);
		} else if (inventory.isFull() && (inventory.contains("Logs") || inventory.contains("Oak logs"))) {
			DropingWood();
		} else if (inventory.isFull() && inventory.contains("Willow logs")) {
			bankingWillow();
		} else if (settings.getRunEnergy() >= runspeed && getSettings().isRunning() == false) {
			log("Too High energy");
			settings.setRunning(true);
			sleep(random(400, 600));
		} else if (!inventory.isEmpty() && !inventory.contains(axe) && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())
				&& !inventory.contains(typesOfLogs)) {
			log("Walking to bank with inventory not empty");
			Banking();
		} else if (inventory.contains(axe) && !equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")) {
			Equipping();
		} else if (inventory.contains(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe")) {
			log("Checking level");
			levelcheck();
		}
	}

	private void levelcheck() throws InterruptedException {
		if (WC < Logslevel) {
			CutWood();
		} else if (WC >= Logslevel && WC < Oaklevel) {
			CuttingOak();
		} else if (WC < 60 && WC >= Oaklevel) {
			CutWillow();
		} else if (WC >= Willowlevel) {
			Upgrade();
		} else if (WC >= Willowlevel) {
			CutYew();
		}
	}

	private void Upgrade() throws InterruptedException {
		if (inventory.isEmptyExcept(axe)
				|| equipment.isWearingItem(EquipmentSlot.WEAPON, "Rune axe") && WC >= Willowlevel) {
			CutYew();
		} else {
			if (!Areas.VarrocGrandextange.contains(myPlayer())) {
				WalkingGe();
			} else if (Areas.VarrocGrandextange.contains(myPlayer())) {
				Withdrawwillows();
			} else if (inventory.contains("Willow logs") && Areas.VarrocGrandextange.contains(myPlayer())) {
				Buyeruneaxe();
			}
		}

	}

	private void Buyeruneaxe() {
		log("Buing rune axe");

	}

	private void Withdrawwillows() throws InterruptedException {
		log("Withdrawwillows");
		if (!bank.isOpen()) {
			bank.open();
			sleep(random(1000, 1231));
		}
		bank.depositAll();
		sleep(random(400, 600));
		bank.notifyAll();
		sleep(random(300, 450));
		bank.withdrawAll("Willow logs");
		sleep(random(400, 600));
		bank.close();
	}

	private void WalkingGe() throws InterruptedException {
		walking.webWalk(area.VarrocGrandextange());
		sleep(random(1500, 2311));
	}

	private void CutYew() throws InterruptedException {
		RS2Object tree = getObjects().closest("Yew");
		if (inventory.isFull() && Areas.RimmingtonYewSpot.contains(myPlayer())) {
			log("Banking yew");
			bankingYew();
		}
		if (!Areas.RimmingtonYewSpot.contains(myPlayer())) {
			log("Walking Yew Spot");
			walking.webWalk(area.RimmingtonYewSpot());
		}
		if (Areas.RimmingtonYewSpot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting yew");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
			}
			while (myPlayer().isAnimating() || myPlayer().isMoving()) {
				sleep(random(500, 750));
			}
		}
	}

	private void bankingYew() throws InterruptedException {
		if (!Areas.PortBank.contains(myPlayer()) && inventory.isFull()) {
			walking.webWalk(area.PortBankSpot());
			sleep(random(1000, 1500));
		}
		if (!depositBox.isOpen()) {
			depositBox.open();
		}
		if (depositBox.isOpen()) {
			getDepositBox().depositAll("Yew logs");
			sleep(random(400, 700));
			depositBox.close();
			sleep(random(300, 600));
		}
		if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
			walking.webWalk(area.RimmingtonYewSpot());
			sleep(random(300, 600));
		}

	}

	private void CutWillow() throws InterruptedException {
		RS2Object tree = getObjects().closest("Willow");
		if (inventory.contains("Oak logs") || inventory.contains("Logs")) {
			DropingWood();
		}
		if (inventory.isFull() && Areas.PortWillowspot.contains(myPlayer())) {
			log("Banking Willow");
			bankingWillow();
		}
		if (!Areas.PortWillowspot.contains(myPlayer())) {
			log("Walking Willowspot");
			walking.webWalk(area.PortWillowspot());
		}
		if (Areas.PortWillowspot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting Willow");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
			}
			while (myPlayer().isAnimating() || myPlayer().isMoving()) {
				sleep(random(500, 750));
			}
		}
	}

	private void bankingWillow() throws InterruptedException {
		if (!Areas.PortWillowspot.contains(myPlayer()) && inventory.isFull()) {
			walking.webWalk(area.PortWillowspot());
			sleep(random(1000, 1500));
		}
		if (!depositBox.isOpen()) {
			depositBox.open();
		}
		sleep(random(500, 800));
		if (depositBox.isOpen()) {
			getDepositBox().depositAll("Willow logs");
			sleep(random(400, 700));
			depositBox.close();
			sleep(random(300, 600));
		}
		if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
			walking.webWalk(area.PortWillowspot());
			sleep(random(300, 600));
		}

	}

	private void CuttingOak() throws InterruptedException {
		RS2Object tree = getObjects().closest("Oak");
		if (inventory.isFull() && Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Dropping Oak");
			DropingWood();
		}
		if (!Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Walking Oak");
			walking.webWalk(area.FaladorWallcuttingspot());
		}
		if (Areas.FaladorWallcuttingspot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting Oak");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
			}
			while (myPlayer().isAnimating() || myPlayer().isMoving()) {
				sleep(random(500, 750));
			}
		}
	}

	private void DropingWood() throws InterruptedException {
		log("Droping Wood");
		while (inventory.contains("Logs") || inventory.contains("Oak logs")) {
			if (inventory.contains("Logs")) {
				log("Droping log");
				inventory.interact("Drop", "Logs");
				sleep(random(600, 769));
			}
			if (inventory.contains("Oak logs")) {
				log("Droping Oak");
				inventory.interact("Drop", "Oak logs");
				sleep(random(600, 769));
			}
			if (inventory.isEmpty() || inventory.isEmptyExcept(axe)) {
				break;
			}
		}

	}

	private void CutWood() throws InterruptedException {
		RS2Object tree = getObjects().closest("Tree");
		if (inventory.isFull() && Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Droping Logs");
			DropingWood();
		}
		if (!Areas.FaladorWallcuttingspot.contains(myPlayer())) {
			log("Walking Tree");
			walking.webWalk(area.FaladorWallcuttingspot());
		}
		if (Areas.FaladorWallcuttingspot.contains(myPlayer())
				&& (inventory.isEmptyExcept(axe) || equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze axe"))) {
			log("Cutting Tree");
			if (!myPlayer().isAnimating() && !myPlayer().isMoving() && tree != null) {
				tree.interact("Chop down");
				sleep(random(2500, 2932));
			}
			while (myPlayer().isAnimating() || myPlayer().isMoving()) {
				sleep(random(500, 750));
			}
		}
	}

	private void Equipping() throws InterruptedException {
		inventory.interact("Wield", "Bronze axe");
		sleep(random(250, 350));
	}

	public void Banking() throws InterruptedException { // THIS REDY
		log("Are you at bank?");
		if (!Banks.DRAYNOR.contains(myPlayer())) {
			walking.webWalk(Banks.DRAYNOR.getRandomPosition());
		}
		if (Banks.DRAYNOR.contains(myPlayer()) && !bank.isOpen()) {
			bank.open();
			log("Is bank open?");
		}
		if (getBank().isOpen()) {
			if (getInventory().isEmpty()) {
				log("Taking stuff");
				getBank().withdraw("Bronze axe", 1);
				sleep(random(200, 500));
				getBank().close();
				log("You got all?");
			} else {
				if (!inventory.isEmpty()) {
					sleep(random(453, 698));
					log("Depositing all");
					bank.depositAll();
					sleep(random(453, 698));
				}
			}
		}
	}

	@Override

	public int onLoop() {
		try {
			Checkup();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1000;
	}

	@Override
	public void onExit() {
		log("GOOD BYE");

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {
		timeRan = System.currentTimeMillis() - this.timeBegan;
		int all = WC + AG + AT + Con + Cock + Craf + Def + Farm + Fire + Fish + Flech + Herb + Hit + Hunt + Mag + Min
				+ Pray + Rang + Rune + Slay + Smit + Str + Tif;
		g.drawImage(backround, 516, 0, null);
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(ft(timeRan), 636, 82);
		g.drawString("" + WC, 570, 51);
		g.drawString("" + AG, 590, 142);
		g.drawString("" + AT, 700, 51);
		g.drawString("" + Con, 685, 145);
		g.drawString("" + Cock, 650, 159);
		g.drawString("" + Craf, 555, 82);
		g.drawString("" + Def, 720, 82);
		g.drawString("" + Farm, 595, 33);
		g.drawString("" + Fire, 560, 67);
		g.drawString("" + Fish, 745, 159);
		g.drawString("" + Flech, 626, 159);
		g.drawString("" + Herb, 532, 33);
		g.drawString("" + Hit, 650, 67);
		g.drawString("" + Hunt, 683, 33);
		g.drawString("" + Mag, 555, 97);
		g.drawString("" + Min, 715, 112);
		g.drawString("" + Pray, 575, 127);
		g.drawString("" + Rang, 703, 128);
		g.drawString("" + Rune, 532, 159);
		g.drawString("" + Slay, 745, 33);
		g.drawString("" + Smit, 718, 97);
		g.drawString("" + Str, 715, 67);
		g.drawString("" + Tif, 561, 112);
		g.drawString("" + Combat, 635, 39);
		g.drawString("" + all, 649, 122); // Total level
		g.drawString("+" + levelsGained, 540, 51); // level ups
		levelsGained = currentLevel - beginningLevel;
		currentLevel = skills.getStatic(Skill.WOODCUTTING);
		beginningLevel = skills.getDynamic(Skill.WOODCUTTING);
		Combat = combat.getCombatLevel();
		WC = skills.getStatic(Skill.WOODCUTTING);
		AG = skills.getStatic(Skill.AGILITY);
		AT = skills.getStatic(Skill.ATTACK);
		Con = skills.getStatic(Skill.CONSTRUCTION);
		Cock = skills.getStatic(Skill.COOKING);
		Craf = skills.getStatic(Skill.CRAFTING);
		Def = skills.getStatic(Skill.DEFENCE);
		Farm = skills.getStatic(Skill.FARMING); // Get Current lvl of skill
		Fire = skills.getStatic(Skill.FIREMAKING);
		Fish = skills.getStatic(Skill.FISHING);
		Flech = skills.getStatic(Skill.FLETCHING);
		Herb = skills.getStatic(Skill.HERBLORE);
		Hit = skills.getStatic(Skill.HITPOINTS);
		Hunt = skills.getStatic(Skill.HUNTER);
		Mag = skills.getStatic(Skill.MAGIC);
		Min = skills.getStatic(Skill.MINING);
		Pray = skills.getStatic(Skill.PRAYER);
		Rang = skills.getStatic(Skill.RANGED);
		Rune = skills.getStatic(Skill.RUNECRAFTING);
		Slay = skills.getStatic(Skill.SLAYER);
		Smit = skills.getStatic(Skill.SMITHING);
		Str = skills.getStatic(Skill.STRENGTH);
		Tif = skills.getStatic(Skill.THIEVING);
	}

	private String ft(long duration) {
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

	private Image getImage(String url) // Get's the background image.
	{
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
		}
		return null;
	}

}
