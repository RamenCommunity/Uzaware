package net.minearchive.mixin.mixins;

import net.minearchive.module.modules.movement.MovementTweaksModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow public abstract void setPose(EntityPose pose);

    @Shadow public abstract boolean isTouchingWater();

    @Inject(method = "setPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"), cancellable = true)
    public void onSetPose(EntityPose pose, CallbackInfo ci) {
        if (pose == EntityPose.SWIMMING && MovementTweaksModule.INSTANCE.enabled && MovementTweaksModule.INSTANCE.noSwim.getValue() && !isTouchingWater()) {
            ci.cancel();
            setPose(EntityPose.STANDING);
        }
    }
}
