package net.minearchive.screen.clickgui;

import net.minearchive.Uzaware;
import net.minearchive.module.Category;
import net.minearchive.module.modules.client.ClickGuiModule;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("DataFlowIssue")
public class ClickGuiScreen extends Screen {
    public static ClickGuiScreen INSTANCE;

    private final List<PanelElement> elements = new ArrayList<>();
    private final int PANEL_MARGIN = 20;
    private final int PANEL_WIDTH = 250;
    private final Animation xAnim = new Animation(0, EnumEasing.SINE.getEasing()), yAnim = new Animation(0, EnumEasing.SINE.getEasing());
    private final ClickGuiModule clickGuiModule;
    private float deltaX, deltaY;
    private boolean shiftPressing;

    public boolean isDragging = false;
    public boolean isKeyListening = false;

    public ClickGuiScreen(ClickGuiModule clickGuiModule) {
        super(Text.of("Uzaware"));
        this.clickGuiModule = clickGuiModule;
        if (elements.isEmpty()) {
            AtomicInteger integer = new AtomicInteger(0);
            Arrays.stream(Category.values()).forEach(c -> elements.add(new PanelElement(c, PANEL_MARGIN + integer.getAndAdd(PANEL_WIDTH + PANEL_MARGIN), PANEL_MARGIN, PANEL_WIDTH)));
        }

        INSTANCE = this;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        xAnim.animateTo(deltaX, 150);
        xAnim.setEasing(EnumEasing.QUAD.getEasing());
        yAnim.animateTo(deltaY, 150);
        yAnim.setEasing(EnumEasing.QUAD.getEasing());
        double x = client.mouse.getX() - deltaX;
        double y = client.mouse.getY() - deltaY;
        Uzaware.nanoVGManager.begin(false);
        NanoVG.nvgTranslate(NanoVGUtils.context, xAnim.getValue(), yAnim.getValue());
        elements.forEach(e -> e.render(context, x, y, delta, 0));
        NanoVG.nvgTranslate(NanoVGUtils.context, -xAnim.getValue(), -yAnim.getValue());
        Uzaware.nanoVGManager.end();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double x = client.mouse.getX() - deltaX;
        double y = client.mouse.getY() - deltaY;
        if (elements.stream().noneMatch(e -> e.mouseClicked(x, y, button))) return super.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        double x = client.mouse.getX() - deltaX;
        double y = client.mouse.getY() - deltaY;
        if (elements.stream().noneMatch(e -> e.mouseReleased(x, y, button))) return super.mouseReleased(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        double x = client.mouse.getX() - deltaX;
        double y = client.mouse.getY() - deltaY;
        if (elements.stream().noneMatch(e -> e.mouseDragged(x, y, button, deltaX, deltaY))) return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        double x = client.mouse.getX() - deltaX;
        double y = client.mouse.getY() - deltaY;
        System.out.println(amount);
        boolean scrolled = elements.stream().noneMatch(e -> e.mouseScrolled(x, y, amount));
        if (shiftPressing && !scrolled) deltaX += (float) (-amount * 50f);
        else deltaY += (float) (amount * 50f);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) shiftPressing = true;
        if (elements.stream().noneMatch(e -> e.keyPressed(keyCode, scanCode, modifiers))) return super.keyPressed(keyCode, scanCode, modifiers);
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) shiftPressing = false;
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
        clickGuiModule.disable();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return !isKeyListening;
    }
}
