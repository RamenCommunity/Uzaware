package net.minearchive.util.render.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minearchive.Uzaware;
import net.minecraft.util.Identifier;

public class AbstractShader {
    protected ManagedCoreShader shader;

    public AbstractShader(String jsonPath) {
        shader = ShaderEffectManager.getInstance().manageCoreShader(Identifier.of(Uzaware.modID, jsonPath));
    }

    public void use() {
        RenderSystem.setShader(() -> shader.getProgram());
    }
}
