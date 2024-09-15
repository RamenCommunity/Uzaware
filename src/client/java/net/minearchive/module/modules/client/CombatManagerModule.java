package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.EnumSetting;

@ModuleInfo(name = "CombatManager", category = Category.CLIENT)
public class CombatManagerModule extends Module {
    public static CombatManagerModule INSTANCE;

    public BooleanSetting player = add(new BooleanSetting("Player", true));
    public BooleanSetting passive = add(new BooleanSetting("Passive", true));
    public BooleanSetting villagerSafe = add(new BooleanSetting("No Villager", true, () -> passive.getValue()));
    public BooleanSetting enemy = add(new BooleanSetting("Enemy", true));

    public EnumSetting<Priority> priority = add(new EnumSetting<>("Priority", Priority.Health));

    public enum Priority { Range, Health }

    public CombatManagerModule() {
        INSTANCE = this;
    }

}
