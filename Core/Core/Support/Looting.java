package Core.Support;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.Script;

public class Looting extends Script {
	public String items;
	int itemnow = 0;
	int itemlater = 0;

	public Looting(String Item) {
		this.items = Item;
	}

	public void Lootitem() {
		GroundItem Item = getGroundItems().closest(items);
		if (Item != null) {
			Item.interact("Take");
			itemnow++;
			Sleep.waitCondition(() -> itemlater < itemnow, 3000);
			itemlater++;
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
