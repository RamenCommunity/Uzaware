package net.minearchive.event.events;

import net.minearchive.event.CancellableEvent;
import net.minecraft.network.packet.Packet;

public final class PacketSendEvent extends CancellableEvent {
    private final Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
