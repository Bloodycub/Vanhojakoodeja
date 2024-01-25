package onebyone.Notredy;
import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;


public class ErnesttheChicken extends Script {
Area Mansion = new Area(3079,3391,3137,3322);
public static String[] VeronicaTalk1 = { "Aha, sounds like a quest. I'll help."};
Area Veronica = new Area(3108, 3330, 3112, 3328);
Area Frontbigdoors = new Area(3106, 3353, 3111, 3352);
Area Poison = new Area(3097, 3366, 3099, 3365);
Area MansioninsideUP = new Area(3118, 3373, 3098, 3354).setPlane(1);
Area Mansioninside = new Area(3127, 3354, 3096, 3373);
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
Area Door2 = new Area(3106, 9760, 3106, 9760);
Area LeverD = new Area(3108, 9767, 3108, 9767);
Area LeverC = new Area(3112, 9760, 3112, 9760);
Area Door3 = new Area(3102, 9759, 3102, 9759);
Area Door31 = new Area(3102, 9757, 3102, 9757);
Area Door4 = new Area(3101, 9760, 3101, 9760);
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
public static String[] Profesortalk1 = { "I'm looking for a guy called Ernest." ,"I'm glad Veronica diden't actually get engaged to a chicken."};
public static String[] Profesortalk2 = {};
Area Phountain = new Area(3089, 3336, 3089, 3336);
Area Mansionoutside = new Area(3084, 3360, 3094, 3330);
Area thirdfloor = new Area(3105, 3364, 3105, 3364).setPlane(1);
Area SpaderoomDoor= new Area(3123, 3360, 3123, 3360);


    @Override
    public void onStart() throws InterruptedException {
    	log("Startup");
    	if(settings.areRoofsEnabled() == false) {
    		getKeyboard().typeString("::toggleroofs");
    		sleep(random(500,700));
    	if(getQuests().isComplete(Quest.ERNEST_THE_CHICKEN)){
    		log("Completed");
    		onExit();
    	}
    	if(!inventory.isEmpty() && !Banks.DRAYNOR.contains(myPlayer())) {
    		Bankingforstart();
    	}
    	CheckUp();
    }
    }
    private void Bankingforstart() throws InterruptedException {
    	walking.webWalk(Banks.DRAYNOR);
		sleep(1000);
		bank.open();
		sleep(random(400,700));
		bank.depositAll();
		sleep(random(400,700));
		bank.close();		
	}

	private void CheckUp() throws InterruptedException {
    	if(!Mansion.contains(myPlayer()) && inventory.isEmpty()) {
    		log("Talk to Veronica");
    		WalkingtoVeronica();
    	}else if (inventory.contains("Coins")) {
    		log("Quest complete");
    		Walkingout();
    	}else if (Frontbigdoors.contains(myPlayer()) && inventory.isEmpty()){
    		log("Opening large front door");
    		WalkingFrontdoors();
    	}else if (Profesor.contains(myPlayer()) && inventory.contains("Oil can") && inventory.contains("Key") && inventory.contains("Poison") && inventory.contains("Fish food") && inventory.contains("Rubber tube") && inventory.contains("Pressure gauge")){
    		log("Talk to prof agen");
    		Talkingprof();
    	}else if(Veronica.contains(myPlayer()) && inventory.isEmpty()) {
    		log("Talking to Veronica");
    		TalkingtoVeronica();
    	}else if((Mansioninside.contains(myPlayer()) && inventory.contains("Poison") && !inventory.contains("Fish food"))){
    		log("Getting fish food");
    		Fishfood();
    	}else if(Mansioninside.contains(myPlayer()) && !inventory.contains("Poison") && !MansioninsideUP.contains(myPlayer())) {
    		log("Getting Poison");
    		Poison();
    	}else if(inventory.contains("Poison") && inventory.contains("Fish food") && !inventory.contains("Spade")){
    		log("Getspade");
    		Spade();
    	}else if(Mansionoutside.contains(myPlayer()) &&inventory.contains("Key") && inventory.contains("Poison") && inventory.contains("Fish food") && !inventory.contains("Pressure gauge")){
        	log("Getting gauge");
        	Gettinggauge();
    	}else if(inventory.contains("Key") && inventory.contains("Poison") && inventory.contains("Fish food")){
    		log("Get rubbertube");
    		Rubbertube();
    	}else if(inventory.contains("Key") && inventory.contains("Poison") && inventory.contains("Fish food") && inventory.contains("Rubber tube")) {
    		log("Walking to celler");
    		Celler();
    	}else if(Celler.contains(myPlayer()) && !inventory.contains("Oil can") && inventory.contains("Key") && inventory.contains("Poison") && inventory.contains("Fish food") && inventory.contains("Rubber tube")) {
    		log("In celler");
    		Cellerpart();
    	}else if(Mansion.contains(myPlayer()) && inventory.contains("Oil can") && inventory.contains("Key") && inventory.contains("Poison") && inventory.contains("Fish food") && inventory.contains("Rubber tube")) {
    		log("Going to Doctor");
    		Goingtodoctor();
    	}
    		
    	
    	
    	
    	
    	
	}

