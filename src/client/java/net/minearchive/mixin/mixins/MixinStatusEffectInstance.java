package net.minearchive.mixin.mixins;

import net.minearchive.mixin.ducks.StatusEffectInstanceDuck;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(StatusEffectInstance.class)
public class MixinStatusEffectInstance implements StatusEffectInstanceDuck {
    @Unique private int shimejiReborn_maxDuration;

    @Inject(method = "<init>(Lnet/minecraft/entity/effect/StatusEffect;IIZZZLnet/minecraft/entity/effect/StatusEffectInstance;Ljava/util/Optional;)V", at = @At(value = "RETURN"))
    public void initPotionEffect(StatusEffect type,
                                 int duration,
                                 int amplifier,
                                 boolean ambient,
                                 boolean showParticles,
                                 boolean showIcon,
                                 StatusEffectInstance hiddenEffect,
                                 @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<StatusEffectInstance.FactorCalculationData> factorCalculationData,
                                 CallbackInfo ci) {
        uzaware$maxDuration(duration);
    }

    @Inject(method = "copyFrom", at = @At(value = "RETURN"))
    public void initPotionEffect(StatusEffectInstance that, CallbackInfo ci) {
        uzaware$maxDuration(((MixinStatusEffectInstance) (Object) that).shimejiReborn_maxDuration);
    }

    @Inject(method = "upgrade", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;duration:I", opcode = Opcodes.PUTFIELD))
    public void combineInSetDuration(StatusEffectInstance that, CallbackInfoReturnable<Boolean> cir) {
        uzaware$maxDuration(((MixinStatusEffectInstance) (Object) that).shimejiReborn_maxDuration);
    }

    @Unique
    @Override
    public void uzaware$maxDuration(int maxDuration) {
        this.shimejiReborn_maxDuration = maxDuration;
    }

    @Unique
    @Override
    public int uzaware$maxDuration() {
        return this.shimejiReborn_maxDuration;
    }
}
