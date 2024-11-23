package net.gudenau.minecraft.wootingcraft.api;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * A utility class that is used to map GLFW scancodes to ones for the Wooting APIs.
 */
public final class KeycodeMapper {
    private KeycodeMapper() {
        throw new AssertionError();
    }

    /**
     * The GLFW scancode to USB HID scancode map.
     */
    private static final Int2IntMap GLFW_TO_HID = Int2IntMaps.unmodifiable(new Int2IntOpenHashMap() {{
        put(GLFW_KEY_SPACE, 0x2C);
        put(GLFW_KEY_APOSTROPHE, 0x34);
        put(GLFW_KEY_COMMA, 0x36);
        put(GLFW_KEY_MINUS, 0x2D);
        put(GLFW_KEY_PERIOD, 0x37);
        put(GLFW_KEY_SLASH, 0x38);
        put(GLFW_KEY_0, 0x27);
        put(GLFW_KEY_1, 0x1E);
        put(GLFW_KEY_2, 0x1F);
        put(GLFW_KEY_3, 0x20);
        put(GLFW_KEY_4, 0x21);
        put(GLFW_KEY_5, 0x22);
        put(GLFW_KEY_6, 0x23);
        put(GLFW_KEY_7, 0x24);
        put(GLFW_KEY_8, 0x25);
        put(GLFW_KEY_9, 0x26);
        put(GLFW_KEY_SEMICOLON, 0x33);
        put(GLFW_KEY_EQUAL, 0x2E);
        put(GLFW_KEY_A, 0x04);
        put(GLFW_KEY_B, 0x05);
        put(GLFW_KEY_C, 0x06);
        put(GLFW_KEY_D, 0x07);
        put(GLFW_KEY_E, 0x08);
        put(GLFW_KEY_F, 0x09);
        put(GLFW_KEY_G, 0x0A);
        put(GLFW_KEY_H, 0x0B);
        put(GLFW_KEY_I, 0x0C);
        put(GLFW_KEY_J, 0x0D);
        put(GLFW_KEY_K, 0x0E);
        put(GLFW_KEY_L, 0x0F);
        put(GLFW_KEY_M, 0x10);
        put(GLFW_KEY_N, 0x11);
        put(GLFW_KEY_O, 0x12);
        put(GLFW_KEY_P, 0x13);
        put(GLFW_KEY_Q, 0x14);
        put(GLFW_KEY_R, 0x15);
        put(GLFW_KEY_S, 0x16);
        put(GLFW_KEY_T, 0x17);
        put(GLFW_KEY_U, 0x18);
        put(GLFW_KEY_V, 0x19);
        put(GLFW_KEY_W, 0x1A);
        put(GLFW_KEY_X, 0x1B);
        put(GLFW_KEY_Y, 0x1C);
        put(GLFW_KEY_Z, 0x1D);
        put(GLFW_KEY_LEFT_BRACKET, 0x2F);
        put(GLFW_KEY_BACKSLASH, 0x31);
        put(GLFW_KEY_RIGHT_BRACKET, 0x30);
        put(GLFW_KEY_GRAVE_ACCENT, 0x35);
        //TODO put(GLFW_KEY_WORLD_1, 0x);
        //TODO put(GLFW_KEY_WORLD_2, 0x);
        put(GLFW_KEY_ESCAPE, 0x29);
        put(GLFW_KEY_ENTER, 0x28);
        put(GLFW_KEY_TAB, 0x2B);
        put(GLFW_KEY_BACKSPACE, 0x2A);
        put(GLFW_KEY_INSERT, 0x49);
        put(GLFW_KEY_DELETE, 0x4C);
        put(GLFW_KEY_RIGHT, 0x4F);
        put(GLFW_KEY_LEFT, 0x50);
        put(GLFW_KEY_DOWN, 0x51);
        put(GLFW_KEY_UP, 0x52);
        put(GLFW_KEY_PAGE_UP, 0x4B);
        put(GLFW_KEY_PAGE_DOWN, 0x4E);
        put(GLFW_KEY_HOME, 0x4A);
        put(GLFW_KEY_END, 0x4D);
        put(GLFW_KEY_CAPS_LOCK, 0x39);
        put(GLFW_KEY_SCROLL_LOCK, 0x47);
        put(GLFW_KEY_NUM_LOCK, 0x53);
        put(GLFW_KEY_PRINT_SCREEN, 0x46);
        put(GLFW_KEY_PAUSE, 0x48);
        put(GLFW_KEY_F1, 0x3A);
        put(GLFW_KEY_F2, 0x3B);
        put(GLFW_KEY_F3, 0x3C);
        put(GLFW_KEY_F4, 0x3D);
        put(GLFW_KEY_F5, 0x3E);
        put(GLFW_KEY_F6, 0x3F);
        put(GLFW_KEY_F7, 0x40);
        put(GLFW_KEY_F8, 0x41);
        put(GLFW_KEY_F9, 0x42);
        put(GLFW_KEY_F10, 0x43);
        put(GLFW_KEY_F11, 0x44);
        put(GLFW_KEY_F12, 0x45);
        put(GLFW_KEY_F13, 0x68);
        put(GLFW_KEY_F14, 0x69);
        put(GLFW_KEY_F15, 0x6A);
        put(GLFW_KEY_F16, 0x6B);
        put(GLFW_KEY_F17, 0x6C);
        put(GLFW_KEY_F18, 0x6D);
        put(GLFW_KEY_F19, 0x6E);
        put(GLFW_KEY_F20, 0x6F);
        put(GLFW_KEY_F21, 0x70);
        put(GLFW_KEY_F22, 0x71);
        put(GLFW_KEY_F23, 0x72);
        put(GLFW_KEY_F24, 0x73);
        //TODO put(GLFW_KEY_F25, 0x);
        put(GLFW_KEY_KP_0, 0x62);
        put(GLFW_KEY_KP_1, 0x59);
        put(GLFW_KEY_KP_2, 0x5A);
        put(GLFW_KEY_KP_3, 0x5B);
        put(GLFW_KEY_KP_4, 0x5C);
        put(GLFW_KEY_KP_5, 0x5D);
        put(GLFW_KEY_KP_6, 0x5E);
        put(GLFW_KEY_KP_7, 0x5F);
        put(GLFW_KEY_KP_8, 0x60);
        put(GLFW_KEY_KP_9, 0x61);
        put(GLFW_KEY_KP_DECIMAL, 0x63);
        put(GLFW_KEY_KP_DIVIDE, 0x54);
        put(GLFW_KEY_KP_MULTIPLY, 0x55);
        put(GLFW_KEY_KP_SUBTRACT, 0x56);
        put(GLFW_KEY_KP_ADD, 0x57);
        put(GLFW_KEY_KP_ENTER, 0x58);
        put(GLFW_KEY_KP_EQUAL, 0x67);
        put(GLFW_KEY_LEFT_SHIFT, 0xE1);
        put(GLFW_KEY_LEFT_CONTROL, 0xE0);
        put(GLFW_KEY_LEFT_ALT, 0xE2);
        put(GLFW_KEY_LEFT_SUPER, 0xE3);
        put(GLFW_KEY_RIGHT_SHIFT, 0xE5);
        put(GLFW_KEY_RIGHT_CONTROL, 0xE4);
        put(GLFW_KEY_RIGHT_ALT, 0xE6);
        put(GLFW_KEY_RIGHT_SUPER, 0xE7);
        put(GLFW_KEY_MENU, 0x76);
    }});

