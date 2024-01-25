
package Core.Support;


import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.util.ArrayList;


public class Dropper extends Script {
	
	private boolean dropAll = false;
	
	private boolean buryBones = false;
	
	
	public void bury() {
		buryBones = !buryBones;
	}
	
	private String[] itemsToDrop = {"Bronze axe", 		"Tinderbox", 		"Small fishing net", 	"Shrimps",
									"Bronze pickaxe", 	"Bronze dagger", 	"Bronze longsword", 	"Wooden shield",
									"Shortbow",			"Bronze arrows", 	"Air rune", 			"Mind rune",
									"Water rune", 		"Earth rune", 		"Body rune", 			"Bucket", 
									"Pot", 				"Bread"};
	
	private ArrayList<Integer> slots = new ArrayList<>();
	
	private int[] orderedSlots = {	0,	1,	2,	3, 
									7,	6,	5,	4, 
									8,	9,	10,	11, 
									15,	14,	13,	12, 
									16,	17,	18,	19, 
									23,	22,	21,	20, 
									24,	25,	26,	27};
	
	/**
	 * Empty constructor, drops tut island items by default
	 */
	public Dropper() {
		
	}
	
	/**
	 * Drops all listed items
	 * @param list This is a string list of item names
	 */
	public Dropper(String[] list) {
		this.itemsToDrop = list;
	}
	

	/**
	 * Using this constructor causes all items to be dropped
	 * @param dropAllItems Anything here lol
	 */
	public Dropper(Boolean dropAllItems) {
		this.dropAll = true;
	}
	

	@Override
	public void onStart() throws InterruptedException {
		
		ArrayList<String> items = new ArrayList<>();
		for (String s : itemsToDrop) {
			items.add(s);
		}
		
		for (int i : orderedSlots) {
			if ( getInventory().getItemInSlot(i) != null && ( items.contains( getInventory().getItemInSlot(i).getName() ) || dropAll) ) {
				slots.add(i);
			}
		}
		
		if (!buryBones)
			keyboard.pressKey(16);
		
	}

	boolean flag = false;
	@Override
	public int onLoop() {
		if (slots.size() > 0) {
			int a = slots.get(0);
			slots.remove(0);
			if (!flag) {
				flag = true;
				mouse.move( getInventory().getMouseDestination(a) );
			}
			mouse.click(false);
			try {
				sleep(random(1,30));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (slots.size() > 0) {
				mouse.move(getInventory().getMouseDestination(slots.get(0)));
			}
		}
		if (slots.size() == 0) {
			keyboard.releaseKey(16);
			stop(false);
		}
		if (!buryBones)
			return random(45,80);
		else
			return random(700,900);

	}

	@Override
	public void onExit() {
	}

}