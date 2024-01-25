package Core.Support;

import org.osbot.rs07.script.Script;

public class Cameraup extends Script {
	
	
    	public void onStart() throws InterruptedException {
    	if (camera.getPitchAngle() < 44) {
    	    log("Moving camera angle");
    	    int targetPitch = random(44, 67);
    	    log(targetPitch);
    	    if (camera.movePitch(targetPitch)) {}}
    	camera.toTop();
    	onExit();
    	
    	}

		@Override
		public int onLoop() throws InterruptedException {
			// TODO Auto-generated method stub
			return 0;
		}
}