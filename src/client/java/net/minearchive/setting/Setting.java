package net.minearchive.setting;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Setting<T> {
    private T value;
    private String name;
    private Supplier<Boolean> visible;
    private Consumer<T> onValueChange = v -> {};

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

    public Setting<T> setValue(T value) {
        if (this.value != value) onValueChange.accept(value);
        this.value = value;
        return this;
    }

    public Setting<T> setName(String name) {
        this.name = name;
        return this;
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

    public Setting<T> setVisible(Supplier<Boolean> visible) {
        this.visible = visible;
        return this;
    }

    public Setting<T> setOnValueChange(Consumer<T> onValueChange) {
        this.onValueChange = onValueChange;
        return this;
    }

    public abstract <S extends Setting<T>> S build();
}
