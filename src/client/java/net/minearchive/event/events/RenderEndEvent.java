package net.minearchive.event.events;

import net.minecraft.client.gui.DrawContext;

public record RenderEndEvent(DrawContext context, float tickDelta) {
}
