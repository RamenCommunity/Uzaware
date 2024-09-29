package net.minearchive.event.events;

import net.minearchive.event.CancellableEvent;
import net.minecraft.client.particle.Particle;

public class ParticleEvent extends CancellableEvent {
    private final Particle particle;

    public ParticleEvent(Particle particle) {
        this.particle = particle;
    }

    public Particle getParticle() {
        return particle;
    }
}
