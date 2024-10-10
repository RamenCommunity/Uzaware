package net.minearchive.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityUtils {
    public static Vec3d prevPos(Entity e) {
        return new Vec3d(e.prevX, e.prevY, e.prevZ);
    }

    public static Vec3d lastRenderPos(Entity e) {
        return new Vec3d(e.lastRenderX, e.lastRenderY, e.lastRenderZ);
    }

    public static BlockPos flooredPos(Entity e) {
        return new BlockPos((int) Math.floor(e.getX()), (int) Math.floor(e.getY()), (int) Math.floor(e.getZ()));
    }
}
