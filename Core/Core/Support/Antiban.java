package Core.Support;

import org.osbot.rs07.script.Script;

public class Antiban extends Script {

	public void CameraUp() {

		if (camera.getPitchAngle() < 50) {
			log("Moving camera angle");
			int targetPitch = random(50, 67);
			log(targetPitch);
			camera.movePitch(targetPitch);
		}
	}

	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
