package net.minearchive.screen.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.elements.AbstractSettingElement;
import net.minearchive.setting.settings.EnumSetting;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public class EnumElement extends AbstractSettingElement<EnumSetting<? extends Enum<?>>> {
    private boolean opened = false;
    private int v = 0;
    private final Animation boxHeight = new Animation(0, EnumEasing.QUART.getEasing());
    private final Animation a = new Animation(0, EnumEasing.QUART.getEasing());
    private final Animation selecting = new Animation(0, EnumEasing.QUART.getEasing());
    private final Animation yAnim = new Animation(0, EnumEasing.QUART.getEasing());
    private final Animation selectAlpha = new Animation(0, EnumEasing.QUART.getEasing());

    public EnumElement(EnumSetting<? extends Enum<?>> setting, float x, float y, float width, float height) {
        super(setting, x, y ,width, height);
        for (int i = 0; i < setting.contents().length; i++) {
            if (setting.getValue().name().equals(setting.contents()[i].name())) this.v = i;
        }
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        this.offset = offset;
        this.boxHeight.animateTo(opened ? t.contents().length + 1 : 1, 350);
        this.a.animateTo(opened ? 1 : 0, 350);
        this.yAnim.animateTo(0, 550);
        this.selectAlpha.animateTo(1, 550);
        this.selecting.animateTo(v, 250);
        NVGPaint paint = NanoVGUtils.linearGradient(x + 30, offset + 70, x + width - 30, offset + 72, SimpleColor.of(0xFFFAC0FF).floatAlpha(a.getValue()), SimpleColor.of(0xFFB3A5FF).floatAlpha(a.getValue()), NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.ntr.draw(t.getName(), x + 30, offset + 10, 22, 0xFFFFFFFF);
        NanoVGUtils.rounded(x + 30, offset + 30, width - 60, 40 * boxHeight.getValue(), 5, SimpleColor.of(0xD13C3C3C), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.line(x + 30, offset + 70, x + width - 30, offset + 72, paint);

        NanoVGUtils.ntr.draw(t.getValue().name(), x + 30 + (width - 60) / 2f, offset + 55 - yAnim.getValue(), 22, SimpleColor.of(0xFFFFFFFF).floatAlpha(selectAlpha.getValue()).color(), NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVGUtils.symbols.draw("\uE313", x + 30 + width - 60, offset + 55, 22, 0xFFFFFFFF, NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_MIDDLE);
        paint = NanoVGUtils.linearGradient(x + 40, offset + 80, x + width - 40, offset + 100, SimpleColor.of(0xFFFAC0FF).floatAlpha(a.getValue()), SimpleColor.of(0xFFB3A5FF).floatAlpha(a.getValue()), NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.rounded(x + 80, offset + 78f + 40 * selecting.getValue(), width - 160, 25, 5, paint, NanoVGUtils.Pattern.FILL);
        for (int i = 0; i < t.contents().length; i++) {
            NanoVGUtils.ntr.draw(t.contents()[i].name(), x + 30 + (width - 60) / 2f, offset + 95 + i * 40, 22, SimpleColor.of(0xFFFFFFFF).floatAlpha(a.getValue()).color(), NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
        }
        if (ClientDebuggerModule.INSTANCE.componentDebug.getValue()) NanoVGUtils.rect(x, offset, width, height(), SimpleColor.of(0xffff0000), NanoVGUtils.Pattern.STROKE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (MouseUtils.hover(mouseX, mouseY, x + 30, offset + 30, width - 60, 40)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                t.next();
                this.selectAlpha.setValue(0);
                this.yAnim.setValue(-15);
            }
            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) opened = !opened;
            for (int i = 0; i < t.contents().length; i++) {
                if (t.getValue().name().equals(t.contents()[i].name())) this.v = i;
            }
        }

        if (MouseUtils.hover(mouseX, mouseY, x + 30, offset + 70, width - 60, 40 * boxHeight.getValue()) && opened) {
            for (int i = 0; i < t.contents().length; i++) {
                if (MouseUtils.hover(mouseX, mouseY, x + 30, offset + 70 + i * 40, width - 60, 40)) {
                    if (!t.getValue().name().equals(t.contents()[i].name())) {
                        this.v = i;
                        this.selectAlpha.setValue(0);
                        this.yAnim.setValue(-15);
                    }
                    t.setValueByName(t.contents()[i].name());
                }
            }
        }
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
        return 40 * (boxHeight.getValue() + 1) - 5;
    }
}
