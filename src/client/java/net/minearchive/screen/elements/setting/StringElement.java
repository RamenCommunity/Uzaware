package net.minearchive.screen.elements.setting;

import net.minearchive.screen.IElement;
import net.minearchive.setting.settings.StringSetting;
import net.minecraft.client.gui.DrawContext;

public class StringElement implements IElement {
    private final StringSetting setting;
    private float x, y, width, height;

    public StringElement(StringSetting setting, float x, float y, float width, float height) {
        this.setting = setting;
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {

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
