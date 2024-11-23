package net.gudenau.minecraft.wootingcraft.api;

import net.gudenau.minecraft.wootingcraft.mixin.KeyBindingAccessor;
import net.gudenau.minecraft.wootingcraft.natives.WootingAnalog;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A keybinding that supports analog inputs. The original Minecraft digital input subsystem works as normal, to get the
 * analog value you need to call {@link #pressedAmount()}.
 */
public final class AnalogKeyBinding extends KeyBinding {
    /**
     * A list of all created analog bindings.
     */
    private static final List<AnalogKeyBinding> ANALOG_BINDINGS = new ArrayList<>();

    /**
     * Called by {@link net.gudenau.minecraft.wootingcraft.mixin.RenderSystemMixin} to update the analog keyboard state
     * every frame.
     *
     * @hidden
     */
    public static void updatePressedStates() {
        var minecraft = MinecraftClient.getInstance();
        // Prevent movement when there is a screen open/window is not in focus
        if(
            !minecraft.mouse.isCursorLocked() ||
            minecraft.currentScreen != null ||
            !minecraft.isWindowFocused()
        ) {
            unpressAll();
            return;
        }

        try(var stack = MemoryStack.stackPush()) {
            // 256 should be big enough, not like you are going to press every single key anyway.
            final var bufferSize = 256;
            var codeBuffer = stack.mallocShort(bufferSize);
            var analogBuffer = stack.mallocFloat(bufferSize);

            var result = WootingAnalog.wooting_analog_read_full_buffer(codeBuffer, analogBuffer, bufferSize);
            if(result < 0) {
                throw new RuntimeException("Failed to read analog Wooting keys: " + WootingAnalog.wootingErrorString(result));
            }

            // Convert the analog bindings into a map, excluding any of the ones that are unbound/bound to the mouse
            var keys = ANALOG_BINDINGS.stream()
                .map((binding) -> {
                    var key = binding.getKey();
                    if(key.getCategory() != InputUtil.Type.KEYSYM) {
                        return null;
                    }
                    var keyCode = KeycodeMapper.glfwToHid(key.getCode());
                    if(keyCode == -1) {
                        return null;
                    }

                    return Map.entry((short) keyCode, binding);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

            // Set the pressed state of the keys returned by the API, the first read after a release is 0 by contract so
            // we don't have to worry about that.
            int updated = 0;
            for(int i = 0; i < result && updated < keys.size(); i++) {
                var key = keys.get(codeBuffer.get(i));
                if(key != null) {
                    updated++;
                    key.pressedAmount = analogBuffer.get(i);
                }
            }
        }
    }

    /**
     * Sets all key states to be released, called by {@link net.gudenau.minecraft.wootingcraft.mixin.KeyBindingMixin}.
     *
     * @hidden
     */
    public static void unpressAll() {
        ANALOG_BINDINGS.forEach((binding) -> binding.pressedAmount = 0);
    }

    /**
     * The amount that this key is pressed.
     */
    private float pressedAmount;

    /**
     * Creates a new analog key binding, the only supported type is {@link InputUtil.Type#KEYSYM KEYSYM}.
     *
     * @param translationKey The translation key to use
     * @param code The default keycode
     * @param category The category of this binding
     */
    public AnalogKeyBinding(String translationKey, int code, String category) {
        super(translationKey, InputUtil.Type.KEYSYM, code, category);

        ANALOG_BINDINGS.add(this);
    }

    /**
     * Gets the amount this key is pressed.
     *
     * @return The pressed amount
     */
    public float pressedAmount() {
        return pressedAmount;
    }

    /**
     * Gets the key for this binding, a simple helper method.
     *
     * @return The currently bound key
     */
    @NotNull
    private InputUtil.Key getKey() {
        return ((KeyBindingAccessor)(Object) this).getBoundKey();
    }
}
