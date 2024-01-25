package UltimateLooter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

public final class Main extends Script {
	
    @Override
	public void onStart() {
		log("onStart");
		if(settings.areRoofsEnabled() == false) {
			getKeyboard().typeString("::toggleroofs");
		}
		
		JFrame frame = new gui();
		
		frame.pack(); //en tiiä tarviiko tätä
		
		frame.setVisible(true);
    }
	

	@Override
	public int onLoop(){
		return 1500;
	}
}