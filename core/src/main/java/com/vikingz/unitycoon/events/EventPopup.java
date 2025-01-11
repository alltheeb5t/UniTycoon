package com.vikingz.unitycoon.events;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vikingz.unitycoon.menus.PopupMenu;

/**
 * Creates a popup for events. It is used to display the event details and the user options.
 * This new class was implemented to complete FR_EVENT_DISPLAY.
 */
public class EventPopup {

    public PopupMenu getPopup() {
        return popup;
    }

    final PopupMenu popup;

    /**
     * Creates a new event popup
     * @param skin Skin used to style content
     * @param message Message used in the event
     * @param runnable Functionality of the close button
     */
    public EventPopup(Skin skin, String message, Runnable runnable) {

        popup = new PopupMenu(skin, message);
        popup.setupClose(runnable);
    }

    /**
     * Creates a new event popup with choices
     * @param skin Skin used to style content
     * @param message Message used in the event
     * @param leftRun Functionality of the left button
     * @param leftText Text on the left button
     * @param rightRun Functionality of the right button
     * @param rightText Text on the right button
     */
    public EventPopup(Skin skin, String message, Runnable leftRun, String leftText, Runnable rightRun, String rightText) {

        popup = new PopupMenu(skin, message);
        popup.setupButtons(leftRun, leftText, rightRun, rightText);
    }
}
