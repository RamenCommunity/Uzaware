package net.minearchive.screen.elements.setting;

import net.minearchive.screen.IElement;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.ColorAnimation;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class BooleanElement implements IElement {
    private final BooleanSetting setting;
    private final float x, y, width, height;
    private float offset = 0;
    private final ColorAnimation colorT, colorB;
    private final Animation circleAnim;

    public BooleanElement(BooleanSetting setting, float x, float y, float width, float height) {
        this.setting = setting;
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;
        this.colorT = new ColorAnimation(new Color(this.setting.getValue() ? 0xFFFAC0FF : 0xFF323232), EnumEasing.SINE.getEasing());
        this.colorB = new ColorAnimation(new Color(this.setting.getValue() ? 0xFFB3A5FF : 0xFF323232), EnumEasing.SINE.getEasing());
        this.circleAnim = new Animation(setting.getValue() ? 1 : 0, EnumEasing.SINE.getEasing());
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        this.offset = offset;
        colorT.setAnimation(new Color(this.setting.getValue() ? 0xFFFAC0FF : 0xFF323232), 150);
        colorB.setAnimation(new Color(this.setting.getValue() ? 0xFFB3A5FF : 0xFF323232), 150);
        circleAnim.animateTo(setting.getValue() ? 1 : 0, 150);
        NanoVGUtils.ntr.draw(setting.getName(), x + 30, offset + 12, 22, 0xffffffff);
        NanoVGUtils.rounded(x + width - 70, offset + 8, 40, 20, 9, SimpleColor.of(colorT.getColor()), SimpleColor.of(colorB.getColor()), NanoVGUtils.Pattern.FILL, NanoVGUtils.Orientation.VERTICAL);
        NanoVGUtils.circle(x + width - 70 + 10 + 20 * circleAnim.getValue(), offset + 8 + 10, 8, SimpleColor.of(Color.white), NanoVGUtils.Pattern.FILL);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (MouseUtils.hover(mouseX, mouseY, x + width - 70, offset + 8, 40, 20) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            setting.setValue(!setting.getValue());
            return true;
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
        return 30;
    }
}
