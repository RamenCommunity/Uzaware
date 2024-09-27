package net.minearchive.util;

import net.minearchive.AccessMC;
import net.minecraft.item.Item;

import java.util.Comparator;
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

    public static void pick(int i) {
        if (i < 0 || 8 < i) return;
        client.player.getInventory().selectedSlot = i;
    }
}
