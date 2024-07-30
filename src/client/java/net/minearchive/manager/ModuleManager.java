package net.minearchive.manager;

import net.minearchive.module.Module;

import java.util.*;

public class ModuleManager {
    public static ModuleManager INSTANCE;

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        INSTANCE = this;
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
