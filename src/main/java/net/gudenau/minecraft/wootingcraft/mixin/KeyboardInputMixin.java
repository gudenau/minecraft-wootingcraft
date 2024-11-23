package net.gudenau.minecraft.wootingcraft.mixin;

import net.gudenau.minecraft.wootingcraft.api.AnalogKeyBinding;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.PlayerInput;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
    @Shadow @Final private GameOptions settings;

    @Unique
    private static float gud_wootingcraft$getMovement(@NotNull KeyBinding positive, @NotNull KeyBinding negative) {
        if(positive instanceof AnalogKeyBinding analogPositive && negative instanceof AnalogKeyBinding analogNegative) {
            return analogPositive.pressedAmount() - analogNegative.pressedAmount();
        }

        var positivePressed = positive.isPressed();
        var negativePressed = negative.isPressed();
        if(positivePressed == negativePressed) {
            return 0;
        } else {
            return positivePressed ? 1 : -1;
        }
    }

    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = true
    )
    private void tick(boolean slowDown, float slowFactor, CallbackInfo ci) {
        var longMovement = gud_wootingcraft$getMovement(settings.forwardKey, settings.backKey);
        var latMovement = gud_wootingcraft$getMovement(settings.leftKey, settings.rightKey);

        playerInput = new PlayerInput(
            longMovement > 0,
            longMovement < 0,
            latMovement > 0,
            latMovement < 0,
            settings.jumpKey.isPressed(),
            settings.sneakKey.isPressed(),
            settings.sprintKey.isPressed()
        );

        if (slowDown) {
            longMovement *= slowFactor;
            latMovement *= slowFactor;
        }

        movementForward = longMovement;
        movementSideways = latMovement;

        ci.cancel();
    }
}
