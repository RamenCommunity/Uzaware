package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class DoubleSetting extends Setting<Double> {
    private double min, max, step;

    public DoubleSetting(String name, double value, double min, double max, double step, Supplier<Boolean> visible) {
        super(name, value, visible);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public DoubleSetting(String name, double value, double min, double max, Supplier<Boolean> visible) {
        this(name, value, min, max, 0.000001D, visible);
    }

    public DoubleSetting(String name, double value, double min, double max, double step) {
        this(name, value, min, max, step, () -> true);
    }

    public DoubleSetting(String name, double value, double min, double max) {
        this(name, value, min, max, () -> true);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DoubleSetting build() {
        return this;
    }
}
