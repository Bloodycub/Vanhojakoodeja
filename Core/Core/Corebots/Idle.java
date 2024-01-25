package Core.Corebots;

import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import Core.Support.*;

import java.util.Random;

public class Idle extends Skeleton {
	Time time = new Time();
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
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		while(dialogues.isPendingContinuation()) {
			log("Pending continue");
			dialogues.clickContinue();
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
		m = this.getMouse();
	}

	@Override
	public int onLoop() {
		try {
			// r = new Random(r.nextInt());
			int randomstop = 250 + r.nextInt(200);
			int randox = xmin + r.nextInt(xw);
			int randoy = ymin + r.nextInt(yw);
			m.move(randox, randoy);
			sleep(50 + r.nextInt(50));
			m.click(false);
			sleep(50 + r.nextInt(50));
			m.move(0, randomstop);

			// m.moveOutsideScreen();

			int randomsleep = r.nextInt(100);
			randomsleep = 1 == randomsleep ? 1 : 0;
			int millis = 180 * 1000 + r.nextInt(119 * 1000) + randomsleep * (120000 + r.nextInt(120 * 1000));

			log("Point " + randox + " " + randoy + ". " + (randomsleep == 0 ? "" : "AFK logout this loop. ")
					+ "Next loop in " + millis / 1000 + " seconds");

			return millis; //
			// The amount of time in milliseconds before the loop starts over
		} catch (Exception e) {
			log(e);
			return 5000;
		}

	}

}