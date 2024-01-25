package sections;

import org.osbot.rs07.api.Configs;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.script.MethodProvider;

import util.Readingfromweb;
import util.Sleep;
import util.event.DisableAudioEvent;
import util.event.EnableFixedModeEvent;
import util.event.ToggleRoofsHiddenEvent;
import util.event.ToggleShiftDropEvent;
import util.widget.CachedWidget;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public final class RuneScapeGuideSection extends TutorialSection {
    private enum UsernameCheckStatus {
        NOT_AVAILABLE(0),
        CHECKING(1),
        AVAILABLE(2);

        int bitNum;

        UsernameCheckStatus(int bitNum) {
            this.bitNum = bitNum;
        }

        static UsernameCheckStatus getUsernameCheckStatus(Configs configs) {
            int configVal = configs.get(1042);

            for (UsernameCheckStatus checkStatus : values()) {
                if ((configVal & (1 << checkStatus.bitNum)) != 0) {
                    return checkStatus;
                }
            }

            return null;
        }
    }

    private final CachedWidget inputNameWidget = new CachedWidget(w -> w.getMessage().contains("Please pick a unique display name"));
    private final CachedWidget setNameWidget = new CachedWidget("Set name");
    private final CachedWidget lookNameWidget = new CachedWidget("Look up name");
    private final CachedWidget chooseDisplayNameWidget = new CachedWidget("Choose display name");
    private final CachedWidget suggestionsWidget = new CachedWidget("one of our suggestions");
    private final CachedWidget creationScreenWidget = new CachedWidget("Head");
    private final CachedWidget experienceWidget = new CachedWidget("What's your experience with Old School Runescape?");

    private boolean isAudioDisabled;

    public RuneScapeGuideSection() {
        super("Gielinor Guide");
    }

    @Override
    public final void onLoop() throws InterruptedException {
        if (pendingContinue()) {
            selectContinue();
            return;
        }

        switch (getProgress()) {
            case 0:
            case 1:
            case 2:
                if (chooseDisplayNameWidget.isVisible(getWidgets())) {
                    setDisplayName();
                } else if (creationScreenWidget.isVisible(getWidgets())) {
                    createRandomCharacter();
                } else if (experienceWidget.isVisible(getWidgets())) {
                    if (getDialogues().completeDialogue("I am brand new! This is my first time here.")) {
                        Sleep.sleepUntil(() -> !experienceWidget.isVisible(getWidgets()), 2000, 600);
                    }
                } else {
                    talkToInstructor();
                }
                break;
            case 3:
                getTabs().open(Tab.SETTINGS);
                break;
            case 10:
                if (!EnableFixedModeEvent.isFixedModeEnabled(this)) {
                    execute(new EnableFixedModeEvent());
                } else if (!isAudioDisabled) {
                    isAudioDisabled = disableAudio();
                } else if (!getSettings().areRoofsEnabled()) {
                    toggleRoofsHidden();
                } else if (!getSettings().isShiftDropActive()) {
                    toggleShiftDrop();
                } else if (getObjects().closest("Door").interact("Open")) {
                    Sleep.sleepUntil(() -> getProgress() != 10, 5000, 600);
                }
                break;
            default:
                talkToInstructor();
                break;
        }
    }

    private void setDisplayName() throws InterruptedException {
        UsernameCheckStatus checkStatus = UsernameCheckStatus.getUsernameCheckStatus(getConfigs());

        if (checkStatus == null) {
            log("Couldn't determine username check status");
            getBot().getScriptExecutor().stop();
            return;
        }

        switch (checkStatus) {
            case NOT_AVAILABLE:
                if (suggestionsWidget.isVisible(getWidgets())) {
                }
                	widgets.interact(558, 8, "Look up name");
                	sleep(random(1500,2000));
                if(inputNameWidget.isVisible(getWidgets()) && getKeyboard().typeString(generateRandomString(), true)) {
                	sleep(random(2000,3000));
                    Sleep.sleepUntil(() -> UsernameCheckStatus.getUsernameCheckStatus(getConfigs()) == UsernameCheckStatus.CHECKING, 2000, 100);
                }
                break;
            case CHECKING:
                Sleep.sleepUntil(() -> UsernameCheckStatus.getUsernameCheckStatus(getConfigs()) != UsernameCheckStatus.CHECKING, 2000, 100);
                break;
            case AVAILABLE:
                if (setNameWidget.interact(getWidgets())) {
                    Sleep.sleepUntil(
                            () -> !chooseDisplayNameWidget.isVisible(getWidgets()),
                            8000,
                            600
                    );
                }
                break;
        }
    }

    private String generateRandomString() {
    	Readingfromweb read = new Readingfromweb();
    	return read.returnstring();
    }

    private void createRandomCharacter() throws InterruptedException {
        if (new Random().nextInt(2) == 1) {
            getWidgets().getWidgetContainingText("Female").interact();
        }

        final RS2Widget[] childWidgets = getWidgets().getWidgets(creationScreenWidget.get(getWidgets()).get().getRootId());
        Collections.shuffle(Arrays.asList(childWidgets));

        for (final RS2Widget childWidget : childWidgets) {
            if (childWidget.getToolTip() == null) {
                continue;
            }
            if (childWidget.getToolTip().contains("Change") || childWidget.getToolTip().contains("Recolour")) {
                clickRandomTimes(childWidget);
            }
        }

        if (getWidgets().getWidgetContainingText("Accept").interact()) {
            Sleep.sleepUntil(() -> !creationScreenWidget.isVisible(getWidgets()), 3000, 600);
        }
    }

    private void clickRandomTimes(final RS2Widget widget) throws InterruptedException {
        int clickCount = new Random().nextInt(4);

        for (int i = 0; i < clickCount; i++) {
            if (widget.interact()) {
                MethodProvider.sleep(150);
            }
        }
    }

    private boolean disableAudio() {
        Event disableAudioEvent = new DisableAudioEvent();
        execute(disableAudioEvent);
        return disableAudioEvent.hasFinished();
    }

    private boolean toggleRoofsHidden() {
        Event toggleRoofsHiddenEvent = new ToggleRoofsHiddenEvent();
        execute(toggleRoofsHiddenEvent);
        return toggleRoofsHiddenEvent.hasFinished();
    }

    private boolean toggleShiftDrop() {
        Event toggleShiftDrop = new ToggleShiftDropEvent();
        execute(toggleShiftDrop);
        return toggleShiftDrop.hasFinished();
    }
}