package net.minearchive.module;

import net.minearchive.AccessMC;
import net.minearchive.setting.Setting;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.FloatSetting;

import java.util.ArrayList;
import java.util.List;

import static net.minearchive.Uzaware.EVENT_BUS;

public class Module implements AccessMC {
    public final String name        = getAnnotation().name();
    public final String description = getAnnotation().description();
    public int keybind              = getAnnotation().keybind();
    public final Category category  = getAnnotation().category();
    public boolean enabled          = getAnnotation().enable();
    public final List<Setting<?>> settings = new ArrayList<>();

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
            EVENT_BUS.register(this);
        } else {
            onDisable();
            EVENT_BUS.unregister(this);
        }
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

    public boolean nullCheck() {
        return client.world == null || client.player == null;
    }

    public  <T extends Setting<?>> T add(T t) {
        settings.add(t);
        return t;
    }

    public ModuleInfo getAnnotation() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) return this.getClass().getAnnotation(ModuleInfo.class);
        else throw new RuntimeException("ModuleInfo Annotation is not found! Can't initialize module!");
    }
}
