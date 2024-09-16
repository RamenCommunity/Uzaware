package net.minearchive.mixin.mixins;

import net.minearchive.mixin.ducks.ChatHudDuck;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.network.message.MessageSignatureData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.ListIterator;

@Mixin(ChatHud.class)
public abstract class MixinChatHud implements ChatHudDuck {

    @Shadow @Final private List<ChatHudLine> messages;

    @Shadow protected abstract void refresh();

    @Override
    public void uzaware$remove(@Nullable MessageSignatureData signature, boolean onlyFirst) {
        if (signature == null) return;

        ListIterator<ChatHudLine> listIterator = this.messages.listIterator();
        boolean changed = false;
        while (listIterator.hasNext()) {
            ChatHudLine message = listIterator.next();
            if (signature.equals(message.signature())) {
                listIterator.remove();
                changed = true;
                if (onlyFirst) break;
            }
        }

        if (changed) refresh();
    }

}
