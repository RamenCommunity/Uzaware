package net.minearchive.setting.settings;

import net.minearchive.setting.KeyBind;
import net.minearchive.setting.Setting;

import java.util.function.Supplier;

public class KeyBindSetting extends Setting<KeyBind> {
    public KeyBindSetting(String name, KeyBind value) {
        super(name, value);
    }

    public KeyBindSetting(String name, KeyBind value, Supplier<Boolean> visible) {
        super(name, value, visible);
    }

    @Override
    @SuppressWarnings("unchecked")
    public KeyBindSetting build() {
        return this;
    }
}
