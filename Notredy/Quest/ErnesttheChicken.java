package Notredy.Quest;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Areas;
import Core.Support.Sleep;

import java.awt.*;

public class ErnesttheChicken extends Script {
	Area Mansion = new Area(3079, 3391, 3137, 3322);
	public static String[] VeronicaTalk1 = { "Aha, sounds like a quest. I'll help." };
	Area Veronica = new Area(3108, 3330, 3112, 3328);
	Area Frontbigdoors = new Area(3106, 3353, 3111, 3352);
	Area Poison = new Area(3097, 3366, 3099, 3365);
	Area MansioninsideUP = new Area(3118, 3373, 3098, 3354).setPlane(1);
	Area Mansioninside = new Area(new int[][] { { 3091, 3354 }, { 3127, 3354 }, { 3127, 3361 }, { 3120, 3361 },
			{ 3120, 3374 }, { 3097, 3374 }, { 3097, 3364 }, { 3091, 3364 }, { 3091, 3354 } });
	Area Fishfood = new Area(3109, 3360, 3108, 3355).setPlane(1);
	Area Bigstartsup = new Area(3107, 3360, 3109, 3359);
	Area Bigstartdown = new Area(3107, 3366, 3110, 3367).setPlane(1);
	Area Spade = new Area(3120, 3360, 3122, 3357);
	Area Digspot = new Area(3084, 3359, 3086, 3362);
	Area Compost = new Area(3081, 3365, 3089, 3356);
	Area Bookshelf = new Area(3097, 3362, 3099, 3356);
	Area Rubberroom = new Area(3107, 3368, 3105, 3366);
	Area Celler = new Area(3086, 9770, 3127, 9741);
	Area LeverA = new Area(3108, 9745, 3108, 9745);
	Area LeverB = new Area(3118, 9752, 3118, 9752);
	Area Door1 = new Area(3108, 9757, 3108, 9757);
	Area Door2 = new Area(3105, 9761, 3106, 9761);
	Area LeverD = new Area(3108, 9767, 3108, 9767);
	Area LeverC = new Area(3112, 9760, 3112, 9760);
	Area Door3 = new Area(3102, 9759, 3102, 9759);
	Area Door31 = new Area(3102, 9757, 3102, 9757);
	Area Door4 = new Area(3101, 9760, 3101, 9761);
	Area Door5 = new Area(3097, 9762, 3097, 9762);
	Area LeverE = new Area(3097, 9767, 3097, 9767);
	Area LeverF = new Area(3096, 9765, 3096, 9765);
	Area Door6 = new Area(3099, 9765, 3099, 9765);
	Area Door7 = new Area(3104, 9765, 3104, 9765);
	Area Door71 = new Area(3106, 9765, 3106, 9765);
	Area Door61 = new Area(3101, 9765, 3101, 9765);
	Area Door8 = new Area(3102, 9764, 3102, 9764);
	Area Door9 = new Area(3101, 9755, 3101, 9755);
	Area OilcanPoint = new Area(3093, 9755, 3093, 9755);
	Area Door91 = new Area(3099, 9755, 3099, 9755);
	Area LadderUp = new Area(3117, 9753, 3117, 9753);
	Area Mansionlever = new Area(3096, 3357, 3096, 3357);
	Area Profesor = new Area(3108, 3368, 3112, 3362).setPlane(2);
	Area ErnestProfroombig = new Area(3112, 3370, 3104, 3362).setPlane(2);

	public static String[] Profesortalk2 = {};
	Area Phountain = new Area(3089, 3336, 3089, 3336);
	Area Mansionoutside = new Area(3084, 3360, 3094, 3330);
	Area thirdfloor = new Area(3105, 3364, 3105, 3364).setPlane(1);
	Area SpaderoomDoor = new Area(3123, 3360, 3123, 3360);
	int loopcheck = random(5, 10);
	Areas areas = new Areas();

	@Override
	public void onStart() throws InterruptedException {
		if (getQuests().isComplete(Quest.ERNEST_THE_CHICKEN)) {
			log("Completed");
			onExit();
		}
		log("Ernest the Chicken Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		if (!tabs.isOpen(Tab.INVENTORY)) {
			log("Inventory not open");
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 300, 5000);
		}
		while (settings.areRoofsEnabled() == false) {
			log("Roof off");
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}

