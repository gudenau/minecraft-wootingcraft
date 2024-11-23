package net.gudenau.minecraft.wootingcraft.impl;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.gudenau.minecraft.wootingcraft.api.RgbSession;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.Cleaner;

/**
 * The extra state used in the keybinding screen, used by
 * {@link net.gudenau.minecraft.wootingcraft.mixin.KeybindsScreenMixin KeybindsScreenMixin}.
 *
 * @hidden
 */
public final class KeybindsScreenState implements AutoCloseable {
    /**
     * Just in case something unforeseen happens, we use this to cleanup after ourselves.
     */
    private static final Cleaner CLEANER = Cleaner.create();

    /**
     * The session for changing keyboard colors.
     */
    private final RgbSession session;

    /**
     * Used as an attempt to prevent resource leaks and to prevent the keyboard from getting stuck.
     */
    private final Cleaner.Cleanable cleaner;

    /**
     * Tracks how many usages each key has.
     */
    private final Object2IntMap<InputUtil.Key> keyUsage = new Object2IntOpenHashMap<>();

    public KeybindsScreenState() {
        this.session = new RgbSession();
        this.cleaner = CLEANER.register(this, session::close);
    }

    /**
     * Sends the buffered updates to the keyboard.
     */
    public void flush() {
        session.flush();
    }

    /**
     * Resets all keys to defaults and resets the RGB colors back to black.
     */
    public void clear() {
        keyUsage.clear();
        session.clear();
    }

    /**
     * Sets a specific key to the correct color based on it's usage.
     *
     * @param key
     * @param count
     */
    private void setKey(@NotNull InputUtil.Key key, int count) {
        int color = switch(count) {
            // Black for 0
            case 0 -> 0x00_00_00;
            // Green for 1
            case 1 -> 0x00_FF_00;
            // Red for more than 1
            default -> 0xFF_00_00;
        };

        session.setKey(key, color);
    }

    /**
     * Marks a key as being used one more time and updates the buffered colors.
     *
     * @param key The key to bump
     */
    public void incrementKey(@NotNull InputUtil.Key key) {
        if(key.getCategory() != InputUtil.Type.KEYSYM) {
            return;
        }

        var count = keyUsage.compute(key, (k, value) -> value == null ? 1 : value + 1);
        setKey(key, count);
    }

    /**
     * Marks a key as being used one fewer time and updates the buffered colors.
     *
     * @param key The key to bump
     */
    public void decrementKey(@NotNull InputUtil.Key key) {
        if(key.getCategory() != InputUtil.Type.KEYSYM) {
            return;
        }

        var count = keyUsage.compute(key, (k, value) -> value == null ? 0 : Math.max(value - 1, 0));
        setKey(key, count);
    }

    /**
     * Resets the keyboard colors and frees the used native memory.
     */
    @Override
    public void close() {
        cleaner.clean();
    }
}
