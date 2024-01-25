package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Copy.Freshspawn;
import bots.*;
 
public final class Main extends Script {
		 int WC; //Woodcutting
	     int AG; //Agility
	     int AT; //Attack
	     int Con; // construction
	     int Cock; // cooking
	     int Craf; //crafting
	     int Def; // defence
	     int Farm; //farming
	     int Fire; //firemaking
	     int Fish; //fishing
	     int Flech; // fleching
	     int Herb; //Herblore
	     int Hit; // Hitpoints
	     int Hunt; //Hunting
	     int Mag; //mage
	     int Min; //mining
	     int Pray; //Prayer
	     int Rang; //range
	     int Rune; //Runecrafting
	     int Slay; //Slayer
	     int Smit; //Smithing
	     int Str; //Strenght
	     int Tif; //Tiefing
	long timeBegan;
	long timeRan;	int beginningXP;	int currentXp;	int xpGained;	int currentLevel;	int beginningLevel;
	int levelsGained;	int xpPerHour;	double nextLevelXp;	long timeTNL;	double xpTillNextLevel;
    final int[] XP_TABLE = {0,0,83,174,276,388,512,650,801, 969,1154, 1358,1584,1833,2107,2411,2746,3115,
        3523, 3973,4470, 5018,5624,6291, 7028,7842,  8740, 9730,10824,12031,13363,14833,16456,18247,
        20224,22406,24815,27473,30408,33648,37224,41171, 45529,50339,55649,61512, 67983, 75127,83014,91721,
        101333,111945,123660,136594, 150872,166636,184040, 203254,224466,247886,273742,302288,333804,
        368599,407015,449428,496254,547953,605032,668051,737627,814445,899257,992895,1096278,1210421,1336443,
        1475581,1629200,1798808,1986068,2192818,2421087,2673114,2951373,3258594,3597792,3972294,4385776,4842295,
        5346332,5902831,6517253,7195629,7944614,8771558,9684577,10692629,11805606, 13034431,200000000};

	private final BotSkeleton woodcut = new Woodcutting(); //joka botille t�m�
	private final BotSkeleton freshSpawn = new Freshspawn();
	Area Tutisland = new Area(3062, 3139, 3160, 3055); // tut island
	Area LBSpawnarea = new Area(3222, 3236, 3240, 3219); //Spawn area
	
	private int timeUntilNewActivity = 0; 

	private int state = 0;
	
	private boolean doingRandomWithTimer = false;
	
	private long timeAlpha = 0;
	
	private int availableStates = 2;
	
	private BotSkeleton currentBot;
	
    @Override
	public void onStart() throws InterruptedException {
    	sleep(500);
        timeBegan = System.currentTimeMillis();
        beginningXP = skills.getExperience(Skill.WOODCUTTING);
        beginningLevel = skills.getStatic(Skill.WOODCUTTING);
        timeTNL = 0;
    	
    	//Testing Test = new Testing();
    	
    	woodcut.exchangeContext(getBot());  //joka botille t�m�
    	freshSpawn.exchangeContext(getBot());
    	log("Start");
    	sleep(1000);
    	
    	timeUntilNewActivity = random(1200000, 1800000);
    	
    	//Default botti joka runnaa heti
    	currentBot = freshSpawn;
    	//Test.onStart(getBot());
    }
	
    //Call this function to get a new random activity
    private void randomState() {
		int lastState = state;
		while (lastState == state || state != 0) {
			state = (random(0,1000) % availableStates);
		}
    }

    
    @Override
    public int onLoop() throws InterruptedException {
    	timeAlpha = System.currentTimeMillis() - timeAlpha; //How much time has passed since the last time this was updated
    	
    	//Choose which bot is the current one being used
    	switch(state) {
	    	case 0:
	    		currentBot = freshSpawn;
	    		break;
	    	case 1:
	    		currentBot = woodcut;
	    		break;	
    	}
    	
    	//Run a one loop of current bot and check return codes
    	switch(currentBot.onLoop()) {
    		case 0:
    			//Nothing here, bot is working
    			break;
    		
    		case 7:
    			randomState();
    			break;
    			
    	}
    		
    	
    	
    	if (doingRandomWithTimer) {
        	timeUntilNewActivity -= timeAlpha;
        	
        	if (timeUntilNewActivity <= 0) {
        		timeUntilNewActivity = random(1200000, 1800000); //Set a new random time
        		randomState(); //Set a new random state, not 0 (fresh spawn) or the last one used
        	}
    	}

    	
	   return 2000;
    }
    
   
    @Override
    public void onExit() {
    	stop(true);
    }	
    
    
    @Override
    public void onPaint(Graphics2D g) {
    	Image backround = getImage("https://i.imgur.com/mzaMiac.png");
        g.drawImage(backround, 516, 0, null);
        g.drawString("" + ft(timeTNL), 642,97); //To Next level
        g.setColor(Color.decode("#00ff04")); //Color
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
        g.drawString("" + Herb, 532,33);
        g.drawString("" + Hit, 650, 67);
        g.drawString("" + Hunt, 683, 33);
        g.drawString("" + Mag, 555,97);
        g.drawString("" + Min, 715, 112);
        g.drawString("" + Pray, 575,127);
        g.drawString("" + Rang, 703, 128);
        g.drawString("" + Rune, 532,159);
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