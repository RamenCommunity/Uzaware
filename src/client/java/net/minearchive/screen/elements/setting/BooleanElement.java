package net.minearchive.screen.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.AbstractElement;
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

public class BooleanElement extends AbstractElement<BooleanSetting> {
    private final ColorAnimation colorT, colorB;
    private final Animation circleAnim;

    public BooleanElement(BooleanSetting setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
        this.colorT = new ColorAnimation(new Color(setting.getValue() ? 0xFFFAC0FF : 0xCC323232), EnumEasing.SINE.getEasing());
        this.colorB = new ColorAnimation(new Color(setting.getValue() ? 0xFFB3A5FF : 0xCC323232), EnumEasing.SINE.getEasing());
        this.circleAnim = new Animation(setting.getValue() ? 1 : 0, EnumEasing.SINE.getEasing());
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        colorT.setAnimation(new Color(t.getValue() ? 0xFFFAC0FF : 0xCC323232, true), 150);
        colorB.setAnimation(new Color(t.getValue() ? 0xFFB3A5FF : 0xCC323232, true), 150);
        circleAnim.animateTo(t.getValue() ? 1 : 0, 150);
        NanoVGUtils.ntr.draw(t.getName(), x + 30, offset + 8, 22, 0xFFFFFFFF);

        NanoVGUtils.stroke(2F);
        NanoVGUtils.rounded(x + width - 71, offset + 4, 42, 22, 11, SimpleColor.of(0x59000000), NanoVGUtils.Pattern.STROKE);
        NanoVGUtils.stroke(1F);

        NanoVGUtils.rounded(x + width - 70, offset + 5, 40, 20, 10, SimpleColor.of(colorT.getColor()), SimpleColor.of(colorB.getColor()), NanoVGUtils.Pattern.FILL, NanoVGUtils.Orientation.VERTICAL);

        NanoVGUtils.circle(x + width - 70 + 10 + 20 * circleAnim.getValue(), offset + 5 + 10, 7, SimpleColor.of(Color.white), NanoVGUtils.Pattern.FILL);
        if (ClientDebuggerModule.INSTANCE.componentDebug.getValue()) NanoVGUtils.rect(x, offset, width, height(), SimpleColor.of(0xFFFF0000), NanoVGUtils.Pattern.STROKE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (MouseUtils.hover(mouseX, mouseY, x + width - 70, offset + 8, 40, 20) && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            t.setValue(!t.getValue());
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
