package Core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.osbot.BotApplication;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Corebots.*;
import Core.Quests.*;
import Core.Support.*;
import script.TutorialIsland;
import sections.TutorialSection;
import sections.WizardSection;

public final class Core extends Script {
	Areas areas = new Areas();
	public int currentLevel;
	public long timeRan;
	public int levelsGained;
	public int Combat; // Combat level
	public int beginningLevel;
	public long timeBegan;
	int loopcheck = random(5, 10);
	Timer ShortBreak;
	Timer ShortBreakCounter;
	Timer LongBreak;
	Timer LongBreakCounter;
	Timer SleepBreak;
	Timer SleepBreakCounter;
	Timer Timers;
	Timer Timerun;
	Timer Break;
	private boolean onpaint = false;
	private boolean ShortFlag = false;
	private boolean LongFlag = false;
	private boolean SleepFlag = false;
	private boolean LogoutTrigger = false;
	public boolean tutisland = false;
	private LoginEvent loginEvent;
	private String Nimi = "";

	private MouseTrail trail = new MouseTrail(0, 255, 255, 4000, this);
	private MouseCursor cursor = new MouseCursor(52, 2, Color.RED, this);

	private ArrayList<Skeleton> bots = new ArrayList<>(); // 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean doingRandomWithTimer = true;
	//////////////////////////////////////// Ajastin///////////////////////////////////////////////////////////////
	// private long timeUntilNewActivity = 0;
	//////////////////////////////////////// Kuinka monta mahdolista
	//////////////////////////////////////// vaihtoehtoa///////////////////////////////////
	private int state = 0;
	private int availableStates = 0;
	//////////////////////////////////////// minuutissa on 60000 millisekunttia
	//////////////////////////////////////// Activity
	//////////////////////////////////////// Times//////////////////////////////////////////////////////
	// private final int _Maxtimeuntilnewthing = 3 * 5000; // Testi ajat Maximi aika
	// private final int _Mintimeuntilnewthing = 0 * 1000; // Test ajatt Minimi aika

	private final int _MintimeuntilnewActivity = 42 * 60000; // Minimi aika
	private final int _MaxtimeuntilnewActivity = 96 * 60000; // Maximi aika
	////////////////////////////////////// Short
	////////////////////////////////////// break///////////////////////////////////////////////////////////	
	private final int MinimumtimeforShortbreak = 4 * 60000; // Minimi aika taukoon
	private final int MaximumtimeforShortbreak = 37 * 60000; // Maximi aika taukoon

	private final int MinimumtimeforShortCount = 3 * 60000; // Minimi aikatauvolla
	private final int MaximumtimeforShortCount = 8 * 60000; // Maximiaika tauvolla
	////////////////////////////////////// Long
	////////////////////////////////////// break///////////////////////////////////////////////////////////
	private final int MinimumtimeforLongbreak = 59 * 60000; // Minimi aika taukoon
	private final int MaximumtimeforLongbreak = 99 * 60000; // Maximi aika taukoon

	private final int MinimumtimeforLongCount = 32 * 60000; // Minimi aika tauvolla
	private final int MaximumtimeforLongCount = 91 * 60000; // Maximi aika tauvolla
	////////////////////////////////////// Sleep
	////////////////////////////////////// break///////////////////////////////////////////////////////////
	private final int MinimumtimeforSleep = 4 * 3600000; // Minimi aika taukoon
	private final int MaximumtimeforSleep = 6 * 3600000; // Maximi aika taukoon

	private final int MinimumtimeforSleepCount = 8 * 3600000; // Minimi aika Nukkuu
	private final int MaximumtimeforSleepCount = 16 * 3600000; // Maximi aika Nukkuu
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Skeleton currentBot;
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void AddBots() {
		/////// Lis�� jokainen botti bots listaan ///////
		// bots.add(new BucketPickerandfiller());
		bots.add(new Chickenkiller());
		bots.add(new Chickenkiller());
		bots.add(new CookingShrimpandAnovy());
		// bots.add(new Cowkiller());
		bots.add(new Fishing());
		bots.add(new Woodcutter());
		// bots.add(new Potpicker());
		// bots.add(new PotofFlour());
		// bots.add(new Idle());
		// bots.add(new Potatopicker());
		bots.add(new Whoolspinner());
		bots.add(new Shopbuyer());
		bots.add(new MonymMakingwoodcutter());

		// QUESTS//

		bots.add(new Xmarktospot());
		bots.add(new Doricsquest());
		bots.add(new SheepShrederquest());
		bots.add(new Romeaandjulette());
		bots.add(new RuneMysteries());
		bots.add(new WitchsPotion());
		bots.add(new Coockassist());
		/////////

	}

