package Core.Support;

import org.osbot.rs07.script.Script;


public class ForallStart extends Script{

	public void RoofOff() throws InterruptedException {
		if(settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 3000);
		}
	}

	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
