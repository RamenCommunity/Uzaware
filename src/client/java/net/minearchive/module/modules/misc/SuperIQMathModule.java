package net.minearchive.module.modules.misc;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;

@ModuleInfo(name = "SuperIQMath", category = Category.MISC)
public class SuperIQMathModule extends Module {
    @Override
    public void onEnable() {
        if (nullCheck()) return;
        client.player.networkHandler.sendChatMessage("superuzawa");
        toggle();
    }
}
