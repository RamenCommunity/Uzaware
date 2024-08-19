package net.minearchive.mixin.mixins;

import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minearchive.Uzaware.modName;
import static net.minearchive.Uzaware.version;

@Mixin(Window.class)
public class MixinWindow {
    @Redirect(method = "setTitle", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetWindowTitle(JLjava/lang/CharSequence;)V", remap = false))
    public void OnSetTitle(long window, CharSequence title) {
        if (title instanceof String && ((String) title).contains("Minecraft")) {
            GLFW.glfwSetWindowTitle(window, modName + "  |  v" + version);
        } else {
            GLFW.glfwSetWindowTitle(window, title);
        }
    }
}
