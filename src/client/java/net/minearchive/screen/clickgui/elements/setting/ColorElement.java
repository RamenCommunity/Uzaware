package net.minearchive.screen.clickgui.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.clickgui.elements.AbstractSettingElement;
import net.minearchive.setting.settings.ColorSetting;
import net.minearchive.util.render.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NVGPaint;

import java.awt.*;

public class ColorElement extends AbstractSettingElement<ColorSetting> {
    private float slider, cursorX, cursorY;

    public ColorElement(ColorSetting setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        this.offset = offset;
        float h = NanoVGUtils.ntr.height(22);
        NanoVGUtils.rounded(x, offset, width, height(), 5, SimpleColor.of(0xD13C3C3C), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.ntr.draw(t.getName(), x + 30, offset + 10, 22, 0xffffffff);
        NanoVGUtils.rounded(x + width - 100f, offset - (h / 2), 70f, 20f, 5f, SimpleColor.of(t.getValue()), NanoVGUtils.Pattern.FILL);
        renderHSB(x + 30, offset + 30, width - 60, width - 60, 10, getRGBMax(t.getValue(), true));
        NanoVGUtils.stroke(3);
        NanoVGUtils.rounded(x + 39, offset + 39, width - 78, width - 78, 10, SimpleColor.of(0xffffffff), NanoVGUtils.Pattern.STROKE);

        float[] hsb = Color.RGBtoHSB(t.getValue().getRed(), t.getValue().getGreen(), t.getValue().getBlue(), null);

        if (ClientDebuggerModule.INSTANCE.componentDebug.getValue()) NanoVGUtils.rect(x, offset, width, height(), SimpleColor.of(0xffff0000), NanoVGUtils.Pattern.STROKE);
    }

    /**
     * <a href="https://github.com/Polyfrost/OneConfig/blob/f4a128250f2a26b262b64c22c4d03ecdfd8ec20d/src/main/java/cc/polyfrost/oneconfig/internal/renderer/NanoVGHelperImpl.java#L311">i love</a>
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     * @param cornerRadius cornerRadius
     * @param target target
     */
    private void renderHSB(float x, float y, float width, float height, float cornerRadius, int target) {
        NanoVGUtils.rounded(x, y, width, height, cornerRadius, SimpleColor.of(target), NanoVGUtils.Pattern.FILL);

        NVGPaint lt2rt = NanoVGUtils.linearGradient(x, y, x + width, y, SimpleColor.of(new Color(255, 255, 255)), SimpleColor.of(new Color(255, 255, 255, 0)), NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.rounded(x, y, width, height, cornerRadius, lt2rt, NanoVGUtils.Pattern.FILL);

        NVGPaint lt2rb = NanoVGUtils.linearGradient(x, y, x, y + height, SimpleColor.of(new Color(0, 0, 0, 0)), SimpleColor.of(new Color(0, 0, 0, 255)), NanoVGUtils.Orientation.VERTICAL);
        NanoVGUtils.rounded(x, y, width, height, cornerRadius, lt2rb, NanoVGUtils.Pattern.FILL);
    }

    private int getRGBMax(Color color, boolean maxBrightness) {
        float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float brightness = maxBrightness ? 1.0f : 0.0f;

        Color resultColor = Color.getHSBColor(hsbValues[0], hsbValues[1], brightness);
        return (resultColor.getRGB() & 0x00ffffff) | (color.getAlpha() << 24);
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
        return width;
    }
}
