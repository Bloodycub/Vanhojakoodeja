package Trash;

import Mainclass.main.handlers.Handler;
import CockKiller.main.handlers.KillHandler;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;



public class Mainclass extends Script {

    private final Skill[] skillsToTrack = new Skill[]{Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.HITPOINTS, Skill.RANGED, Skill.MAGIC, Skill.PRAYER};
    private Painter painter;
    private Handler stateHandler = new KillHandler(this);

    @Override
    public void onStart() {
        painter = new Painter(this, getName(), getVersion(), skillsToTrack);
        for (Skill skill : skillsToTrack) {
            getExperienceTracker().start(skill);
        }
    }

    @Override
    public int onLoop() throws InterruptedException {
        if (stateHandler.isStopped()) {
            stop();
        } else {
            stateHandler.handleNextState();
        }
        return 200;
    }

    @Override
    public void onExit() {

    }

    public void onPaint(Graphics2D g) {
        painter.setTimer(stateHandler.getTimer());
        painter.setPaintStrings(stateHandler.getPaintStrings());
        painter.paintTo(g);
    }

}