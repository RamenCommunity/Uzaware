package net.minearchive.screen;

import net.minecraft.client.gui.DrawContext;

public interface Element {
    void render(DrawContext context, int mouseX, int mouseY, float delta);
    boolean mouseClicked(double mouseX, double mouseY, int button);
    boolean mouseReleased(double mouseX, double mouseY, int button);
    boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY);
    boolean keyPressed(int keyCode, int scanCode, int modifiers);
    boolean keyReleased(int keyCode, int scanCode, int modifiers);
    boolean mouseScrolled(double mouseX, double mouseY, double amount);
    boolean charTyped(char chr, int modifiers);
    float height();
}
