package net.minearchive.mixin.mixins;

import net.minearchive.event.events.PacketReceiveEvent;
import net.minearchive.event.events.PacketSendEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minearchive.Uzaware.EVENT_BUS;

@Mixin(value = ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At(value = "HEAD"), cancellable = true)
    public void send(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        EVENT_BUS.post(event);

        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "handlePacket", at = @At(value = "HEAD"), cancellable = true)
    private static void handlePacket(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        PacketReceiveEvent event = new PacketReceiveEvent(packet);
        EVENT_BUS.post(event);

        if (event.isCancelled()) ci.cancel();
    }
}
