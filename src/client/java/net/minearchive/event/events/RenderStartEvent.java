package net.minearchive.event.events;

import net.minecraft.client.gui.DrawContext;

public record RenderStartEvent(DrawContext context, float tickDelta) {
}
