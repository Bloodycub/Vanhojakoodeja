package UltimateOaker;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.osbot.rs07.script.Script;

public class Mouser extends Script{
	
	private boolean mouseOut = false;
	
	public void moveMouseOut() {
		int r = random(0,31);
		Point p = mouse.getPosition();
		p.x = 0;
		if (r < 20) {
			p.y += random(-10, 10);
		} else if (r < 25) {
			p.y += random(-30, 30);
		} else {
			return;
		}
		
		p.y = p.y >= 0 ? p.y : 0;
		p.y = p.y <= 498 ? p.y : 498;
		
		mouse.move(p.x,  p.y);
		try {
			sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mouseOut = true;
	}
	
	public void moveMouseIn() {
		if (mouseOut) {
			mouseOut = false;
			int y = 498;
			int x = 0;
			int r = random(0,31);
			if (r < 10) {
				y = random(53,127);
			} else if (r < 28) {
				y = random(9,498);
			} else {
				x = random(0, 107);
			}
			getBot().getMouseEventHandler().generateBotMouseEvent(MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 0, false, MouseEvent.NOBUTTON, true);
		}
	}


	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

}
