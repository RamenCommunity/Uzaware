package net.minearchive.screen;

import net.minearchive.Uzaware;
import net.minearchive.module.Category;
import net.minearchive.module.modules.client.ClickGuiModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClickGuiScreen extends Screen {
    private final List<PanelElement> elements = new ArrayList<>();
    private final int PANEL_MARGIN = 20;
    private final int PANEL_WIDTH = 250;

    public ClickGuiScreen() {
        super(Text.of("Uzaware"));

        if (elements.isEmpty()) {
            AtomicInteger integer = new AtomicInteger(0);
            Arrays.stream(Category.values()).forEach(c -> elements.add(new PanelElement(c, PANEL_MARGIN + integer.getAndAdd(PANEL_WIDTH + PANEL_MARGIN), PANEL_MARGIN, PANEL_WIDTH)));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        double x = client.mouse.getX();
        double y = client.mouse.getY();
        Uzaware.nanoVGManager.begin(false);
        elements.forEach(e -> e.render(context, x, y, delta, 0));
        Uzaware.nanoVGManager.end();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double x = client.mouse.getX();
        double y = client.mouse.getY();
        if (elements.stream().noneMatch(e -> e.mouseClicked(x, y, button))) return super.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        double x = client.mouse.getX();
        double y = client.mouse.getY();
        if (elements.stream().noneMatch(e -> e.mouseReleased(x, y, button))) return super.mouseReleased(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        double x = client.mouse.getX();
        double y = client.mouse.getY();
        if (elements.stream().noneMatch(e -> e.mouseDragged(x, y, button, deltaX, deltaY))) return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        double x = client.mouse.getX();
        double y = client.mouse.getY();
        if (elements.stream().noneMatch(e -> e.mouseScrolled(x, y, amount))) return super.mouseScrolled(mouseX, mouseY, amount);
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (elements.stream().noneMatch(e -> e.keyPressed(keyCode, scanCode, modifiers))) return super.keyPressed(keyCode, scanCode, modifiers);
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (elements.stream().noneMatch(e -> e.keyReleased(keyCode, scanCode, modifiers))) return super.keyReleased(keyCode, scanCode, modifiers);
        return false;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (elements.stream().noneMatch(e -> e.charTyped(chr, modifiers))) return super.charTyped(chr, modifiers);
        return false;
    }

    @Override
    public void close() {
        ClickGuiModule.INSTANCE.disable();
    }
}
