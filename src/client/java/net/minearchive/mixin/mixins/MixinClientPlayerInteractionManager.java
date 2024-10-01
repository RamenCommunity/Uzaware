package net.minearchive.mixin.mixins;

import net.minearchive.event.events.DamageBlockEvent;
import net.minearchive.mixin.ducks.ClientPlayerInteractionManagerDuck;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minearchive.Uzaware.EVENT_BUS;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager implements ClientPlayerInteractionManagerDuck {
    @Shadow protected abstract void syncSelectedSlot();

    @Override
    public void uzaware$updateSelect() {
        syncSelectedSlot();
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    public void onBlockBreak(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        DamageBlockEvent event = new DamageBlockEvent(pos, direction);
        EVENT_BUS.post(event);

        if (event.isCancelled()) cir.setReturnValue(false);
    }
}
