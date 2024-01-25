package Core.Quests;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;
import java.awt.*;

public class RuneMysteries extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();
	String talisman = "Air talisman";
	String Gift = "Research package";
	String Note = "Notes";

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		if (getQuests().isComplete(Quest.RUNE_MYSTERIES)) {
			onLoop();
		}
		if (combat.getCombatLevel() <= 18) {
			onLoop();
		}
		log("Rune Mysteries Quest Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if(dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
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

	private void Checkup() throws InterruptedException {
		/* Rune mysteries
		 * 63:0 not started 63:1 start 63:2 wizzard takes talisman 63:3 Got pacage 63:4
		 * gift givven 63:5 got Note 63:6 complete
		 * 
		 */
		if (getQuests().isComplete(Quest.RUNE_MYSTERIES)) {
			onLoop();
		}
		if (getConfigs().get(63) == 0) {
			log("Not started quest");
			if (inventory.isEmpty()) {
				Start();
			} else {
				Bankingstuff();
			}
			if (dialogues.isPendingContinuation()) {
				log("Pending continue");
				dialogues.clickContinue();
				sleep(random(800,1200));
			}
		} else if ((getConfigs().get(63) == 1 || getConfigs().get(63) == 2) && inventory.contains(talisman)) {
			log("Started quest and got talisman");
			GotoTower();
		} else if (getConfigs().get(63) == 3 && inventory.contains(Gift)) {
			log("Delivere gift to varrock wizzard");
			WalkingtoVarrock();
		} else if (getConfigs().get(63) == 4 && !inventory.contains(Gift)) {
			log("No gift speacking agen");
			WalkingtoVarrock();
		} else if (getConfigs().get(63) == 5 && inventory.contains(Note)) {
			Completing();
		}
	}

	private void Completing() throws InterruptedException {
		NPC Wizzard = getNpcs().closest("Sedridor");

		if (!Areas.SedriodorRoom.contains(myPlayer())) {
			walking.webWalk(areas.SedriodorRoom());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Wizzard != null && !getDialogues().inDialogue()) {
			Wizzard.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
		}
		if (getDialogues().inDialogue()) {
			String[] WizzardSpeach = { "" };
			dialogues.completeDialogue(WizzardSpeach);
			Sleep.waitCondition(() -> getConfigs().get(63) == 6 && !inventory.contains(Note), 500, 5000);
		}
		if (getConfigs().get(63) == 6 && !inventory.contains(Note)) {
			log("Completed");
			onLoop();
		}
	}

	private void WalkingtoVarrock() throws InterruptedException {
		NPC Wizzard = getNpcs().closest("Aubury");
		Area LBSpawn = new Area(3217, 3227, 3226, 3211);

		if (!Areas.VarroWizzard.contains(myPlayer())) {
			walking.webWalk(areas.VarroWizzard());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Wizzard != null && !getDialogues().inDialogue()) {
			Wizzard.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
		}
		if (getDialogues().inDialogue() && getConfigs().get(63) != 4) {
			String[] WizzardSpeach = { "I have been sent here with a package for you." };
			dialogues.completeDialogue(WizzardSpeach);
			Sleep.waitCondition(() -> getConfigs().get(63) == 4 && !inventory.contains(Gift), 500, 5000);
		}
		if (getDialogues().inDialogue() && getConfigs().get(63) == 4) {
			String[] WizzardSpeach = {};
			dialogues.completeDialogue(WizzardSpeach);
			Sleep.waitCondition(() -> getConfigs().get(63) == 5 && inventory.contains(Note), 500, 5000);
		}
		if (getConfigs().get(63) == 5 && inventory.contains(Note) && !LBSpawn.contains(myPlayer())) {
			getMagic().castSpell(Spells.LunarSpells.HOME_TELEPORT);
			Sleep.waitCondition(() -> LBSpawn.contains(myPlayer()), 500, 20000);
		}
	}

	private void GotoTower() throws InterruptedException {
		NPC Wizzard = getNpcs().closest("Sedridor");

		if (!Areas.SedriodorRoom.contains(myPlayer())) {
			walking.webWalk(areas.SedriodorRoom());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Wizzard != null && !getDialogues().inDialogue()) {
			Wizzard.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
		}
		if (getDialogues().inDialogue()) {
			String[] WizzardSpeach = { "I'm looking for the head wizard.", "Ok, here you are.", "Yes, certainly." };
			dialogues.completeDialogue(WizzardSpeach);
			Sleep.waitCondition(() -> getConfigs().get(63) == 3 && inventory.contains(Gift), 500, 5000);
		}
	}

	private void Bankingstuff() throws InterruptedException {
		if (!inventory.isEmptyExcept(talisman)) {
			if (!Areas.LumbgeBank.contains(myPlayer())) {
				log("Walking Lb bank");
				walking.webWalk(areas.LumbgeBank.getRandomPosition());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				sleep(random(500,1000));
				Sleep.waitCondition(() -> bank.isOpen(), 3000);

				if (!inventory.isEmpty()) {
					bank.depositAll();
					sleep(random(1500,2200));

					Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
				}
				bank.close();
				Sleep.waitCondition(() -> bank.close(), 500, 3000);
			}
		}
	}

	private void Start() throws InterruptedException {
		NPC Duke = getNpcs().closest("Duke Horacio");

		if (!Areas.DukeHoracioRoom.contains(myPlayer())) {
			walking.webWalk(areas.DukeHoracioRoom());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Duke != null && !getDialogues().inDialogue()) {
			Duke.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
		}
		if (getDialogues().inDialogue()) {
			String[] DukeSpeak = { "Have you any quests for me?", "Sure, no problem." };
			dialogues.completeDialogue(DukeSpeak);
			Sleep.waitCondition(() -> getConfigs().get(63) == 1 && inventory.contains(talisman), 500, 5000);
		}
	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 0, 11);

	}

	@Override
	public int onLoop() {
		time.Timerun();
		if (getQuests().isComplete(Quest.RUNE_MYSTERIES)) {
			log("Quest completed");
			return -10;

		}
		if (combat.getCombatLevel() <= 18) {
			log("Too low level");
			return -1;
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 400+random(100); }	

}
