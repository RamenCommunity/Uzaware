package net.minearchive.manager;

import net.minearchive.module.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {

    public static ModuleManager INSTANCE;

    private final List<Module> modules = new ArrayList<>();
    private final Map<Class<? extends Module>, Module> classModuleMap = new HashMap<>();

    public ModuleManager() {
        INSTANCE = this;
    }

    public Module getInstance(Class<? extends Module> clazz) {
        return classModuleMap.getOrDefault(clazz, null);
    }

    public void register(Class<? extends Module> clazz) {
        try {
            Module module = clazz.getDeclaredConstructor().newInstance();
            classModuleMap.put(clazz, module);
            modules.add(module);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