	private void Walkingout() throws InterruptedException {
		RS2Object Door= getObjects().closest("Door");
		RS2Object StairsDown= getObjects().closest("Staircase");
		StairsDown.interact("Climb-down");
		sleep(2000);
		walking.webWalk(Bigstartdown);
		sleep(1000);
		StairsDown.interact("Climb-down");
		sleep(1000);
		walking.webWalk(SpaderoomDoor);
		sleep(1000);
		Door.interact("Open");
		sleep(random(1599,1923));
		Bankingforstart();
		
		
	}

	private void Talkingprof() throws InterruptedException {
		NPC ProfesorGuy = getNpcs().closest("Professor Oddenstein");
			if(Profesor.contains(myPlayer()))
			if(ProfesorGuy.isOnScreen() && Profesor.contains (myPlayer())  && Veronica !=null){
				ProfesorGuy.interact("Talk-to");
				 if (getDialogues().isPendingContinuation()) {
			        	log("Continue");
						getDialogues().clickContinue();
						sleep(Script.random(800, 1200));
			        		} else if(getDialogues().completeDialogue(Profesortalk2)) {
			        			log("Chosing dialog for Continue 2");
			        				sleep(Script.random(800, 1200));
					}}		
	}

	private void Goingtodoctor() throws InterruptedException {
		RS2Object Stairsup= getObjects().closest("Staircase");
		NPC ProfesorGuy = getNpcs().closest("Professor Oddenstein");
		walking.webWalk(Bigstartsup);
		sleep(1000);
		if(Bigstartsup.contains(myPlayer())) {
			Stairsup.interact("Climb-up");
			sleep(random(1000,1500));
		}else if (Bigstartdown.contains(myPlayer())) {
			walking.webWalk(thirdfloor);
			sleep(1000);
			if(Stairsup.hasAction("Climb-up")){
				Stairsup.interact("Climb-up");
				sleep(random(1000,1500));}
		walking.webWalk(Profesor);
			sleep(random(1000,1500));
			if(Profesor.contains(myPlayer()))
			if(ProfesorGuy.isOnScreen() && Profesor.contains (myPlayer())  && Veronica !=null){
				ProfesorGuy.interact("Talk-to");
				 if (getDialogues().isPendingContinuation()) {
			        	log("Continue");
						getDialogues().clickContinue();
						sleep(Script.random(800, 1200));
			        		} else if(getDialogues().completeDialogue(Profesortalk1)) {
			        			log("Chosing dialog for Continue 1");
			        				sleep(Script.random(800, 1200));
					}}}
	}

