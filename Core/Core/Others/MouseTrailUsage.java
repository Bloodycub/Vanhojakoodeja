package Core.Others;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.script.Script;

import Core.Support.*;
	

public class MouseTrailUsage extends Script {
	
	private MouseTrail trail = new MouseTrail(0, 255, 255, 4000, this);
	private MouseCursor cursor = new MouseCursor(52, 2, Color.RED, this);
	
	
	@Override
	public void onPaint(Graphics2D g) {

		trail.paint(g);
		cursor.paint(g);
}


	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}
}