package Notredy;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.GetLevels;
import Core.Support.Sleep;
import Core.Support.Time;
import Support.*;
import java.awt.*;

public class PizzaMaker extends Script {
	Areas areas = new Areas();
	GetLevels levels = new GetLevels();
	Time time = new Time();
	int Potofflour = 0;
	int Jugofwater = 0;
	int Tomato = 0;
	int Chees = 0;
	int Pizzabase = 0;
	int Bucketofwater = 0;
	String Bw = "Bucket of water";

	@Override
	public void onStart() throws InterruptedException {
		time.Starttime();
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 5000);
		while (settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
			Sleep.waitCondition(() -> settings.areRoofsEnabled() == true, 2000);
		}
	}

	private void Checkup() throws InterruptedException {
		if (inventory.contains("Jug of water") && inventory.contains("Pot of flour")) {
			log("Making Doe");
			MakingDoewithJug();
		} else if (Tomato >= 9 && Chees >= 9 && Pizzabase >= 9 && (Potofflour < 9 || Jugofwater < 9 || Bucketofwater < 9)) {
			log("Making pizza base");
			MakingPizza();
		} else if ((!(inventory.contains("Jug of water") || inventory.contains(Bw))) && !inventory.contains("Pot of flour"))
			log("Going bank");
		GettingMaterials();
	}

	private void MakingPizza() throws InterruptedException {
		if (!bank.isOpen()) {
			log("Bank not open opening bank");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
			Potofflour = (int) bank.getAmount("Pot of flour");
			Jugofwater = (int) bank.getAmount("Jug of water");
			Tomato = (int) bank.getAmount("Tomato");
			Chees = (int) bank.getAmount("Cheese");
			Pizzabase = (int) bank.getAmount("Pizza base");
			}else {	
				log("Bank is open");
			if (!inventory.isEmpty()) {
				log("Inventory not empty");
				bank.depositAll();
			}
			if (Tomato >= 9 && Chees >= 9 && Pizzabase >= 9) {
				bank.withdraw("Tomato", 9);
				Sleep.waitCondition(() -> inventory.contains("Tomato"), 3000);
				bank.withdraw("Cheese", 9);
				Sleep.waitCondition(() -> inventory.contains("Cheese"), 3000);
				bank.withdraw("Pizza base", 9);
				Sleep.waitCondition(() -> inventory.contains("Pizza base"), 3000);
				bank.close();
			while(inventory.contains("Tomato") && inventory.contains("Pizza base")) {
				log("Looping for pizza base");
				inventory.interact("Use", "Tomato");
				inventory.interactWithNameThatContains("Use", "Pizza base");
				getWidgets().interact(270,14,38,"Make");
				Sleep.waitCondition(() -> inventory.getAmount("Incomplete pizza") == 9, 3000);
			}while (inventory.contains("Incomplete pizza") && inventory.contains("Cheese")) {
				log("Looping for Uncoocked pizza");
				inventory.interact("Use", "Cheese");
				inventory.interactWithNameThatContains("Use", "Incomplete pizza");
				getWidgets().interact(270,14,38,"Make");
				Sleep.waitCondition(() -> inventory.getAmount("Uncooked pizza") == 9, 3000);	
			}
			}else {
				log("Out of ressourses");
				stop();
			}
		}
	}

	private void GettingMaterials() throws InterruptedException {
		if (!Banks.DRAYNOR.contains(myPlayer())) {
			log("Walking bank");
			walking.webWalk(Banks.DRAYNOR);
			Sleep.waitCondition(() -> !myPlayer().isMoving(), 500,3000);
		}

		if (!bank.isOpen()) {
			log("Bank not open");
			bank.open();
			Sleep.waitCondition(() -> bank.isOpen(), 3000);
			Potofflour = (int) bank.getAmount("Pot of flour");
			Jugofwater = (int) bank.getAmount("Jug of water");
			Tomato = (int) bank.getAmount("Tomato");
			Chees = (int) bank.getAmount("Cheese");
			Pizzabase = (int) bank.getAmount("Pizza base");
			Bucketofwater = (int) bank.getAmount(Bw);

			if (!inventory.isEmpty()) {
				log("Inventory not empty");
				bank.depositAll();
			}
			if (inventory.isEmpty() && Jugofwater >= 9 && Potofflour >= 9) {
				log("Taking jug and flour");
				bank.withdraw("Jug of water", 9);
				Sleep.waitCondition(() -> inventory.contains("Jug of water"), 3000);
				bank.withdraw("Pot of flour", 9);
				Sleep.waitCondition(() -> inventory.contains("Pot of flour"), 3000);
			} else {
				MakingPizza();
			}
		}
		bank.close();
	}

	private void MakingDoewithJug() {
		log("Making doe");
		inventory.interact("Use", "Jug of water");
		Sleep.waitCondition(() -> inventory.isItemSelected(), 3000);
		inventory.interactWithNameThatContains("Use", "Pot of flour");
		Sleep.waitCondition(() -> getWidgets().isVisible(270, 1), 3000);
		getWidgets().interact(270, 16, 38, "Make");
		Sleep.waitCondition(() -> (!(inventory.contains("Jug of water") && inventory.contains("Pot of flour"))), 7000,
				30000);

	}

	@Override
	public int onLoop() throws InterruptedException {
		time.Timerun();
		Checkup();

		return random(1000); // The amount of time in milliseconds before the loop starts over

	}

	@Override
	public void onExit() {

		// Code here will execute after the script ends

	}

	@Override
	public void onPaint(Graphics2D g) {
		g.setColor(Color.decode("#00ff04")); // Color
		g.drawString(time.HoursMinSec(time.Timerun()), 460, 335);

	}

}