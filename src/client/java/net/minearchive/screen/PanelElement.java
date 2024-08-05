package net.minearchive.screen;

import net.minearchive.module.Category;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;

public class PanelElement implements Element {
    private final Category c;
    private final float x;
    private final float y;
    private final float width;

    public PanelElement(Category c, float x, float y, float width) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        NanoVGUtils.rounded(x, y, width, 100, 10, SimpleColor.of(0x99FFFFFF), NanoVGUtils.Pattern.FILL);
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
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
