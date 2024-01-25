package Core;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;

import java.awt.*;

public class Skeleton extends Script {
	Areas areas = new Areas();
	Time time = new Time();
	protected void startup() throws InterruptedException {
		
	}

	@Override
	public void onStart() throws InterruptedException {
		startup();
	}

	@Override
	public int onLoop() {

		return random(100); // The amount of time in milliseconds before the loop starts over

	}


	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {


	}

	

}