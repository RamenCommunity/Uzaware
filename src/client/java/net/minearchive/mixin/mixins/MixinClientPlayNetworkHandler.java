package net.minearchive.mixin.mixins;

import net.minearchive.event.events.MessageSendEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
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

        if (!Arrays.stream(symbol).allMatch(content::startsWith)) {
            MessageSendEvent event = new MessageSendEvent(content);
            EVENT_BUS.post(event);

            if (!event.isCancelled()) {
                ignoredChatMessage = true;
                sendChatMessage(event.getMessage());
                ignoredChatMessage = false;
            }
            ci.cancel();
        }
    }

}
