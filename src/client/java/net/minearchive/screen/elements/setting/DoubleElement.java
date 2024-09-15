package net.minearchive.screen.elements.setting;

import net.minearchive.screen.ClickGuiScreen;
import net.minearchive.screen.IElement;
import net.minearchive.setting.settings.DoubleSetting;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import org.joml.Math;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGPaint;

public class DoubleElement implements IElement {
    private final DoubleSetting setting;
    private final float x, y, width, height;
    private boolean dragging = false;

    public DoubleElement(DoubleSetting setting, float x, float y, float width, float height){
        this.setting = setting;
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        NVGPaint paint = NanoVGUtils.linearGradient(x + 30, offset + 36, width - 60, 14, SimpleColor.of(0xFFFAC0FF), SimpleColor.of(0xFFB3A5FF), NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.ntr.draw(setting.getName()+ " : " + String.format("%.6f", setting.getValue()), x + 30, offset + 10, 22, 0xffffffff);
        NanoVGUtils.rounded(x + 30, offset + 36, width - 60, 14, 7, SimpleColor.of(0xFF191919), NanoVGUtils.Pattern.FILL);
        if (setting.getValue() != setting.getMin()) NanoVGUtils.rounded(x + 30, offset + 36, (float) ((width - 60) * ( setting.getValue() / (setting.getMax() - setting.getMin()))), 14, 7, paint, NanoVGUtils.Pattern.FILL);
        NanoVGUtils.rounded(x + 30, offset + 36, width - 60, 14, 7, SimpleColor.of(0xFF292929), NanoVGUtils.Pattern.STROKE);

        if ((GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS)) {
            if ((MouseUtils.hover(mouseX, mouseY, x + 30, offset + 36, width - 60, 14) && !ClickGuiScreen.isDragging) || dragging) {
                float p = (float) ((mouseX - (x + 30)) / (width - 60));
                setting.setValue(Math.clamp(setting.getMin(), setting.getMax(), (int) setting.getMin() + (setting.getMax() - setting.getMin()) * p));
                dragging = true;
            }
        } else if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_RELEASE) {
            dragging = false;
            ClickGuiScreen.isDragging = false;
        }
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
        return 55;
    }
}
