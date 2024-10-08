package net.minearchive.util;

import net.minearchive.manager.NanoVGManager;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.Struct;

import java.util.ArrayList;
import java.util.List;

public class NanoVGUtils {
    public static long context = -1;
    public static final NanoVGManager.Font ntr = new NanoVGManager.Font("/assets/uzaware/font/NTR-Regular.ttf", "NTR-Regular");
    public static final NanoVGManager.Font symbols = new NanoVGManager.Font("/assets/uzaware/font/MaterialSymbolsRounded.ttf", "MaterialSymbolsRounded");

    public static final List<box> stencilBoxes = new ArrayList<>();
    public static boolean usingStencil = false;

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
        if (stencilUnContain(x, y, width, height)) return;
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRect(context, x, y, width, height);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void rect(float x, float y, float width, float height, NVGPaint paint, Pattern pattern) {
        assertInitialize();
        if (stencilUnContain(x, y, width, height)) return;
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRect(context, x, y, width, height);
        painter(pattern, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, SimpleColor color, Pattern pattern) {
        assertInitialize();
        if (stencilUnContain(x, y, width, height)) return;
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, NVGPaint paint, Pattern pattern) {
        assertInitialize();
        if (stencilUnContain(x, y, width, height)) return;
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        painter(pattern, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void rounded(float x, float y, float width, float height, float radius, SimpleColor start, SimpleColor end, Pattern pattern, Orientation orientation) {
        assertInitialize();
        if (stencilUnContain(x, y, width, height)) return;
        NVGPaint calloc = linearGradient(x, y, x + width, y + height, start, end, orientation);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgRoundedRect(context, x, y, width, height, radius);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void line(float fromX, float fromY, float toX, float toY, SimpleColor color) {
        assertInitialize();
        if (stencilUnContain(fromX, fromY, toX - fromX, toY - fromY)) return;
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgMoveTo(context, fromX, fromY);
        NanoVG.nvgLineTo(context, toX, toY);
        painter(Pattern.STROKE, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void line(float fromX, float fromY, float toX, float toY, NVGPaint paint) {
        assertInitialize();
        if (stencilUnContain(fromX, fromY, toX - fromX, toY - fromY)) return;
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgMoveTo(context, fromX, fromY);
        NanoVG.nvgLineTo(context, toX, toY);
        painter(Pattern.STROKE, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void line(float fromX, float fromY, float toX, float toY, SimpleColor start, SimpleColor end) {
        assertInitialize();
        if (stencilUnContain(fromX, fromY, toX - fromX, toY - fromY)) return;
        NVGPaint paint = linearGradient(fromX, fromY, toX, toY, start, end, Orientation.LINE);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgMoveTo(context, fromX, fromY);
        NanoVG.nvgLineTo(context, toX, toY);
        painter(Pattern.STROKE, paint);
        NanoVG.nvgClosePath(context);
        paint.free();
    }

    public static void shadow(float x, float y, float width, float height, float radius, SimpleColor color) {
        assertInitialize();
        if (stencilUnContain(x, y, width, height)) return;
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
        if (stencilUnContain(x - radius, y - radius, x + radius, y + radius)) return;
        NVGColor calloc = setupColor(color);
        NanoVG.nvgBeginPath(context);
        NanoVG.nvgCircle(context, x, y, radius);
        painter(pattern, calloc);
        NanoVG.nvgClosePath(context);
        calloc.free();
    }

    public static void beginScissor(float x, float y, float width, float height) {
        assertInitialize();
        NanoVG.nvgScissor(context, x, y, width, height);
        stencilBoxes.add(new box(x, y, width, height));
        usingStencil = true;
    }

    public static void endScissor() {
        assertInitialize();
        NanoVG.nvgResetScissor(context);
        stencilBoxes.clear();
        usingStencil = false;
    }

    public static boolean stencilUnContain(float x, float y, float width, float height) {
        return stencilBoxes.stream().filter(b -> isIntersecting(b, x, y, width, height)).toList().isEmpty() && NanoVGUtils.usingStencil;
    }

    private static boolean isIntersecting(box b1, float x, float y, float width, float height) {
        return b1.x < x + width && b1.x + b1.width > x &&
                b1.y < y + height && b1.y + b1.height > y;
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
        startColor.free();
        endColor.free();
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

    public record box(float x, float y, float width, float height) {  }
}
