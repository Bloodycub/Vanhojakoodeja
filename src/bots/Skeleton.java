package bots;
import org.osbot.rs07.script.MethodProvider;



	public abstract class Skeleton extends MethodProvider {
	

    public abstract void onStart() throws InterruptedException;
	

    public abstract void onLoop() throws InterruptedException;
   

}