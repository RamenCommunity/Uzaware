package net.minearchive.module;

import net.minearchive.AccessMC;

public class Module implements AccessMC {
    public final String name        = getAnnotation().name();
    public final String description = getAnnotation().description();
    public final int keybind        = getAnnotation().keybind();
    public final Category category  = getAnnotation().category();
    public boolean enabled          = false;

    public void toggle() {
        if (enabled) onDisable();
        else onEnable();
        enabled = !enabled;
    }

    public final void enable() {
        if (!enabled) {
            enabled = true;
            onEnable();
        }
    }

    public final void disable() {
        if (enabled) {
            enabled = false;
            onDisable();
        }
    }

    public void onTick() { }
    public void onEnable() { }
    public void onDisable() { }

    public ModuleInfo getAnnotation() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) return this.getClass().getAnnotation(ModuleInfo.class);
        else throw new RuntimeException("ModuleInfo Annotation is not found! Can't initialize module!");
    }
}
