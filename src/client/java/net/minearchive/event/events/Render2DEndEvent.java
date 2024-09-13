package net.minearchive.event.events;

import net.minecraft.client.gui.DrawContext;

public record Render2DEndEvent(DrawContext context, float tickDelta) {
}
