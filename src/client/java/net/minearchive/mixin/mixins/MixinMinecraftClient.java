package net.minearchive.mixin.mixins;

import net.minearchive.Uzaware;
import net.minearchive.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;framebuffer:Lnet/minecraft/client/gl/Framebuffer;", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    public void initOnSetFramebuffer(RunArgs args, CallbackInfo ci) {
        Uzaware.nanoVGManager.create();
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void onUpdate(boolean tick, CallbackInfo ci) {
        new Thread(() -> Uzaware.EVENT_BUS.post(new UpdateEvent())).start();
    }
}
