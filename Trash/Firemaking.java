package Trash;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import java.awt.Graphics2D;
import java.util.concurrent.TimeUnit;


public class Firemaking extends Script {
	
    public static Area woodcuttingspot = new Area(3025, 3328, 3068, 3314);
    //final String[] Bronze axe = {"Bronze axe", "Iron axe", "Steel axe", "Mithril axe", "Adamant axe", "Rune axe", "Dragon axe"};
    final String[] typesOfLogs = {
        "Logs",
        "Oak logs",
        "Willow logs",
        "Maple logs",
        "Mahogany logs",
        "Yew logs",
        "Magic logs"
    };
    final String[] groundObjects = {
        "Fire",
        "Daisies",
        "Fern",
        "Stones",
        "Thistle"
    };
    final String[] treetypes = {
        "Oak",
        "Yew",
        "tree"
    };
    final String[] treetype = {
        "tree"
    };
    final String[] Bronzea = {
        "Bronze axe"
    };
    private long timeBegan;
    private long timeRan;
    private int beginningXP;
    private int currentXp;
    private int xpGained;
    private int currentLevel;
    private int beginningLevel;
    private int levelsGained;
    private int xpPerHour;
    private int beginningXp;
    private double nextLevelXp;
    private long timeTNL;
    private double xpTillNextLevel;
    final int[] XP_TABLE = { 
    			  0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154,
    	          1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018,
    	          5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
    	          16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224,
    	          41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721,
    	          101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254,
    	          224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428,
    	          496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
    	          1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068,
    	          2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294,
    	          4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614,
    	          8771558, 9684577, 10692629, 11805606, 13034431, 200000000 };
    

        @Override
        	public void onStart() throws InterruptedException {
        	 log("Start up");
             timeBegan = System.currentTimeMillis();
             beginningXP = skills.getExperience(Skill.FIREMAKING);
             beginningLevel = skills.getStatic(Skill.FIREMAKING);
             timeBegan = System.currentTimeMillis(); 
             beginningXp = skills.getExperience(Skill.FIREMAKING); 
             timeTNL = 0; 
             
        }        
        public void Checkforlogsandlvl() throws InterruptedException {
        	//my level == this and if less drop rest
        	
        	
        while (inventory.contains("Oak logs")) {
        		log("Droping Oak");
        		inventory.drop("Oak logs");
        		sleep(random(396,623));
        		}
        if(inventory.contains("logs") && !inventory.contains("Oak logs")) {
        	log("Got tree but no oaks make fire");
        		Firemakingg();
        	}
        }
        	
        
     
        public void Firemakingg() throws InterruptedException {
        if(myPlayer().isAnimating()) {
        	log("Animating");
        		sleep(random(500,999));
        			}else{	
        if (inventory.contains(typesOfLogs)) {
            log("you got logs");
                if (inventory.contains("logs")) {
                	sleep(random(468,823));
                	inventory.interact("Use", "Tinderbox");
                	sleep(random(2042,3623));
                	inventory.interactWithNameThatContains("Use","logs");
                	sleep(random(3042,5623));
                	
                 //// continium here
               
                // TODO: CHECK SURROUNDING AREA
                }}
        			}}

                        
                        
    @Override
        public int onLoop() throws InterruptedException {
    	Checkforlogsandlvl();

    	   return random(700) ; //The amount of time in milliseconds before the loop starts over
        }
       
        @Override
        public void onExit() {


            //Code here will execute after the script ends


        }	
    
        public void onPaint(Graphics2D g) {
    	timeRan = System.currentTimeMillis() - this.timeBegan; 
    	g.drawString(ft(timeRan), 580, 30); 
    	currentXp = skills.getExperience(Skill.FIREMAKING); 
    	xpGained = currentXp - beginningXP; 
    	g.drawString("" + xpGained,580, 40);
    	currentLevel = skills.getStatic(Skill.FIREMAKING);
    	g.drawString("" + beginningLevel, 580,50);
    	g.drawString("" + currentLevel, 580,60);
    	levelsGained = currentLevel - beginningLevel;
    	g.drawString("" + levelsGained, 580, 70);
    	currentXp = skills.getExperience(Skill.FIREMAKING);
        currentLevel = skills.getStatic(Skill.FIREMAKING);
        xpGained = currentXp - beginningXp;
        xpPerHour = (int)( xpGained / ((System.currentTimeMillis() - this.timeBegan) / 3600000.0D));
        nextLevelXp = XP_TABLE[currentLevel + 1];
        xpTillNextLevel = nextLevelXp - currentXp;
        if (xpGained >= 1)
        {  timeTNL = (long) ((xpTillNextLevel / xpPerHour) * 3600000); }
        g.drawString("" + ft(timeTNL), 580, 80);      
  }
    
    
        private String ft(long duration) {
    	String res = "";
			long days = TimeUnit.MILLISECONDS.toDays(duration);
			long hours = TimeUnit.MILLISECONDS.toHours(duration)
			- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
			long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
			- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
			.toHours(duration));
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
			- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
			.toMinutes(duration));
			if (days == 0) {
			res = (hours + ":" + minutes + ":" + seconds);
			} else {
			res = (days + ":" + hours + ":" + minutes + ":" + seconds);
			}
			return res;


    }
        }
            