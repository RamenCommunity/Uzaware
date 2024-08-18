package net.minearchive.mixin.client;

import net.minearchive.module.modules.render.FullBrightModule;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LightmapTextureManager.class)
public class MixinLightmapTextureManager {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;", ordinal = 1))
    public Object fullBright(SimpleOption<Double> instance) {
        return (FullBrightModule.INSTANCE.enabled) ? (double) Float.MAX_VALUE : instance.getValue();
    }
}
