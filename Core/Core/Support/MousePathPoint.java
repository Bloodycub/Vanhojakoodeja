package Core.Support;

import java.awt.Point;

public class MousePathPoint extends Point {

	private static final long serialVersionUID = 1L;
	private long finishTime;

	public MousePathPoint(int x, int y, int lastingTime) {
		super(x, y);
		finishTime = System.currentTimeMillis() + lastingTime;
	}

	public boolean isUp() {
		return System.currentTimeMillis() > finishTime;
	}
}