	public void Settimes() {
		log("Setting times");
		Timers = new Timer(random(_MintimeuntilnewActivity, _MaxtimeuntilnewActivity));

		ShortBreak = new Timer(random(MinimumtimeforShortbreak, MaximumtimeforShortbreak));
		ShortBreakCounter = new Timer(random(MinimumtimeforShortCount, MaximumtimeforShortCount));

		LongBreak = new Timer(random(MinimumtimeforLongbreak, MaximumtimeforLongbreak));
		LongBreakCounter = new Timer(random(MinimumtimeforLongCount, MaximumtimeforLongCount));

		SleepBreak = new Timer(random(MinimumtimeforSleep, MaximumtimeforSleep));
		SleepBreakCounter = new Timer(random(MinimumtimeforSleepCount, MaximumtimeforSleepCount));

	}

	@Override
	public void onStart() throws InterruptedException {
		Nimi = getBot().getUsername();
		log(Nimi);
		if (!getClient().isLoggedIn()) {
			loginToAccount(Nimi, Salasana);
		}
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 15000);
		Timerun = new Timer(0);
		CheckIfinTutorialisland();
		AddBots();
		Settimes();
		
		availableStates = bots.size();
		for (Skeleton bot : bots) {
			bot.exchangeContext(getBot());
		}

