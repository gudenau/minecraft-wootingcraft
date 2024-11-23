package net.gudenau.minecraft.wootingcraft.mixin;

import net.gudenau.minecraft.wootingcraft.natives.Binder;
import net.gudenau.minecraft.wootingcraft.natives.WootingAnalog;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class Plugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String s) {}

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String s, String s1) {
        return Binder.supported() && WootingAnalog.foundKeyboard();
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}
}
