package net.minearchive.manager;

import net.minearchive.AccessMC;
import net.minearchive.util.NanoVGUtils;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

public class NanoVGManager implements AccessMC {
    private long context;

    public void create() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS);
        NanoVGUtils.context = context;
    }

    public void begin(boolean scaled) {
        int width = scaled ? client.getWindow().getScaledWidth() : client.getWindow().getWidth();
        int height = scaled ? client.getWindow().getScaledHeight() : client.getWindow().getHeight();
        float factor = scaled ? (float) client.getWindow().getScaleFactor() : 1F;
        NanoVG.nvgBeginFrame(context, width, height, factor);
    }

    public void end() {
        NanoVG.nvgEndFrame(context);
    }
}
