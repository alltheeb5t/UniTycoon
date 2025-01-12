package com.vikingz.unitycoon.headless;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.vikingz.unitycoon.global.GameConfig;
import com.vikingz.unitycoon.global.GameConfigManager;

public class GameConfigTest extends TestSuper {

    /**
     * Attempt to save a new value to the config file, save it and then load the saved value
     * Relates to: FR_MUTEABLE
     */
    @Test
    public void testConfigFileLoadSave() {
        FileHandle confFile = Gdx.files.internal("config/gameconf.bin");

        // Test initial load
        assertAll("Test that file load ok", () -> GameConfigManager.loadGameConfig());
        float initialSoundVolume = GameConfig.getInstance().SoundVolumeValue;

        // Attempt to save config to file
        GameConfig.getInstance().SoundVolumeValue = 300;
        assertAll("Test that file saving is ok", () -> GameConfigManager.saveGameConfig());
        
        assertTrue(confFile.exists(), "Check that save file has been created");
        
        // Load and confirm retrieved data is as expected
        GameConfig.getInstance().SoundVolumeValue = -300;  // Set bogus value to check this gets overwritten

        GameConfigManager.loadGameConfig();
        assertEquals(300, GameConfig.getInstance().SoundVolumeValue, "Check that saved value was loaded successfully");

        // Cleanup and reset sound volume to initial value
        GameConfig.getInstance().SoundVolumeValue = initialSoundVolume;
        GameConfigManager.saveGameConfig();
    }

    /**
     * Confirm that a basic load operation overwrites existing stored config values
     */
    @Test
    public void testConfigLoadOnly() {
        // Load and confirm retrieved data is as expected
        GameConfig.getInstance().SoundVolumeValue = -300;  // Set bogus value to check this gets overwritten

        GameConfigManager.loadGameConfig();
        assertNotEquals(-300, GameConfig.getInstance().SoundVolumeValue, "Check that configuration is loaded successfully");
    }
}
