package net.minearchive.event.events;

import net.minecraft.client.gui.DrawContext;

public record Render2DStartEvent(DrawContext context, float tickDelta) {
}
