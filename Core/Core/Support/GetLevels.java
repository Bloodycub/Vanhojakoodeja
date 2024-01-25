package Core.Support;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;

public class GetLevels extends Script {
	public int WC;// Woodcutting
	public int AG; // Agility
	public int AT; // Attack
	public int Con; // construction
	public int Cock; // cooking
	public int Craf;// crafting
	public int Def;// defence
	public int Farm;// farming
	public int Fire;// firemaking
	public int Fish;// fishing
	public int Flech;// fleching
	public int Herb;// Herblore
	public int Hit;// Hitpoints
	public int Hunt;// Hunting
	public int Mag;// mage
	public int Min;// mining
	public int Pray; // Prayer
	public int Rang;// range
	public int Rune;// Runecrafting
	public int Slay;// Slayer
	public int Smit;// Smithing
	public int Str;// Strenght
	public int Tif;// Tiefing-
	public int Combat; // Combat level

	public int getWoodcuttinglevel() {
		WC = skills.getStatic(Skill.WOODCUTTING);
		return WC;
	}

	public int getAgilityLevel() {
		AG = skills.getStatic(Skill.AGILITY);
		return AG;
	}

	public int getAttackLevel() {
		AT = skills.getStatic(Skill.ATTACK);
		return AT;
	}

	public int getConstructionLevel() {
		Con = skills.getStatic(Skill.CONSTRUCTION);
		return Con;
	}

	public int getCookingLevel() {
		Cock = skills.getStatic(Skill.COOKING);
		return Cock;
	}

	public int getCraftingLevel() {
		Craf = skills.getStatic(Skill.CRAFTING);
		return Craf;
	}

	public int getDefenceLevel() {
		Def = skills.getStatic(Skill.DEFENCE);
		return Def;
	}

	public int getFamingLevel() {
		Farm = skills.getStatic(Skill.FARMING);
		return Farm;
	}

	public int getFireLevel() {
		Fire = skills.getStatic(Skill.FIREMAKING);
		return Fire;
	}

	public int getFishingLevel() {
		Fish = skills.getStatic(Skill.FISHING);
		return Fish;
	}

	public int getFlechingLevel() {
		Flech = skills.getStatic(Skill.FLETCHING);
		return Flech;
	}

	public int getHitpointsLevel() {
		Hit = skills.getStatic(Skill.HITPOINTS);
		return Hit;
	}

	public int getHerbalismLevel() {
		Herb = skills.getStatic(Skill.HERBLORE);
		return Herb;
	}

	public int getHuntingLevel() {
		Hunt = skills.getStatic(Skill.HUNTER);
		return Hunt;
	}

	public int getMagicLevel() {
		Mag = skills.getStatic(Skill.MAGIC);
		return Mag;
	}

	public int getMainingLevel() {
		Min = skills.getStatic(Skill.MINING);
		return Min;
	}

	public int getPrayerLevel() {
		Pray = skills.getStatic(Skill.PRAYER);
		return Pray;
	}

	public int getRangeLevel() {
		Rang = skills.getStatic(Skill.RANGED);
		return Rang;
	}

	public int getRunecraftingLevel() {
		Rune = skills.getStatic(Skill.RUNECRAFTING);
		return Rune;
	}

	public int getSlayerLevel() {
		Slay = skills.getStatic(Skill.SLAYER);
		return Slay;
	}

	public int getSmithingLevel() {
		Smit = skills.getStatic(Skill.SMITHING);
		return Smit;
	}

	public int getStrenghtLevel() {
		Str = skills.getStatic(Skill.STRENGTH);
		return Str;
	}

	public int getThifingLevel() {
		Tif = skills.getStatic(Skill.THIEVING);
		return Tif;
	}

	public int TotaCombatLevel() {
		Combat = combat.getCombatLevel();
		return Combat;
	}

	@Override
	public int onLoop() {
		return 100;
	}
}
