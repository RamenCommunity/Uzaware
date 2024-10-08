package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class FloatSetting extends Setting<Float> {
    private float min, max, step;

    public FloatSetting(String name, float value, float min, float max, float step, Supplier<Boolean> visible) {
        super(name, value, visible);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public FloatSetting(String name, float value, float min, float max, Supplier<Boolean> visible) {
        this(name, value, min, max, 0.01F, visible);
    }

    public FloatSetting(String name, float value, float min, float max, float step) {
        this(name, value, min, max, step, () -> true);
    }

    public FloatSetting(String name, float value, float min, float max) {
        this(name, value, min, max, () -> true);
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FloatSetting build() {
        return this;
    }
}
