package net.gudenau.minecraft.wootingcraft.mixin;

import net.gudenau.minecraft.wootingcraft.impl.KeybindsScreenState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeybindsScreen.class)
public abstract class KeybindsScreenMixin extends GameOptionsScreen {
    @Shadow @Nullable public KeyBinding selectedKeyBinding;

    @Unique
    private KeybindsScreenState gud_wootingcraft$state;

    public KeybindsScreenMixin() {
        super(null, null, null);
        throw new AssertionError();
    }

    @Inject(
        method = "<init>",
        at = @At("RETURN")
    )
    private void init(Screen parent, GameOptions gameOptions, CallbackInfo ci) {
        gud_wootingcraft$state = new KeybindsScreenState();
        for(KeyBinding binding : gameOptions.allKeys) {
            var boundKey = ((KeyBindingAccessor) binding).getBoundKey();
            gud_wootingcraft$state.incrementKey(boundKey);
        }
        gud_wootingcraft$state.flush();
    }

    @Inject(
        method = "mouseClicked",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;setBoundKey(Lnet/minecraft/client/util/InputUtil$Key;)V",
            shift = At.Shift.BEFORE
        )
    )
    private void mouseClickedUnbind(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> ci) {
        var boundKey = ((KeyBindingAccessor) selectedKeyBinding).getBoundKey();
        gud_wootingcraft$state.decrementKey(boundKey);
    }

    @Inject(
        method = "mouseClicked",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;setBoundKey(Lnet/minecraft/client/util/InputUtil$Key;)V",
            shift = At.Shift.AFTER
        )
    )
    private void mouseClickedBind(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> ci) {
        var boundKey = ((KeyBindingAccessor) selectedKeyBinding).getBoundKey();
        gud_wootingcraft$state.incrementKey(boundKey);
        gud_wootingcraft$state.flush();
    }

    @Inject(
        method = "keyPressed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;setBoundKey(Lnet/minecraft/client/util/InputUtil$Key;)V",
            shift = At.Shift.BEFORE
        )
    )
    private void keyPressedUnbind(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> ci) {
        var boundKey = ((KeyBindingAccessor) selectedKeyBinding).getBoundKey();
        gud_wootingcraft$state.decrementKey(boundKey);
    }

    @Inject(
        method = "keyPressed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;setBoundKey(Lnet/minecraft/client/util/InputUtil$Key;)V",
            shift = At.Shift.AFTER
        )
    )
    private void keyPressedBind(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> ci) {
        var boundKey = ((KeyBindingAccessor) selectedKeyBinding).getBoundKey();
        gud_wootingcraft$state.incrementKey(boundKey);
        gud_wootingcraft$state.flush();
    }

    // private synthetic method_60342(Lnet/minecraft/client/gui/widget/ButtonWidget;)V
    @Inject(
        method = "method_60342",
        at = @At("TAIL")
    )
    private void resetAll(ButtonWidget buttonWidget, CallbackInfo ci) {
        gud_wootingcraft$state.clear();
        for(KeyBinding binding : gameOptions.allKeys) {
            var boundKey = ((KeyBindingAccessor) binding).getBoundKey();
            gud_wootingcraft$state.incrementKey(boundKey);
        }
        gud_wootingcraft$state.flush();
    }

    @Override
    public void close() {
        super.close();
        gud_wootingcraft$state.close();
    }
}
