package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class IntegerSetting extends Setting<Integer> {
    private int min, max;

    public IntegerSetting(String name, int value, int min, int max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }

    public IntegerSetting(String name, int value, int min, int max, Supplier<Boolean> visible) {
        super(name, value, visible);
        this.min = min;
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IntegerSetting build() {
        return this;
    }
}
