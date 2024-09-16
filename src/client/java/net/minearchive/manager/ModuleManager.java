package net.minearchive.manager;

import net.minearchive.module.Module;
import net.minearchive.module.modules.client.ClickGuiModule;
import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.module.modules.client.ElementSample;
import net.minearchive.module.modules.hud.WaterMarkModule;
import net.minearchive.module.modules.misc.ChatSuffixModule;
import net.minearchive.module.modules.render.FullBrightModule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ModuleManager {
    public static ModuleManager INSTANCE;
    public final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        INSTANCE = this;
        //Combat

        //Misc
        register(ChatSuffixModule.class);

        //Movement

        //Render
        register(FullBrightModule.class);

        //Client
        register(ClickGuiModule.class);
        register(ElementSample.class);
        register(ClientDebuggerModule.class);

        //Hud
        register(WaterMarkModule.class);

        modules.sort(Comparator.comparing(m -> m.name));
    }

    public Optional<Module> getInstance(Class<? extends Module> clazz) {
        return modules.stream().filter(m -> m.getClass() == clazz).findFirst();
    }

    public void register(Class<? extends Module> clazz) {
        try {
            Module module = clazz.getDeclaredConstructor().newInstance();
            modules.add(module);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
