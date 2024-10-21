package net.minearchive.util.render.shader.shaders;

import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.minearchive.util.render.shader.AbstractShader;

public class BlurShader extends AbstractShader {
    private final Uniform1f radiusUniform;

    public BlurShader() {
        super("shaders/post/blur.json");
        this.radiusUniform = shader.findUniform1f("radius");
    }

    public void setRadius(float radius) {
        radiusUniform.set(radius);
    }
}
