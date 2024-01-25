package onebyone.Notredy;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import java.awt.Color;


public class CombatBeginer extends Script {
	
	
    private int WC; //Woodcutting
    private int AG; //Agility
    private int AT; //Attack
    private int Con; // construction
    private int Cock; // cooking
    private int Craf; //crafting
    private int Def; // defence
    private int Farm; //farming
    private int Fire; //firemaking
    private int Fish; //fishing
    private int Flech; // fleching
    private int Herb; //Herblore
    private int Hit; // Hitpoints
    private int Hunt; //Hunting
    private int Mag; //mage
    private int Min; //mining
    private int Pray; //Prayer
    private int Rang; //range
    private int Rune; //Runecrafting
    private int Slay; //Slayer
    private int Smit; //Smithing
    private int Str; //Strenght
    private int Tif; //Tiefing
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
    final int[] XP_TABLE = {
        0,
        0,
        83,
        174,
        276,
        388,
        512,
        650,
        801,
        969,
        1154,
        1358,
        1584,
        1833,
        2107,
        2411,
        2746,
        3115,
        3523,
        3973,
        4470,
        5018,
        5624,
        6291,
        7028,
        7842,
        8740,
        9730,
        10824,
        12031,
        13363,
        14833,
        16456,
        18247,
        20224,
        22406,
        24815,
        27473,
        30408,
        33648,
        37224,
        41171,
        45529,
        50339,
        55649,
        61512,
        67983,
        75127,
        83014,
        91721,
        101333,
        111945,
        123660,
        136594,
        150872,
        166636,
        184040,
        203254,
        224466,
        247886,
        273742,
        302288,
        333804,
        368599,
        407015,
        449428,
        496254,
        547953,
        605032,
        668051,
        737627,
        814445,
        899257,
        992895,
        1096278,
        1210421,
        1336443,
        1475581,
        1629200,
        1798808,
        1986068,
        2192818,
        2421087,
        2673114,
        2951373,
        3258594,
        3597792,
        3972294,
        4385776,
        4842295,
        5346332,
        5902831,
        6517253,
        7195629,
        7944614,
        8771558,
        9684577,
        10692629,
        11805606,
        13034431,
        200000000
    };
    private final Image bg = getImage("https://i.imgur.com/b8lZ5lD.png");
    Area[] banks = {Banks.LUMBRIDGE_UPPER};
    public static Area Cows = new Area(2935,3269, 2915,3290);
    public static Area Spawn = new Area(3232,3212, 3218,3228);




   

    
    public void Checkup() throws InterruptedException {
    	
    // inveotry emprty then go kill
    	//tp to LB
    log("Start checking");
   // if (Spawn.contains(myPlayer()) && !inventory.contains("Pot")) {
    //	if (inventory.contains("Bronze sword") || (inventory.contains("Wooden shield")) && ){
    	//	equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze shield");
    		//equipment.isWearingItem(EquipmentSlot.SHIELD, "Wooden shield"):
    		
    	//}else {
    	//}
    	
    	
    if(inventory.contains("Bronze sword") || (inventory.contains("Wooden shield")) && (!inventory.contains("Pot"))){
    	  log("Not Fresh");
    	  Equip();
     } else if(!myPlayer().isUnderAttack() && Cows.contains(myPlayer()) && !myPlayer().isAnimating()){
        	  log("Loot");
        	  Loot();
      }else if (inventory.contains("Bronze sword") && (inventory.contains("Wooden shield")) && (inventory.contains("Pot"))) {
        	log("Fresh");
        	Fresh();
    }else if (camera.getPitchAngle() < 44) {
              log("Moving camera angle");
              movecamera();
              
    }else if (Cows.contains(myPlayer()) && !getCombat().isFighting() && equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze Sword") && equipment.isWearingItem(EquipmentSlot.SHIELD, "Wooden shield")) {
    	  log("You are in area and redy to kill");
    	  sleep(random(random(3000,4123)));
    	  Killcows();	
    }else if (!Cows.contains(myPlayer()) && equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze Sword") && !inventory.contains("Pot")){
    	Gotoarea();
    	
    }else if (equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze shield") && equipment.isWearingItem(EquipmentSlot.SHIELD, "Wooden shield")) {
    	  log("You got all lets go");
    	  Gotoarea();
    } else if (!getInventory().contains("Bronze sword") && Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && getInventory().contains("Pot")) {
    	log("No Bronze sword but in bank area");
        Atlbbank();	
    } else if (inventory.contains("Pot") && !Banks.LUMBRIDGE_UPPER.contains(myPlayer()) ) {
        log("Walking to bank");
        walking.webWalk(banks);	
    }else if (inventory.isEmpty() && (!Banks.LUMBRIDGE_UPPER.contains(myPlayer()))) {
        log("inventory empty");
        walking.webWalk(banks);	
    }else if (inventory.isEmpty() && (Banks.LUMBRIDGE_UPPER.contains(myPlayer()))) {
    	log("Your are at bank but empty inventory");
    	Atlbbank();
    } else if (getInventory().contains("Bronze sword") && Banks.LUMBRIDGE_UPPER.contains(myPlayer()) && getInventory().contains("Pot")) {
    	log("No Sword but in bank area and got pot");
        Atlbbank();
        
    }  	
     
    	
    }
    private void Fresh() throws InterruptedException {
    	log("Inventory full bank it");
        walking.webWalk(banks);	
        sleep(500);
        Atlbbank();
	}
	private void movecamera() {
    	int targetPitch = random(44, 67);
        log(targetPitch);
        camera.movePitch(targetPitch);
		
	}
	private void Loot() throws InterruptedException {
    	GroundItem gitems = groundItems.closest("Bones");
    	    while (inventory.isFull()) {
    			if(myPlayer().isAnimating()) {
    			sleep(400);
    		log("Inventory is full burying bones");
    		inventory.interact("Bury", "Bones");
    		if(!myPlayer().isUnderAttack() && Cows.contains(myPlayer()) && !myPlayer().isAnimating()) {
    	    	  if (gitems != null && gitems.exists())
    	      	log("You killed Cow loot");
    		log("Looting");
    			gitems.interact("Take");
    		sleep(random(1523,1853));
    	    }else {
    	    	Killcows();
    		
    		
    	}
    	
	}
    }}	
	private void Killcows() throws InterruptedException {
		NPC enemy = npcs.closest("Chicken", "Cow","Cow calf");
    	if (enemy != null && enemy.exists() && !(enemy.getHealthPercent() > 0) &&  !getCombat().isFighting() && !enemy.isUnderAttack() && !myPlayer().isUnderAttack() && !myPlayer().isAnimating()) {
    	    log("Attacking");
    	    enemy.interact("Attack");
    	    sleep(random(1500,2240));
    	    while(enemy != null && enemy.exists() && enemy.getHealthPercent() < 0 && enemy.isUnderAttack() && myPlayer().isUnderAttack() && myPlayer().isAnimating()) {
    	    sleep(random(1500,2240));
    	       
    	}}
		
	}
	private void Gotoarea() throws InterruptedException {
		log("Walking to area");
		getWalking().webWalk(Cows.getRandomPosition());
		sleep(random(400,629));
	}
	private void Equip() throws InterruptedException {
		sleep(random(1523,2942));
    	getInventory().interact("Wield", "Bronze sword");
    	log("Equip Sword");
    	sleep(random(1329,1523));
    	getInventory().interact("Wield", "Wooden shield");
    	log("Equip Shield");
    	sleep(random(1329,1523));
    	log("Equiped items");
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
    
    public void Atlbbank() throws InterruptedException { // THIS MOD
        log("Are you at bank?");
        if (!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
            Checkup();
        } else if (Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
            bank.open();
            log("Is bank open?");
            getBank().isOpen();
            if (getBank().isOpen()) {
                sleep(random(453, 898));
                log("Depositing all");
                bank.depositAll();
                sleep(random(453, 898));
            if (getInventory().isEmpty()) {
                log("Taking stuff");
                getBank().withdraw("Bronze sword", 1);
                log("Taking sword");
                sleep(random(853, 1298));
                getBank().withdraw("Wooden shield", 1);
                log("Taking Shield");
                sleep(random(200, 500));
                getBank().close();
                log("You got all?");
                Equip();
            }
        }
        }}
    
    @Override
    public void onStart() throws InterruptedException {
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
    	
        return random(1200); //The amount of time in milliseconds before the loop starts over


    }

    @Override
    public void onExit() {
    	log("Good BYE");

        //Code here will execute after the script ends


    }

    @Override
    public void onPaint(Graphics2D g) {

        g.drawImage(bg, 516, 0, null);
        g.setColor(Color.decode("#00ff04")); //Color
        timeRan = System.currentTimeMillis() - this.timeBegan;
        currentXp = skills.getExperience(Skill.WOODCUTTING);
        xpGained = currentXp - beginningXP;
        g.drawString(ft(timeRan), 625, 75);
        g.drawString("" + xpGained, 608, 104);
        g.drawString("" + beginningLevel, 580, 50);
        g.drawString("" + levelsGained, 580, 70);
        g.drawString("" + WC, 557, 47);
        g.drawString("" + AG, 570, 135);
        g.drawString("" + AT, 715, 45);
        g.drawString("" + Con, 700, 140);
        g.drawString("" + Cock, 650, 150);
        g.drawString("" + Craf, 540, 74);
        g.drawString("" + Def, 731, 76);
        g.drawString("" + Farm, 570, 23);
        g.drawString("" + Fire, 540, 60);
        g.drawString("" + Fish, 536, 153);
        g.drawString("" + Flech, 626, 153);
        g.drawString("" + Herb, 755, 153);
        g.drawString("" + Hit, 660, 60);
        g.drawString("" + Hunt, 695, 23);
        g.drawString("" + Mag, 538, 93);
        g.drawString("" + Min, 729, 107);
        g.drawString("" + Pray, 555, 122);
        g.drawString("" + Rang, 709, 122);
        g.drawString("" + Rune, 521, 26);
        g.drawString("" + Slay, 745, 27);
        g.drawString("" + Smit, 724, 93);
        g.drawString("" + Str, 723, 64);
        g.drawString("" + Tif, 541, 107);
        WC = skills.getStatic(Skill.WOODCUTTING);
        AG = skills.getStatic(Skill.AGILITY);
        AT = skills.getStatic(Skill.ATTACK);
        Con = skills.getStatic(Skill.CONSTRUCTION);
        Cock = skills.getStatic(Skill.COOKING);
        Craf = skills.getStatic(Skill.CRAFTING);
        Def = skills.getStatic(Skill.DEFENCE);
        Farm = skills.getStatic(Skill.FARMING); //Get Current lvl of skill
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
        xpPerHour = (int)(xpGained / ((System.currentTimeMillis() - this.timeBegan) / 3600000.0));
        nextLevelXp = XP_TABLE[currentLevel + 1];
        xpTillNextLevel = nextLevelXp - currentXp;
        if (xpGained >= 1) {
            timeTNL = (long)((xpTillNextLevel / xpPerHour) * 3600000);
        }
        g.drawString("" + ft(timeTNL), 580, 80);
    }

    private String ft(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) -
            TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }

    private Image getImage(String url) //Get's the background image.
    {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {}
        return null;
    }


}
