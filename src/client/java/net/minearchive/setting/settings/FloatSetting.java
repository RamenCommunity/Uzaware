package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class FloatSetting extends Setting<Float> {
    private float min, max;

    public FloatSetting(String name, float value, float min, float max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }

    public FloatSetting(String name, float value, float min, float max, Supplier<Boolean> visible) {
        super(name, value, visible);
        this.min = min;
        this.max = max;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FloatSetting build() {
        return this;
    }
}
