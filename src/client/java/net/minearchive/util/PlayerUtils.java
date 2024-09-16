package net.minearchive.util;

import net.minearchive.AccessMC;
import net.minecraft.util.math.Vec3d;

public class PlayerUtils implements AccessMC {
    public static Vec3d directionSpeed(double speed) {
        //noinspection DataFlowIssue
        return new Vec3d(-client.player.forwardSpeed, 0, client.player.sidewaysSpeed).normalize().rotateY((float) Math.toRadians(90 - client.player.renderYaw)).multiply(speed);
    }

    public static boolean isMoving() {
        //noinspection DataFlowIssue
        return client.player.input.movementSideways != 0.0 || client.player.input.movementForward != 0.0F;
    }
}
