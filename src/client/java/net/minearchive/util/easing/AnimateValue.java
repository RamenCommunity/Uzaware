package net.minearchive.util.easing;

import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;

import java.util.stream.IntStream;

public class AnimateValue {

    private SingleValue[] values;
    private final int maxLength;
    private IEasing easing;

    public AnimateValue(EnumEasing easing, int maxLength) {
        this.easing = easing.getEasing();
        this.maxLength = maxLength;
        values = new SingleValue[maxLength];
        for (int i = 0; i < maxLength; i++) {
            values[i] = new SingleValue(easing);
        }
    }

    public void draw(float x, float y, float size, String value, double duration, SimpleColor color) {
        float width = 0;
        char[] formatted = String.format("%1$-" + this.maxLength + "s", value).toCharArray();

        for (int i = 0; i < values.length; i++) {
            values[i].draw(x + width, y, size, color, formatted[i], duration);
            width += NanoVGUtils.ntr.width(String.valueOf(formatted[i]), size);
        }
    }

    public static class SingleValue {
        private final Animation animation;

        public SingleValue(EnumEasing easing) {
            this.animation = new Animation(0.0f, easing.getEasing());
        }

        public void draw(float x, float y, float size, SimpleColor color, char value, double duration) {
            float height = NanoVGUtils.ntr.height(size);

            if (Character.isDigit(value)) {
                int numericValue = Character.getNumericValue(value);
                animation.animateTo(numericValue, duration);
                NanoVGUtils.beginScissor(x, y, NanoVGUtils.ntr.width(String.valueOf(numericValue), size), height);
                IntStream.range(0, 10).forEach(i -> NanoVGUtils.ntr.draw(String.valueOf(i), x, y + (height * i) - (animation.getValue() * height), size, color.color()));
                NanoVGUtils.endScissor();
            } else {
                NanoVGUtils.ntr.draw(String.valueOf(value), x, y, size, color.color());
            }
        }
    }
}
