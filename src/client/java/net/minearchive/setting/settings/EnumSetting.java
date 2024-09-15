package net.minearchive.setting.settings;

import net.minearchive.setting.Setting;
import org.jetbrains.annotations.NotNull;

public class EnumSetting<T extends Enum<T>> extends Setting<Enum<T>> {
    private final T[] contents;

    public EnumSetting(String name, T value) {
        super(name, value);
        contents = value.getDeclaringClass().getEnumConstants();
    }

    public T[] contents() {
        return contents;
    }

    public void setValueByName(String name) {
        for (T enumConstant : getValue().getDeclaringClass().getEnumConstants()) {
            if (enumConstant.name().equals(name)) setValue(enumConstant);
        }
    }

    public void next() {
        setValue(contents[(getValue().ordinal() + 1) % (contents.length)]);
    }

    public void back() {
        setValue(contents[(getValue().ordinal() == 0 ? contents.length - 1 : getValue().ordinal()) - 1]);
    }
}
