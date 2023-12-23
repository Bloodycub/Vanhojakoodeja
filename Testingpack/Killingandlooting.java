package Testingpack;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;
import TestpackCore.MonsterKiller;

import java.awt.*;
import java.util.ArrayList;

public class Killingandlooting extends Script {
	Areas areas = new Areas();
	Timer Timerun;
	int loopcheck =random(5,10);
	public ArrayList<String> items;
	MonsterKiller a = new MonsterKiller("Chicken",true,items) ;

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

	private void Checkup() throws InterruptedException {
		if(dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
		}
		a.KillingMonster();
		
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
		try {
			Checkup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	