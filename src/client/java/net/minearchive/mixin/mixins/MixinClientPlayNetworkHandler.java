package net.minearchive.mixin.mixins;

import net.minearchive.Uzaware;
import net.minearchive.event.events.MessageSendEvent;
import net.minearchive.manager.CommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.LightData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

import static net.minearchive.Uzaware.EVENT_BUS;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler {
    @Shadow public abstract void sendChatMessage(String content);
    @Shadow public abstract ClientCommandSource getCommandSource();
    @Shadow @Final private MinecraftClient client;

    @Unique
    private final String[] symbol = new String[] {
            ",", ".", "/", "\\", "_", ":", ";", "*", "+", "]", "}", "@", "`", "[", "{",
            "\"", "#", "$", "%", "&", "'", "-", "=", "^", "~", "|"
    };

    @Unique
    private boolean ignoredChatMessage = false;

    /**
     * I Don't have any idea<br>
     * but idea is <a href="https://github.com/MeteorDevelopment/meteor-client/blob/11d4efc103718710475851d952c2cb67f22b4b63/src/main/java/meteordevelopment/meteorclient/mixin/ClientPlayNetworkHandlerMixin.java#L140">here</a>
     */
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String content, CallbackInfo ci) {
        if (ignoredChatMessage) return;

        if (Arrays.stream(symbol).noneMatch(content::startsWith)) {
            MessageSendEvent event = new MessageSendEvent(content);
            EVENT_BUS.post(event);

            if (!event.isCancelled()) {
                ignoredChatMessage = true;
                sendChatMessage(event.getMessage());
                ignoredChatMessage = false;
            }
            ci.cancel();
            return;
        }

        if (content.startsWith(Uzaware.prefix)) {
            try {
                Uzaware.commandManager.dispatcher.execute(content, getCommandSource());
            } catch (Exception ignored) { }

            client.inGameHud.getChatHud().addToMessageHistory(content);
            ci.cancel();
        }
    }

}
