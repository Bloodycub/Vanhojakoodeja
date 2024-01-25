package UltimateOaker;

import java.awt.Point;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import Core.Support.Sleep;

public class Banker extends Script{
	
	@Override
	public void onStart() {
		try {
			RS2Object bankku = getObjects().closest("Bank booth");
			bankku.interact("Bank");
			sleep(random(150,400));
			
			if (hoverItem()) {
				Sleep.waitCondition(() -> bank.isOpen(), 300, 5000);
				sleep(random(49,151));
				
				mouse.click(true);
				sleep(random(101,131));
				
				Point p = mouse.getPosition();
				p.x += random(-10,10);
				p.y += 81 + random(0,10);
				mouse.move(p.x, p.y);
				sleep(random(250,500));
				
				mouse.click(false);
				sleep(random(500,800));
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean hoverItem() {
		for(int i = 0; i < 100; i++) {
			int slot = random(2,13);
			if (inventory.getItemInSlot(slot) != null)
				if (inventory.getItemInSlot(slot).nameContains("Oak logs")) {
					inventory.hover(slot);
					return true;
				}
		}
		return false;
	}

	@Override
	public int onLoop(){
		
		return 100;
	}

}
