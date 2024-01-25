package Chickenkiller;

import java.util.concurrent.TimeUnit;

import org.osbot.rs07.script.Script;

public class Time extends Script {

	private long timeBegan; // Start time
	private long timeRan; // Time run

	public void Starttime() throws InterruptedException {
		timeBegan = System.currentTimeMillis();

	}

	@Override
	public int onLoop() throws InterruptedException {
		return 0;
	}

	public long Timerun() {
		timeRan = System.currentTimeMillis() - this.timeBegan;
		return timeRan;
	}

	public String Convertingtime() {
		return HoursMinSec(timeRan);
	}

	public String HoursMinSec(long duration) {
		String res = "";
		long days = TimeUnit.MILLISECONDS.toDays(duration);
		long hours = TimeUnit.MILLISECONDS.toHours(duration)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
		long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
		if (days == 0) {
			res = (hours + ":" + minutes + ":" + seconds);
		} else {
			res = (days + ":" + hours + ":" + minutes + ":" + seconds);
		}
		return res;
	}

}
