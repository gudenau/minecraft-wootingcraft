package net.gudenau.minecraft.wootingcraft.mixin;

import net.gudenau.minecraft.wootingcraft.api.AnalogKeyBinding;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {
    @Inject(
        method = "unpressAll",
        at = @At("RETURN")
    )
    private static void unpressAll(CallbackInfo ci) {
        AnalogKeyBinding.unpressAll();
    }
}
