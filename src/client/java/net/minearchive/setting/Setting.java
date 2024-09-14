package net.minearchive.setting;

public class Setting<T> {
    private T value;
    String name;

    public Setting(String name, T value) {
        this.value = value;
        this.name = name;
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
}
