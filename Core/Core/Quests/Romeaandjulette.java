package Core.Quests;

import java.awt.*;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

public class Romeaandjulette extends Skeleton {
	Areas areas = new Areas();
	Time time = new Time();
	int Bushempty = 0;
	int berryamount = random(2, 4);

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		log("Core Romea and Julette Has started");
		if (getConfigs().get(144) == 100) {
			onLoop();
		}
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if (dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
			sleep(random(800, 1200));
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
				sleep(random(500, 1000));
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
				bank.depositAll();
				sleep(random(1500, 2200));

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
			sleep(random(800, 1200));
		}

		if (!inventory.isEmpty() && !inventory.contains("Message") && !inventory.contains("Cadava potion")
				&& !inventory.contains("Cadava berries")) {
			log("Empty your pockets");
			Banking();
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
		} else if (getConfigs().get(144) == 40 && inventory.getAmount("Cadava berries") <= 1) {
			log("Getting berrys");
			Gettingberrys();
		} else if (getConfigs().get(144) >= 40 && inventory.getAmount("Cadava berries") >= 1
				&& getConfigs().get(144) < 60) {
			log("Going shop");
			GoingtoShop();
		} else if (getConfigs().get(144) >= 40 && !inventory.contains("Cadava potion") && getConfigs().get(144) < 60) {
			log("Lost potion???");
			Gettingberrys();

		} else if (getConfigs().get(144) == 60) {
			log("Juliet died");
			Walkingtovarrockmid();
		} else if (getConfigs().get(144) == 100) {
			log("Completed");
			onLoop();
		}
		/*
		 * Romeo and juliette 144:10 start 144:20 julette letter 144;30 ask priest
		 * 144:40 priest talked 144:50 potion Potion 144:60 juliet died 144:100
		 * Completed
		 */

	}

	private void Walkingtovarrockmid() throws InterruptedException {
		if (!Areas.VarrockmidBig.contains(myPlayer())) {
			log("Walking to Romeo");
			walking.webWalk(areas.VarrockMid());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Areas.VarrockmidBig.contains(myPlayer())) {
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
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
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
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
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
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
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
		if (!Areas.Varrockpriest.contains(myPlayer())) {
			walking.webWalk(areas.Varrockpriest());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Priest != null) {
			Priest.interact("Talk-to");
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 40) {
					log("Getting berrys");
					Gettingberrys();
				}
			}
		}
	}

	private void Gettingberrys() throws InterruptedException {
		int randomberryamount = random(1, 2);
		GroundItem potion = getGroundItems().closest("Cadava potion");
		if (!inventory.contains("Cadava potion") && potion != null) {
			log("Wopsee Potion on ground");
			potion.interact("Pick up");
		}
		if (!Areas.Cadavaberrys.contains(myPlayer())) {
			log("Walking to Berrys");
			walking.webWalk(areas.Cadavaberrys());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (inventory.getAmount("Cadava berries") < randomberryamount) {
			if (Areas.Cadavaberrys.contains(myPlayer())) {
				log("Getting berrys");
				RS2Object berries = getObjects().closest("Cadava bush");
				if (berries.getModelIds().length == 2) {
					berries.interact("Pick-from");
					sleep(random(1500, 2200));
					Sleep.waitCondition(() -> !myPlayer().isMoving() && !myPlayer().isAnimating(), 500, 5000);
				}
				if (inventory.contains("Cadava berries")) {
					GoingtoShop();
				}
			}
		}
	}

	private void GoingtoShop() throws InterruptedException {
		if (inventory.getAmount("Cadava berries") >= 1) {
			if (!Areas.Potionshop.contains(myPlayer())) {
				walking.webWalk(areas.Potionshop());
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			NPC Aptekkari = getNpcs().closest("Apothecary");
			String[] Firsttalk = { "Talk about something else.", "Talk about Romeo & Juliet." };
			if (Aptekkari != null) {
				Aptekkari.interact("Talk-to");
				Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
				if (getDialogues().inDialogue()) {
					dialogues.completeDialogue(Firsttalk);
					Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
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
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
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
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
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
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
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
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
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
			Sleep.waitCondition(() -> getDialogues().inDialogue(), 500, 5000);
			if (getDialogues().inDialogue()) {
				dialogues.completeDialogue(Firsttalk);
				Sleep.waitCondition(() -> !getDialogues().inDialogue(), 500, 5000);
				if (getConfigs().get(144) == 20) {
					log("Speaking to Romeo");
					Walkingtovarrockmid();
				}
			}
		}

	}

	@Override
	public int onLoop() {
		time.Timerun();
		if (getConfigs().get(144) == 100) {
			log("Completed");
			return -10;
		}
		try {
			Checkup();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 400+random(100); }
	

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