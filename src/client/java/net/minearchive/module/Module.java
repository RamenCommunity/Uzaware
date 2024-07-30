package net.minearchive.module;

public class Module {
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

    public void enable() {
        if (!enabled) {
            enabled = true;
            onEnable();
        }
    }

    public void disable() {
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
