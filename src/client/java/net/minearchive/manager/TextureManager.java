package net.minearchive.manager;

import net.minearchive.Uzaware;
import net.minearchive.util.render.NanoVGUtils;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static net.minearchive.util.render.Font.byteBuffer;

public class TextureManager {
    private final Map<String, Integer> textureMap = new HashMap<>();

    public NVGPaint getTexture(String identifier, float width, float height) {
        return getTexture(identifier, 0, 0, width, height);
    }

    public NVGPaint getTexture(String identifier, float x, float y, float width, float height) {
        NVGPaint paint = NVGPaint.calloc();
        NanoVG.nvgImageSize(NanoVGUtils.context, textureMap.get(identifier), new int[] { (int) width }, new int[] { (int) height });
        NanoVG.nvgImagePattern(NanoVGUtils.context, x, y, width, height, 0, textureMap.get(identifier), 1f, paint);
        return paint;
    }

    public NVGPaint getTexture(int image, float x, float y, float width, float height) {
        NVGPaint paint = NVGPaint.calloc();
        NanoVG.nvgImageSize(NanoVGUtils.context, image, new int[] { (int) width }, new int[] { (int) height });
        NanoVG.nvgImagePattern(NanoVGUtils.context, x, y, width, height, 0, image, 1f, paint);
        return paint;
    }

    public void createTexture(InputStream stream, String identifier) {
        if (!textureMap.containsKey(identifier)) {
            try {
                textureMap.put(identifier, NanoVG.nvgCreateImageMem(NanoVGUtils.context, NanoVG.NVG_IMAGE_NEAREST | NanoVG.NVG_IMAGE_GENERATE_MIPMAPS, byteBuffer(stream)));
            } catch (Exception e) {
                Uzaware.LOGGER.error("Error caused while creating texture", e);
            }
        }
    }


}
