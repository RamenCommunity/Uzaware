package net.minearchive.mixin.mixins;

import net.minearchive.Uzaware;
import net.minearchive.manager.ModuleManager;
import net.minearchive.module.HudModule;
import net.minearchive.util.MouseUtils;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen extends Screen {
    @Shadow protected abstract void onChatFieldUpdate(String chatText);

    protected MixinChatScreen(Text title) { super(title); }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (client == null) return;
        float x = (float) client.mouse.getX();
        float y = (float) client.mouse.getY();

        ModuleManager.INSTANCE.modules.stream().filter(m -> m instanceof HudModule).map(m -> ((HudModule) m)).toList().forEach(m -> {
            if (MouseUtils.hover(x, y, m.x, m.y, m.width, m.height)) {
                if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS
                        && ModuleManager.INSTANCE.dragging == null) {
                    ModuleManager.INSTANCE.dragging = m;
                    m.oldMouseX = x;
                    m.oldMouseY = y;
                } else if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_RELEASE) {
                    ModuleManager.INSTANCE.dragging = null;
                }
                m.alpha.animateTo(0.5f, 150);
            } else m.alpha.animateTo(0, 150);

            if (ModuleManager.INSTANCE.dragging == m) {
                m.x += x - m.oldMouseX;
                m.y += y - m.oldMouseY;

                m.oldMouseX = x;
                m.oldMouseY = y;
            }

            Uzaware.nanoVGManager.begin(false);
            NanoVGUtils.rounded(m.x, m.y, m.width, m.height, m.round, SimpleColor.of(0x00000000).floatAlpha(m.alpha.getValue()), NanoVGUtils.Pattern.FILL);
            NanoVGUtils.ntr.draw(m.name, m.x, m.y, 32, SimpleColor.of(0x00ffffffff).floatAlpha(m.alpha.getValue() * 2f).color());
            NanoVGUtils.symbols.draw("\uf71e", m.x + m.width / 2f, m.y + m.height / 2f, 38, SimpleColor.of(0x00ffffffff).floatAlpha(m.alpha.getValue() * 2f).color(), NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
            Uzaware.nanoVGManager.end();
        });
    }
}
