package net.minearchive.module.modules.movement;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;

@ModuleInfo(name = "MovementTweaks", category = Category.MOVEMENT)
public class MovementTweaksModule extends Module {
    public static MovementTweaksModule INSTANCE;

    public BooleanSetting noSwim = add(new BooleanSetting("No Block Swim", false));
    public BooleanSetting noRiptide = add(new BooleanSetting("No Riptide", false));

    public MovementTweaksModule() {
        INSTANCE = this;
    }

}
