package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

public class DoubleSetting extends Setting<Double> {
    private double min, max;

    public DoubleSetting(String name, double value, double min, double max) {
        super(name, value);
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
}
