package net.minearchive.manager;

import net.minearchive.AccessMC;
import net.minearchive.mixin.ducks.StencilFramebufferDuck;
import net.minearchive.util.GLState;
import net.minearchive.util.NanoVGUtils;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static net.minearchive.manager.Font.byteBuffer;

public class NanoVGManager implements AccessMC {
    private long context;
    private final GLState state = new GLState();

    private final Map<String, Integer> textureMap = new HashMap<>();

    public void create() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
        NanoVGUtils.create(context);
        StencilFramebufferDuck stencil = (StencilFramebufferDuck) client.getFramebuffer();
        if (!stencil.uzaware$stencilEnabled())
            stencil.uzaware$enableStencil();
    }

    public void begin(boolean scaled) {
        state.backupGlState();
        int width = scaled ? client.getWindow().getScaledWidth() : client.getWindow().getWidth();
        int height = scaled ? client.getWindow().getScaledHeight() : client.getWindow().getHeight();
        float factor = scaled ? (float) client.getWindow().getScaleFactor() : 1F;
        NanoVG.nvgBeginFrame(context, width, height, factor);
    }

    public void end() {
        NanoVG.nvgEndFrame(context);
        state.restoreGlState();
//        GL11C.glDisable(GL11C.GL_STENCIL_TEST);
    }

    public NVGPaint getTextures(String identifier, float width, float height) {
        NVGPaint paint = NVGPaint.calloc();
        NanoVG.nvgImageSize(context, textureMap.get(identifier), new int[] { (int) width }, new int[] { (int) height });
        NanoVG.nvgImagePattern(context, 0, 0, width, height, 0, textureMap.get(identifier), 1f, paint);
        return paint;
    }

    public void createTexture(InputStream stream, String identifier) {
        if (!textureMap.containsKey(identifier)) {
            try {
                textureMap.put(identifier, NanoVG.nvgCreateImageMem(context, NanoVG.NVG_IMAGE_NEAREST, byteBuffer(stream)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
