package Trash;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

public class testbot extends Script{

	@Override
	public int onLoop() throws InterruptedException {
		checkup();
		return 50000;
	}

	private void checkup() throws InterruptedException {
		walking.webWalk(Banks.GRAND_EXCHANGE.getRandomPosition());
		sleep(random(50000,80000));
		
	}

}
