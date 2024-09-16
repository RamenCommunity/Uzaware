package net.minearchive.module;

import org.lwjgl.glfw.GLFW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
    String name();
    String description()    default "";
    int keybind()           default GLFW.GLFW_KEY_UNKNOWN;
    boolean enable()        default false;
    Category category();
}
