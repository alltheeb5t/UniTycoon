package com.vikingz.unitycoon.global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * This class is to simplify the Skin loading process, and allow us to make changes without 
 * needing to edit every ui element.
 
 * This class has been refactored slightly to make the code more readable, however it is largely unchanged.
 */
public class GameSkins {

    // Skins loaded from assets
    final Skin defaultSkin;
    final Skin quantumSkin;

    /**
     * Constructor creates and loads GameSkins from assets files
     */
    public GameSkins(){
        //Default Theme Glassy ui
        defaultSkin = new Skin(Gdx.files.internal("glassy-ui/skin/glassy-ui.json"));
        //Theme Quantum Horizon ui
        quantumSkin = new Skin(Gdx.files.internal("quantum-ui/skin/quantum-horizon-ui.json"));
    }


    public Skin getDefaultSkin(){
        return defaultSkin;
    }

    public Skin getQuantumSkin(){
        return quantumSkin;
    }
}
