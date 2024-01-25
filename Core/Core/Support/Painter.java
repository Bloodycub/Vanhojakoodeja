package Core.Support;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import javax.imageio.ImageIO;

public class Painter {
	
	public MethodProvider api;
	public int WC = api.skills.getStatic(Skill.WOODCUTTING);// Woodcutting
	public int AG = api.skills.getStatic(Skill.AGILITY);
	public int AT = api.skills.getStatic(Skill.ATTACK); // Attack
	public int Con = api.skills.getStatic(Skill.CONSTRUCTION); // construction
	public int Cock = api.skills.getStatic(Skill.COOKING); // cooking
	public int Craf = api.skills.getStatic(Skill.CRAFTING); // crafting
	public int Def =  api.skills.getStatic(Skill.DEFENCE); // defence
	public int Farm = api.skills.getStatic(Skill.FARMING); // farming
	public int Fire =  api.skills.getStatic(Skill.FIREMAKING); // firemaking
	public int Fish = api.skills.getStatic(Skill.FISHING);// fishing
	public int Flech = api.skills.getStatic(Skill.FLETCHING); // fleching
	public int Herb = api.skills.getStatic(Skill.HERBLORE); // Herblore
	public int Hit = api.skills.getStatic(Skill.HITPOINTS); // Hitpoints
	public int Hunt = api.skills.getStatic(Skill.HUNTER); // Hunting
	public int Mag = api.skills.getStatic(Skill.MAGIC); // mage
	public int Min = api.skills.getStatic(Skill.MINING); // mining
	public int Pray = api.skills.getStatic(Skill.PRAYER); // Prayer
	public int Rang = api.skills.getStatic(Skill.RANGED); // range
	public int Rune = api.skills.getStatic(Skill.RUNECRAFTING);// Runecrafting
	public int Slay = api.skills.getStatic(Skill.SLAYER);// Slayer
	public int Smit  = api.skills.getStatic(Skill.SMITHING); // Smithing
	public int Str = api.skills.getStatic(Skill.STRENGTH); // Strenght
	public int Tif = api.skills.getStatic(Skill.THIEVING);// Tiefing
	public int currentLevel;
	public long timeRan;
	public int levelsGained;
	public final Image backround = getImage("https://i.imgur.com/mzaMiac.png");
	public int Combat; // Combat level
	public int beginningLevel;
	public long timeBegan;

	public void onStart() {
		timeBegan = System.currentTimeMillis();
		beginningLevel = api.skills.getStatic(Skill.WOODCUTTING);
		timeBegan = System.currentTimeMillis();
	}
	public void paintTo(Graphics2D g) {
		this.timeBegan = System.currentTimeMillis();
		this.beginningLevel = api.skills.getStatic(Skill.WOODCUTTING);
		this.timeRan = System.currentTimeMillis() - this.timeBegan;
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
		currentLevel = api.skills.getStatic(Skill.WOODCUTTING);
		beginningLevel = api.skills.getDynamic(Skill.WOODCUTTING);
		Combat = api.combat.getCombatLevel();
		
		
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

	public Image getImage(String url) // Get's the background image.
	{
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
		}
		return null;
	}

}
