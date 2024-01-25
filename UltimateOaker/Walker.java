package UltimateOaker;
import java.util.LinkedList;


import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Condition;
import org.osbot.rs07.event.WalkingEvent;

public class Walker extends Script {
	
	LinkedList<Position> path = new LinkedList<>();
	
	WalkingEvent w;
	
	Mouser mouser;
	
	public Walker(Position[] pathArray) {
		setPath(pathArray);
	}
	
	public void addMouser(Mouser m) {
		mouser = m;
	}
	
	public Walker(LinkedList<Position> pathList) {
		this.path = pathList;
	}
	
	public void setPath(Position[] pathArray) {
		for (Position pos : pathArray)
			path.add(pos);
	}
	
	
	public void reversePath() {
		LinkedList<Position> help = new LinkedList<>();
		for (int i = path.size() - 1; i >= 0; i--) {
			help.add(path.get(i));
		}
		path = help;
	}
	
	public boolean finished() {
		return myPlayer().getPosition().distance(path.getLast()) <= 5;
	}
	
	
	private int findClosestIndex() {
		
		int closestDist = 1000;
		int closestIndex = 0;
		Position currentPos = myPlayer().getPosition();
		
		for (int i = 0; i < path.size(); i++) {
			int dist = currentPos.distance(path.get(i));
			if (dist < closestDist) {
				closestDist = dist;
				closestIndex = i;
			}
		}
		
		return closestIndex;
	}
	
	@Override
	public void onStart() {
		w = new WalkingEvent();
		int index = findClosestIndex();
		for (int i = index; i >= 0; i--) {
			path.remove(i);
		}
		w.setPath(path);
		w.setMiniMapDistanceThreshold(5);
		w.setMinDistanceThreshold(random(3,7));
		w.setOperateCamera(false);
		w.setBreakCondition(new Condition() {
			@Override
			public boolean evaluate() {
				return myPlayer().getPosition().distance(path.getLast()) <= 5;
			}
		});
		if (mouser != null) mouser.moveMouseIn();
		if (inventory.getEmptySlots() < 10) 
			w.setEnergyThreshold(100);
		else
			w.setEnergyThreshold(20);

		execute(w);
		
	}
	
	@Override
	public int onLoop(){
		return 100;
	}

}
