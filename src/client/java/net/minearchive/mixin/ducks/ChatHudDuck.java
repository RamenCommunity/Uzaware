package net.minearchive.mixin.ducks;

import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface ChatHudDuck {
    void uzaware$add(Text message, int id);
    void uzaware$remove(@Nullable MessageSignatureData signatureData, boolean all);
}
