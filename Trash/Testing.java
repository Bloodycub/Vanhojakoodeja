package Trash;

import java.awt.Graphics2D;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.Painter;
import Core.Support.RSExchange;

public class Testing extends Script {
	// Areas area = new Areas();
	Painter paint = new Painter();
	private final RSExchange rsExchange = new RSExchange();

	@Override
	public void onStart() throws InterruptedException {
		log("Wroking");
		rsExchange.getExchangeItem("Adamant arrow").ifPresent(x->log(x));

	}

	@Override
	public int onLoop() {
		testing();
		return random(700); // The amount of time in milliseconds before the loop starts over

	}

	private void testing() {
    	
    	log("addyarrows");
	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {
		paint.paintTo(g);
		}
		// This is where you will put your code for paint(s)

	}

