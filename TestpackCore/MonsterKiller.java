package TestpackCore;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.osbot.rs07.Bot;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

import Core.Support.*;

public class MonsterKiller {
	public String myNpc;
	public Boolean loot = false;
	// private List<String> items;
	public List<String> items;
	Script s;

	public MonsterKiller(String monster) {
		this.myNpc = monster;
	}

	/*
	 * public myNpcKiller(String myNpc, Boolean Loot, List<String> Items) {
	 * this.myNpc = myNpc; this.loot = Loot; this.items = Items = new
	 * ArrayList<String>(); }
	 */
	@SuppressWarnings({ "static-access", "unlikely-arg-type" })
	public void KillingmyNpc(String asd) {
		Predicate<NPC> npcFilter = n -> n != null && n.getName().contains(asd) && n.hasAction("Attack")
				&& n.isAttackable() && !n.isUnderAttack();

		NPC M = s.npcs.getAll().stream().filter(npcFilter).findFirst().get();
		if (M.interact("Attack")) {
			try {
				s.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Sleep.waitCondition(() -> !M.isInteracting(s.myPlayer()) && !M.isHitBarVisible(), 200, 60000);
		}

		if (loot == true) {
			List<GroundItem> gi = s.getGroundItems().getAll();
			if (!M.isInteracting(s.myPlayer())) {
				for (int a = 0; a < gi.size(); a++) {
					for (int i = 0; i < items.size(); i++) {
						if (gi.get(a).equals(items.get(i))) {
							s.log("Ground item list contains and equals own list");
							GroundItem item = gi.get(a);
							s.log("Ground item list contains item list");
							if (item != null) {
								s.log("Looting");
								item.interact("Take");
								try {
									s.sleep(s.random(1500, 2500));
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								s.log("Looted");
							}
						}
					}
				}
			}
		}
	}

}
