package net.minearchive.module.modules.render;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;


@ModuleInfo(name = "FullBright", category = Category.RENDER)
public class FullBrightModule extends Module {
    public static FullBrightModule INSTANCE;

    public FullBrightModule() {
        INSTANCE = this;
        this.enabled = true;
    }
}
