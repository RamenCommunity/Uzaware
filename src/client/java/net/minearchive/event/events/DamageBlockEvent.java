package net.minearchive.event.events;

import net.minearchive.event.CancellableEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class DamageBlockEvent extends CancellableEvent {
    private final BlockPos pos;
    private final Direction direction;

    public DamageBlockEvent(BlockPos pos, Direction direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction getDirection() {
        return direction;
    }
}
