package UltimateOaker;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Support.MouseCursor;
import Core.Support.MouseTrail;
import Core.Support.Sleep;

public class Main extends Script {
	
	Position[] path = {
		    new Position(3012, 3355, 0),
		    new Position(3012, 3355, 0),
		    new Position(3012, 3356, 0),
		    new Position(3012, 3357, 0),
		    new Position(3012, 3358, 0),
		    new Position(3012, 3359, 0),
		    new Position(3011, 3359, 0),
		    new Position(3010, 3359, 0),
		    new Position(3009, 3359, 0),
		    new Position(3008, 3359, 0),
		    new Position(3008, 3358, 0),
		    new Position(3008, 3357, 0),
		    new Position(3008, 3356, 0),
		    new Position(3008, 3355, 0),
		    new Position(3008, 3354, 0),
		    new Position(3008, 3353, 0),
		    new Position(3008, 3352, 0),
		    new Position(3008, 3351, 0),
		    new Position(3008, 3350, 0),
		    new Position(3008, 3349, 0),
		    new Position(3008, 3348, 0),
		    new Position(3008, 3347, 0),
		    new Position(3008, 3346, 0),
		    new Position(3008, 3345, 0),
		    new Position(3008, 3344, 0),
		    new Position(3008, 3343, 0),
		    new Position(3008, 3342, 0),
		    new Position(3008, 3341, 0),
		    new Position(3008, 3340, 0),
		    new Position(3008, 3339, 0),
		    new Position(3008, 3338, 0),
		    new Position(3008, 3337, 0),
		    new Position(3008, 3336, 0),
		    new Position(3008, 3335, 0),
		    new Position(3008, 3334, 0),
		    new Position(3008, 3333, 0),
		    new Position(3008, 3332, 0),
		    new Position(3008, 3331, 0),
		    new Position(3008, 3330, 0),
		    new Position(3008, 3329, 0),
		    new Position(3008, 3328, 0),
		    new Position(3008, 3327, 0),
		    new Position(3008, 3326, 0),
		    new Position(3007, 3325, 0),
		    new Position(3007, 3325, 0),
		    new Position(3007, 3324, 0),
		    new Position(3007, 3323, 0),
		    new Position(3008, 3322, 0),
		    new Position(3009, 3322, 0),
		    new Position(3010, 3322, 0),
		    new Position(3011, 3322, 0),
		    new Position(3012, 3322, 0),
		    new Position(3013, 3322, 0),
		    new Position(3013, 3323, 0),
		    new Position(3014, 3323, 0),
		    new Position(3015, 3323, 0),
		    new Position(3016, 3323, 0),
		    new Position(3017, 3323, 0),
		    new Position(3018, 3323, 0),
		    new Position(3019, 3323, 0),
		    new Position(3020, 3323, 0),
		    new Position(3021, 3323, 0),
		    new Position(3022, 3323, 0),
		    new Position(3023, 3323, 0),
		    new Position(3024, 3324, 0),
		    new Position(3025, 3325, 0),
		    new Position(3026, 3325, 0),
		    new Position(3027, 3326, 0),
		    new Position(3028, 3326, 0),
		    new Position(3029, 3326, 0),
		    new Position(3030, 3326, 0),
		    new Position(3031, 3326, 0),
		    new Position(3032, 3326, 0),
		    new Position(3033, 3326, 0),
		    new Position(3034, 3326, 0),
		    new Position(3035, 3326, 0),
		    new Position(3036, 3326, 0),
		    new Position(3037, 3326, 0),
		    new Position(3038, 3326, 0),
		    new Position(3039, 3326, 0),
		    new Position(3040, 3326, 0),
		    new Position(3041, 3326, 0),
		    new Position(3042, 3326, 0),
		    new Position(3043, 3326, 0),
		    new Position(3044, 3326, 0),
		    new Position(3045, 3326, 0),
		    new Position(3046, 3326, 0),
		    new Position(3047, 3326, 0),
		    new Position(3048, 3326, 0),
		    new Position(3049, 3326, 0),
		    new Position(3050, 3326, 0),
		    new Position(3051, 3326, 0),
		    new Position(3052, 3326, 0),
		    new Position(3053, 3326, 0),
		    new Position(3054, 3326, 0),
		    new Position(3055, 3326, 0),
		    new Position(3056, 3325, 0)
		};
	
	private MouseTrail trail = new MouseTrail(0, 255, 255, 4000, this);
	private MouseCursor cursor = new MouseCursor(52, 2, Color.RED, this);
	
	private Banker banker;
	private Cutter cutter;
	private Mouser mouser;
	private Walker walker;

	
	public void onStart() {
		banker = new Banker();
		banker.exchangeContext(getBot());
		
		cutter = new Cutter();
		cutter.exchangeContext(getBot());
		
		mouser = new Mouser();
		mouser.exchangeContext(getBot());
		
		walker = new Walker(path);
		walker.exchangeContext(getBot());
		
		cutter.addMouser(mouser);
		walker.addMouser(mouser);
		cutter.onStart();
	}
	
	
	private void bankAndBack() {
		settings.setRunning(false);
		walker.reversePath();
		walker.onStart();
		Sleep.waitCondition(() -> walker.finished(), random(1000,2000), 80000);
		
		banker.onStart();
		settings.setRunning(true);
		walker.setPath(path);
		walker.onStart();
		Sleep.waitCondition(() -> walker.finished(), random(1000,2000), 80000);
		cutter.onStart();
	}

	@Override
	public int onLoop() throws InterruptedException{
		int ret = cutter.onLoop();
		if (ret == 6666) {
			sleep(random(500, 7000));
			bankAndBack();
			return random(100,300);
		}
		return ret;
	}
	
	public void onPaint(Graphics2D g) {
		trail.paint(g);
		cursor.paint(g);
		for (Position p : path) {
			g.drawPolygon(p.getPolygon(bot));
		}
	}

}
