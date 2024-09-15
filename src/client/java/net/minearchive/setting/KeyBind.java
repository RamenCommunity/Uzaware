package net.minearchive.setting;

import org.lwjgl.glfw.GLFW;

public class KeyBind {
    private int key;

    public KeyBind(int key) {
        this.key = key;
    }

    public String getKeyName() {
        return GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