		// Default botti joka runnaa heti
		currentBot = bots.get(random(0, availableStates - 1));
		currentBot.onStart();
	}

	private void CheckIfinTutorialisland() throws InterruptedException {
		if(Areas.Tutisland.contains(myPlayer()) || Areas.Tutisland2.contains(myPlayer())) {
			log("You are in Tut island complet it now");
			bots.add(new TutorialIsland());
			currentBot = bots.get(0);
			currentBot.onStart();
			tutisland = true;
		}
		
	}

	private void randomState() {
		// Call this function to get a new random activity
		int lastState = state;
		while (lastState == state || state == 0) {
			state = (random(0, 1000) % availableStates);
		}
	}

	public void Onbreaktimers() throws InterruptedException {
		if (ShortFlag == true || LongFlag == true || SleepFlag == true) {
			if (ShortBreakCounter.tick() && ShortFlag == true && LongFlag == false && SleepFlag == false) {
				log("Im inside counter check");
				ShortFlag = false;
				log("Logging in from Short brake");
				ShortBreak = new Timer(random(MinimumtimeforShortbreak, MaximumtimeforShortCount));
				if(client.isLoggedIn() == false) {
					log("Not logged in logging in");
				loginToAccount(Nimi, Salasana);
				}
			}
			if (LongBreakCounter.tick() && LongFlag == true && SleepFlag == false) {
				LongFlag = false;
				ShortFlag = false;
				LongBreak = new Timer(random(MinimumtimeforLongbreak, MaximumtimeforLongbreak));
				ShortBreak = new Timer(random(MinimumtimeforShortbreak, MaximumtimeforShortCount));
				log("Logging in from Long brake");
				loginToAccount(Nimi, Salasana);
				
			}
			if (SleepBreakCounter.tick() && SleepFlag == true) {
				SleepFlag = false;
				LongFlag = false;
				ShortFlag = false;
				log("Logging in from Sleep brake");
				SleepBreak = new Timer(random(MinimumtimeforSleep, MaximumtimeforSleep));
				ShortBreak = new Timer(random(MinimumtimeforShortbreak, MaximumtimeforShortbreak));
				LongBreak = new Timer(random(MinimumtimeforLongbreak, MaximumtimeforLongbreak));
				loginToAccount(Nimi, Salasana);
				
			}
			if (ShortFlag == true || LongFlag == true || SleepFlag == true) {
			log("Sleeping");
			sleep(random(random(15000,30000)));
		}
		}
	}

	@SuppressWarnings("static-access")
	public void Logout() throws InterruptedException {
		if ((ShortFlag == true || LongFlag == true || SleepFlag == true) && LogoutTrigger == true
				&& client.isLoggedIn() == true) {
			log("After sleep");
			widgets.interact(548, 43, "Logout");
			Sleep.waitCondition(() -> widgets.isVisible(182, 9), 200, 5000);
			sleep(random(200, 600));
			log("Logout");
			widgets.interact(182, 9, "Logout");
			sleep(random(5000, 10000));
			LogoutTrigger = false;
			loginEvent.setLoggedOut();
		}
	}

	
	public void Breaktime() throws InterruptedException {
		if (ShortBreak.tick() == true && ShortFlag == false && LongFlag == false && SleepFlag == false) {
			log("Short Break enabled");
			mouse.moveOutsideScreen();
			ShortFlag = true;
			LogoutTrigger = true;
			ShortBreakCounter = new Timer(random(MinimumtimeforShortCount, MaximumtimeforShortCount));
			
		} else if (LongBreak.tick() && LongFlag == false && SleepFlag == false) {
			log("Long Break enabled");
			LongBreakCounter = new Timer(random(MinimumtimeforLongCount, MaximumtimeforLongCount));
			LongFlag = true;
			LogoutTrigger = true;
			
		} else if (SleepBreak.tick() && SleepFlag == false) {
			log("Sleep Break enabled");
			SleepBreakCounter = new Timer(random(MinimumtimeforSleepCount, MaximumtimeforSleepCount));
			SleepFlag = true;
			LogoutTrigger = true;
		}
		
		if (ShortFlag == false && LongFlag == false && SleepFlag == false && client.isLoggedIn() == false) {
			log("You are disconected");
			loginToAccount(Nimi, Salasana);
		}
		Logout();
	}

	@Override
	public int onLoop() throws InterruptedException {
		if(tutisland == true) {
			currentBot.onLoop();
		}
		Onbreaktimers(); // OnBreak Check time
		Breaktime(); // Check If on break
		if ((ShortFlag == true || LongFlag == true || SleepFlag == true) && client.isLoggedIn() == true)  {
			LogoutTrigger = true;
		}
		if(ShortFlag == false && LongFlag == false && SleepFlag == false) {
		if (dialogues.isPendingContinuation()) {
			log("Stuck In dialogue");
			dialogues.clickContinue();
		}

		int ret = currentBot.onLoop();
		if (ret == -11) {
			log("Completed tut island");
			tutisland = false;
		}
		if (ret == -1) {
			// Set activity time
			log("Removing bot");
			ret = 10;
			Timers = new Timer(0);
			currentBot = bots.get(random(0, availableStates - 1));
		}

		if (ret == -10) {
// Remove Bot
// Katso mik� return value tulee onLoopista?
// Pienempi kuin 0? Tee jotain j�nn��
// Default -10 on ett� poistaa Botin arraysta
			log("New bot");
			ret = 10;
			Timers = new Timer(0);
			bots.remove(currentBot);
			availableStates = bots.size();
		}

		if (doingRandomWithTimer) {
// Timing
			if (Timers.tick()) {
				log("Timer Tick new time");
				Timers = new Timer(random(_MintimeuntilnewActivity, _MaxtimeuntilnewActivity));
				randomState();
				currentBot = bots.get(state);
				currentBot.onStart();
			}
		}
		return ret;
	}else {
		sleep(random(10000,30000));
	}
		return 0;
	}
	private void loginToAccount(String username, String password) throws InterruptedException {
		if(client.isLoggedIn() == false) {
		log("Doing login");
		loginEvent = new LoginEvent(username, password);
		log("in login hey");
		getBot().addLoginListener(loginEvent);
		execute(loginEvent);
		Sleep.waitCondition(() -> widgets.isVisible(378, 87), 200,30000);
		widgets.interact(378, 87, "Play");
		log("Pressed play");
		sleep(random(1000, 2000));
		Sleep.waitCondition(() -> myPlayer().isVisible() && myPlayer().isOnScreen(), 1000, 10000);
		log("Done here in login");
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		if (client.isLoggedIn() && onpaint == false && myPlayer().isVisible() && myPlayer().isOnScreen()) {
			onpaint = true;
		}
		if (onpaint == true) {
			g.setColor(Color.decode("#00ff04")); // Color
			if (ShortFlag == true || LongFlag == true || SleepFlag == true) {
				if (ShortFlag == true && LongFlag == false && SleepFlag == false) {
					g.drawString("Till Sleeping Break " + SleepBreak, 0, 51);
					g.drawString("Till Long Break " + LongBreak, 0, 41);
					g.drawString("Till Short Break Ends " + ShortBreakCounter, 0, 31);
					g.drawString("Script name: " + currentBot.getName(), 0, 61);
				}
				if (LongFlag == true && SleepFlag == false) {
					g.drawString("Till Long Break Ends " + LongBreakCounter, 0, 41);
					g.drawString("Till Sleeping Break " + SleepBreak, 0, 51);
					g.drawString("Script name: " + currentBot.getName(), 0, 61);
				}
				if (SleepFlag == true) {
					g.drawString("Till Sleeping Break Ends " + SleepBreakCounter, 0, 51);
					g.drawString("Script name: " + currentBot.getName(), 0, 61);
				}
			} else {
				g.drawString("Till Sleeping Break " + SleepBreak, 0, 51);
				g.drawString("Till Long Break " + LongBreak, 0, 41);
				g.drawString("Till Short Break " + ShortBreak, 0, 31);
				g.drawString("Time runned " + Timerun, 0, 11);
				g.drawString("Next Activity " + Timers, 0, 21);
				g.drawString("Script name: " + currentBot.getName(), 0, 61);
				trail.paint(g);
				cursor.paint(g);
			}
		}
	}

	@Override
	public void onExit() {
		log("onExit im");
		log("Too long playing");
	}
}