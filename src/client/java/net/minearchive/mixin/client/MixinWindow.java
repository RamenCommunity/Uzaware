package net.minearchive.mixin.client;

import net.minearchive.Uzaware;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minearchive.Uzaware.modName;
import static net.minearchive.Uzaware.version;

@Mixin(Window.class)
public class MixinWindow {

    @Redirect(method = "setTitle", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetWindowTitle(JLjava/lang/CharSequence;)V"))
    public void OnSetTitle(long window, CharSequence title) {
        if (title instanceof String && ((String) title).contains("Minecraft")) {
            GLFW.glfwSetWindowTitle(window, modName + "  |  v" + version);
        } else {
            GLFW.glfwSetWindowTitle(window, title);
        }
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void init(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        Uzaware.nanoVGManager.create();
    }
}
