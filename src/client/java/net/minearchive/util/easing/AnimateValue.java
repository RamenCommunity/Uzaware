package net.minearchive.util.easing;

import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import org.lwjgl.nanovg.NanoVG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AnimateValue {
    List<SingleValue> singleValues = new ArrayList<>();

    private int value;
    IEasing easing;

    public AnimateValue(int value, EnumEasing easing, int maxLength) {
        this.value = value;
        this.easing = easing.getEasing();
        for (int i = 0; i < maxLength; i++) {
            singleValues.add(new SingleValue(easing));
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void draw(float x, float y, float size, double duration, SimpleColor color) {
        long context = NanoVGUtils.context;
        float width = NanoVGUtils.ntr.width("0", size * String.valueOf(value).length());
        float height = NanoVGUtils.ntr.height(size);
        NanoVG.nvgSave(context);
        NanoVG.nvgScissor(context, x - width / 2f, y - height / 2f, width, height);


        NanoVG.nvgResetScissor(context);
        NanoVG.nvgRestore(context);
    }

    public static class SingleValue {
        private final Animation animation;

        public SingleValue(EnumEasing easing) {
            this.animation = new Animation(0.0f, easing.getEasing());
        }

        public void draw(float x, float y, float size, SimpleColor color, int value, double duration) {
            float height = NanoVGUtils.ntr.height(size);
            animation.animateTo(value, duration);
            NanoVG.nvgScissor(NanoVGUtils.context, x, y, NanoVGUtils.ntr.width("0", size), height);
            NanoVG.nvgTranslate(NanoVGUtils.context, 0, -animation.getValue() * height);
            IntStream.range(0, 10).forEach(i -> {
                if ((i < value || i > value)  && Math.abs(i - value) >= 2) return;
                NanoVGUtils.ntr.draw(String.valueOf(i), x, y + (height * i), size, color.color());
            });
            NanoVG.nvgTranslate(NanoVGUtils.context, 0, animation.getValue() * height);
            NanoVG.nnvgResetScissor(NanoVGUtils.context);
        }
    }
}
