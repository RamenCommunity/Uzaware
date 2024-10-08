package net.minearchive.module.modules.combat;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.*;

import java.awt.*;

@ModuleInfo(name = "Element Sample", category = Category.COMBAT)
public class ElementSample extends Module {
    public BooleanSetting booleanSetting = add(new BooleanSetting("Boolean", true));
    public IntegerSetting integerSetting = add(new IntegerSetting("Integer", 5, 0, 10));
    public IntegerSetting integerSetting2 = add(new IntegerSetting("Integer2", 5, 0, 10, 2));
    public FloatSetting floatSetting = add(new FloatSetting("Float", 5.0f, 0.0f, 10.0f));
    public FloatSetting floatSetting2 = add(new FloatSetting("Float2", 5.0f, 0.0f, 10.0f, 0.5F));
    public DoubleSetting doubleSetting = add(new DoubleSetting("Double", 5.0, 0.0, 10.0));
    public DoubleSetting doubleSetting2 = add(new DoubleSetting("Double2", 5.0, 0.0, 10.0, 0.5F));
    public EnumSetting<Sample> enumSetting = add(new EnumSetting<>("Enum", Sample.One));
    public StringSetting stringSetting = add(new StringSetting("String", "default"));
    public ColorSetting colorSetting = add(new ColorSetting("Color", Color.RED));

    public enum Sample { One, Two, Three }
}
