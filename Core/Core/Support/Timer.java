package Core.Support;

import java.util.concurrent.TimeUnit;

public class Timer {
	private long time;
	
	boolean triggered = false;

	/**
	 * 
	 * @param time Time in milliseconds untill public function tick() returns true
	 */
	public Timer(long time) {
		long currentSystemTime = System.currentTimeMillis();
		this.time = time + currentSystemTime;
	}

	/**
	 * 
	 * @return True for only once when timer has reached desired time
	 */
	public boolean tick() {
		if (triggered) {
			return false;
		}
		if (System.currentTimeMillis() >= time) {
			triggered = true;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return True always when timer has reached desired time
	 */
	public boolean isComplete() {
		return System.currentTimeMillis() >= time;
	}

	/**
	 * 
	 * @return Time in ms untill timer reaches destination, starts counting
	 *         backwards after that
	 */
	public long timeRemaining() {
		return Math.abs(time - System.currentTimeMillis());
	}
	
	@Override
	public String toString() {
		String res = "";
		long duration = timeRemaining();
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
