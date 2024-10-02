package net.minearchive.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class EntityUtils {
    public static Vec3d prevPos(Entity e) {
        return new Vec3d(e.prevX, e.prevY, e.prevZ);
    }

    public static Vec3d lastRenderPos(Entity e) {
        return new Vec3d(e.lastRenderX, e.lastRenderY, e.lastRenderZ);
    }
}
