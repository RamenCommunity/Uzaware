package net.minearchive.mixin.mixins;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PlayerMoveC2SPacket.class)
public interface AccessorPlayerMoveC2SPacket {
    @Mutable
    @Accessor(value = "onGround")
    void setOnGround(boolean onGround);
}
