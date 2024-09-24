package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class DoubleSetting extends Setting<Double> {
    private double min, max;

    public DoubleSetting(String name, double value, double min, double max) {
        super(name, value);
        this.min = min;
        this.max = max;
    }

    public DoubleSetting(String name, double value, double min, double max, Supplier<Boolean> visible) {
        super(name, value, visible);
        this.min = min;
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DoubleSetting build() {
        return this;
    }
}
