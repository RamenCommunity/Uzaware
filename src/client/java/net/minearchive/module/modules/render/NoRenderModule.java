package net.minearchive.module.modules.render;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ModuleInfo(name = "NoRender", category = Category.RENDER, enable = true)
public class NoRenderModule extends Module {
    public BooleanSetting weather = add(new BooleanSetting("Weather", true));
    public static NoRenderModule INSTANCE;

    public NoRenderModule() {
        INSTANCE = this;
    }

    public void noWeather(CallbackInfo ci) {
        if (enabled && weather.getValue()) ci.cancel();
    }
}
