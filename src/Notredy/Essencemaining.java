package Notredy;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;

import java.awt.*;

public class Essencemaining extends Script {
	Areas areas = new Areas();
	Timer Timerun;

	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
		log("Core Essence maining Has started");
		if(!quests.isComplete(Quest.RUNE_MYSTERIES)) {
			onLoop();
		}
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if (!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 300, 5000);
		}
		while (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}
	}

	private void Checkup() throws InterruptedException {
		if(!inventory.contains("Bronze pickaxe") && !getEquipment().isWieldingWeaponThatContains("Bronze pickaxe")) {
			log("No pick");
			GettingPickaxe();
		}else if(!Areas.EssencemineBIG.contains(myPlayer()) && !Areas.VarroWizzard.contains(myPlayer()) 
				&& !inventory.isEmpty() && !Areas.RuneEssenceminepath.contains(myPlayer())) {
			log("Not in areas walk to bank");
			GettingPickaxe();
		}else if(Areas.VarroWizzard.contains(myPlayer())) {
			log("Teleporting");
			Teleport();
		}else if(!Areas.EssencemineBIG.contains(myPlayer()) || !Areas.VarroWizzard.contains(myPlayer())) {
			log("Walking to mine essence");
			WalkingtoWizzard();
		}else if(Areas.EssencemineBIG.contains(myPlayer()) && !inventory.isFull()) {
			log("Maining");
			Mine();
		}else if(inventory.isFull()  && Areas.EssencemineBIG.contains(myPlayer())) {
			log("You are full");
			Teleportingout();
		}else if(inventory.isFull()) {
			log("Banking");
			GettingPickaxe();
		}
	}

	private void Teleportingout() {
		if (Areas.EssenceMineSpot1.contains(myPlayer())) {
			log("Walking Teleport 1");
			walking.webWalk(areas.EssenceMineSpotteleport1);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpot2.contains(myPlayer())) {
			log("Walking Teleport 2");
			walking.webWalk(areas.EssenceMineSpotteleport2);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpot3.contains(myPlayer())) {
			log("Walking Teleport 3");
			walking.webWalk(areas.EssenceMineSpotteleport3);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpot4.contains(myPlayer())) {
			log("Walking Teleport 4");
			walking.webWalk(areas.EssenceMineSpotteleport4);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpotteleport1.contains(myPlayer()) || Areas.EssenceMineSpotteleport3.contains(myPlayer())
				|| Areas.EssenceMineSpotteleport3.contains(myPlayer())
				|| Areas.EssenceMineSpotteleport4.contains(myPlayer())) {
			GroundItem Portal = getGroundItems().closest("Portal");

			if (Portal != null) {
				log("Tping out");
				Portal.interact("Use");
				Sleep.waitCondition(() -> Areas.VarroWizzard.contains(myPlayer()), 500, 5000);
			}
		}
	}

	private void Mine() throws InterruptedException {
		if (Areas.EssenceMineSpot1.contains(myPlayer())) {
			log("Walking spot 1");
			walking.webWalk(areas.EssenceMineSpot1mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpot2.contains(myPlayer())) {
			log("Walking spot 2");
			walking.webWalk(areas.EssenceMineSpot2mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpot3.contains(myPlayer())) {
			log("Walking spot 3");
			walking.webWalk(areas.EssenceMineSpot3mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpot4.contains(myPlayer())) {
			log("Walking spot 4");
			walking.webWalk(areas.EssenceMineSpot4mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.EssenceMineSpotMID.contains(myPlayer())) {
			log("Taking Random mine");
			Randommine();
		}
		if (Areas.EssenceMineSpot1.contains(myPlayer()) || Areas.EssenceMineSpot2.contains(myPlayer())
				|| Areas.EssenceMineSpot3.contains(myPlayer()) || Areas.EssenceMineSpot4.contains(myPlayer())) {
			GroundItem Rock = getGroundItems().closest("Rune Essence");

			if (Rock != null) {
				log("Maining rock");
				Rock.interact("Mine");
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> inventory.isFull() || getDialogues().isPendingContinuation(), 1000, 50000);
			}
		}
	}

	private void Randommine() {
		int random = random(1, 4);

		if (random == 1) {
			log("Walking spot 1 by random");
			walking.webWalk(areas.EssenceMineSpot1mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (random == 2) {
			log("Walking spot 2 by random");
			walking.webWalk(areas.EssenceMineSpot2mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (random == 3) {
			log("Walking spot 3 by random");
			walking.webWalk(areas.EssenceMineSpot3mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (random == 4) {
			log("Walking spot 4 by random");
			walking.webWalk(areas.EssenceMineSpot4mine);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
	}

	private void Teleport() {
		NPC Wizzard = getNpcs().closest("Aubury");

		if (Wizzard != null) {
			Wizzard.interact("Teleport");
			Sleep.waitCondition(() -> Areas.EssencemineBIG.contains(myPlayer()), 500, 5000);
		}

	}

	private void WalkingtoWizzard() {
		if (!Areas.VarroWizzard.contains(myPlayer())) {
			walking.webWalk(areas.VarroWizzard());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}

	}

	private void GettingPickaxe() throws InterruptedException {
		if (!Areas.VarrockBankupperbank.contains(myPlayer())) {
			log("Walking bank");
			walking.webWalk(areas.VarrockBankupperbank());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}if(bank.isOpen()) {
			log("Reset");
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 3000);
		}
		if (!bank.isOpen()) {
			log("Open bank");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
			if(!inventory.isEmpty()) {
			bank.depositAll();
			Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);}
			if (inventory.isEmpty() && !equipment.isWearingItem(EquipmentSlot.WEAPON, "Bronze pickaxe")) {
				bank.withdraw("Bronze pickaxe", 1);
				Sleep.waitCondition(() -> inventory.contains("Bronze pickaxe"), 500, 3000);
				inventory.interact("Wield", "Bronze pickaxe");
				if(!inventory.isEmpty()) {
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);}
			}
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		if(!quests.isComplete(Quest.RUNE_MYSTERIES)) {
			log("Rune mysteries not completed!!!");
			return -1;
		}
		Checkup();

		return random(111);

	}

	@Override
	public void onExit() {

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString("Time runned " +Timerun, 0, 11);


	}

}