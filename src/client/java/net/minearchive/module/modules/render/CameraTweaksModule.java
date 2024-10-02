package net.minearchive.module.modules.render;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.FloatSetting;

@ModuleInfo(name = "CameraTweaks", category = Category.RENDER)
public class CameraTweaksModule extends Module {
    public static CameraTweaksModule INSTANCE;
    public final BooleanSetting clip = add(new BooleanSetting("CameraClip", true));
    public final FloatSetting distance = add(new FloatSetting("Distance", 4.0F, 0.0F, 50.0F, clip::getValue));

    public CameraTweaksModule() {
        INSTANCE = this;
    }

    public boolean clip() {
        return enabled && clip.getValue();
    }

    public double distance() {
        return enabled ? distance.getValue() : 4.0D;
    }
}
