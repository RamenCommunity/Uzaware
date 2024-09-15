package net.minearchive.module.modules.client;

import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.*;

@ModuleInfo(name = "Element Sample", category = Category.COMBAT)
public class ElementSample extends Module {
    public BooleanSetting booleanSetting = add(new BooleanSetting("Boolean", true));
    public IntegerSetting integerSetting = add(new IntegerSetting("Integer", 5, 0, 10));
    public FloatSetting floatSetting = add(new FloatSetting("Float", 5.0f, 0.0f, 10.0f));
    public DoubleSetting doubleSetting = add(new DoubleSetting("Double", 5.0, 0.0, 10.0));
    public StringSetting stringSetting = add(new StringSetting("String", "default"));
    public EnumSetting<Sample> enumSetting = add(new EnumSetting<>("Enum", Sample.One));

    enum Sample { One, Two, Three }
}
