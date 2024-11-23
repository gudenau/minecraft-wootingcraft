package net.gudenau.minecraft.wootingcraft.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.gudenau.minecraft.wootingcraft.api.AnalogKeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSystem.class, remap = false)
public class RenderSystemMixin {
    @Inject(
        method = "pollEvents",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/glfw/GLFW;glfwPollEvents()V",
            shift = At.Shift.AFTER
        )
    )
    private static void pollEvents(CallbackInfo ci) {
        AnalogKeyBinding.updatePressedStates();
    }
}
