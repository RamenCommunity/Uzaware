package net.minearchive.manager;

import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Font {
    private ByteBuffer buffer;
    public final String assetsPath;
    public final String name;
    private long nvg;

    public Font(String assetsPath, String name) {
        this.assetsPath = assetsPath;
        this.name = name;
        try {
            InputStream stream = this.getClass().getResourceAsStream(assetsPath);
            this.buffer = byteBuffer(stream);
        } catch (IOException e) {
            LogManager.getLogger("FontLoader").error("failed find resource \"{}\"", assetsPath, e);
        }
    }

    public void draw(String text, float x, float y, float size, int color) {
        this.draw(text, x, y, size, color, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
    }

    public void draw(String text, float x, float y, float size, int color, int align) {
        byte r = (byte) (color >> 0x10 & 0xFF);
        byte g = (byte) (color >> 0x8 & 0xFF);
        byte b = (byte) (color & 0xFF);
        byte a = (byte) (color >> 0x18 & 0xFF);

        float width = width(text, size);
        float height = height(size);
        float offsetX = 0f, offsetY = 0f;

        switch (align) {
            case NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP:
                offsetX = 0f;
                offsetY = 0f;
                break;
            case NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE:
                offsetX = 0f;
                offsetY = -(height / 2f);
                break;
            case NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BOTTOM:
                offsetX = 0f;
                offsetY = -height;
                break;
            case NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_TOP:
                offsetX = -(width / 2f);
                offsetY = 0f;
                break;
            case NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE:
                offsetX = -(width / 2f);
                offsetY = -(height / 2f);
                break;
            case NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_BOTTOM:
                offsetX = -(width / 2f);
                offsetY = -height;
                break;
            case NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_TOP:
                offsetX = -width;
                offsetY = 0f;
                break;
            case NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_MIDDLE:
                offsetX = -width;
                offsetY = -(height / 2f);
                break;
            case NanoVG.NVG_ALIGN_RIGHT | NanoVG.NVG_ALIGN_BOTTOM:
                offsetX = -width;
                offsetY = -height;
                break;
        }

        if (NanoVGUtils.stencilUnContain(x + offsetX, y + offsetY, width, height)) return;
        NanoVG.nvgBeginPath(nvg);
        NanoVG.nvgFontSize(nvg, size);
        NanoVG.nvgTextAlign(nvg, align);
        NanoVG.nvgFontFace(nvg, name);
        NVGColor nvgColor = NVGColor.calloc();
        NanoVG.nvgRGBA(r, g, b, a, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgText(nvg, x, y, text);
        NanoVG.nvgRGBAf(0, 0, 0, 0, nvgColor);
        NanoVG.nvgFillColor(nvg, nvgColor);
        NanoVG.nvgFill(nvg);
        NanoVG.nvgClosePath(nvg);
        nvgColor.free();
    }

    public void formatDraw(String text, float x, float y, float size, int color) {
        String[] split = text.split("§");
        Arrays.stream(split).toList().forEach(string -> {
            if (!string.isEmpty()) {
                Color textColor = switch (string.charAt(0)) {
                    case '0' -> new Color(0, 0, 0);
                    case '1' -> new Color(0, 0, 170);
                    case '2' -> new Color(0, 170, 0);
                    case '3' -> new Color(0, 170, 170);
                    case '4' -> new Color(170, 0, 0);
                    case '5' -> new Color(170, 0, 170);
                    case '6' -> new Color(255, 170, 0);
                    case '7' -> new Color(170, 170, 170);
                    case '8' -> new Color(85, 85, 85);
                    case '9' -> new Color(85, 85, 255);
                    case 'a' -> new Color(85, 255, 85);
                    case 'b' -> new Color(85, 255, 255);
                    case 'c' -> new Color(255, 85, 85);
                    case 'd' -> new Color(255, 85, 255);
                    case 'e' -> new Color(255, 255, 85);
                    case 'f' -> new Color(255, 255, 255);
                    default -> new Color(color);
                };
                string = string.substring(1);
                draw(string, x, y, size, SimpleColor.of(textColor).color());
                NanoVG.nvgTranslate(NanoVGUtils.context, width(string, size), 0);
            }
        });
        String str = text.replaceAll("\u00A7.", "");
        NanoVG.nvgTranslate(NanoVGUtils.context, -width(str, size), 0);
    }

    public float width(String text, float scale) {
        float[] bounds = new float[4];
        NanoVG.nvgFontSize(nvg, scale);
        NanoVG.nvgFontFace(nvg, name);
        return NanoVG.nvgTextBounds(nvg, 0, 0, text, bounds);
    }

    public float height(float scale) {
        float[] ascender = new float[1];
        float[] descender = new float[1];
        float[] line = new float[1];

        NanoVG.nvgFontFace(nvg, name);
        NanoVG.nvgFontSize(nvg, scale);
        NanoVG.nvgTextMetrics(nvg, ascender, descender, line);

        return line[0];
    }

    public void loadFont(long nvg) {
        this.nvg = nvg;
        NanoVG.nvgCreateFontMem(nvg, name, buffer, 1);
        LogManager.getLogger("FontLoader").info("Loaded {}", name);
    }

    public static ByteBuffer byteBuffer(@Nullable InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        for (int n = 0; n != -1; n = input.read(buffer)) {
            output.write(buffer, 0, n);
        }
        byte[] bytes = output.toByteArray();
        ByteBuffer data = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes);
        data.flip();
        return data;
    }
}
