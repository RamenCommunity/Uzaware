package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;

@ModuleInfo(name = "CombatManager", category = Category.CLIENT)
public class CombatManage extends Module {
    public static CombatManage INSTANCE;

    public CombatManage() {
        INSTANCE = this;
    }

}
