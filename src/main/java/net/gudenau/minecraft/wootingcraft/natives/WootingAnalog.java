package net.gudenau.minecraft.wootingcraft.natives;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public final class WootingAnalog {
    private WootingAnalog() {
        throw new AssertionError();
    }

    public static final int WootingAnalog_KeycodeType_HID = 0,
        WootingAnalog_KeycodeType_ScanCode1 = 1,
        WootingAnalog_KeycodeType_VirtualKey = 2,
        WootingAnalog_KeycodeType_VirtualKeyTranslate = 3;

    public static final int WootingAnalogResult_Ok = 1,
        WootingAnalogResult_UnInitialized = -2000,
        WootingAnalogResult_NoDevices = -1999,
        WootingAnalogResult_DeviceDisconnected = -1998,
        WootingAnalogResult_Failure = -1997,
        WootingAnalogResult_InvalidArgument = -1996,
        WootingAnalogResult_NoPlugins = -1995,
        WootingAnalogResult_FunctionNotFound = -1994,
        WootingAnalogResult_NoMapping = -1993,
        WootingAnalogResult_NotAvailable = -1992,
        WootingAnalogResult_IncompatibleVersion = -1991,
        WootingAnalogResult_DLLNotFound = -1990;

    private static final Binder BINDER = Binder.of("wooting_analog_wrapper");

    private static final MethodHandle wooting_analog_initialise = BINDER.bind("wooting_analog_initialise", JAVA_INT);
    public static int wooting_analog_initialise() {
        try {
            return (int) wooting_analog_initialise.invokeExact();
        } catch(Throwable e) {
            throw new RuntimeException("Failed to invoke wooting_analog_initialise", e);
        }
    }

    private static final MethodHandle wooting_analog_set_keycode_mode = BINDER.bind("wooting_analog_set_keycode_mode", JAVA_INT, JAVA_INT);
    public static int wooting_analog_set_keycode_mode(int mode) {
        try {
            return (int) wooting_analog_set_keycode_mode.invokeExact(mode);
        } catch(Throwable e) {
            throw new RuntimeException("Failed to invoke wooting_analog_set_keycode_mode", e);
        }
    }

    private static final MethodHandle wooting_analog_read_full_buffer = BINDER.bind("wooting_analog_read_full_buffer", JAVA_INT, ADDRESS, ADDRESS, JAVA_INT);
    public static int wooting_analog_read_full_buffer(@NotNull ShortBuffer code_buffer, @NotNull FloatBuffer analog_buffer, int len) {
        try {
            return (int) wooting_analog_read_full_buffer.invokeExact(MemorySegment.ofBuffer(code_buffer), MemorySegment.ofBuffer(analog_buffer), len);
        } catch(Throwable e) {
            throw new RuntimeException("Failed to invoke wooting_analog_read_full_buffer", e);
        }
    }

    // Helpers.

    @NotNull
    public static String wootingErrorString(int error) {
        return switch(error) {
            case WootingAnalogResult_UnInitialized -> "UnInitialized";
            case WootingAnalogResult_NoDevices -> "NoDevices";
            case WootingAnalogResult_DeviceDisconnected -> "DeviceDisconnected";
            case WootingAnalogResult_Failure -> "Failure";
            case WootingAnalogResult_InvalidArgument -> "InvalidArgument";
            case WootingAnalogResult_NoPlugins -> "NoPlugins";
            case WootingAnalogResult_FunctionNotFound -> "FunctionNotFound";
            case WootingAnalogResult_NoMapping -> "NoMapping";
            case WootingAnalogResult_NotAvailable -> "NotAvailable";
            case WootingAnalogResult_IncompatibleVersion -> "IncompatibleVersion";
            case WootingAnalogResult_DLLNotFound -> "DLLNotFound";
            default -> "UNKNOWN(" + error + ')';
        };
    }

    private static int keyboards = -1;

    public static boolean foundKeyboard() {
        if(keyboards == -1) {
            keyboards = wooting_analog_initialise();
            if(keyboards < 0) {
                keyboards = 0;
            }
        }

        return keyboards > 0;
    }
}
