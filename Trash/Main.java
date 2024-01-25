package Trash;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import bots.*;

public final class Main extends Script {

	private ArrayList<Skelor> bots = new ArrayList<>();
	
	Area Tutisland = new Area(3062, 3139, 3160, 3055); // tut island
	Area LBSpawnarea = new Area(3222, 3236, 3240, 3219); // Spawn area

	private long timeUntilNewActivity = 0;

	private int state = 0;

	private boolean doingRandomWithTimer = true;

	private long lastTimeMillis = 0;
	private long currentTimeMillis = 0;

	private int availableStates = 2;

	private final int _Maxtimeuntilnewthing = 180 *  60000; 
	private final int _Mintimeuntilnewthing = 30 *  60000;
	
	//Private Skeleton currentBot;
	private Skelor currentBot;

	@Override
	public void onStart() throws InterruptedException {
		sleep(500);
		
		
		///////Lis�� jokainen botti bots listaan
		bots.add(new Worble());
		bots.add(new Whooler());
		
		
		currentTimeMillis = System.currentTimeMillis();

		
		for(Skelor bot : bots) {
			bot.exchangeContext(getBot());
		}
		availableStates = bots.size();
		
		log("Start");
		sleep(1000);

		timeUntilNewActivity = random(_Mintimeuntilnewthing, _Maxtimeuntilnewthing); // Set a new random time
		log("Time until new activity : "+timeUntilNewActivity);

		// Default botti joka runnaa heti
		currentBot = bots.get(0);
		currentBot.onStart();
	}

	// Call this function to get a new random activity
	private void randomState() {
		int lastState = state;
		while (lastState == state || state == 0) {
			state = (random(0, 1000) % availableStates);
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		lastTimeMillis = currentTimeMillis;
		currentTimeMillis = System.currentTimeMillis();
		long timeAlpha = (currentTimeMillis - lastTimeMillis); // How much time has passed since the last time this
																	// was updated
		log("Timealpha "+timeAlpha);
		
		////// Katso mik� return value tulee onLoopista?
		//Pienempi kuin 0? Tee jotain j�nn��
		int ret = currentBot.onLoop();
		if (ret < 0) {
			timeUntilNewActivity = 0;
		}
		
		
		if (doingRandomWithTimer) {
			timeUntilNewActivity -= timeAlpha;

			if (timeUntilNewActivity <= 0) {
				timeUntilNewActivity = random(_Mintimeuntilnewthing, _Maxtimeuntilnewthing); // Set a new random time
				log("Time until new activity : "+timeUntilNewActivity);
				randomState(); // Set a new random state, not 0 (fresh spawn) or the last one used
				currentBot = bots.get(state);
				currentBot.onStart();
			}
		}

		return ret;
	}

	@Override
	public void onExit() {
		stop(true);
	}

}