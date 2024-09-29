package net.minearchive.mixin.mixins;

import net.minearchive.Uzaware;
import net.minearchive.event.events.ParticleEvent;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ParticleManager.class)
public class MixinParticleManager {
    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At(value = "HEAD"), cancellable = true)
    public void addParticle(Particle particle, CallbackInfo ci) {
        ParticleEvent event = new ParticleEvent(particle);
        Uzaware.EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
