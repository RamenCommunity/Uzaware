package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;

public class EnumSetting extends Setting<Enum<?>> {
    public EnumSetting(String name, Enum<?> value) {
        super(name, value);
    }
}
