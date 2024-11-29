package com.vikingz.unitycoon.events;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.vikingz.unitycoon.menus.PopupMenu;

public class EventPopup {

    private final PopupMenu popup;

    /**
     * Creates a new event popup
     *
     */
    public EventPopup(Skin skin, String message, Runnable button, String buttonText) {

        popup = new PopupMenu(skin, message);
        popup.setupRightBtn(button, buttonText);
    }

    /**
     * Creates a new event popup with choices
     *
     */
    public EventPopup(Skin skin, String message, Runnable leftRun, String leftText, Runnable rightRun, String rightText) {

        popup = new PopupMenu(skin, message);
        popup.setupButtons(leftRun, leftText, rightRun, rightText);
    }

}
