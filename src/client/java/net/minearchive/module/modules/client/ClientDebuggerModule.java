package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;

@ModuleInfo(name = "Debugger", category = Category.CLIENT)
public class ClientDebuggerModule extends Module {

    public static ClientDebuggerModule INSTANCE;

    public final BooleanSetting componentDebug = add(new BooleanSetting("Component box", false));

    public ClientDebuggerModule() {
        INSTANCE = this;
    }

}
