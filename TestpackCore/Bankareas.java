package TestpackCore;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.Script;

public class Bankareas extends Script{
	public final static Area LumbBank = new Area(3210, 3218, 3208, 3220).setPlane(2);
	public final static Area DraynorBank = new Area(3094, 3245, 3092, 3241);
	public final static Area VarrockDownBank = new Area(3183, 3437, 3181, 3443);
	public final static Area VarrockUpperBank = new Area(3254, 3422, 3252, 3419);
	public final static Area FaladorWest = new Area(new int[][] { { 2948, 3367 }, { 2945, 3367 }, { 2945, 3368 },
			{ 2943, 3368 }, { 2943, 3370 }, { 2948, 3370 } });
	public final static Area Ge = new Area(
			new int[][] { { 3164, 3488 }, { 3166, 3488 }, { 3167, 3489 }, { 3167, 3491 }, { 3166, 3492 },
					{ 3163, 3492 }, { 3163, 3489 }, { 3161, 3487 }, { 3167, 3487 }, { 3169, 3488 }, { 3169, 3491 },
					{ 3168, 3493 }, { 3164, 3494 }, { 3160, 3493 }, { 3159, 3491 }, { 3161, 3487 }, { 3163, 3489 } });

	private String bank;
	private Area BankArea;

	public Bankareas(String Bank) {
		this.bank = Bank;
	}

	public Position GetBank() {
		if (bank.equals("LumbBank")) {
			this.BankArea = LumbBank;
			return LumbBank.getRandomPosition();
		}
		if (bank.equals("DraynorBank")) {
			if(myPlayer().getCombatLevel() >= 12) {
				this.BankArea = DraynorBank;
				return DraynorBank.getRandomPosition();
			}
			if(myPlayer().getCombatLevel() < 12) {
				log("Oi You are level " + myPlayer().getCombatLevel() + " You must be over 11 or you"
						+ " may get attacked by wizzard");
				return LumbBank.getRandomPosition();
			}
		}
		if (bank.equals("VarrockDownBank")) {
			this.BankArea = VarrockDownBank;
			return VarrockDownBank.getRandomPosition();
		}
		if (bank.equals("VarrockUpperBnak")) {
			this.BankArea = VarrockUpperBank;
			return VarrockUpperBank.getRandomPosition();
		}
		if (bank.equals("FaladorWest")) {
			this.BankArea = FaladorWest;
			return FaladorWest.getRandomPosition();
		}
		return null;
		}

	public boolean BankContainsmyPlayer(Player myPlayer) {
		if (this.BankArea.contains(myPlayer)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
