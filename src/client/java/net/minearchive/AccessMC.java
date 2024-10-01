package net.minearchive;

import net.minecraft.client.MinecraftClient;

public interface AccessMC {
    MinecraftClient client = MinecraftClient.getInstance();

    default boolean nullCheck() {
        return client.world == null || client.player == null;
    }
}
