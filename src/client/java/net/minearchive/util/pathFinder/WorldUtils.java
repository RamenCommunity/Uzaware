package net.minearchive.util.pathFinder;

import net.minearchive.AccessMC;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.stream.IntStream;

public class WorldUtils implements AccessMC {
    public static List<BlockPos> cube(BlockPos pos, float range) {
        int cx = pos.getX(), cy = pos.getY(), cz = pos.getZ();
        int r = (int) Math.floor(range);

        return IntStream.rangeClosed(-r, r)
                .boxed()
                .flatMap(x -> IntStream.rangeClosed(-r, r)
                        .boxed()
                        .flatMap(y -> IntStream.rangeClosed(-r, r)
                                .mapToObj(z -> new BlockPos(cx + x, cy + y, cz + z))))
                .filter(p -> p.getSquaredDistanceFromCenter(cx, cy, cz) < r * r)
                .toList();
    }
}
