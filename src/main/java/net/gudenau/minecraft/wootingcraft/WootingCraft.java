package net.gudenau.minecraft.wootingcraft;

import net.fabricmc.api.ModInitializer;

import net.gudenau.minecraft.wootingcraft.natives.Binder;
import net.gudenau.minecraft.wootingcraft.natives.WootingAnalog;
import net.gudenau.minecraft.wootingcraft.natives.WootingRgb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.gudenau.minecraft.wootingcraft.natives.WootingAnalog.*;
import static net.gudenau.minecraft.wootingcraft.natives.WootingRgb.*;

public final class WootingCraft implements ModInitializer {
    public static final String MOD_ID = "gud_wootingcraft";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        if(!Binder.supported()) {
            LOGGER.info("Your platform is unsupported, disabling WootingCraft");
            return;
        }

        if(!WootingAnalog.foundKeyboard()) {
            LOGGER.info("Did not find a Wooting keyboard, disabling WootingCraft");
            return;
        }

        LOGGER.info("A Wooting keyboard was found, enjoy WootingCraft!");

        // This is the default, but make sure it's the correct one.
        var result = wooting_analog_set_keycode_mode(WootingAnalog_KeycodeType_HID);
        if(result != WootingAnalogResult_Ok) {
            throw new RuntimeException("Failed to set Wooting analog keycode type: " + wootingErrorString(result));
        }

        wooting_rgb_reset_rgb();
        // Try to be nice to the user and have a last ditch attempt to reset the RGB values in case of a game crash.
        Runtime.getRuntime().addShutdownHook(new Thread(WootingRgb::wooting_rgb_reset_rgb));
    }
}
