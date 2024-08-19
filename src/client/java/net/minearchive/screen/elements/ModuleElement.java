package net.minearchive.screen.elements;

import net.minearchive.module.Module;
import net.minearchive.screen.AbstractElement;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NanoVG;

public class ModuleElement extends AbstractElement<Module> {
    public ModuleElement(Module module, float x, float y, float width, float height) {
        super(module, x, y, width, height);
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        super.render(context, mouseX, mouseY, delta, offset);
        NanoVGUtils.rounded(x + 20,
                y + offset,
                width - 40,
                height,
                5,
                SimpleColor.of(t.enabled ? 0xFFFAC0FF : 0xD93C3C3C),
                SimpleColor.of(t.enabled ? 0xFFB3A5FF : 0xD93C3C3C),
                NanoVGUtils.Pattern.FILL,
                NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.stroke(2F);
        NanoVGUtils.rounded(x + 20 - 1,
                y + offset - 1,
                width - 40 + 2,
                height + 2,
                6,
                SimpleColor.of((t.enabled ? 0xFFFAC0FF | 0x00191919 : 0x4D000000)),
                SimpleColor.of((t.enabled ? 0xFFB3A5FF | 0x00191919 : 0x4D000000)),
                NanoVGUtils.Pattern.STROKE,
                NanoVGUtils.Orientation.HORIZONTAL);
        NanoVGUtils.ntr.draw(t.name, x + 30, y + offset + 3 + height / 2F, 20, 0xFFFFFFFF, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVGUtils.symbols.draw("\uE946", x + width - 37, y + offset + 2 + height / 2F, 20, 0x99FFFFFF, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        System.out.println("click " + t + " " + button);
        if (MouseUtils.hover(mouseX, mouseY, x + 20, y + offset, width - 40, height)) {
            t.toggle();
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
        return height + 15;
    }
}
