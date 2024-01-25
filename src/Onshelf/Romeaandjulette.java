package Onshelf;

import Support.*;
import java.awt.*;

import org.osbot.rs07.api.GroundItems;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Sleep;
import Core.Support.Time;

public class Romeaandjulette extends Script {
	Areas areas = new Areas();
	Time time = new Time();
	int Bushempty = 0;
	int berryamount = random(2, 4);

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Romea and Julette Has started");
		if (getQuests().isComplete(Quest.ROMEO_JULIET)) {
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
		if (!inventory.isEmpty() && !inventory.contains("Message") && !inventory.contains("Cadava potion")
				&& !inventory.contains("Cadava berries")) {
			log("Empty your pockets");
			Banking();
		}
	}

	private void Banking() throws InterruptedException {
		if (inventory.isEmpty()) {
			log("Inventory empty");
		} else {
			if (!Areas.VarrokAlapankki.contains(myPlayer())) {
				log("Walking Varrock bank");
				walking.webWalk(areas.VarrokAlapankki());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 3000);
			}
			if (!bank.isOpen()) {
				log("Open bank");
				bank.open();
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 3000);
				bank.close();
				Sleep.waitCondition(() -> bank.close(), 500, 3000);
			}
		}
	}

	private void Checkup() throws InterruptedException {
		log("Check up");
		if (getDialogues().inDialogue()) {
			log("Bugged in dialog");
			dialogues.clickContinue();
		}
		if (getConfigs().get(144) < 10) {
			log("Starting Romea and juliette");
			Walkingtovarrockmid();
		} else if (getConfigs().get(144) == 10) {
			log("Going to juliet");
			walkingtoJuliette();
		} else if (getConfigs().get(144) == 20 && inventory.contains("Message")) {
			log("Taking letter for romeo");
			Walkingtovarrockmid();
		} else if (getConfigs().get(144) == 30) {
			log("Doing priest part");
			TalktoPriest();
		} else if (getConfigs().get(144) == 40) {
			log("Getting berrys");
			Gettingberrys();
		}else if(getConfigs().get(144) >= 40 && !inventory.contains("Cadava potion") && getConfigs().get(144) < 60) {
			log("Lost potion???");
			Gettingberrys();
		} else if (getConfigs().get(144) == 50 && inventory.contains("Cadava potion")) {
			log("Making potion");
			walkingtoJuliettewithpotion();
		} else if (getConfigs().get(144) == 60) {
			log("Juliet died");
			Walkingtovarrockmid();
		} else if (getConfigs().get(144) == 100) {
			log("Completed");
			onLoop();
		}
		/*
		 * 144:10 start 144:20 julette letter 144;30 ask priest 144:40 priest talked
		 * 144:50 potion Potion 144:60 juliet died 144:100 Completed
		 */

	}

	private void Walkingtovarrockmid() throws InterruptedException {
		if (!Areas.VarrockMid.contains(myPlayer())) {
			log("Walking to Romeo");
			walking.webWalk(areas.VarrockMid());
			Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.VarrockMid.contains(myPlayer())) {
			if (getConfigs().get(144) < 10) {
				log("Stating to talk");
				StartQuestFromromeo();
			}
			if (getConfigs().get(144) == 20 && inventory.contains("Message")) {
				log("Taking letter for romeo");
				Givingmessagetoromeo();
			}
			if (getConfigs().get(144) == 60) {
				log("Juliet died");
				JulietDied();
			}
		}

	}

	private void JulietDied() throws InterruptedException {
		NPC Romeo = getNpcs().closest("Romeo");
		String[] Firsttalk = { "" };
		if (Romeo != null) {
			Romeo.interact("Talk-to");
			Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 100) {
					log("Completed");
					onLoop();
				}
			}
		}
	}

	private void Givingmessagetoromeo() throws InterruptedException {
		NPC Romeo = getNpcs().closest("Romeo");
		String[] Firsttalk = { "Ok, thanks." };
		if (Romeo != null) {
			Romeo.interact("Talk-to");
			Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 30) {
					log("Speaking to Juliet");
					WalkingtoPriest();
				}
			}
		}
	}

	private void WalkingtoPriest() throws InterruptedException {
		if (!Areas.Varrockpriest.contains(myPlayer())) {
			log("Walking to Priest");
			walking.webWalk(areas.Varrockpriest());
			Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.Varrockpriest.contains(myPlayer())) {
			if (getConfigs().get(144) == 30) {
				log("Stating to talk");
				TalktoPriest();
			}
		}
	}

	private void TalktoPriest() throws InterruptedException {
		NPC Priest = getNpcs().closest("Father Lawrence");
		String[] Firsttalk = { "" };
		if (Priest != null) {
			Priest.interact("Talk-to");
			Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 40) {
					log("Getting berrys");
					Gettingberrys();
				}
			}
		}
	}

	private void Gettingberrys() throws InterruptedException {
		int flag = 0;
		GroundItem potion = getGroundItems().closest("Cadava potion");
		if(!inventory.contains("Cadava potion") && potion != null) {
				log("Wopsee Potion on ground");
				potion.interact("Pick up");
			}
		if (!Areas.Cadavaberrys.contains(myPlayer())) {
			log("Walking to Berrys");
			walking.webWalk(areas.Cadavaberrys());
			Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (inventory.getAmount("Cadava berries") <= 1) {
			if (Areas.Cadavaberrys.contains(myPlayer())) {
				log("Getting berrys");
				RS2Object berrys = getObjects().closest("Cadava bush");
				if (berrys != null) {
					berrys.interact("Pick-from");
					sleep(random(2500, 3000));
					Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
					if(flag == 3) {
						flag = 0;
						worlds.hopToF2PWorld();
					}
					if (!inventory.contains("Cadava bush")) {
						flag++;
						log("Walking to Berrys & relocating my self");
						walking.webWalk(areas.Cadavaberrys());
						Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
					}
				}
			}
		} else {
			GoingtoShop();
		}
	}

	private void GoingtoShop() throws InterruptedException {
		if (inventory.getAmount("Cadava berries") >= 1) {
			if (!Areas.Potionshop.contains(myPlayer())) {
				walking.webWalk(areas.Potionshop());
				Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			NPC Aptekkari = getNpcs().closest("Apothecary");
			String[] Firsttalk = { "Talk about something else.", "Talk about Romeo & Juliet." };
			if (Aptekkari != null) {
				Aptekkari.interact("Talk-to");
				Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
				if (getDialogues().inDialogue()) {
					dialogues.completeDialogue(Firsttalk);
					Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
					if (getConfigs().get(144) == 50 && inventory.contains("Cadava potion")) {
						log("Speaking to Juliet");
						walkingtoJuliettewithpotion();
					}
				}
			}
		}
	}

	private void walkingtoJuliettewithpotion() throws InterruptedException {
		if (!Areas.JuletBalconybig.contains(myPlayer())) {
			log("Walking to Juliet balcony");
			walking.webWalk(areas.JuletBalcony());
			Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.JuletBalconybig.contains(myPlayer())) {
			log("Stating to talk");
			TalkingtoJulietonewithpotion();
		}
	}

	private void TalkingtoJulietonewithpotion() throws InterruptedException {
		NPC Juliette = getNpcs().closest("Juliet");
		String[] Firsttalk = { "" };
		if (Juliette != null) {
			Juliette.interact("Talk-to");
			Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 60) {
					log("Speaking to Romeo");
					Walkingtovarrockmid();
				}
			}
		}

	}

	private void StartQuestFromromeo() throws InterruptedException {
		NPC Romeo = getNpcs().closest("Romeo");
		String[] Firsttalk = { "Perhaps I could help to find her for you?", "Yes, ok, I'll let her know.",
				"Ok, thanks." };
		if (Romeo != null) {
			Romeo.interact("Talk-to");
			Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 10) {
					log("Speaking to Juliet");
					walkingtoJuliette();
				}
			}
		}
	}

	private void walkingtoJuliette() throws InterruptedException {
		if (!Areas.JuletBalconybig.contains(myPlayer())) {
			log("Walking to Juliet balcony");
			walking.webWalk(areas.JuletBalcony());
			Core.Support.Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.JuletBalconybig.contains(myPlayer())) {
			log("Stating to talk");
			TalkingtoJulietone();
		}
	}

	private void TalkingtoJulietone() throws InterruptedException {
		NPC Juliette = getNpcs().closest("Juliet");
		String[] Firsttalk = { "" };
		if (Juliette != null) {
			Juliette.interact("Talk-to");
			Core.Support.Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Core.Support.Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 20) {
					log("Speaking to Romeo");
					Walkingtovarrockmid();
				}
			}
		}

	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		if (getQuests().isComplete(Quest.ROMEO_JULIET)) {
			//return -10;
			stop();
		}
		Checkup();

		return random(1000); // The amount of time in milliseconds before the loop starts over

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

}