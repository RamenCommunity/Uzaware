package net.minearchive.util;

import net.minearchive.AccessMC;
import net.minearchive.mixin.ducks.ChatHudDuck;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;

import java.nio.charset.StandardCharsets;

public class ChatUtil implements AccessMC {

    public static void sendPlayerMessage(String message) {
        if (nullCheck()) return;
        client.player.networkHandler.sendChatMessage(message);
    }

    public static void addMessage(String message) {
        client.inGameHud.getChatHud().addMessage(Text.of(message));
    }

    public static void addMessage(int id, Text message) {
        ((ChatHudDuck) (client.inGameHud.getChatHud())).uzaware$remove(new MessageSignatureData(signature(String.format("%s", id))), true);
        client.inGameHud.getChatHud().addMessage(
                message,
                new MessageSignatureData(signature(String.format("%s", id))),
                client.isConnectedToLocalServer() ? MessageIndicator.singlePlayer() : MessageIndicator.system()
        );
    }

    private static boolean nullCheck() {
        return client.world == null || client.player == null;
    }

    private static byte[] signature(String identifier) {
        byte[] bytes = new byte[256];
        byte[] identifierBytes = identifier.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(identifierBytes, 0, bytes, 0, Math.min(bytes.length, identifierBytes.length));
        return bytes;
    }
}
