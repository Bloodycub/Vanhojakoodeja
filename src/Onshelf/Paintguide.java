package Onshelf;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.net.URL;

import org.osbot.rs07.api.ui.Skill;
import java.awt.Color;




public class Paintguide extends Script implements Paint {
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
    final int[] XP_TABLE = {0,0,83,174,276,388,512,650,801, 969,1154, 1358,1584,1833,2107,2411,2746,3115,
            3523, 3973,4470, 5018,5624,6291, 7028,7842,  8740, 9730,10824,12031,13363,14833,16456,18247,
            20224,22406,24815,27473,30408,33648,37224,41171, 45529,50339,55649,61512, 67983, 75127,83014,91721,
            101333,111945,123660,136594, 150872,166636,184040, 203254,224466,247886,273742,302288,333804,
            368599,407015,449428,496254,547953,605032,668051,737627,814445,899257,992895,1096278,1210421,1336443,
            1475581,1629200,1798808,1986068,2192818,2421087,2673114,2951373,3258594,3597792,3972294,4385776,4842295,
            5346332,5902831,6517253,7195629,7944614,8771558,9684577,10692629,11805606, 13034431,200000000};



    @Override

    public void onStart() throws InterruptedException {
        sleep(500);
        timeBegan = System.currentTimeMillis();
        beginningXP = skills.getExperience(Skill.WOODCUTTING);
        beginningLevel = skills.getStatic(Skill.WOODCUTTING);
        timeTNL = 0;




        //Code here will execute before the loop is started
    }

    @Override
    public int onLoop() {


        return random(22700); //The amount of time in milliseconds before the loop starts over


    }

    @Override
    public void onExit() {


        //Code here will execute after the script ends


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

	@Override
	public int getTransparency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PaintContext createContext(ColorModel arg0, Rectangle arg1, Rectangle2D arg2, AffineTransform arg3,
			RenderingHints arg4) {
		// TODO Auto-generated method stub
		return null;
	}
}


//Lets look at what we have above for a second so that you understand it. The two letters ft are relating to the formatting method above. So you are saying format(timeRan). You then have two numbers after that, 1 and 1. these relate to X and Y on the screen. you can find these values by turning on the ' Mourse Position Debug' in the client settings.

//So now you can display the time your script has ran for, congratulations.



//To see if you got everything correct, see the full script here: http://pastebin.com/5baWdRuW