package net.minearchive.screen.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.ClickGuiScreen;
import net.minearchive.screen.elements.AbstractSettingElement;
import net.minearchive.setting.settings.FloatSetting;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import org.joml.Math;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGPaint;

public class FloatElement extends AbstractSettingElement<FloatSetting> {
    private boolean dragging = false;

    public FloatElement(FloatSetting setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        NVGPaint paint = NanoVGUtils.linearGradient(x + 30, offset + 21, x + width - 30, offset + 45, SimpleColor.of(0xFFFAC0FF), SimpleColor.of(0xFFB3A5FF), NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.ntr.draw(t.getName() + " : " + String.format("%.2f", t.getValue()), x + 30, offset + 5, 22, 0xffffffff);
        NanoVGUtils.stroke(2F);
        NanoVGUtils.rounded(x + 29, offset + 25, width - 58, 16, 8, SimpleColor.of(0x59000000), NanoVGUtils.Pattern.STROKE);
        NanoVGUtils.stroke(1F);
        NanoVGUtils.rounded(x + 30, offset + 26, width - 60, 14, 7, SimpleColor.of(0xCC323232), NanoVGUtils.Pattern.FILL);
        if (t.getValue() > t.getMin()) NanoVGUtils.rounded(x + 30, offset + 26, (width - 60) * Math.clamp(0, 1, (t.getValue() / (t.getMax() - t.getMin()))), 14, 7, paint, NanoVGUtils.Pattern.FILL);

        if ((GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS)) {
            if ((MouseUtils.hover(mouseX, mouseY, x + 30, offset + 26, width - 60, 14) && !ClickGuiScreen.INSTANCE.isDragging) || dragging) {
                float p = (float) ((mouseX - (x + 30)) / (width - 60));
                float value = Math.clamp(t.getMin(), t.getMax(), t.getMin() + (t.getMax() - t.getMin()) * p);
                float rounded = Math.max(t.getMin(), Math.min(t.getMax(), Math.round(value / t.getStep()) * t.getStep()));
                t.setValue(rounded);
                dragging = true;
                ClickGuiScreen.INSTANCE.isDragging = true;
            }
        } else if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_RELEASE) {
            dragging = false;
            ClickGuiScreen.INSTANCE.isDragging = false;
        }
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
        return 45;
    }
}
