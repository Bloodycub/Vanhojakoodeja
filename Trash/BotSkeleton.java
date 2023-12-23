package Trash;
import org.osbot.rs07.script.MethodProvider;



	public abstract class BotSkeleton extends MethodProvider {
	

    public abstract void onStart() throws InterruptedException;
	

    public abstract int onLoop() throws InterruptedException;
   

}

//Ei saa olla overridejä
//Katso woodcutting.java