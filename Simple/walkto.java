package Simple;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.*;
import java.awt.*;

public class walkto extends Script {
	Areas areas = new Areas();
	Timer Timerun;
	Area area = new Area(2956, 3513, 2961, 3510);
	@Override
	public void onStart() throws InterruptedException {
		Timerun = new Timer(0);
		log("XXXXXX BOT NAME XXXXXXXXXXX Has started");
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000,5000);
		if(!tabs.isOpen(Tab.INVENTORY)) {
			tabs.open(Tab.INVENTORY);
			Sleep.waitCondition(() -> tabs.isOpen(Tab.INVENTORY), 300,5000);
		}
		while(settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 5000);
		}
	}

	private void Checkup() {
walking.webWalk(area.getRandomPosition());
	}

	@Override
	public int onLoop() {
		Checkup();

		return 100+random(111);

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
