package UltimateOaker;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

public class Cutter extends Script{
	
	private RS2Object tree = null;
	
	private Position treePos;
	
	private boolean hovering = false;
	
	private final boolean log = false;
	
	private Mouser mouser;
	
	public static boolean disconnected = false;
	
	@Override
	public void onStart() {
		if (mouser == null) mouser = new Mouser();
		tree = getObjects().closest("Oak");
		treePos = tree.getPosition();
		log("tree check");
	}
	
	public void addMouser(Mouser m) {
		mouser = m;
	}
	
	private boolean bankBeforeInventoryFull() {
		int i = inventory.getEmptySlots();
		switch (i) {
			case 0:
				return true;
			case 1:
				if (random(0,100) < 50) return true;
			case 2:
				if (random(0,100) < 10) return true;
		}
		return false;
	}

	@Override
	public int onLoop() throws InterruptedException{
		if (inventory.isFull()) {
			if (log) log("inventory full");
			return 6666; //No more chopping bitch
		}
		
		if (myPlayer().isAnimating()) {
			if (log) log("player is animating");
			if (mouse.getPosition().x != 0) {
				if (random(0,100) < 6) {
					mouser.moveMouseOut(); 
				}
			}
			return random(1000,2000);
		}
		
		tree = getObjects().get(treePos.getX(), treePos.getY()).get(0);
		
		if (tree.getName().equals("Tree stump")) {
			if (!hovering) {
				int r = random(4500, 8000);
				if (bankBeforeInventoryFull()) return 6666;
				if (r > 7500) {
					if (random(0,31) <= 5) {
						r += random(5000,10000);
					}
				}
				if (log) log("hovering a cut tree " + r);
				sleep(r);
				tree = getObjects().get(treePos.getX(), treePos.getY()).get(0);
				mouser.moveMouseIn();
				sleep(1);
				tree.hover();
				hovering = true;
				return random(10,30);
			}
		}

		if (hovering) {
			if (log) log("waiting for tree spawn");
			if (tree.getName().equals("Oak")) {
				sleep(random(200,300));
				mouse.click(false);
				hovering = false;
				log("clicking spawned wood");
				sleep(random(100,150));
				mouser.moveMouseOut();
				return random(1000,2000);
			}
			return 100;
		}
		
		if (log) log("tree interact");
		mouser.moveMouseIn();
		sleep(1);
		tree.interact("Chop down");
		sleep(random(250,350));
		mouser.moveMouseOut();
		
		return random(3000,4000);
	}
	
	

}


