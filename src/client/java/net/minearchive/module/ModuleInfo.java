package net.minearchive.module;

import org.lwjgl.glfw.GLFW;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String name();
    String description()    default "";
    int keybind()           default GLFW.GLFW_KEY_UNKNOWN;
    Category category();
}
