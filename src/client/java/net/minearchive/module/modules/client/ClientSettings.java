package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;

@ModuleInfo(name = "ClientSettings", category = Category.CLIENT, enable = true)
public class ClientSettings extends Module {
    public static ClientSettings INSTANCE;
    public final BooleanSetting chatNotify = add(new BooleanSetting("ChatNotify", true));

    public ClientSettings() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        toggle();
    }
}
