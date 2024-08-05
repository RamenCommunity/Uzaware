package net.minearchive.mixin.client;

import net.minearchive.Uzaware;
import net.minearchive.module.Module;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;onKeyPressed(Lnet/minecraft/client/util/InputUtil$Key;)V"))
    public void onKeyPressed(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        Uzaware.moduleManager.modules.stream().filter(m -> {
            System.out.println(m.keybind);
            return m.keybind == key;
        }).forEach(Module::toggle);
    }
}
