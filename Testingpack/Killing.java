package Testingpack;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;
import TestpackCore.MonsterKiller;
import sections.TutorialSection;
import sections.WizardSection;

import java.awt.*;

public class Killing extends Script {
	Areas areas = new Areas();
	Timer Timerun;
	int loopcheck =random(5,10);
    private final Skeleton m = new MonsterKiller("Chicken");


	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
        m.exchangeContext(getBot());
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
		if(dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
		}
		
		
		
		
		
		
		MonsterKiller a = new MonsterKiller("Chicken");
		a.KillingmyNpc("Chicken");
	}
	
	
	
	
	
	
	
	
	
	

	@Override
	public int onLoop() {
		int Counter = 0;
		if(Counter == loopcheck){
			@SuppressWarnings("unused")
			int loopcheck =random(5,10);
			if(dialogues.isPendingContinuation()) {
				log("Stuck");
				dialogues.clickContinue();
			}
			Counter = 0;
		}
		Checkup();

		return 400+random(100);

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