	private void Cellerpart() throws InterruptedException {
		RS2Object LeverA1= getObjects().closest("Lever A");
		RS2Object LeverB1= getObjects().closest("Lever B");
		RS2Object Door11= getObjects().closest("Door");
		RS2Object Door12= getObjects().closest("Door");
		RS2Object LeverC1= getObjects().closest("Lever C");
		RS2Object LeverD1= getObjects().closest("Lever D");
		RS2Object Door13= getObjects().closest("Door");
		RS2Object Door14= getObjects().closest("Door");
		RS2Object Door15= getObjects().closest("Door");
		RS2Object LeverE1= getObjects().closest("Lever E");
		RS2Object LeverF1= getObjects().closest("Lever F");
		RS2Object Door16= getObjects().closest("Door");
		RS2Object Door17= getObjects().closest("Door");
		RS2Object Door18= getObjects().closest("Door");
		RS2Object Door19= getObjects().closest("Door");
		GroundItem Oilcan = getGroundItems().closest("Oil can");
		RS2Object Ladder= getObjects().closest("Ladder");
		RS2Object LeverM= getObjects().closest("Pull");

		walking.webWalk(LeverA);
		sleep(random(600,900));
		if(LeverA.contains(myPlayer())) {
			LeverA1.interact("Pull");
			sleep(random(600,900));}
			
		walking.webWalk(LeverB);
		sleep(random(600,900));
		if(LeverB.contains(myPlayer())) {
			LeverB1.interact("Pull");
			sleep(random(600,900));}
			
		walking.webWalk(Door1);
			sleep(random(600,900));
			if(Door1.contains(myPlayer())) {
				Door11.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(LeverC);
			sleep(random(600,900));
			if(LeverC.contains(myPlayer())) {
				LeverC1.interact("Pull");
				sleep(random(600,900));}	
		
		walking.webWalk(LeverD);
			sleep(random(600,900));
			if(LeverD.contains(myPlayer())) {
				LeverD1.interact("Pull");
				sleep(random(600,900));}	
			
			
		walking.webWalk(Door2);
			sleep(random(600,900));
			if(Door2.contains(myPlayer())) {
				Door12.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(Door3);
			sleep(random(600,900));
			if(Door3.contains(myPlayer())) {
				Door13.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(LeverA);
			sleep(random(600,900));
			if(LeverA.contains(myPlayer())) {
				LeverA1.interact("Pull");
				sleep(random(600,900));}
				
		walking.webWalk(LeverB);
			sleep(random(600,900));
			if(LeverB.contains(myPlayer())) {
				LeverB1.interact("Pull");
				sleep(random(600,900));}	
			
		walking.webWalk(Door31);
			sleep(random(600,900));
			if(Door31.contains(myPlayer())) {
				Door13.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(Door4);
			sleep(random(600,900));
			if(Door4.contains(myPlayer())) {
				Door14.interact("Open");
				sleep(random(600,900));}	
		
		walking.webWalk(Door5);
			sleep(random(600,900));
			if(Door5.contains(myPlayer())) {
				Door15.interact("Open");
				sleep(random(600,900));}	
			
		walking.webWalk(LeverE);
			sleep(random(600,900));
			if(LeverE.contains(myPlayer())) {
				LeverE1.interact("Pull");
				sleep(random(600,900));}
				
		walking.webWalk(LeverF);
			sleep(random(600,900));
			if(LeverF.contains(myPlayer())) {
				LeverF1.interact("Pull");
				sleep(random(600,900));}	
			
		walking.webWalk(Door6);
			sleep(random(600,900));
			if(Door6.contains(myPlayer())) {
				Door16.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(Door7);
			sleep(random(600,900));
			if(Door7.contains(myPlayer())) {
				Door17.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(LeverC);
			sleep(random(600,900));
			if(LeverC.contains(myPlayer())) {
				LeverC1.interact("Pull");
				sleep(random(600,900));}
			
		walking.webWalk(Door71);
			sleep(random(600,900));
			if(Door71.contains(myPlayer())) {
				Door17.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(Door61);
			sleep(random(600,900));
			if(Door61.contains(myPlayer())) {
				Door16.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(LeverE);
			sleep(random(600,900));
			if(LeverE.contains(myPlayer())) {
				LeverE1.interact("Pull");
				sleep(random(600,900));}
			
		walking.webWalk(Door8);
			sleep(random(600,900));
			if(Door8.contains(myPlayer())) {
				Door18.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(Door3);
			sleep(random(600,900));
			if(Door3.contains(myPlayer())) {
				Door13.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(Door9);
			sleep(random(600,900));
			if(Door9.contains(myPlayer())) {
				Door19.interact("Open");
				sleep(random(600,900));}
			
		walking.webWalk(OilcanPoint);
			sleep(random(600,900));
			if(OilcanPoint.contains(myPlayer())) {
				Oilcan.interact("Take");
				sleep(random(600,900));}
			
		walking.webWalk(Door91);
			sleep(random(600,900));
			if(Door91.contains(myPlayer())) {
				Door19.interact("Open");
				sleep(random(600,900));}
			
		if(inventory.contains("Oil can")) {	
		walking.webWalk(LadderUp);
			sleep(random(600,900));
			if(LadderUp.contains(myPlayer())) {
				Ladder.interact("Climb-up");
				sleep(random(600,900));}}
		
		walking.webWalk(Mansionlever);
		sleep(random(600,900));
		if(Mansionlever.contains(myPlayer())) {
			LeverM.interact("Pull");
			sleep(random(2000,4000));}
	
	}

	private void Celler() throws InterruptedException {
		RS2Object BookshelfDoor= getObjects().closest("Bookcase");
		RS2Object Ladder= getObjects().closest("Ladder");
		walking.webWalk(Bookshelf);
		sleep(random(1002,1200));
		BookshelfDoor.interact("Search");
		sleep(random(1002,1200));
		Ladder.interact("Climb-down");
		sleep(random(1002,1200));
		
	}

	private void Rubbertube() throws InterruptedException {
		GroundItem Rubberhose = getGroundItems().closest("Rubber tube");
		RS2Object Frontdoors = getObjects().closest("Large door");
		RS2Object RubberroomDoor = getObjects().closest("Door");
		if(Compost.contains(myPlayer()) && inventory.contains("Key")) {
			log("Walking Front Doors");
			log("Rubbertube part");
			walking.webWalk(Frontbigdoors);
			sleep(random(987,1134));
			if(Frontbigdoors.contains(myPlayer()) && Frontdoors !=null) {
				Frontdoors.interact("Open");
				sleep(random(1052,1300));	}
			walking.webWalk(Rubberroom);
			sleep(random(1052,1300));
			if(!inventory.contains("Rubber tube")) {
			if(Rubberhose !=null && Rubberhose.isOnScreen()) {
				RubberroomDoor.interact("Open");
				sleep(random(1052,1300));
				Rubberhose.interact("Take");
				sleep(random(987,1134));
				RubberroomDoor.interact("Open");
			}if(Rubberhose == null) {
				sleep(5000);
			}
			}
		}
	}

	private void Spade() throws InterruptedException {
		RS2Object Door= getObjects().closest("Door");
		GroundItem SpadeGround = getGroundItems().closest("Spade");
		RS2Object Compost = getObjects().closest("Compost heap");
		if(MansioninsideUP.contains(myPlayer())) {
			RS2Object StartsDownLobby = getObjects().closest("Staircase");
			walking.webWalk(Bigstartdown);
			sleep(random(1599,2323));
			StartsDownLobby.interact("Climb-down");
			sleep(random(1199,1423));		}
			walking.webWalk(Spade);
			sleep(random(3799,5923));
			SpadeGround.interact("Take");
			sleep(random(1199,1423));
			walking.webWalk(SpaderoomDoor);
			sleep(1000);
			Door.interact("Open");
			sleep(random(1599,1923));
			walking.webWalk(Digspot);
			sleep(random(1799,1923));
			Compost.interact("Search");
			sleep(random(3099,3523));
			Gettinggauge();
	}

	private void Gettinggauge() throws InterruptedException {
		RS2Object Fhountain = getObjects().closest("Fountain");
		walking.webWalk(Phountain);
		sleep(1000);
		inventory.interact("Use", "Poison");
		sleep(random(800,921));
		inventory.interactWithNameThatContains("Use","Fish food");
		sleep(random(800,921));
		inventory.interact("Use", "Poisoned fish food");
		sleep(random(800,921));
		Fhountain.interact("Use");
		sleep(random(1500,1821));
		Fhountain.interact("Search");
		sleep(random(1500,1821));
		while (getDialogues().isPendingContinuation()) {
        	log("Continue");
			getDialogues().clickContinue();
			sleep(Script.random(800, 1200));}
	}

	private void Fishfood() throws InterruptedException {
		GroundItem fishFood = groundItems.closest("Fish food");
		RS2Object StartsupLobby = getObjects().closest("Staircase");
		if(!Bigstartsup.contains(myPlayer())) {
		walking.webWalk(Bigstartsup.getRandomPosition());
		sleep(random(1499,1923));
		StartsupLobby.interact("Climb-up");
		sleep(random(1199,1423));}
		walking.webWalk(Fishfood.getRandomPosition());
		sleep(random(2000));
		if(Fishfood.contains(myPlayer())) {
			log("Taking fish food");
			if(fishFood !=null) {
			fishFood.interact("Take");
			sleep(random(500,800));
			}else if(fishFood == null){
				log("Waiting for fish food");
				sleep(5000);
			}
		}
	}
		
	private void Poison() throws InterruptedException {
		GroundItem Poisonbottle = getGroundItems().closest("Poison");
		log("Walking poison");
		walking.webWalk(Poison);
		sleep(random(800,1131));
		if(Poisonbottle !=null) {
			Poisonbottle.interact("Take");
			sleep(random(500,800));
		}else if (!inventory.contains("Poison")){
			log("Waiting for poison");
			sleep(5000);
			
		}
		
		
	}

	private void TalkingtoVeronica() throws InterruptedException {
		NPC Veronica = getNpcs().closest("Veronica");
			if(Veronica.isOnScreen() && Veronica !=null) {
				Veronica.interact("Talk-to");
				sleep(2000);
				if(getDialogues().completeDialogue(VeronicaTalk1)) {
			        			log("Chosing dialog for Continue 1");
			        				sleep(Script.random(800, 1200));
			        					WalkingFrontdoors();
			}
			}
	}

	private void WalkingFrontdoors() throws InterruptedException {
		RS2Object Frontdoors = getObjects().closest("Large door");
		log("Walking Front Doors");
		log("Walking Front door part");
		walking.webWalk(Frontbigdoors);
		sleep(random(987,1134));
		if(Frontbigdoors.contains(myPlayer()) && Frontdoors !=null) {
			Frontdoors.interact("Open");
			sleep(random(1052,1300));
			
		}
	}

	private void WalkingtoVeronica() throws InterruptedException {
		walking.webWalk(Veronica.getRandomPosition());
		sleep(1000);		
		TalkingtoVeronica();
	}

	@Override
    public int onLoop() throws InterruptedException {
		CheckUp();

	   return random(1700) ; //The amount of time in milliseconds before the loop starts over


    }
   
    @Override
    public void onExit() {
    	log("Good Bye");
    	stop();

        //Code here will execute after the script ends


    }	

    @Override


    public void onPaint(Graphics2D g) {


        //This is where you will put your code for paint(s)


    }	
}