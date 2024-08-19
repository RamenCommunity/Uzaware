package net.minearchive.screen;

import net.minearchive.AccessMC;
import net.minecraft.client.gui.DrawContext;

public interface IElement extends AccessMC {
    void render(DrawContext context, double mouseX, double mouseY, float delta, float offset);
    boolean mouseClicked(double mouseX, double mouseY, int button);
    boolean mouseReleased(double mouseX, double mouseY, int button);
    boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY);
    boolean mouseScrolled(double mouseX, double mouseY, double amount);
    boolean keyPressed(int keyCode, int scanCode, int modifiers);
    boolean keyReleased(int keyCode, int scanCode, int modifiers);
    boolean charTyped(char chr, int modifiers);
    float height();
}
