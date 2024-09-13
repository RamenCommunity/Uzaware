package net.minearchive.util;

import java.awt.*;

/**
 * The SimpleColor class represents a simple color with RGB and alpha components.
 * The color value is represented as an integer with the format: 0xAARRGGBB.
 * Each component (alpha, red, green, blue) ranges from 0 to 255.
 */
public class SimpleColor extends Color {
    /**
     * The integer value representing the color.
     */
    private int value;

    /**
     * Constructs a new SimpleColor object with the given integer value.
     *
     * @param value The integer value representing the color in the format 0xAARRGGBB.
     */
    public SimpleColor(int value) {
        super(value);
        this.value = value;
    }

    /**
     * Constructs a new SimpleColor object with the specified RGB components and alpha component.
     * The values for each component should be between 0 and 255.
     *
     * @param red   The red component of the color (0 to 255).
     * @param green The green component of the color (0 to 255).
     * @param blue  The blue component of the color (0 to 255).
     * @param alpha The alpha component of the color (0 to 255).
     */
    public SimpleColor(int red, int green, int blue, int alpha) {
        super(red, green, blue, alpha);
        value = (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
    }

    /**
     * Constructs a new SimpleColor object with the specified RGB components.
     * The values for each component should be between 0 and 255.
     * The alpha component will be set to 255 (opaque).
     *
     * @param red   The red component of the color (0 to 255).
     * @param green The green component of the color (0 to 255).
     * @param blue  The blue component of the color (0 to 255).
     */
    public SimpleColor(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    /**
     * Constructs a new SimpleColor object with the given java.awt.Color object.
     * The color's RGB and alpha components will be used to create the SimpleColor.
     *
     * @param color The {@link Color} object to create the SimpleColor from.
     */
    public SimpleColor(Color color) {
        super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();
        value = (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
    }

    /**
     * Retrieves the alpha component of the color.
     *
     * @return The alpha component of the color (0 to 255).
     */
    public int alpha() {
        return value >> 24 & 0xff;
    }

    /**
     * Retrieves the red component of the color.
     *
     * @return The red component of the color (0 to 255).
     */
    public int red() {
        return value >> 16 & 0xFF;
    }

    /**
     * Retrieves the green component of the color.
     *
     * @return The green component of the color (0 to 255).
     */
    public int green() {
        return value >> 8 & 0xFF;
    }

    /**
     * Retrieves the blue component of the color.
     *
     * @return The blue component of the color (0 to 255).
     */
    public int blue() {
        return value & 0xFF;
    }

    /**
     * Retrieves the alpha component of the color as a floating-point value between 0 and 1.
     *
     * @return The alpha component of the color as a float (0.0 to 1.0).
     */
    public float floatAlpha() {
        return (value >> 24 & 0xff) / 255F;
    }

    /**
     * Retrieves the red component of the color as a floating-point value between 0 and 1.
     *
     * @return The red component of the color as a float (0.0 to 1.0).
     */
    public float floatRed() {
        return (value >> 16 & 0xFF) / 255F;
    }

    /**
     * Retrieves the green component of the color as a floating-point value between 0 and 1.
     *
     * @return The green component of the color as a float (0.0 to 1.0).
     */
    public float floatGreen() {
        return (value >> 8 & 0xFF) / 255F;
    }

    /**
     * Retrieves the blue component of the color as a floating-point value between 0 and 1.
     *
     * @return The blue component of the color as a float (0.0 to 1.0).
     */
    public float floatBlue() {
        return (value & 0xFF) / 255F;
    }

    /**
     * Retrieves the integer value representing the color.
     *
     * @return The integer value representing the color in the format 0xAARRGGBB.
     */
    public int color() {
        return value;
    }

    /**
     * Sets the alpha component of the color.
     *
     * @param alpha The alpha component of the color to set (0 to 255).
     */
    public void alpha(int alpha) {
        this.value = this.value & 0x00FFFFFF | alpha;
    }

    /**
     * Sets the red component of the color.
     *
     * @param red The red component of the color to set (0 to 255).
     */
    public void red(int red) {
        this.value = this.value & 0xFF00FFFF | red << 16;
    }

    /**
     * Sets the green component of the color.
     *
     * @param green The green component of the color to set (0 to 255).
     */
    public void green(int green) {
        this.value = this.value & 0xFFFF00FF | green << 8;
    }

    /**
     * Sets the blue component of the color.
     *
     * @param blue The blue component of the color to set (0 to 255).
     */
    public void blue(int blue) {
        this.value = this.value & 0xFFFFFF00 | blue;
    }

    /**
     * Sets the alpha component of the color using a floating-point value.
     *
     * @param alpha The alpha component of the color (0.0 to 1.0).
     */
    public void floatAlpha(float alpha) {
        this.value = this.value & 0x00FFFFFF | (int) (alpha * 255F) << 24;
    }

    /**
     * Sets the red component of the color using a floating-point value.
     *
     * @param red The red component of the color (0.0 to 1.0).
     */
    public void floatRed(float red) {
        this.value = this.value & 0xFF00FFFF | (int) (red * 255F) << 16;
    }

    /**
     * Sets the green component of the color using a floating-point value.
     *
     * @param green The green component of the color (0.0 to 1.0).
     */
    public void floatGreen(float green) {
        this.value = this.value & 0xFFFF00FF | (int) (green * 255F) << 8;
    }

    /**
     * Sets the blue component of the color using a floating-point value.
     *
     * @param blue The blue component of the color (0.0 to 1.0).
     */
    public void floatBlue(float blue) {
        this.value = this.value & 0xFFFFFF00 | (int) (blue * 255F);
    }

    public static SimpleColor of(Color color) {
        return new SimpleColor(color);
    }

    public static SimpleColor of(int red, int green, int blue) {
        return new SimpleColor(red, green, blue);
    }

    public static SimpleColor of(int red, int green, int blue, int alpha) {
        return new SimpleColor(red, green, blue, alpha);
    }

    public static SimpleColor of(int color) {
        return new SimpleColor(color);
    }
}