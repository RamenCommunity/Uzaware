package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;
import net.minearchive.util.SimpleColor;

import java.awt.*;

public class ColorSetting extends Setting<Color> {
    public ColorSetting(String name, Color value) {
        super(name, value);
    }

    public SimpleColor getAsSimpleColor() {
        return SimpleColor.of(getValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ColorSetting build() {
        return this;
    }
}
