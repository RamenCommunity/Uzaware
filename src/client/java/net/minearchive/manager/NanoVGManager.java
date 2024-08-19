package net.minearchive.manager;

import net.minearchive.AccessMC;
import net.minearchive.mixin.ducks.StencilFramebufferDucks;
import net.minearchive.util.GLState;
import net.minearchive.util.NanoVGUtils;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

public class NanoVGManager implements AccessMC {
    private long context;
    private final GLState state = new GLState();

    public void create() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
        NanoVGUtils.create(context);
        StencilFramebufferDucks stencil = (StencilFramebufferDucks) client.getFramebuffer();
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
}
