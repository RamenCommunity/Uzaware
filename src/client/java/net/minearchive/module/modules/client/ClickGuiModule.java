package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.screen.ClickGuiScreen;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(name = "ClickGui", category = Category.CLIENT, keybind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public class ClickGuiModule extends Module {
    @Override
    public void onEnable() {
        if (nullCheck()) {
            disable();
            return;
        }
        client.setScreen(ClickGuiScreen.INSTANCE == null ? new ClickGuiScreen(this) : ClickGuiScreen.INSTANCE);
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;
        if (client.currentScreen instanceof ClickGuiScreen)
            client.setScreen(null);
    }
}
