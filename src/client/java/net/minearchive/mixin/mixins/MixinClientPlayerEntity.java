package net.minearchive.mixin.mixins;

import net.minearchive.module.modules.movement.MovementTweaksModule;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends MixinLivingEntity {
    public MixinClientPlayerEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void noJumpDelay(CallbackInfo ci) {
        if (MovementTweaksModule.INSTANCE.enabled && MovementTweaksModule.INSTANCE.noJumpDelay.getValue()) {
            setJumpingCooldown(0);
        }
    }
}
