package Trash;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Skelor extends Script {

	protected void startup() throws InterruptedException {
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("");
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
	}

	@Override
	public void onStart() throws InterruptedException {
		startup();
	}

	@Override
	public int onLoop() {

		return random(690); // The amount of time in milliseconds before the loop starts over

	}


	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {

		// This is where you will put your code for paint(s)

	}

}