    /**
     * The GLFW scancode to packed Wooting RGB positions. The lower 16 bits are the row while the higher 16 bits are the
     * column.
     */
    private static final Int2IntMap GLFW_TO_RGB = Int2IntMaps.unmodifiable(new Int2IntOpenHashMap() {{
        put(GLFW_KEY_ESCAPE, addr(0, 0));
        put(GLFW_KEY_F1, addr(2, 0));
        put(GLFW_KEY_F2, addr(3, 0));
        put(GLFW_KEY_F3, addr(4, 0));
        put(GLFW_KEY_F4, addr(5, 0));
        put(GLFW_KEY_F5, addr(6, 0));
        put(GLFW_KEY_F6, addr(7, 0));
        put(GLFW_KEY_F7, addr(8, 0));
        put(GLFW_KEY_F8, addr(9, 0));
        put(GLFW_KEY_F9, addr(10, 0));
        put(GLFW_KEY_F10, addr(11, 0));
        put(GLFW_KEY_F11, addr(12, 0));
        put(GLFW_KEY_F12, addr(13, 0));
        put(GLFW_KEY_PRINT_SCREEN, addr(14, 0));
        put(GLFW_KEY_PAUSE, addr(15, 0));
        put(GLFW_KEY_SCROLL_LOCK, addr(16, 0));
        put(GLFW_KEY_GRAVE_ACCENT, addr(0, 1));
        put(GLFW_KEY_1, addr(1, 1));
        put(GLFW_KEY_2, addr(2, 1));
        put(GLFW_KEY_3, addr(3, 1));
        put(GLFW_KEY_4, addr(4, 1));
        put(GLFW_KEY_5, addr(5, 1));
        put(GLFW_KEY_6, addr(6, 1));
        put(GLFW_KEY_7, addr(7, 1));
        put(GLFW_KEY_8, addr(8, 1));
        put(GLFW_KEY_9, addr(9, 1));
        put(GLFW_KEY_0, addr(10, 1));
        put(GLFW_KEY_MINUS, addr(11, 1));
        put(GLFW_KEY_EQUAL, addr(12, 1));
        put(GLFW_KEY_BACKSPACE, addr(13, 1));
        put(GLFW_KEY_INSERT, addr(14, 1));
        put(GLFW_KEY_HOME, addr(15, 1));
        put(GLFW_KEY_PAGE_UP, addr(16, 1));
        put(GLFW_KEY_NUM_LOCK, addr(17, 1));
        put(GLFW_KEY_KP_DIVIDE, addr(18, 1));
        put(GLFW_KEY_KP_MULTIPLY, addr(19, 1));
        put(GLFW_KEY_KP_SUBTRACT, addr(20, 1));
        put(GLFW_KEY_TAB, addr(0, 2));
        put(GLFW_KEY_Q, addr(1, 2));
        put(GLFW_KEY_W, addr(2, 2));
        put(GLFW_KEY_E, addr(3, 2));
        put(GLFW_KEY_R, addr(4, 2));
        put(GLFW_KEY_T, addr(5, 2));
        put(GLFW_KEY_Y, addr(6, 2));
        put(GLFW_KEY_U, addr(7, 2));
        put(GLFW_KEY_I, addr(8, 2));
        put(GLFW_KEY_O, addr(9, 2));
        put(GLFW_KEY_P, addr(10, 2));
        put(GLFW_KEY_LEFT_BRACKET, addr(11, 2));
        put(GLFW_KEY_RIGHT_BRACKET, addr(12, 2));
        put(GLFW_KEY_BACKSLASH, addr(13, 2));
        put(GLFW_KEY_DELETE, addr(14, 2));
        put(GLFW_KEY_END, addr(15, 2));
        put(GLFW_KEY_PAGE_DOWN, addr(16, 2));
        put(GLFW_KEY_KP_7, addr(17, 2));
        put(GLFW_KEY_KP_8, addr(18, 2));
        put(GLFW_KEY_KP_9, addr(19, 2));
        put(GLFW_KEY_KP_ADD, addr(20, 2));
        put(GLFW_KEY_CAPS_LOCK, addr(0, 3));
        put(GLFW_KEY_A, addr(1, 3));
        put(GLFW_KEY_S, addr(2, 3));
        put(GLFW_KEY_D, addr(3, 3));
        put(GLFW_KEY_F, addr(4, 3));
        put(GLFW_KEY_G, addr(5, 3));
        put(GLFW_KEY_H, addr(6, 3));
        put(GLFW_KEY_J, addr(7, 3));
        put(GLFW_KEY_K, addr(8, 3));
        put(GLFW_KEY_L, addr(9, 3));
        put(GLFW_KEY_SEMICOLON, addr(10, 3));
        put(GLFW_KEY_APOSTROPHE, addr(11, 3));
        put(GLFW_KEY_ENTER, addr(13, 3));
        put(GLFW_KEY_KP_4, addr(17, 3));
        put(GLFW_KEY_KP_5, addr(18, 3));
        put(GLFW_KEY_KP_6, addr(19, 3));
        put(GLFW_KEY_LEFT_SHIFT, addr(0, 4));
        put(GLFW_KEY_Z, addr(2, 4));
        put(GLFW_KEY_X, addr(3, 4));
        put(GLFW_KEY_C, addr(4, 4));
        put(GLFW_KEY_V, addr(5, 4));
        put(GLFW_KEY_B, addr(6, 4));
        put(GLFW_KEY_N, addr(7, 4));
        put(GLFW_KEY_M, addr(8, 4));
        put(GLFW_KEY_COMMA, addr(9, 4));
        put(GLFW_KEY_PERIOD, addr(10, 4));
        put(GLFW_KEY_SLASH, addr(11, 4));
        put(GLFW_KEY_RIGHT_SHIFT, addr(13, 4));
        put(GLFW_KEY_UP, addr(15, 4));
        put(GLFW_KEY_KP_1, addr(17, 4));
        put(GLFW_KEY_KP_2, addr(18, 4));
        put(GLFW_KEY_KP_3, addr(19, 4));
        put(GLFW_KEY_KP_ENTER, addr(20, 4));
        put(GLFW_KEY_LEFT_CONTROL, addr(0, 5));
        put(GLFW_KEY_LEFT_SUPER, addr(1, 5));
        put(GLFW_KEY_LEFT_ALT, addr(2, 5));
        put(GLFW_KEY_SPACE, addr(6, 5));
        put(GLFW_KEY_RIGHT_ALT, addr(10, 5));
        put(GLFW_KEY_RIGHT_SUPER, addr(11, 5));
        put(GLFW_KEY_RIGHT_CONTROL, addr(13, 5));
        put(GLFW_KEY_LEFT, addr(14, 5));
        put(GLFW_KEY_DOWN, addr(15, 5));
        put(GLFW_KEY_RIGHT, addr(16, 5));
        put(GLFW_KEY_KP_0, addr(18, 5));
        put(GLFW_KEY_KP_DECIMAL, addr(19, 5));
    } static int addr(int col, int row) { return row | (col << 16);} });

    /**
     * Converts a GLFW scancode to a HID scancode.
     *
     * @param glfw The GLFW scancode to convert
     * @return The USB HID scancode or -1 if it can't be mapped
     */
    public static int glfwToHid(int glfw) {
        return GLFW_TO_HID.getOrDefault(glfw, -1);
    }

    /**
     * Converts a GLFW scancode to a RGB coordinate.
     *
     * @param glfw The GLFW scancode to convert
     * @return The RGB coordinate scancode or -1 if it can't be mapped
     */
    public static int glfwToRgb(int glfw) {
        return GLFW_TO_RGB.getOrDefault(glfw, -1);
    }
}
