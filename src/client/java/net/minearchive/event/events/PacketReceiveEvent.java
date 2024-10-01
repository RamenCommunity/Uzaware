package net.minearchive.event.events;

import net.minearchive.event.CancellableEvent;
import net.minecraft.network.packet.Packet;

public final class PacketReceiveEvent extends CancellableEvent {
    private final Packet<?> packet;

    public PacketReceiveEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
