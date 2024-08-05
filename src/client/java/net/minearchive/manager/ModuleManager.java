package net.minearchive.manager;

import net.minearchive.module.Module;
import net.minearchive.module.modules.client.ClickGuiModule;

import java.util.*;

public class ModuleManager {
    public static ModuleManager INSTANCE;
    public final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        INSTANCE = this;
        register(ClickGuiModule.class);
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
