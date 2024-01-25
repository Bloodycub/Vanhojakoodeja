package Trash;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Woodcutterold extends Script {

	public static Area Portbank = new Area(3043, 3234, 3046, 3236);

	public static Area woodcuttingspot = new Area(3025, 3328, 3068, 3314);
	public static Area woodcuttingspotwillow = new Area(3055, 3351, 3065, 3256);
	String[] axe = { "Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe" };
	final String[] typesOfLogs = { "Logs", "Oak logs", "Willow logs", "Maple logs", "Mahogany logs", "Yew logs",
			"Magic logs" };
	final String[] groundObjects = { "Fire", "Daisies", "Fern", "Stones", "Thistle" };
	final String[] Willow = { "Willow", };
	final String[] treetype = { "tree","Willow","Oak", "Maple", "Mahogany", "Yew","Magic logs"  };
	final String[] Bronzea = { "Bronze axe" };
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
	private int beginningXP;
	private int currentXp;
	private int xpGained;
	private int currentLevel;
	private int beginningLevel;
	private int levelsGained;
	private int xpPerHour;
	private double nextLevelXp;
	private long timeTNL;
	private double xpTillNextLevel;
	final int[] XP_TABLE = { 0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746,
			3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247,
			20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014,
			91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288,
			333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278,
			1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792,
			3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
			11805606, 13034431, 200000000 };

	private final Image backround = getImage("https://i.imgur.com/mzaMiac.png");
	// if attack lvl is XXX equip if not dont equip
	// check if have axe in inventory or wielded
	int runspeed = random(20, 37);

	public void Checkup() throws InterruptedException {
    	if (!inventory.isEmpty() && !inventory.contains(axe) && !Banks.LUMBRIDGE_UPPER.contains(myPlayer())&& !woodcuttingspot.contains(myPlayer())) {
    		log("Walking to bank with inventory not empty");
            walking.webWalk(Banks.LUMBRIDGE_UPPER);
            Atlbbank();
    	}else if(settings.getRunEnergy() >= runspeed) {
    		log("Too low energy");
    		settings.setRunning(true);
    		sleep(random(400,600));
    	}else if (woodcuttingspot.contains(myPlayer()) && (inventory.contains("Oak logs") || inventory.contains("logs")) && inventory.isFull()) {
    		log("Droping Logs,Oaks");
    		Dropwood();
    		
    		
        } else if (!inventory.isFull() && inventory.contains("logs")) {
            log("Inventory not full"));
            Cutwood();
        } else if (woodcuttingspot.contains(myPlayer()) && getInventory().contains("Bronze axe")) {
            log("Lets get chopping");
            Cutwood();
        } else if (!woodcuttingspot.contains(myPlayer()) && getInventory().contains("Bronze axe") && !inventory.contains("Pot")) {
            log("You got axe but not at spot");
            Walktospot();
        } else if (inventory.contains("Pot") && !Banks.LUMBRIDGE_UPPER.contains(myPlayer()) ) {
            log("Walking to bank");
            walking.webWalk(banks);
        } else if (woodcuttingspot.contains(myPlayer()) && getInventory().contains("Bronze axe")) {
            if (woodcuttingspot.contains(myPlayer()) && getInventory().contains("Bronze axe")) {
                log("Lets get chopping");
                Cutwood();
            } else if (!woodcuttingspot.contains(myPlayer()) && getInventory().contains("Bronze axe")) {
                log("You got axe but not at spot");
                Walktospot();
            } else if (woodcuttingspot.contains(myPlayer()) && !getInventory().contains("Bronze axe") && !getInventory().contains("Pot")) {
                log("your at spot but you dont have axe but dont have pot eather where did you lost your axe???");
                walking.webWalk(banks);
                Atlbbank();
            } else if (!getInventory().contains("Bronze axe") && Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && getInventory().contains("Pot")) {
            	log("No axe but in bank area");
                Atlbbank();
            }
        }
    }

	public void Checkinglvl() throws InterruptedException {
		log("Checking level");
		if (skills.getStatic(Skill.WOODCUTTING) < 35) {
			log("Moving to Willow spot");
			if (inventory.contains("Bronze axe")) {
				log("Walking to Willows");
				walking.webWalk(woodcuttingspotwillow);
				Chopwillow();

			}
		}
		// WC = skills.getStatic(Skill.WOODCUTTING);
		// woodcuttingspotwillow

	}

	private void Chopwillow() throws InterruptedException {
		if (getInventory().isFull()) {
			log("Banking willow");
			walking.webWalk(Portbank);
			log("Banking in port");
			Bankingport();
		} else {
			if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
				log("Im idle redy to chop");
				if (woodcuttingspotwillow.contains(myPlayer())) {
					if (camera.getPitchAngle() < 44) {
						log("Moving camera angle");
						int targetPitch = random(44, 67);
						log(targetPitch);
						if (camera.movePitch(targetPitch)) {
						}
					}
					camera.toTop();
					while (!inventory.isFull()) {
						RS2Object tree = getObjects().closest(Willow);
						if (Willow != null) {
							if (tree.interact("Chop down")) {
								sleep(random(3500, 5932));
								if (myPlayer().isAnimating()) {
									sleep(random(700, 950));
								}
							}
						}
					}
				}
			}
		}
	}

	private void Bankingport() throws InterruptedException {
		if (woodcuttingspotwillow.contains(myPlayer()) && inventory.isFull()) {
			myPlayer().interact("Bank deposit box");
			log("Open deposit box");
			depositBox.open();
			sleep(random(500, 1000));
			log("Depositbox open");
			if (getDepositBox().isOpen()) {
				log("Depositing Willow");
				depositBox.depositAllExcept(Bronzea);
				sleep(random(500, 1000));
				depositBox.close();
				sleep(random(500, 1000));
				getDepositBox().close();
				Checkinglvl(); // Check level and walk to willow spot }
			}
		}

	}

	public void Walktospot() throws InterruptedException { // ADD MAYBE
		log("Walking to chop spot");
		getWalking().webWalk(woodcuttingspot.getRandomPosition());
	}

	public void Cutwood() throws InterruptedException { // ADD ITEMS
		if (getInventory().isFull()) {
			log("Drop wood ");
			Dropwood();
		} else if (!myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Im idle redy to chop");
			if (woodcuttingspot.contains(myPlayer())) {
				if (camera.getPitchAngle() < 44) {
					log("Moving camera angle");
					int targetPitch = random(44, 67);
					log(targetPitch);
					if (camera.movePitch(targetPitch)) {
					}
				}
				camera.toTop();
				RS2Object tree = getObjects().closest(treetype);
				if (treetype != null) {
					if (tree.interact("Chop down")) {
						sleep(random(3500, 5932));
						if (myPlayer().isAnimating()) {
							sleep(random(700, 950));
						}
					}
				}
			}
		}
	}

	}

	public void Dropwood() throws InterruptedException {
		if (getInventory().isFull() && getInventory().contains("Bronze axe")) {
			log("Droping Wood");
			while (inventory.contains("Logs") || inventory.interact("Drop", "Oak logs")) {
				inventory.interact("Drop", "Logs");
				inventory.interact("Drop", "Oak logs");
				log("Droping log");
				sleep(random(600, 969));
			}
		} else {
			Checkup();
		}
	}

	public void Checkareain() throws InterruptedException { // THIS REDY
		if (myPlayer().isMoving()) {
			log("Sleeping");
			sleep(random(987, 1532));
			if (woodcuttingspot.contains(myPlayer()) && getInventory().contains("Bronze axe")) {
				Cutwood();
			}
		} else {
			log("Oops somthing wrong not in zone? / no axe?");
			Checkup();
		}
	}

	public void Checkforpot() throws InterruptedException { // THIS REDY

		log("Checking pot");
		if (inventory.contains("Pot")) {
			log("You got pot you are fresh go to bank");
			walking.webWalk(banks);
			Atlbbank();
		} else {
			Checkup();
		}
	}

	public void Atlbbank() throws InterruptedException { // THIS REDY
		log("Are you at bank?");
		if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			Checkup();
		} else if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
			bank.open();
			log("Is bank open?");
			getBank().isOpen();
			if (getBank().isOpen()) {
				if (getInventory().isEmpty()) {
					log("Taking stuff");
					getBank().withdraw("TinderBox", 1);
					sleep(random(453, 898));
					getBank().withdraw("Bronze axe", 1);
					sleep(random(200, 500));
					getBank().close();
					log("You got all?");
					// Equiping();
				} else {
					if (getBank().isOpen() && !getInventory().isEmpty()) {
						sleep(random(453, 898));
						log("Depositing all");
						bank.depositAll();
						sleep(random(453, 898));
					}
				}
			}
		}

	}

	// public void Equiping() throws InterruptedException{ // ADD ITEMS

	// if (!getInventory().contains("Pot") && (getInventory().contains("Bronze
	// axe"))){
	// getInventory().contains("Bronze axe");
	// log("wielding axe");
	// getInventory().getItem("Bronze axe").interact("Wield");
	// }else {
	// if (!getInventory().contains("Bronze axe")) {
	// log("Dont have Axe");
	// Atlbbank();

	@Override
	public void onStart() throws InterruptedException {
		log("Start up");
		sleep(3000);
		timeBegan = System.currentTimeMillis();
		beginningXP = skills.getExperience(Skill.WOODCUTTING);
		beginningLevel = skills.getStatic(Skill.WOODCUTTING);
		timeBegan = System.currentTimeMillis();
		timeTNL = 0;
		Checkup();
	}

	@Override

	public int onLoop() throws InterruptedException {
		Checkup();

		return 1000;
	}

	@Override
	public void onExit() {
		log("GOOD BYE");

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {

		g.drawImage(backround, 516, 0, null);
		g.drawString("" + ft(timeTNL), 642, 97); // To Next level
		g.setColor(Color.decode("#00ff04")); // Color
		timeRan = System.currentTimeMillis() - this.timeBegan;
		currentXp = skills.getExperience(Skill.WOODCUTTING);
		xpGained = currentXp - beginningXP;
		g.drawString(ft(timeRan), 636, 82);
		g.drawString("" + xpGained, 450, 104);
		g.drawString("" + beginningLevel, 450, 93);
		g.drawString("" + levelsGained, 450, 80);
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
		currentXp = skills.getExperience(Skill.WOODCUTTING);

		levelsGained = currentLevel - beginningLevel;
		xpPerHour = (int) (xpGained / ((System.currentTimeMillis() - this.timeBegan) / 3600000.0));
		nextLevelXp = XP_TABLE[currentLevel + 1];
		xpTillNextLevel = nextLevelXp - currentXp;
		if (xpGained >= 1) {
			timeTNL = (long) ((xpTillNextLevel / xpPerHour) * 3600000);
		}

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
