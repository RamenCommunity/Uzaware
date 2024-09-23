package net.minearchive.module.modules.movement;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.ColorAnimation;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.entity.EntityPose;

@ModuleInfo(name = "MovementTweaks", category = Category.MOVEMENT)
public class MovementTweaksModule extends Module {
    public static MovementTweaksModule INSTANCE;

    public EntityPose pose;

    public BooleanSetting noSwim = add(new BooleanSetting("No Block Swim", false));
    public BooleanSetting noRiptide = add(new BooleanSetting("No Riptide", false));

    public MovementTweaksModule() {
        INSTANCE = this;
    }

}
