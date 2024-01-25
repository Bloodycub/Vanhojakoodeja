package Core.Quests;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.awt.*;

public class Doricsquest extends Skeleton {
	public int Min; // mining
	Areas areas = new Areas();
	String[] Dialogues = { "I wanted to use your anvils.", "Yes, I will get you the materials." };
//Fix null check on ore

	@Override
	public void onStart() throws InterruptedException {
		log("Dorics Quest Has started");
		//// FIX CONFIGS
		if (getConfigs().get(101) == 9) {
			onLoop();
		}
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if(dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
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

	private void CheckUp() throws InterruptedException {
		int Inventorysize = Inventory.SIZE - getInventory().getEmptySlots();
		Min = skills.getDynamic(Skill.MINING);
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800,1200));
		}
		//// FIX CONFIGS
		if (getConfigs().get(101) == 9) {
			log("Quest completed");
			onLoop();
		} else if (!(inventory.contains("Bronze pickaxe") || getEquipment().isWieldingWeapon("Bronze pickaxe"))
				|| Inventorysize >= 15 && !Areas.Quarrybig.contains(myPlayer())) {
			log("Banking");
			Bankall();
		} else if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 3000);
		} else if (inventory.getAmount("Copper ore") == 4 && Min >= 15 && inventory.getAmount("Iron ore") < 2) {
			log("Got copper walk to iron mine");
			Ironmine();
		} else if (inventory.getAmount("Iron ore") == 2 && inventory.getAmount("Copper ore") == 4 && Min >= 15
				&& inventory.getAmount("Clay") < 6) {
			log("Got iron and copper Walk to clay");
			Claymine();
		} else if (inventory.getAmount("Clay") == 6 && inventory.getAmount("Iron ore") == 2
				&& inventory.getAmount("Copper ore") == 4 && Min >= 15) {
			log("Got all lets go to hut");
			Completingquest();
		} else if (Min >= 15 && inventory.getAmount("Copper ore") < 4) {
			log("Mining level 15 and mining ores for quest");
			MineCopper();
		} else if (Min < 15 && inventory.contains("Bronze pickaxe")
				|| getEquipment().isWieldingWeapon("Bronze pickaxe")) {
			Gettinglevels();
		} else if (Min >= 15) {
			log("Checking space");
			Checkquestitems();
		}
	}

	private void Checkquestitems() throws InterruptedException {
		if (!inventory.isItemSelected()) {
			if (inventory.getAmount("Copper ore") > 4) {
				log("Dropping copper");
				inventory.drop("Copper ore");
				sleep(random(500, 800));
			}
			if (inventory.getAmount("Iron ore") > 2) {
				log("Dropping Iron");
				inventory.drop("Iron ore");
				sleep(random(500, 800));
			}
			if (inventory.getAmount("Clay") > 6) {
				log("Dropping Clay");
				inventory.drop("Clay");
				sleep(random(500, 800));
			}

			if (inventory.getAmount("Copper ore") < 4) {
				MineCopper();
			}
			if (inventory.getAmount("Iron ore") == 2 && inventory.getAmount("Copper ore") == 4) {
				Ironmine();
			}
			if (inventory.getAmount("Clay") < 6 && inventory.getAmount("Iron ore") == 2
					&& inventory.getAmount("Copper ore") == 4) {
				Claymine();
			}
		} else {
			inventory.deselectItem();
		}
	}

	private void Completingquest() throws InterruptedException {
		Area NextRoom = new Area(2950, 3454, 2953, 3453);
		if (!Areas.Doricsquesthut.contains(myPlayer())) {
			walking.webWalk(areas.Doricsquesthut());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		NPC Doric = getNpcs().closest("Doric");
		RS2Object RoomDoor = getObjects().closest("Door");

		if(NextRoom.contains(Doric)) {
			RoomDoor.hasAction("Open");
			RoomDoor.interact("Open");
			Sleep.waitCondition(() -> RoomDoor.hasAction("Close") , 200,5000);
		}
		if (getDialogues().inDialogue()) {
			dialogues.completeDialogue(Dialogues);
		} else {
			Doric.interact("Talk-to");
			Sleep.waitCondition(() -> dialogues.inDialogue(), 5000);
		}

	}

	private void MineCopper() throws InterruptedException {
		if (Areas.Quarrybig.contains(myPlayer())) {
			log("Dont have Ore");
			Gettinglevels();
		} else {
			log("Not in area go to main");
			WalkingCopper();

		}
	}

	private void Claymine() throws InterruptedException {
		if (Areas.ClayQuarry.contains(myPlayer())) {
			log("Mining Clay");
			MineClay();
		} else {
			log("Walking to Clay");
			walkingtoClay();
		}
	}

	private void MineClay() throws InterruptedException {
		if (!Areas.ClayQuarry.contains(myPlayer())) {
			log("Walking Clay one");
			walking.webWalk(areas.ClayQuarry);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 30000);
		}
		if (Areas.ClayQuarry.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Redy to mine");
			RS2Object Ore = getObjects().closest("Rocks");
			if (Ore != null) {
				log("Mining");
				Ore.interact("Mine");
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> !myPlayer().isAnimating(), 500, 30000);
				sleep(random(5000, 8000));
			}
		}
	}

	private void walkingtoClay() throws InterruptedException {
		log("Walking clay");
		if (!Areas.ClayQuarry.contains(myPlayer())) {
			walking.webWalk(areas.ClayQuarry);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 30000);
		}
	}

	private void Ironmine() throws InterruptedException {
		if (!Areas.IronoreQuarry.contains(myPlayer())) {
			log("Walking irone one");
			walkingtoiron();
		}
		if (Areas.IronoreQuarry.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Redy to mine");
			RS2Object Ore = getObjects().closest("Rocks");
			if (Ore != null) {
				log("Mining Ironone");
				Ore.interact("Mine");
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> !myPlayer().isAnimating(), 500, 30000);
				sleep(random(8000, 10000));
			}
		}
	}

	private void Inventoryfull() throws InterruptedException {
		log("Droping tin");
		inventory.dropAll("Tin ore");
		Sleep.waitCondition(() -> !inventory.contains("Tin ore"), 500, 3000);
		log("Droping Copper ore");
		inventory.dropAll("Copper ore");
		Sleep.waitCondition(() -> !inventory.contains("Copper ore"), 500, 3000);
	}

	private void walkingtoiron() throws InterruptedException {
		if (!Areas.IronoreQuarry.contains(myPlayer())) {
			walking.webWalk(areas.IronoreQuarry);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 30000);
		}
	}

	private void WalkingCopper() throws InterruptedException {
		if (!Areas.CopperoreQuarry.contains(myPlayer())) {
			walking.webWalk(areas.CopperoreQuarry);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 30000);
		}
	}

	private void Gettinglevels() throws InterruptedException {
		if (inventory.getAmount("Copper Ore") == 4 && Min > 15) {
			log("Got 4 copper and mining 15");
			CheckUp();
		} else if (inventory.isFull()) {
			log("Inventory full true");
			Inventoryfull();
		}

		if (!Areas.CopperoreQuarry.contains(myPlayer())){
			walking.webWalk(areas.CopperoreQuarry);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.CopperoreQuarry.contains(myPlayer()) && !myPlayer().isAnimating() && !myPlayer().isMoving()) {
			log("Redy to mine");
			RS2Object Ore = getObjects().closest("Rocks");
			if (Ore != null) {
				log("Mining");
				Ore.interact("Mine");
				if(dialogues.isPendingContinuation()) {
					log("Mining");
					Ore.interact("Mine");	
				}
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> !myPlayer().isAnimating() || dialogues.isPendingContinuation(), 500, 30000);
				sleep(random(12000, 15000));
			}
		}
	}

	public void Bankall() throws InterruptedException {
			if (!Areas.DraynorBank.contains(myPlayer()) && !bank.isOpen()) {
				log("Walking DraynorBank bank");
				walking.webWalk(Areas.DraynorBank);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
			}
			if(!bank.isOpen()) {
			log("Open bank");
			bank.open();
			sleep(random(500,1000));
			Sleep.waitCondition(() -> bank.isOpen(), 200,3000);}
			if (bank.isOpen()) {
				if (!inventory.isEmpty()) {
					bank.depositAll();
					sleep(random(1500,2200));

					Sleep.waitCondition(() -> inventory.isEmpty(),200, 3000);
				}
				bank.withdraw("Bronze pickaxe", 1);
				Sleep.waitCondition(() -> inventory.contains("Bronze pickaxe"), 200,3000);
				inventory.interact("Wield", "Bronze pickaxe");
				Sleep.waitCondition(() -> getEquipment().isWieldingWeapon("Bronze pickaxe"),500,5000);
				if (!inventory.isEmpty()){
					bank.depositAll();
					sleep(random(1500,2200));

					Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				}
				bank.close();				
			}
		}

	@Override
	public int onLoop() {
		if (getConfigs().get(101) == 9) {
			log("Dorics_quest Complete");
			return -10;
		}
		try {
			CheckUp();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 400+random(100); }
	

	@Override
	public void onExit() {
		log("Good Bye");
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.drawString(Min + " == 15", 0, 11);

	}

}