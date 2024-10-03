package net.minearchive.mixin.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow
    private int jumpingCooldown;

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    public void setJumpingCooldown(int jumpingCooldown) {
        this.jumpingCooldown = jumpingCooldown;
    }
}
