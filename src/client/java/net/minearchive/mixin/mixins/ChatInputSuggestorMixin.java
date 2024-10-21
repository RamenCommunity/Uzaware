package net.minearchive.mixin.mixins;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minearchive.Uzaware;
import net.minearchive.manager.CommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin {
    @Shadow @Nullable private ParseResults<CommandSource> parse;
    @Shadow @Final TextFieldWidget textField;
    @Shadow @Nullable private ChatInputSuggestor.SuggestionWindow window;
    @Shadow boolean completingSuggestions;
    @Shadow @Nullable private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow protected abstract void showCommandSuggestions();

    @Inject(method = "refresh", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void onRefresh(CallbackInfo ci, String string, StringReader stringReader) {
        String prefix = Uzaware.prefix;
        int l = prefix.length();

        if (stringReader.canRead(l) && stringReader.getString().startsWith(prefix, stringReader.getCursor())) {
            stringReader.setCursor(stringReader.getCursor() + l);

            if (this.parse == null) {
                //noinspection DataFlowIssue
                this.parse = Uzaware.commandManager.dispatcher.parse(stringReader, MinecraftClient.getInstance().getNetworkHandler().getCommandSource());
            }

            int cursor = textField.getCursor();

            if (cursor >= l && (this.window == null || !this.completingSuggestions)) {
                this.pendingSuggestions = Uzaware.commandManager.dispatcher.getCompletionSuggestions(this.parse, cursor);
                this.pendingSuggestions.thenRun(() -> {
                    if (this.pendingSuggestions.isDone()) {
                        this.showCommandSuggestions();
                    }
                });
            }

            ci.cancel();
        }
    }
}
