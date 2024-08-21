package net.minearchive.util;

import net.minearchive.manager.Font;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Struct;

public class NanoVGUtils {
    public static long context = -1;
    public static Font ntr = new Font("/assets/uzaware/font/NTR-Regular.ttf", "NTR-Regular");
    public static Font symbols = new Font("/assets/uzaware/font/MaterialSymbolsRounded.ttf", "MaterialSymbolsRounded");

    public static void create(long context) {
        if (NanoVGUtils.context == -1) {
            NanoVGUtils.context = context;
            ntr.loadFont(context);
            symbols.loadFont(context);
        } else {
            throw new IllegalStateException("already created nanovg");
        }
    }

    public static void rect(float x, float y, float width, float height, SimpleColor color, Pattern pattern) {
        assertInitialize();
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRect(context, x, y, width, height);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void rect(float x, float y, float width, float height, NVGPaint paint, Pattern pattern) {
        assertInitialize();
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRect(context, x, y, width, height);
        painter(pattern, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, SimpleColor color, Pattern pattern) {
        assertInitialize();
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, NVGPaint paint, Pattern pattern) {
        assertInitialize();
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        painter(pattern, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, SimpleColor start, SimpleColor end, Pattern pattern, Orientation orientation) {
        assertInitialize();
        NVGPaint calloc = linearGradient(x, y, x + width, y + height, start, end, orientation);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void line(float fromX, float fromY, float toX, float toY, SimpleColor color) {
        assertInitialize();
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgMoveTo(context, fromX, fromY);
        NanoVG.nvgLineTo(context, toX, toY);
        painter(Pattern.STROKE, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void line(float startX, float startY, float endX, float endY, SimpleColor start, SimpleColor end) {
        assertInitialize();
        NVGPaint paint = linearGradient(startX, startY, endX, endY, start, end, Orientation.LINE);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgMoveTo(context, startX, startY);
        NanoVG.nvgLineTo(context, endX, endY);
        painter(Pattern.STROKE, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void shadow(float x, float y, float width, float height, float radius, SimpleColor color) {
        assertInitialize();
        NVGColor inner = setupColor(color);
        color.alpha(0);
        NVGColor outer = setupColor(color);
        NVGPaint paint = NVGPaint.calloc();
        NanoVG.nvgBoxGradient(context, x, y + 4, width, height, radius, 4, inner, outer, paint);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgPathWinding(context, NanoVG.NVG_SOLID);
        NanoVG.nvgRoundedRect(context, x - 4, y, width + 8, height + 8, radius);
        NanoVG.nvgPathWinding(context, NanoVG.NVG_HOLE);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        NanoVG.nvgFillPaint(context, paint);
        NanoVG.nvgFill(context);
        NanoVG.nvgClosePath(context);
        inner.free();
        outer.free();
        paint.free();
    }

    public static void circle(float x, float y, float radius, SimpleColor color, Pattern pattern) {
        assertInitialize();
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgCircle(context, x, y, radius);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void stroke(float width) {
        assertInitialize();
        NanoVG.nvgStrokeWidth(context, width);
    }

    public static void lineCap(int cap) {
        assertInitialize();
        NanoVG.nvgLineCap(context, cap);
    }

    public static NVGColor setupColor(SimpleColor color) {
        assertInitialize();
        NVGColor calloc = NVGColor.calloc();
        NanoVG.nvgRGBAf(color.floatRed(), color.floatGreen(), color.floatBlue(), color.floatAlpha(), calloc);
        return calloc;
    }

    public static NVGPaint linearGradient(float startX, float startY, float endX, float endY, SimpleColor start, SimpleColor end, Orientation orientation) {
        assertInitialize();
        NVGColor startColor = setupColor(start);
        NVGColor endColor = setupColor(end);
        NVGPaint calloc = NVGPaint.calloc();
        switch (orientation) {
            case LINE -> NanoVG.nvgLinearGradient(context, startX, startY, endX, endY, startColor, endColor, calloc);
            case HORIZONTAL -> NanoVG.nvgLinearGradient(context, startX, startY, endX, startY, startColor, endColor, calloc);
            case VERTICAL -> NanoVG.nvgLinearGradient(context, startX, startY, startX, endY, startColor, endColor, calloc);
        }
        return calloc;
    }

    public static NVGPaint imagePaintFromID(int texId, int x, int y, int width, int height, float angle, float alpha) {
        NVGPaint calloc = NVGPaint.calloc();
        NanoVG.nvgImagePattern(context, x, y, width, height, angle, texId, alpha, calloc);
        return calloc;
    }

    public static void painter(Pattern p, Struct struct) {
        switch (p) {
            case FILL -> {
                if (struct instanceof NVGColor color) {
                    NanoVG.nvgFillColor(context, color);
                    NanoVG.nvgFill(context);
                } else if (struct instanceof NVGPaint paint) {
                    NanoVG.nvgFillPaint(context, paint);
                    NanoVG.nvgFill(context);
                }
            }
            case STROKE -> {
                if (struct instanceof NVGColor color) {
                    NanoVG.nvgStrokeColor(context, color);
                    NanoVG.nvgStroke(context);
                } else if (struct instanceof NVGPaint paint) {
                    NanoVG.nvgStrokePaint(context, paint);
                    NanoVG.nvgStroke(context);
                }
            }
        }
    }

    public static void assertInitialize() {
        if (context == -1)
            throw new IllegalStateException("Uzaware NanoVG isn't initialized");
    }

    public enum Pattern {
        STROKE, FILL
    }

    public enum Orientation {
        LINE, HORIZONTAL, VERTICAL
    }
}
