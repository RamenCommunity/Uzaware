package net.minearchive.mixin.mixins;

import net.minearchive.module.modules.render.CameraTweaksModule;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Camera.class)
public class MixinCamera {
    @ModifyVariable(method = "clipToSpace", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double modifyClipToSpace(double value) {
        return CameraTweaksModule.INSTANCE.distance();
    }

    @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
    private void onClipToSpace(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        if (CameraTweaksModule.INSTANCE.clip()) {
            cir.setReturnValue(desiredCameraDistance);
        }
    }
}
