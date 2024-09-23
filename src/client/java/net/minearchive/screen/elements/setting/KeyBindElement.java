package net.minearchive.screen.elements.setting;

import net.minearchive.module.modules.client.ClientDebuggerModule;
import net.minearchive.screen.AbstractElement;
import net.minearchive.screen.ClickGuiScreen;
import net.minearchive.setting.settings.KeyBindSetting;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;

public class KeyBindElement extends AbstractElement<KeyBindSetting> {
    private boolean listening;

    public KeyBindElement(KeyBindSetting setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        float realW = Math.max(NanoVGUtils.ntr.width(listening ? "Listening..." : t.getValue().getKeyName(), 22) + 20, 70);
        NanoVGUtils.ntr.draw(t.getName(), x + 30, offset + 13, 22, 0xffffffff);
        NanoVGUtils.rounded(x + width - 30 - realW, offset + 9, realW, 22, 5, SimpleColor.of(0xD13C3C3C), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.ntr.draw(listening ? "Listening..." : t.getValue().getKeyName(), x + width - 30 - realW / 2f, offset + 24, 22, 0xffffffff, NanoVG.NVG_ALIGN_MIDDLE | NanoVG.NVG_ALIGN_CENTER);
        ClickGuiScreen.INSTANCE.isKeyListening = listening;
        if (ClientDebuggerModule.INSTANCE.componentDebug.getValue()) NanoVGUtils.rect(x, offset, width, height(), SimpleColor.of(0xffff0000), NanoVGUtils.Pattern.STROKE);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (MouseUtils.hover(mouseX, mouseY, x, offset, width, height())) {
            listening = MouseUtils.hover(mouseX, mouseY, x + width - 100, offset + 11, 70, 22);
        } else {
            listening = false;
        }
        return listening;
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
        if (listening) {
            if (keyCode == GLFW.GLFW_KEY_UNKNOWN || keyCode == GLFW.GLFW_KEY_ESCAPE ||
                    keyCode == GLFW.GLFW_KEY_DELETE || keyCode == GLFW.GLFW_KEY_BACKSLASH) t.getValue().setKey(GLFW.GLFW_KEY_UNKNOWN);
            else t.getValue().setKey(keyCode);
            listening = false;
        }
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
        return 35;
    }
}
