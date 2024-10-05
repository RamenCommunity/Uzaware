package net.minearchive.util;

import net.minearchive.AccessMC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils implements AccessMC {
    public static List<Block> unbreakable = new ArrayList<>();

    static {
        unbreakable.addAll(List.of(
                Blocks.BEDROCK,
                Blocks.AIR,
                Blocks.WATER,
                Blocks.WATER_CAULDRON,
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON,
                Blocks.COMMAND_BLOCK,
                Blocks.CHAIN_COMMAND_BLOCK,
                Blocks.REPEATING_COMMAND_BLOCK,
                Blocks.STRUCTURE_BLOCK,
                Blocks.STRUCTURE_VOID,
                Blocks.BARRIER,
                Blocks.END_PORTAL,
                Blocks.END_GATEWAY,
                Blocks.END_PORTAL_FRAME
        ));
    }

    public static void damageBlock(BlockPos pos, Direction direction) {
        if (client.player == null || client.world == null) return;
        client.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction, 0));
    }

    @Nullable
    public static BlockState getBlockState(BlockPos pos) {
        if (client.player == null || client.world == null) return null;
        return client.world.getBlockState(pos);
    }

    public static boolean equal(BlockPos pos, Block block) {
        if (client.player == null || client.world == null) return false;
        return client.world.getBlockState(pos).getBlock() == block;
    }
}
