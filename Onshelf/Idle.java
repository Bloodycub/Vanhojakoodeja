package Onshelf;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.GetLevels;
import Core.Support.Time;
import Support.*;
import java.util.Random;

public class Idle extends Script {
	Time time = new Time();
	GetLevels a = new GetLevels();
	Random r = new Random(System.currentTimeMillis());
	Mouse m;
	int xmin = 550;
	int ymin = 400;
	int xw = 150;
	int yw = 55;

	@Override
	public void onStart() throws InterruptedException {
		log("Idle has Started");
		log("lets go");
		m = this.getMouse();
		sleep(1000);
	}

	@Override
	public int onLoop() {
		try {
			// r = new Random(r.nextInt());
			int randomstop = 250 + r.nextInt(200);
			int randox = xmin + r.nextInt(xw);
			int randoy = ymin + r.nextInt(yw);
			m.move(randox,randoy);
			sleep(50 + r.nextInt(50));
			m.click(false);
			sleep(50 + r.nextInt(50));
			m.move(0, randomstop);
			
			
			
			//m.moveOutsideScreen();
			

			int randomsleep = r.nextInt(100);
			randomsleep = 1 == randomsleep ? 1 : 0;
			int millis = 180*1000 + r.nextInt(119*1000) + randomsleep * (120000 + r.nextInt(120*1000));
			
			log("Point " + randox + " " + randoy + ". " + ( randomsleep == 0 ? "" : "AFK logout this loop. " ) + "Next loop in " + millis/1000 + " seconds");
			
			return millis; //
			// The amount of time in milliseconds before the loop starts over
		} catch (Exception e) {
			log(e);
			return 5000;
		}

	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}



}