package net.gudenau.minecraft.wootingcraft.natives;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class WootingRgb {
    private WootingRgb() {
        throw new AssertionError();
    }

    public static final int WOOTING_RGB_ROWS = 6;
    public static final int WOOTING_RGB_COLS = 21;

    private static Binder BINDER = Binder.of("wooting-rgb-sdk");

    private static final MethodHandle wooting_rgb_reset_rgb = BINDER.bind("wooting_rgb_reset_rgb", JAVA_INT);
    public static boolean wooting_rgb_reset_rgb() {
        try {
            return ((int) wooting_rgb_reset_rgb.invokeExact()) != 0;
        } catch(Throwable e) {
            throw new RuntimeException("Failed to invoke wooting_rgb_reset_rgb", e);
        }
    }

    private static final MethodHandle wooting_rgb_array_update_keyboard = BINDER.bind("wooting_rgb_array_update_keyboard", JAVA_INT);
    public static boolean wooting_rgb_array_update_keyboard() {
        try {
            return ((int) wooting_rgb_array_update_keyboard.invokeExact()) != 0;
        } catch(Throwable e) {
            throw new RuntimeException("Failed to invoke wooting_rgb_array_update_keyboard", e);
        }
    }

    private static final MethodHandle wooting_rgb_array_set_full = BINDER.bind("wooting_rgb_array_set_full", JAVA_INT, ADDRESS);
    public static boolean wooting_rgb_array_set_full(@NotNull ByteBuffer buffer) {
        try {
            return ((int) wooting_rgb_array_set_full.invokeExact(MemorySegment.ofBuffer(buffer))) != 0;
        } catch(Throwable e) {
            throw new RuntimeException("Failed to invoke wooting_rgb_array_set_full", e);
        }
    }
}
