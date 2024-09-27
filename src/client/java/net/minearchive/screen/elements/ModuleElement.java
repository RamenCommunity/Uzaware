package net.minearchive.screen.elements;

import com.google.common.util.concurrent.AtomicDouble;
import net.minearchive.module.Module;
import net.minearchive.screen.AbstractElement;
import net.minearchive.screen.IElement;
import net.minearchive.screen.elements.setting.*;
import net.minearchive.setting.settings.*;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.ColorAnimation;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleElement extends AbstractElement<Module> {
    private final Module module;
    private final List<IElement> settingComponents = new ArrayList<>();
    private final ColorAnimation setting, backgroundL, backgroundR;
    private final Animation h = new Animation(- 10, EnumEasing.QUART.getEasing()), a;
    private boolean opened;
    private float cache = 0;

    public ModuleElement(Module module, float x, float y, float width, float height) {
        super(module, x, y, width, height);
        module.settings.forEach(s -> {
            if (s instanceof BooleanSetting) settingComponents.add(new BooleanElement((BooleanSetting) s, x, y ,width ,height));
            if (s instanceof IntegerSetting) settingComponents.add(new IntegerElement((IntegerSetting) s, x, y ,width ,height));
            if (s instanceof FloatSetting) settingComponents.add(new FloatElement((FloatSetting) s, x, y ,width ,height));
            if (s instanceof DoubleSetting) settingComponents.add(new DoubleElement((DoubleSetting) s, x, y ,width ,height));
            if (s instanceof StringSetting) settingComponents.add(new StringElement((StringSetting) s, x, y ,width ,height));
            if (s instanceof EnumSetting) settingComponents.add(new EnumElement((EnumSetting<? extends Enum<?>>) s, x, y ,width ,height));
            if (s instanceof KeyBindSetting) settingComponents.add(new KeyBindElement((KeyBindSetting) s, x, y, width, height));
        });
        this.module = module;
        this.setting = new ColorAnimation(opened ? settingComponents.isEmpty() ? SimpleColor.of(0, 0, 0, 0) : SimpleColor.of(0, 0, 0, 94) : SimpleColor.of(0, 0, 0, 0), EnumEasing.QUART.getEasing());
        this.backgroundL = new ColorAnimation(module.enabled ? SimpleColor.of(0xFFFAC0FF) : SimpleColor.of(0xD93C3C3C), EnumEasing.QUART.getEasing());
        this.backgroundR = new ColorAnimation(module.enabled ? SimpleColor.of(0xFFB3A5FF) : SimpleColor.of(0xD93C3C3C), EnumEasing.QUART.getEasing());
        this.a = new Animation(opened ? 1 : 0, EnumEasing.QUART.getEasing());
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        super.render(context, mouseX, mouseY, delta, offset);
        this.offset = offset;
        this.a.animateTo(opened ? 1 : 0, 350);
        this.setting.setAnimation(opened ? settingComponents.isEmpty() ? SimpleColor.of(0, 0, 0, 0) : SimpleColor.of(0, 0, 0, 94) : SimpleColor.of(0, 0, 0, 0), 350);
        this.backgroundL.setAnimation(module.enabled ? new Color(0xFFFAC0FF) : new Color(0xD93C3C3C), 350);
        this.backgroundR.setAnimation(module.enabled ? new Color(0xFFB3A5FF) : new Color(0xD93C3C3C), 350);
        NanoVGUtils.rounded(x + 20,
                y + offset,
                width - 40,
                height,
                5,
                SimpleColor.of(backgroundL.getColor()),
                SimpleColor.of(backgroundR.getColor()),
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
        AtomicDouble off = new AtomicDouble(70);
        NanoVGUtils.beginScissor(x + 20, y + 45 + offset, width - 40, h.getValue());
        NanoVGUtils.rounded(x + 20, y + 45 + offset, width - 40, h.getValue(), 6, SimpleColor.of(setting.getColor()), NanoVGUtils.Pattern.FILL);
        NanoVG.nvgSave(NanoVGUtils.context);
        NanoVG.nvgGlobalAlpha(NanoVGUtils.context, a.getValue());
        settingComponents.forEach(c -> c.render(context, mouseX, mouseY, delta, (float) off.getAndAdd(c.height()) + offset));
        NanoVG.nvgRestore(NanoVGUtils.context);
        NanoVGUtils.endScissor();
        if (cache != off.floatValue() && opened) h.setValue(off.floatValue() - 60);
        else h.animateTo(opened ? off.floatValue() - 60 : 0, 350);
        cache = off.floatValue();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (MouseUtils.hover(mouseX, mouseY, x + 20, y + offset, width - 40, height)) {
            switch (button) {
                case 0 -> t.toggle();
                case 1 -> opened = !opened;
            }
            return true;
        }
        if (!opened) return false;

        return !settingComponents.stream().filter(c -> c.mouseClicked(mouseX, mouseY, button)).toList().isEmpty();
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
        return !settingComponents.stream().filter(c -> c.keyPressed(keyCode, scanCode, modifiers)).toList().isEmpty();
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
        return height + h.getValue() + 10;
    }
}
