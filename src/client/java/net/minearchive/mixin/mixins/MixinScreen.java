package net.minearchive.mixin.mixins;

import net.minearchive.manager.ModuleManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Screen.class)
public abstract class MixinScreen {

    @Shadow @Final protected Text title;

    @Inject(method = "close", at = @At("TAIL"))
    public void close(CallbackInfo ci) {
        if (Objects.equals(title, Text.translatable("chat_screen.title"))) {
            ModuleManager.INSTANCE.dragging = null;
        }
    }

}