		if (inventory.getEmptySlots() < 10) {
			Bankingforstart();
		}
	}

	private void Bankingforstart() throws InterruptedException {
		if (!Areas.DraynorBank.contains(myPlayer())) {
			log("Walking Lb bank");
			walking.webWalk(areas.DraynorBank);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (!inventory.isEmpty()) {
			if (!bank.isOpen()) {
				log("Open bank for Bank for start");
				bank.open();
				Sleep.waitCondition(() -> bank.isOpen(), 3000);
			}
			if (bank.isOpen()) {
				if (!inventory.isEmpty()) {
					bank.depositAll();
					Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
				}
			}
			if (!inventory.isEmpty()) {
				bank.depositAll();
				Sleep.waitCondition(() -> inventory.isEmpty(), 500, 3000);
			}
			bank.close();
			Sleep.waitCondition(() -> !bank.isOpen(), 500, 3000);
		}
	}

	private void CheckUp() throws InterruptedException {
		log("Loop checkup");

		if (getConfigs().get(32) == 0) {
			log("Starting");
			log("Talk to Veronica");
			WalkingtoVeronica();
		} else if (Mansioninside.contains(myPlayer()) && !inventory.contains("Poisoned fish food")
				&& !inventory.contains("Poison") && !inventory.contains("Key")
				&& !inventory.contains("Pressure gauge")) {
			log("Getting Poison");
			Poison();
		} else if ((Mansioninside.contains(myPlayer()) || MansioninsideUP.contains(myPlayer()))
				&& !inventory.contains("Poisoned fish food") && !inventory.contains("Key")
				&& !inventory.contains("Pressure gauge") && inventory.contains("Poison")
				&& !inventory.contains("Fish food")) {
			log("Getting fish food");
			Fishfood();
		} else if (inventory.contains("Poison") && inventory.contains("Fish food")
				&& !inventory.contains("Poisoned fish food") && !inventory.contains("Spade")) {
			log("Getspade");
			Spade();
		} else if (inventory.contains("Poison") && inventory.contains("Fish food")
				&& !inventory.contains("Poisoned fish food") && inventory.contains("Spade")) {
			log("Getting key");
			Gettingkey();
		} else if (inventory.contains("Key") && !inventory.contains("Pressure gauge") && inventory.contains("Spade")) {
			log("Getting gauge");
			Gettinggauge();
		} else if (inventory.contains("Key") && inventory.contains("Pressure gauge")
				&& !inventory.contains("Rubber tube")) {
			log("Get rubbertube");
			Rubbertube();
		} else if (!Celler.contains(myPlayer()) && inventory.contains("Key") && !inventory.contains("Oil can")
				&& inventory.contains("Rubber tube") && inventory.contains("Pressure gauge")) {
			log("Walking to celler");
			Celler();
		} else if (Celler.contains(myPlayer()) && !inventory.contains("Oil can") && inventory.contains("Key")
				&& inventory.contains("Rubber tube")) {
			log("In celler");
			Cellerpart();
		} else if ((Mansion.contains(myPlayer()) || thirdfloor.contains(myPlayer())
				|| MansioninsideUP.contains(myPlayer())) && inventory.contains("Oil can") && inventory.contains("Key")
				&& inventory.contains("Rubber tube")) {
			log("Going to Doctor");
			Goingtodoctor();
		} else if (ErnestProfroombig.contains(myPlayer()) && inventory.contains("Oil can") && inventory.contains("Key")
				&& inventory.contains("Rubber tube") && inventory.contains("Pressure gauge")) {
			log("Talk to prof agen");
			Talkingprof();
		} else if (getConfigs().get(32) == 1 && !ErnestProfroombig.contains(myPlayer())
				&& !thirdfloor.contains(myPlayer()) && !Celler.contains(myPlayer())
				&& !Mansioninside.contains(myPlayer()) && !MansioninsideUP.contains(myPlayer())) {
			log("Opening large front door");
			WalkingFrontdoors();
		} else if (inventory.contains("Coins")) {
			log("Quest complete");
			Walkingout();
		}
	}

	private void Gettingkey() throws InterruptedException {
		if (!Digspot.contains(myPlayer())) {
			walking.webWalk(Digspot);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		RS2Object Compost = getObjects().closest("Compost heap");
		if (Digspot.contains(myPlayer()) && !myPlayer().isMoving()) {
			Compost.interact("Search");
			Sleep.waitCondition(() -> inventory.contains("Key"), 500, 5000);
			Gettinggauge();
		}
		
	}

	/*
	 * 32:1 start ernest 32:3 Give items 101:4 Completed
	 */
	private void Walkingout() throws InterruptedException {
		RS2Object Door = getObjects().closest("Door");
		RS2Object StairsDown = getObjects().closest("Staircase");
		StairsDown.interact("Climb-down");
		sleep(random(1000, 2000));
		walking.webWalk(Bigstartdown);
		sleep(random(1000, 2000));
		StairsDown.interact("Climb-down");
		sleep(random(1000, 2000));
		if (!SpaderoomDoor.contains(myPlayer())) {
			walking.webWalk(SpaderoomDoor);
			sleep(random(2500, 3200));
		}
		Door.interact("Open");
		sleep(random(1000, 2000));
		Bankingforstart();

	}

	private void Talkingprof() throws InterruptedException {
		NPC ProfesorGuy = getNpcs().closest("Professor Oddenstein");
		if (ErnestProfroombig.contains(myPlayer())) {
			if (ProfesorGuy.isOnScreen() && ErnestProfroombig.contains(myPlayer()) && Veronica != null) {
				ProfesorGuy.interact("Talk-to");
				Sleep.waitCondition(() -> dialogues.inDialogue(), 500, 5000);
			}
			if (dialogues.inDialogue()) {
				getDialogues().completeDialogue(Profesortalk2);
				sleep(random(1000, 1500));
			}
		}
		if (inventory.contains("Coins")) {
			log("Quest complete");
			Walkingout();
		}
	}

	private void Goingtodoctor() throws InterruptedException {
		if (!Bigstartsup.contains(myPlayer())) {
			walking.webWalk(Bigstartsup);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		RS2Object Stairsup1 = getObjects().closest("Staircase");
		if (Mansioninside.contains(myPlayer())) {
			if (Bigstartsup.contains(myPlayer())) {
				Stairsup1.interact("Climb-up");
				Sleep.waitCondition(() -> Bigstartdown.contains(myPlayer()), 200, 5000);
			}
		}
		if (!thirdfloor.contains(myPlayer())) {
			walking.webWalk(thirdfloor);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		RS2Object Stairsup = getObjects().closest("Staircase");

		if (Stairsup.hasAction("Climb-up")) {
			Stairsup.interact("Climb-up");
			sleep(random(1000, 1500));
		}
		if (!Profesor.contains(myPlayer())) {
			walking.webWalk(Profesor);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 200, 5000);
		}
		if (Profesor.contains(myPlayer())) {
			NPC ProfesorGuy = getNpcs().closest("Professor Oddenstein");
			if (ProfesorGuy.isOnScreen() && Profesor.contains(myPlayer()) && ProfesorGuy != null) {
				ProfesorGuy.interact("Talk-to");
				Sleep.waitCondition(() -> getDialogues().inDialogue(), 200, 5000);
				String[] Profesortalk1 = { "I'm looking for a guy called Ernest.", "Change him back this instant!" };
				if (getDialogues().completeDialogue(Profesortalk1)) {
					log("Chosing dialog for Continue 1");
					sleep(random(1000, 1500));
				}
			}
		}
	}
	// "I'm glad Veronica diden't actually get engaged to a chicken."

	private void Cellerpart() throws InterruptedException {
		RS2Object LeverA1 = getObjects().closest("Lever A");
		walking.webWalk(LeverA);
		sleep(random(2500, 3200));

		log("Lever a");
		LeverA1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(LeverB);
		sleep(random(2500, 3200));

		log("Lever b");
		RS2Object LeverB1 = getObjects().closest("Lever B");
		LeverB1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(Door1);
		sleep(random(2500, 3200));
		RS2Object Door11 = getObjects().closest("Door");

		Door11.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(LeverC);
		sleep(random(2500, 3200));
		RS2Object LeverC1 = getObjects().closest("Lever C");
		log("Lever c");
		LeverC1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(LeverD);
		sleep(random(2500, 3200));
		RS2Object LeverD1 = getObjects().closest("Lever D");
		log("Lever d");
		LeverD1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(LeverC);
		sleep(random(2500, 3200));

		log("Lever c");
		LeverC1.interact("Pull");
		sleep(random(2500, 3000));
		///////////////////////////////

		walking.webWalk(Door2);
		sleep(random(2500, 3200));
		RS2Object Door12 = getObjects().closest("Door");
		Door12.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door3);
		sleep(random(2500, 3200));
		RS2Object Door13 = getObjects().closest("Door");
		Door13.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(LeverA);
		sleep(random(2500, 3200));

		log("Lever a");
		LeverA1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(LeverB);
		sleep(random(2500, 3200));

		log("Lever b");
		LeverB1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(Door31);
		sleep(random(2500, 3200));
		RS2Object Door131 = getObjects().closest("Door");
		Door131.interact("Open");
		sleep(random(2500, 3000));
		///////////////////
		walking.webWalk(Door4);
		sleep(random(2500, 3200));

		RS2Object Door14 = getObjects().closest("Door");
		Door14.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door5);
		sleep(random(2500, 3200));
		RS2Object Door15 = getObjects().closest("Door");
		Door15.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(LeverE);
		sleep(random(2500, 3200));
		RS2Object LeverE1 = getObjects().closest("Lever E");
		log("Lever e");
		LeverE1.interact("Pull");

		log("Lever f");
		walking.webWalk(LeverF);
		sleep(random(2500, 3200));
		RS2Object LeverF1 = getObjects().closest("Lever F");
		log("Lever f");
		LeverF1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(Door6);
		sleep(random(2500, 3200));
		RS2Object Door166 = getObjects().closest("Door");
		Door166.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door7);
		Sleep.waitCondition(() -> !myPlayer().isMoving(), 2500);
		RS2Object Door177 = getObjects().closest("Door");
		Door177.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(LeverC);
		sleep(random(2500, 3200));

		log("Lever c");
		LeverC1.interact("Pull");
		sleep(random(2500, 3000));

		walking.webWalk(Door71);
		sleep(random(2500, 3200));
		RS2Object Door17 = getObjects().closest("Door");
		Door17.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door61);
		sleep(random(2500, 3200));
		RS2Object Door16 = getObjects().closest("Door");
		Door16.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(LeverE);
		sleep(random(2500, 3200));
		RS2Object LeverE11 = getObjects().closest("Lever E");
		log("Lever e");
		LeverE11.interact("Pull");
		sleep(random(2500, 3000));
		//////////
		walking.webWalk(Door61);
		sleep(random(2500, 3200));
		RS2Object Door161 = getObjects().closest("Door");
		Door161.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door8);
		sleep(random(2500, 3200));
		RS2Object Door18 = getObjects().closest("Door");
		Door18.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door3);
		sleep(random(2500, 3200));

		RS2Object Door1311 = getObjects().closest("Door");
		Door1311.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(Door9);
		sleep(random(2500, 3200));
		RS2Object Door19 = getObjects().closest("Door");
		Door19.interact("Open");
		sleep(random(2500, 3000));

		walking.webWalk(OilcanPoint);
		sleep(random(2500, 3200));

		if (OilcanPoint.contains(myPlayer())) {
			GroundItem Oilcan = getGroundItems().closest("Oil can");
			Oilcan.interact("Take");
			Sleep.waitCondition(() -> inventory.contains("Oil can"), 200, 9000);
		}
		walking.webWalk(Door91);
		sleep(random(2500, 3200));
		RS2Object Door191 = getObjects().closest("Door");

		Door191.interact("Open");
		sleep(random(2500, 3000));

		if (inventory.contains("Oil can")) {
			if (!LeverA.contains(myPlayer())) {
				walking.webWalk(LadderUp);
				sleep(random(2500, 3200));
			}
		}
		if (LadderUp.contains(myPlayer())) {
			RS2Object Ladder = getObjects().closest("Ladder");
			Ladder.interact("Climb-up");
			sleep(random(2500, 3000));
		}

		if (!Mansionlever.contains(myPlayer())) {
			walking.webWalk(Mansionlever);
			sleep(random(2500, 3200));
		}
		if (Mansionlever.contains(myPlayer())) {
			RS2Object LeverM = getObjects().closest("Lever");
			LeverM.interact("Pull");
			sleep(random(2000, 4000));
		}

	}

	private void Celler() throws InterruptedException {
		RS2Object BookshelfDoor = getObjects().closest("Bookcase");
		RS2Object Ladder = getObjects().closest("Ladder");
		if (!Bookshelf.contains(myPlayer())) {
			walking.webWalk(Bookshelf);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		BookshelfDoor.interact("Search");
		sleep(random(1500, 3000));
		Ladder.interact("Climb-down");
		Sleep.waitCondition(() -> Celler.contains(myPlayer()), 200, 5000);
	}

	private void Rubbertube() throws InterruptedException {
		GroundItem RubberhoseG = getGroundItems().closest("Rubber tube");
		RS2Object Frontdoors = getObjects().closest("Large door");
		if (inventory.contains("Key")) {
			log("Walking Front Doors");
			log("Rubbertube part");
			if (!Frontbigdoors.contains(myPlayer()) && !Mansioninside.contains(myPlayer())) {
				walking.webWalk(Frontbigdoors);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			if (Frontbigdoors.contains(myPlayer()) && Frontdoors != null) {
				Frontdoors.interact("Open");
				sleep(random(1052, 2000));
			}
			if (!Rubberroom.contains(myPlayer())) {
				walking.webWalk(Rubberroom);
				Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			}
			if (!inventory.contains("Rubber tube")) {
				if (RubberhoseG != null) {
					RS2Object RubberroomDoor = getObjects().closest("Door");
					camera.toTop();
					sleep(random(300, 500));
					settings.setRunning(true);
					sleep(random(300, 500));
					RubberroomDoor.interact("Open");
					sleep(random(2052, 2500));
					RubberhoseG.interact("Take");
					sleep(random(400, 800));
					Sleep.waitCondition(() -> inventory.contains("Rubber tube"),200,6000);
					RS2Object RubberroomDoorTwo = getObjects().closest("Door");
					RubberroomDoorTwo.interact("Open");
					sleep(random(1652, 2300));
				}
				if (RubberhoseG == null) {
					Sleep.waitCondition(() -> RubberhoseG != null, 500, 50000);
				}
			}
		}
	}

	private void Spade() throws InterruptedException {
		RS2Object Door = getObjects().closest("Door");
		GroundItem SpadeGround = getGroundItems().closest("Spade");

		if (MansioninsideUP.contains(myPlayer())) {
			RS2Object StartsDownLobby = getObjects().closest("Staircase");
			walking.webWalk(Bigstartdown);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			StartsDownLobby.interact("Climb-down");
			sleep(random(300, 500));
		}
		if (!Spade.contains(myPlayer())) {
			walking.webWalk(Spade);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		camera.toEntity(SpadeGround);
		sleep(random(500, 1000));
		if (!inventory.contains("Spade")) {
			SpadeGround.interact("Take");
			Sleep.waitCondition(() -> inventory.contains("Spade"), 500, 5000);
		}
		Area SpadedroomBig = new Area(3120, 3360, 3126, 3354);
		if(inventory.contains("Spade") && !SpadedroomBig.contains(myPlayer())) {
			Spade();
		}
		if (!SpaderoomDoor.contains(myPlayer())) {
			walking.webWalk(SpaderoomDoor);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			Door.interact("Open");
			sleep(random(500, 1000));
		}
	}

	private void Gettinggauge() throws InterruptedException {
		RS2Object Fhountain = getObjects().closest("Fountain");
		if (!Phountain.contains(myPlayer())) {
			walking.webWalk(Phountain);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if(Phountain.contains(myPlayer())) {
		inventory.interact("Use", "Poison");
		Sleep.waitCondition(() -> inventory.isItemSelected(), 200, 5000);
		inventory.interactWithNameThatContains("Use", "Fish food");
		Sleep.waitCondition(() -> inventory.contains("Poisoned fish food"), 200, 5000);
		inventory.interact("Use", "Poisoned fish food");
		Sleep.waitCondition(() -> inventory.isItemSelected(), 200, 5000);
		Fhountain.interact("Use");
		sleep(random(5500, 6821));
		Fhountain.interact("Search");
		Sleep.waitCondition(() -> getDialogues().isPendingContinuation(), 200, 5000);}
		if (getDialogues().isPendingContinuation()) {
			log("Continue");
			dialogues.completeDialogue("");
			Sleep.waitCondition(() -> inventory.contains("Pressure gauge"), 200, 5000);
		}
		Rubbertube();
	}

	private void Fishfood() throws InterruptedException {
		GroundItem fishFoodBox = groundItems.closest("Fish food");
		RS2Object StartsupLobby = getObjects().closest("Staircase");

		if (!Bigstartsup.contains(myPlayer()) && !MansioninsideUP.contains(myPlayer())) {
			walking.webWalk(Bigstartsup.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			StartsupLobby.interact("Climb-up");
			Sleep.waitCondition(() -> MansioninsideUP.contains(myPlayer()), 500, 5000);
		}
		if (!Fishfood.contains(myPlayer())) {
			walking.webWalk(Fishfood.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Fishfood.contains(myPlayer())) {
			log("Taking fish food");
			if (fishFoodBox != null) {
				fishFoodBox.interact("Take");
				Sleep.waitCondition(() -> inventory.contains("Fish food"), 500, 5000);
			} else if (fishFoodBox == null) {
				log("Waiting for fish food");
				Sleep.waitCondition(() -> fishFoodBox != null, 500, 5000);
			}
		}
	}

	private void Poison() throws InterruptedException {
		GroundItem Poisonbottle = getGroundItems().closest("Poison");
		log("Walking poison");
		if (!Poison.contains(myPlayer())) {
			walking.webWalk(Poison);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Poisonbottle != null) {
			Poisonbottle.interact("Take");
			Sleep.waitCondition(() -> inventory.contains("Poison"), 500, 5000);
		} else if (!inventory.contains("Poison")) {
			log("Waiting for poison");
			Sleep.waitCondition(() -> Poisonbottle != null, 500, 50000);
		}
	}

	private void TalkingtoVeronica() throws InterruptedException {
		NPC Veronica = getNpcs().closest("Veronica");
		if (Veronica.isOnScreen() && Veronica != null) {
			Veronica.interact("Talk-to");
			Sleep.waitCondition(() -> dialogues.inDialogue(), 500, 5000);
			if (getDialogues().completeDialogue(VeronicaTalk1)) {
				log("Chosing dialog for Continue 1");
				WalkingFrontdoors();
			}
		}
	}

	private void WalkingFrontdoors() throws InterruptedException {
		RS2Object Frontdoors = getObjects().closest("Large door");
		log("Walking Front Doors");
		log("Walking Front door part");
		if (!Frontbigdoors.contains(myPlayer())) {
			walking.webWalk(Frontbigdoors);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
		}
		if (Frontbigdoors.contains(myPlayer()) && Frontdoors != null) {
			Frontdoors.interact("Open");
			sleep(random(1052, 1500));
		}
	}

	private void WalkingtoVeronica() throws InterruptedException {
		if (!Veronica.contains(myPlayer())) {
			walking.webWalk(Veronica.getRandomPosition());
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500, 5000);
			TalkingtoVeronica();
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		int Counter = 0;
		if (Counter == loopcheck) {
			int loopcheck = random(5, 10);
			if (dialogues.isPendingContinuation()) {
				log("Stuck");
				dialogues.clickContinue();
			}
			Counter = 0;
		}
		CheckUp();

		return random(1700); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onExit() {
		log("Good Bye");
		stop();

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {

		// This is where you will put your code for paint(s)

	}
}