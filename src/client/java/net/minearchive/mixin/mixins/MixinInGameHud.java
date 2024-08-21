package net.minearchive.mixin.mixins;

import net.minearchive.event.events.RenderEndEvent;
import net.minearchive.event.events.RenderStartEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minearchive.Uzaware.EVENT_BUS;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "render", at = @At("HEAD"))
    public void onRenderStart(DrawContext context, float tickDelta, CallbackInfo ci) {
        EVENT_BUS.post(new RenderStartEvent(context, tickDelta));
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onRenderEnd(DrawContext context, float tickDelta, CallbackInfo ci) {
        EVENT_BUS.post(new RenderEndEvent(context, tickDelta));
    }
}
