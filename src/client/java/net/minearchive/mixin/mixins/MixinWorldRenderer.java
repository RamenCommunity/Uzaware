package net.minearchive.mixin.mixins;

import net.minearchive.module.modules.render.NoRenderModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class)
public abstract class MixinWorldRenderer {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderWeather", at = @At(value = "HEAD"), cancellable = true)
    public void noRenderWeather(LightmapTextureManager manager, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        NoRenderModule.INSTANCE.noWeather(ci);
    }

    @Inject(method = "tickRainSplashing", at = @At(value = "HEAD"), cancellable = true)
    public void noTickRainSplashing(Camera camera, CallbackInfo ci) {
        NoRenderModule.INSTANCE.noWeather(ci);
    }
}
