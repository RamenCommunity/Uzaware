package net.minearchive.mixin.mixins;

import net.minearchive.Uzaware;
import net.minearchive.event.events.Render2DEndEvent;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.manager.ModuleManager;
import net.minearchive.module.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minearchive.Uzaware.EVENT_BUS;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "render", at = @At("HEAD"))
    public void onRenderStart(DrawContext context, float tickDelta, CallbackInfo ci) {
        Uzaware.nanoVGManager.begin(false);
        EVENT_BUS.post(new Render2DStartEvent(context, tickDelta));
        Uzaware.nanoVGManager.end();
        ModuleManager.INSTANCE.modules.stream().filter(m -> m instanceof HudModule).map(m -> (HudModule) m).forEach(m -> {
            m.x = Math.clamp(0, MinecraftClient.getInstance().getWindow().getWidth() - m.width, m.x);
            m.y = Math.clamp(0, MinecraftClient.getInstance().getWindow().getHeight() - m.height, m.y);
        });
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onRenderEnd(DrawContext context, float tickDelta, CallbackInfo ci) {
        Uzaware.nanoVGManager.begin(false);
        EVENT_BUS.post(new Render2DEndEvent(context, tickDelta));
        Uzaware.nanoVGManager.end();
    }
}
