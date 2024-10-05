package net.minearchive.manager;

import net.minearchive.Uzaware;
import net.minearchive.util.NanoVGUtils;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static net.minearchive.manager.NanoVGManager.Font.byteBuffer;

public class TextureManager {
    private final NanoVGManager nanoVGManager;

    private final Map<String, Integer> textureMap = new HashMap<>();

    public TextureManager() {
        nanoVGManager = Uzaware.nanoVGManager;
    }

    public NVGPaint getTextures(String identifier, float width, float height) {
        return getTextures(identifier, 0, 0, width, height);
    }

    public NVGPaint getTextures(String identifier, float x, float y, float width, float height) {
        NVGPaint paint = NVGPaint.calloc();
        NanoVG.nvgImageSize(NanoVGUtils.context, textureMap.get(identifier), new int[] { (int) width }, new int[] { (int) height });
        NanoVG.nvgImagePattern(NanoVGUtils.context, x, y, width, height, 0, textureMap.get(identifier), 1f, paint);
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
