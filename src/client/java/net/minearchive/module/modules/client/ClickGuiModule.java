package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.screen.ClickGuiScreen;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(name = "ClickGui", category = Category.CLIENT, keybind = GLFW.GLFW_KEY_RIGHT_SHIFT)
public class ClickGuiModule extends Module {
    public static ClickGuiModule INSTANCE;

    public ClickGuiModule() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        client.setScreen(new ClickGuiScreen());
    }

    @Override
    public void onDisable() {
        if (client.currentScreen instanceof ClickGuiScreen) client.setScreen(null);
    }
}
