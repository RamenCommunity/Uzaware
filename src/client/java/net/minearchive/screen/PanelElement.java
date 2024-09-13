package net.minearchive.screen;

import com.google.common.util.concurrent.AtomicDouble;
import net.minearchive.Uzaware;
import net.minearchive.module.Category;
import net.minearchive.screen.elements.ModuleElement;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NanoVG;

import java.util.ArrayList;
import java.util.List;

public class PanelElement implements IElement {
    private final List<ModuleElement> elements = new ArrayList<>();
    private final Category c;
    private final float x;
    private final float y;
    private final float width;

    public PanelElement(Category c, float x, float y, float width) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.width = width;
        elements.addAll(Uzaware.moduleManager.modules.stream().filter(m -> m.category == c).map(m -> new ModuleElement(m, x, y, width, 40)).toList());
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        float height = elements.size() * 50 + 80;
        NanoVGUtils.shadow(x, y, width, height, 5, SimpleColor.of(0x40000000));
        NanoVGUtils.rounded(x, y, width, height, 5, SimpleColor.of(0x99FFFFFF), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.ntr.draw(c.display(), x + width / 2F, y + 30, 30, 0xFFFFFFFF, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVGUtils.lineCap(NanoVG.NVG_ROUND);
        NanoVGUtils.stroke(4F);
        NanoVGUtils.line(x + 10, y + 50, x + width - 10, y + 50, SimpleColor.of(0xFFFAC0FF), SimpleColor.of(0xFFB3A5FF));
        AtomicDouble off = new AtomicDouble(70);
        elements.forEach(m -> m.render(context, mouseX, mouseY, delta, (float) off.getAndAdd(m.height())));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return elements.stream().anyMatch(e -> e.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return elements.stream().anyMatch(e -> e.mouseReleased(mouseX, mouseY, button));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return elements.stream().anyMatch(e -> e.mouseDragged(mouseX, mouseY, button, deltaX, deltaY));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return elements.stream().anyMatch(e -> e.keyPressed(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return elements.stream().anyMatch(e -> e.keyReleased(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return elements.stream().anyMatch(e -> e.mouseScrolled(mouseX, mouseY, amount));
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return elements.stream().anyMatch(e -> e.charTyped(chr, modifiers));
    }

    @Override
    public float height() {
        return 0;
    }
}
