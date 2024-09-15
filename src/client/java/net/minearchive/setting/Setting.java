package net.minearchive.setting;

import java.util.function.Supplier;

public class Setting<T> {
    private T value;
    private String name;
    private Supplier<Boolean> visible;

    public Setting(String name, T value, Supplier<Boolean> visible) {
        this.value = value;
        this.name = name;
        this.visible = visible;
    }

    public Setting(String name, T value) {
        this.value = value;
        this.name = name;
        this.visible = () -> true;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Supplier<Boolean> getVisible() {
        return visible;
    }

    public void setVisible(Supplier<Boolean> visible) {
        this.visible = visible;
    }
}
