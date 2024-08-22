package net.minearchive.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minearchive.mixin.ducks.StencilFramebufferDuck;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.IntBuffer;

@Mixin(Framebuffer.class)
public abstract class MixinFramebuffer implements StencilFramebufferDuck {
    @Shadow public abstract void resize(int width, int height, boolean getError);

    @Shadow public int viewportWidth;
    @Shadow public int viewportHeight;
    @Unique private boolean stencilEnabled = false;

    @Redirect(method = "initFbo", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V", ordinal = 0))
    private void initFbo$stencilSupport(int target, int level, int internalFormat, int width, int height, int border, int format, int type, IntBuffer pixels) {
        if (stencilEnabled) {
            GlStateManager._texImage2D(target, 0, GL30.GL_DEPTH32F_STENCIL8, width, height, 0, GL30.GL_DEPTH_STENCIL, GL30.GL_FLOAT_32_UNSIGNED_INT_24_8_REV, pixels);
        } else {
            GlStateManager._texImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
        }
    }

    @Redirect(method = "initFbo", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glFramebufferTexture2D(IIIII)V", ordinal = 1))
    private void initFbo$stencilSupport(int target, int attachment, int textureTarget, int texture, int level) {
        if (stencilEnabled) {
            GlStateManager._glFramebufferTexture2D(target, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_TEXTURE_2D, texture, 0);
            GlStateManager._glFramebufferTexture2D(target, GL30.GL_STENCIL_ATTACHMENT, GL30.GL_TEXTURE_2D, texture, 0);
        } else {
            GlStateManager._glFramebufferTexture2D(target, attachment, textureTarget, texture, level);
        }
    }

    @Unique
    @Override
    public boolean uzaware$stencilEnabled() {
        return stencilEnabled;
    }

    @Unique
    @Override
    public void uzaware$enableStencil() {
        if (!this.stencilEnabled) {
            this.stencilEnabled = true;
            resize(viewportWidth, viewportHeight, MinecraftClient.IS_SYSTEM_MAC);
        }
    }
}

