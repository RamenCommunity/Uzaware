package net.minearchive.module.modules.render;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.ParticleEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minecraft.client.particle.ExplosionLargeParticle;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ModuleInfo(name = "NoRender", category = Category.RENDER, enable = true)
public class NoRenderModule extends Module {
    public final BooleanSetting weather = add(new BooleanSetting("Weather", true));
    public final BooleanSetting explosion = add(new BooleanSetting("Explosion", true));
    public static NoRenderModule INSTANCE;

    public NoRenderModule() {
        INSTANCE = this;
    }

    public void noWeather(CallbackInfo ci) {
        if (enabled && weather.getValue()) ci.cancel();
    }

    @Subscribe
    public void onParticle(ParticleEvent event) {
        if (nullCheck()) return;
        if (explosion.getValue() && event.getParticle() instanceof ExplosionLargeParticle) {
            event.setCancelled(true);
        }
    }
}
