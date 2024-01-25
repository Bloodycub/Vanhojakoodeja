package Notredy;

import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;

public class Muleaccepting extends Script {
	private Player player;
	private int Tradeup = 0;
	
	
	@Override
	public void onStart() throws InterruptedException {
		if (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString(" ");
			getKeyboard().typeString("::toggleroofs");
			sleep(random(500, 700));
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		Checkup();

		return random(2000); // The amount of time in milliseconds before the loop starts over

	}

	private void Checkup() throws InterruptedException {
		
		if (!inventory.isEmpty()) {
			log("Banking");
			Banking();
		
		}else if(Tradeup == 1 && !trade.isCurrentlyTrading()) {
			log("Waiting for trade");
			Waitfortrade();
			
		}else if(trade.isCurrentlyTrading()) {
			log("Accepting trade");
			AcceptingTrade();
		}
	//	WalkingSpot();


	}

	private void Waitfortrade() throws InterruptedException {
		player = trade.getLastRequestingPlayer();
		if(player != null) {
				player.interact("Trade with");
				sleep(5000);
			}
	}
		

	private void AcceptingTrade() throws InterruptedException {
			if(trade.didOtherAcceptTrade()) {
			trade.acceptTrade();
			sleep(random(500,1000));
			}else {
			sleep(random(1000,1500));
				}
			Tradeup = 0;
		}
		


	private void Banking() throws InterruptedException {
		if (!bank.isOpen()) {
			bank.open();
			sleep(random(1000, 3000));
		}
		if (bank.isOpen()) {
			bank.depositAll();
			sleep(random(200, 400));
			bank.close();
		}
	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override

	public void onPaint(Graphics2D g) {

		// This is where you will put your code for paint(s)

	}

	@Override
	public void onMessage(Message message){
		log("test");
		if (message.getMessage().endsWith(" wishes to trade with you.")) {
			Tradeup = 1;
			
	
	    }
	}
	

}
