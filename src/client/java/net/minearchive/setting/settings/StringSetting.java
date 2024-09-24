package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class StringSetting extends Setting<String> {
    public StringSetting(String name, String value) {
        super(name, value);
    }

    public StringSetting(String name, String value, Supplier<Boolean> visible) {
        super(name, value, visible);
    }

    @Override
    public StringSetting build() {
        return this;
    }
}
