package main;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Scripts.*;
import util.Sleep;


public final class Core extends Script {
    public static final String VERSION = "v6.2";

    
    private final TutorialSection bankSection = new BankSection();


    @Override
    public void onStart() {
     
        bankSection.exchangeContext(getBot());

        Sleep.sleepUntil(() -> getClient().isLoggedIn() && myPlayer().isVisible() && myPlayer().isOnScreen(), 6000, 500);
    }

    @Override
    public final int onLoop() throws InterruptedException {
        if (isTutorialIslandCompleted()) {
            stop(true);
            return 0;
        }

        switch (getTutorialSection()) {
            case 0:
            
            case 15:
                bankSection.onLoop();
                break;
        }
        return 200;
    }

    private int getTutorialSection() {
        return getConfigs().get(406);
    }

    private boolean isTutorialIslandCompleted() {
        return getConfigs().get(281) == 1000 && myPlayer().isVisible();
    }
}
