package net.minearchive.util;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minearchive.AccessMC;
import net.minearchive.mixin.ducks.ClientPlayerInteractionManagerDuck;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.IntStream;

@SuppressWarnings("DataFlowIssue")
public class InventoryUtils implements AccessMC {

    public static int findHotBarItem(Item item) {
        return IntStream.rangeClosed(0, 8)
                .boxed()
                .filter(i -> client.player.getInventory().getStack(i).getItem() == item)
                .min(Comparator.comparing(i -> i))
                .orElse(-1);
    }

    public static int findBestToolSlot(BlockState blockState) {
        return IntStream.rangeClosed(0, 8)
                .boxed()
                .filter(i -> client.player.getInventory().getStack(i).getItem() instanceof ToolItem)
                .sorted(Comparator.comparing(i -> calcScore(client.player.getInventory().getStack(i), blockState)))
                .max(Comparator.naturalOrder())
                .orElse(-1);
    }

    public static void pick(int i) {
        if (i < 0 || 8 < i) return;
        client.player.getInventory().selectedSlot = i;
    }

    public static void swapInv(int i) {
        if (i < 0 || i > 8) return;
        if (client.player.getInventory().selectedSlot == i) return;
        client.player.getInventory().selectedSlot = i;
    }

    public static void updateHotBar() {
        ((ClientPlayerInteractionManagerDuck) client.interactionManager).uzaware$updateSelect();
    }

    public static double calcScore(ItemStack itemStack, BlockState state) {
        if (itemStack == null) return 0.0d;
        if (!(itemStack.getItem() instanceof ToolItem)) return 0.0d;
        double score = 0;

        score += itemStack.getMiningSpeedMultiplier(state) * 1000;
        score += EnchantmentHelper.get(itemStack).getOrDefault(Enchantments.EFFICIENCY, 0) * 2;
        score += EnchantmentHelper.get(itemStack).getOrDefault(Enchantments.UNBREAKING, 0);
        score += EnchantmentHelper.get(itemStack).getOrDefault(Enchantments.MENDING, 0);

        return score;
    }
}
