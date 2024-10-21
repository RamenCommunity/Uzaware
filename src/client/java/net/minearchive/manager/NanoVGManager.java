package net.minearchive.manager;

import net.minearchive.AccessMC;
import net.minearchive.mixin.ducks.StencilFramebufferDuck;
import net.minearchive.util.render.GLState;
import net.minearchive.util.render.NanoVGUtils;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NanoVGManager implements AccessMC {
    private long context;
    private final GLState state = new GLState();

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
}
