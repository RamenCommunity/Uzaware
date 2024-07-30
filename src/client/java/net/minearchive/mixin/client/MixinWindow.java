package net.minearchive.mixin.client;

import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minearchive.Uzaware.modName;
import static net.minearchive.Uzaware.version;

@Mixin(Window.class)
public class MixinWindow {

    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true)
    public void OnSetTitle(String title, CallbackInfo ci){
        if (title.contains("Minecraft")) {
            ((Window) (Object) this).setTitle(modName + "  |  v" + version);
            ci.cancel();
        }
    }
}
