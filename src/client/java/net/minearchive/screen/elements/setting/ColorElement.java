package net.minearchive.screen.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.AbstractElement;
import net.minearchive.setting.settings.ColorSetting;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;

public class ColorElement extends AbstractElement<ColorSetting> {

    public ColorElement(ColorSetting setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {


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
        return 0;
    }
}
