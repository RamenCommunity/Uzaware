package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, Boolean value, Supplier<Boolean> visible) {
        super(name, value, visible);
    }

    public BooleanSetting(String name, Boolean value) {
        super(name, value);
    }
}
