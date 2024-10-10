package net.minearchive.screen.clickgui.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.clickgui.elements.AbstractSettingElement;
import net.minearchive.setting.settings.StringSetting;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;

public class StringElement extends AbstractSettingElement<StringSetting> {

    public StringElement(StringSetting setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        NanoVGUtils.ntr.draw(t.getName(), x + 30, offset + 10, 22, 0xFFFFFFFF);
        NanoVGUtils.rounded(x + 30, offset + 30, width - 60, 40, 5, SimpleColor.of(0xD13C3C3C), NanoVGUtils.Pattern.FILL);
        if (ClientDebuggerModule.INSTANCE.componentDebug.getValue()) NanoVGUtils.rect(x, offset, width, height(), SimpleColor.of(0xffff0000), NanoVGUtils.Pattern.STROKE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return false;
    }

    @Override
    public float height() {
        return 75;
    }
}
