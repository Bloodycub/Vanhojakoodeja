package Core.Corebots;

import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;
import java.awt.*;

public class ArmoringUp extends Script {
	Areas areas = new Areas();
	Timer Timerun;
	int loopcheck = random(5, 10);

	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
		log("XXXXXX BOT NAME XXXXXXXXXXX Has started");
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
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}
	}

	private void Checkup() {
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
		}
		WalkingBank();
		CheckingForAttackArmor();
		CheckingForRangeArmor();
		CheckingForMageArmor();

	}

	private void WalkingBank() {
//Draynor bank		
	}

	private void CheckingForMageArmor() {
		// TODO Auto-generated method stub

	}

	private void CheckingForRangeArmor() {
		// TODO Auto-generated method stub

	}


	private void CheckingForAttackArmor() throws InterruptedException {
		int[] LevelsArmor = {1,1,1,1,1,1,1}; // bronze iron
		int[] LevelsWepon = {1,1,1,1,1};  // bronze iron
		int[] SteelLevelsArmor = {5,5,5,5,5,5,5}; 
		int[] SteelLevelsWepon = {5,5,5,5,5};  
		int[] BlackLevelsArmor = {10,10,10,10,10,10,10}; 
		int[] BlackLevelsWepon = {10,10,10,10,10};  
		int[] MithrilLevelsArmor = {20,20,20,20,20,20,20}; 
		int[] MithrilLevelsWepon = {20,20,20,20,20};  
		int[] AdamantLevelsArmor = {30,30,30,30,30,30,30}; 
		int[] AdamantLevelsWepon = {30,30,30,30,30};  
		int[] RuneLevelsArmor = {40,40,40,40,40,40,40}; 
		int[] RuneLevelsWepon = {40,40,40,40,40};  
		
		String[] General = { "Leather boots", "Leather gloves" };
		String[] Bronze = { "Bronze full helm", "Bronze med helm", "Bronze platebody", "Bronze chainbody",
				"Bronze kiteshield", "Bronze square shield", "Bronze platelegs" };
		String[] BronzeWepon = { "Bronze scimitar", "Bronze sword", "Bronze longsword", "Bronze mace",
				"Bronze dagger" };
		String[] Iron = { "Iron full helm", "Iron med helm", "Iron platebody", "Iron chainbody", "Iron kiteshield",
				"Iron square shield", "Iron platelegs " };
		String[] IronWepon = { "Iron scimitar", "Iron sword", "Iron longsword", "Iron mace", "Iron dagger" };
		String[] Steel = { "Steel full helm", "Steel med helm", "Steel platebody", "Steel chainbody",
				"Steel kiteshield", "Steel square shield", "Steel platelegs" };
		String[] SteelWepon = { "Steel scimitar", "Steel sword", "Steel longsword", "Steel mace", "Steel dagger" };
		String[] Black = { "Black full helm", "Black med helm", "Black platebody", "Black chainbody",
				"Black kiteshield", "Black square shield", "Black platelegs" };
		String[] BlackWepon = { "Black scimitar", "Black sword", "Black longsword", "Black mace", "Black dagger" };
		String[] Mithril = { "Mithril full helm", "Mithril med helm", "Mithril platebody", "Mithril chainbody",
				"Mithril kiteshield", "Mithril square shield", "Mithril platelegs" };
		String[] MithrilWepon = { "Mithril scimitar", "Mithril sword", "Mithril longsword", "Mithril mace",
				"Mithril dagger" };
		String[] Adamant = { "Adamant full helm", "Adamant med helm", "Adamant platebody", "Adamant chainbody",
				"Adamant kiteshield", "Adamant square shield", "Adamant platelegs" };
		String[] AdamantWepon = { "Adamant scimitar", "Adamant sword", "Adamant longsword", "Adamant mace",
				"Adamant dagger" };
		String[] Rune = { "Rune full helm", "Rune med helm", "Rune platebody", "Rune chainbody", "Rune kiteshield",
				"Rune square shield", "Rune platelegs" };
		String[] RuneWepon = { "Rune scimitar", "Rune sword", "Rune longsword", "Rune mace", "Rune dagger" };

		int Defence = skills.getStatic(Skill.DEFENCE);
		int Attack = skills.getStatic(Skill.ATTACK);
		Boolean Head = equipment.isWearingItem(EquipmentSlot.HAT);
		Boolean Cape = equipment.isWearingItem(EquipmentSlot.CAPE);
		Boolean Amulet = equipment.isWearingItem(EquipmentSlot.AMULET);
		Boolean Chest = equipment.isWearingItem(EquipmentSlot.CHEST);
		Boolean Weapon = equipment.isWearingItem(EquipmentSlot.WEAPON);
		Boolean Shield = equipment.isWearingItem(EquipmentSlot.SHIELD);
		Boolean Legs = equipment.isWearingItem(EquipmentSlot.LEGS);
		Boolean Feet = equipment.isWearingItem(EquipmentSlot.FEET);
		Boolean Hands = equipment.isWearingItem(EquipmentSlot.HANDS);
		Boolean Ring = equipment.isWearingItem(EquipmentSlot.RING);
		Boolean NoRune = false;
		Boolean NoAdamant = false;
		Boolean NoMithril = false;
		Boolean NoSteel = false;
		Boolean NoIron = false;
		Boolean NoBronze = false;
		
		if(Areas.DraynorBank.contains(myPlayer())) {
			if(!bank.isOpen()) {
				bank.open();
				sleep(random(500,1000));
				Sleep.waitCondition(() -> bank.isOpen(), 200,5000);
			}else if(bank.isOpen()) {
				bank.depositAll();
				sleep(random(1500,2000));
				Sleep.waitCondition(() -> inventory.isEmpty(), 200,5000);
				bank.depositWornItems();
				sleep(random(1500,2000));
				Sleep.waitCondition(() -> equipment.isWearingItem(EquipmentSlot.WEAPON) == false, 200,5000);
			
			for(int i = 0; i < Rune.length; i++) {
				if(bank.contains(Rune[i]) && Defence >= RuneLevelsArmor[0]) {
					bank.withdraw(Rune[i], 1);
					sleep(random(500,1000));
					final int a = i;
					Sleep.waitCondition(() -> inventory.contains(Rune[a]), 200,5000);
					inventory.interact("Dress up =!=!=!=!=!", Rune[i]);
				}
				if(Rune.length == i) {
					NoRune = true;
					break;
				}
			}
			// IF Wearin
			for(int i = 0; i < Rune.length; i++) {
				if(bank.contains(Rune[i]) && Defence >= RuneLevelsArmor[0]) {
					bank.withdraw(Rune[i], 1);
					sleep(random(500,1000));
					final int a = i;
					Sleep.waitCondition(() -> inventory.contains(Rune[a]), 200,5000);
					inventory.interact("Dress up =!=!=!=!=!", Rune[i]);
				}
				if(Rune.length == i) {
					NoRune = true;
					break;
				}
			}
			
			
			
			
			
			
			
			
			
			}
		}
		
		
		
	
		
	}

	@Override
	public int onLoop() {
		int Counter = 0;
		if (Counter == loopcheck) {
			int loopcheck = random(5, 10);
			if (dialogues.isPendingContinuation()) {
				log("Stuck");
				dialogues.clickContinue();
			}
			Counter = 0;
		}
		Checkup();

		return 400 + random(100);

	}

	@Override
	public void onExit() {
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString("Time runned " + Timerun, 0, 11);

	}

}