package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class IntegerSetting extends Setting<Integer> {
    private int min, max, step;

    public IntegerSetting(String name, int value, int min, int max, int step, Supplier<Boolean> visible) {
        super(name, value, visible);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public IntegerSetting(String name, int value, int min, int max, Supplier<Boolean> visible) {
        this(name, value, min, max, 1, visible);
    }

    public IntegerSetting(String name, int value, int min, int max, int step) {
        this(name, value, min, max, step, () -> true);
    }

    public IntegerSetting(String name, int value, int min, int max) {
        this(name, value, min, max, () -> true);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IntegerSetting build() {
        return this;
    }
}
