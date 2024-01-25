package script;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import Core.Skeleton;
import sections.*;
import util.Sleep;

public final class TutorialIsland extends Skeleton {

    private final TutorialSection rsGuideSection = new RuneScapeGuideSection();
    private final TutorialSection survivalSection = new SurvivalSection();
    private final TutorialSection cookingSection = new CookingSection();
    private final TutorialSection questSection = new QuestSection();
    private final TutorialSection miningSection = new MiningSection();
    private final TutorialSection fightingSection = new FightingSection();
    private final TutorialSection bankSection = new BankSection();
    private final TutorialSection priestSection = new PriestSection();
    private final TutorialSection wizardSection = new WizardSection();

    @Override
    public void onStart() {
        rsGuideSection.exchangeContext(getBot());
        survivalSection.exchangeContext(getBot());
        cookingSection.exchangeContext(getBot());
        questSection.exchangeContext(getBot());
        miningSection.exchangeContext(getBot());
        fightingSection.exchangeContext(getBot());
        bankSection.exchangeContext(getBot());
        priestSection.exchangeContext(getBot());
        wizardSection.exchangeContext(getBot());

        Sleep.sleepUntil(() -> getClient().isLoggedIn() && myPlayer().isVisible() && myPlayer().isOnScreen(), 6000, 500);
    }

    @Override
    public final int onLoop() {
    	try {
        if (isTutorialIslandCompleted()) {
				sleep(random(3000,5000));
			
        	if(!Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
        		log("Walking bank");
            walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition());
            sleep(random(2000,3000));
            }
        	if(Banks.LUMBRIDGE_UPPER.contains(myPlayer())) {
        		bank.open();
        		Core.Support.Sleep.waitCondition(() -> bank.isOpen(), 200,3000);
        		widgets.interact(664, 28, 0 , "Continue");
        		Core.Support.Sleep.waitCondition(() -> !widgets.isVisible(664), 200,2000);
        		bank.close();
        		stop(true);
        		return -11;
        	}
            return 0;
        }

        switch (getTutorialSection()) {
            case 0:
            case 1:
                rsGuideSection.onLoop();
                break;
            case 2:
            case 3:
                survivalSection.onLoop();
                break;
            case 4:
            case 5:
                cookingSection.onLoop();
                break;
            case 6:
            case 7:
                questSection.onLoop();
                break;
            case 8:
            case 9:
                miningSection.onLoop();
                break;
            case 10:
            case 11:
            case 12:
                fightingSection.onLoop();
                break;
            case 14:
            case 15:
                bankSection.onLoop();
                break;
            case 16:
            case 17:
                priestSection.onLoop();
                break;
            case 18:
            case 19:
            case 20:
                wizardSection.onLoop();
                break;
        }
    	} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return 200;

    }

    private int getTutorialSection() {
    	if(getConfigs().get(2686) != 0) {
    		log("Wrong tutorial island");
    		System.exit(0);
    	}
        return getConfigs().get(406);
    }

    private boolean isTutorialIslandCompleted() {
        return getConfigs().get(281) == 1000 && myPlayer().isVisible();
    }
}
