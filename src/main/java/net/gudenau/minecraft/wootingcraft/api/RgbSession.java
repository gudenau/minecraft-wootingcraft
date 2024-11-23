package net.gudenau.minecraft.wootingcraft.api;

import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

import static net.gudenau.minecraft.wootingcraft.natives.WootingRgb.*;

/**
 * A helper to manage the state of RGB keyboards. All changes are buffered and sent to the keyboard when {@link #flush}
 * is called.
 */
public final class RgbSession implements AutoCloseable {
    /**
     * A buffer to hold the RGB values of the keyboard.
     *
     * @hidden
     */
    private final ByteBuffer colorBuffer = MemoryUtil.memCalloc(WOOTING_RGB_ROWS * WOOTING_RGB_COLS * 3);

    /**
     * Clears the buffer to the default state of every key being off.
     */
    public void clear() {
        MemoryUtil.memSet(colorBuffer, 0);
    }

    /**
     * Sets a specific key to be a specific color. The color is format RGB, 0xXX_RR_GG_BB.
     *
     * @param key The key to set
     * @param color The RGB color
     */
    public void setKey(@NotNull InputUtil.Key key, int color) {
        if(key.getCategory() != InputUtil.Type.KEYSYM) {
            return;
        }

        setKey(key.getCode(), color);
    }

    /**
     * Sends the buffer to the keyboard for display.
     */
    public void flush() {
        wooting_rgb_array_set_full(colorBuffer);
        wooting_rgb_array_update_keyboard();
    }

    /**
     * Internal version of {@link #setKey(InputUtil.Key, int)}.
     *
     * @param scancode The GLFW scancode to set
     * @param color The color to set
     *
     * @hidden
     */
    // These are just to make the colorBuffer ops look a little nicer.
    @SuppressWarnings({"PointlessArithmeticExpression", "PointlessBitwiseExpression"})
    private void setKey(int scancode, int color) {
        var index = KeycodeMapper.glfwToRgb(scancode);
        if(index == -1) {
            return;
        }

        var row = index & 0xFFFF;
        var column = (index >>> 16) & 0xFFFF;
        index = (column + row * WOOTING_RGB_COLS) * 3;
        colorBuffer.put(index + 0, (byte) ((color >>> 16) & 0xFF));
        colorBuffer.put(index + 1, (byte) ((color >>>  8) & 0xFF));
        colorBuffer.put(index + 2, (byte) ((color >>>  0) & 0xFF));
    }

    /**
     * Resets the keyboard to the user's preferred color scheme and releases native memory.
     */
    @Override
    public void close() {
        wooting_rgb_reset_rgb();
        MemoryUtil.memFree(colorBuffer);
    }
}
