package net.minearchive.manager;

import net.minearchive.util.NanoVGUtils;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

    public static ByteBuffer byteBuffer(@Nullable InputStream input) throws IOException, IllegalArgumentException {
        if (input == null) throw new IllegalArgumentException("InputStream was null");
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
