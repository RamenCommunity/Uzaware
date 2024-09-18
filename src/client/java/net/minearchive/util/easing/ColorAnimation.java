package net.minearchive.util.easing;

import net.minearchive.util.SimpleColor;
import org.joml.Math;

import java.awt.*;

public class ColorAnimation {
    private final Animation red;
    private final Animation green;
    private final Animation blue;
    private final Animation alpha;

    public ColorAnimation(Color color, IEasing animation) {
        this.red = new Animation((color == null) ? 0.0f : color.getRed(), animation);
        this.green = new Animation((color == null) ? 0.0f : color.getGreen(), animation);
        this.blue = new Animation((color == null) ? 0.0f : color.getBlue(), animation);
        this.alpha = new Animation((color == null) ? 0.0f : color.getAlpha(), animation);
    }

    public void setAnimation(Color color, float time) {
        this.red.animateTo(color.getRed(), time);
        this.green.animateTo(color.getGreen(), time);
        this.blue.animateTo(color.getBlue(), time);
        this.alpha.animateTo(color.getAlpha(), time);
    }

    public ColorAnimation setValue(Color color) {
        this.red.setValue(color.getRed());
        this.green.setValue(color.getGreen());
        this.blue.setValue(color.getBlue());
        this.alpha.setValue(color.getAlpha());
        return this;
    }

    public Color getColor() {
        return new Color(
                Math.clamp(0, 255, (int) red.getValue()),
                Math.clamp(0, 255, (int) green.getValue()),
                Math.clamp(0, 255, (int) blue.getValue()),
                Math.clamp(0, 255, (int) alpha.getValue())
        );
    }

    public SimpleColor getAsSimpleColor() {
        return SimpleColor.of(getColor());
    }

}
