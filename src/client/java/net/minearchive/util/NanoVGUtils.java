package net.minearchive.util;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.Struct;

public class NanoVGUtils {
    public static long context = -1;

    public static void assertInitialize() {
        if (context == -1)
            throw new IllegalStateException("Uzaware NanoVG isn't initialized");
    }

    public static void rect(float x, float y, float width, float height, SimpleColor color, Pattern pattern) {
        assertInitialize();
        try (NVGColor calloc = setupColor(color)) {
            NanoVG.nvgBeginPath(context);
            NanoVG.nvgRect(context, x, y, width, height);
            painter(pattern, calloc);
            NanoVG.nvgClosePath(context);
            calloc.free();
        }
    }

    public static void rounded(float x, float y, float width, float height, float radius, SimpleColor color, Pattern pattern) {
        assertInitialize();
        try (NVGColor calloc = setupColor(color)) {
            NanoVG.nvgBeginPath(context);
            NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
            painter(pattern, calloc);
            NanoVG.nvgClosePath(context);
            calloc.free();
        }
    }

    public static void circle(float x, float y, float radius, SimpleColor color, Pattern pattern) {
        assertInitialize();
        try (NVGColor calloc = setupColor(color)) {
            NanoVG.nvgBeginPath(context);
            NanoVG.nvgCircle(context, x, y, radius);
            painter(pattern, calloc);
            NanoVG.nvgClosePath(context);
            calloc.free();
        }
    }

    public static void stroke(float width) {
        assertInitialize();
        NanoVG.nvgStrokeWidth(context, width);
    }

    public static NVGColor setupColor(SimpleColor color) {
        assertInitialize();
        try (NVGColor calloc = NVGColor.calloc()) {
            return NanoVG.nvgRGBAf(color.floatRed(), color.floatGreen(), color.floatBlue(), color.floatAlpha(), calloc);
        }
    }

    public static void painter(Pattern p, NVGColor struct) {
        switch (p) {
            case FILL -> {
                NanoVG.nvgFillColor(context, struct);
                NanoVG.nvgFill(context);
            }
            case STROKE -> {
                NanoVG.nvgStrokeColor(context, struct);
                NanoVG.nvgStroke(context);
            }
        }
    }

    public enum Pattern {
        STROKE, FILL
    }
}
