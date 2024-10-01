package net.minearchive.event.events;

import net.minecraft.client.util.math.MatrixStack;

public record Render3DEvent(MatrixStack matrices, float tickDelta) {
}
