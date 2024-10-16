package net.minearchive.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.x150.renderer.render.Renderer3d;
import net.minearchive.event.events.Render3DEvent;
import net.minearchive.event.events.UpdateEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.FloatSetting;
import net.minearchive.setting.settings.IntegerSetting;
import net.minearchive.util.BlockUtils;
import net.minearchive.util.NotificationUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.notification.Notification;
import net.minearchive.util.pathFinder.AStarPathFinder;
import net.minearchive.util.pathFinder.Node;
import net.minearchive.util.pathFinder.WorldUtils;
import net.minearchive.util.timer.Timer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

@ModuleInfo(name = "HoleSnap", category = Category.COMBAT)
@SuppressWarnings("DataFlowIssue")
public class HoleSnapModule extends Module {
    public static HoleSnapModule INSTANCE;

    public final FloatSetting searchRange = add(new FloatSetting("Detect Range", 10, 5, 20));
    public final IntegerSetting timeout = add(new IntegerSetting("Timeout", 500, -1, 1000));
    public BooleanSetting isEcBedrock = add(new BooleanSetting("EnderChest as obsidian", true));
    public BooleanSetting bedrock = add(new BooleanSetting("Bedrock Hole", true));
    public BooleanSetting b1x1 = add(new BooleanSetting("Bedrock 1x1", true));
//    public BooleanSetting b2x1 = add(new BooleanSetting("Bedrock 2x1", true));
//    public BooleanSetting b2x2 = add(new BooleanSetting("Bedrock 2x2", true));
    public BooleanSetting halfObby = add(new BooleanSetting("HalfObby Hole", true));
    public BooleanSetting h1x1 = add(new BooleanSetting("HalfObby 1x1", true));
//    public BooleanSetting h2x1 = add(new BooleanSetting("HalfObby 2x1", true));
//    public BooleanSetting h2x2 = add(new BooleanSetting("HalfObby 2x2", true));
    public BooleanSetting fullObby = add(new BooleanSetting("FullObby Hole", true));
    public BooleanSetting f1x1 = add(new BooleanSetting("FullObby 1x1", true));
//    public BooleanSetting f2x1 = add(new BooleanSetting("FullObby 2x1", true));
//    public BooleanSetting f2x2 = add(new BooleanSetting("FullObby 2x2", true));

    private final HolePathFinder pathFinder = new HolePathFinder();
    private final List<Node> path = new CopyOnWriteArrayList<>();
    private final Timer timer = new Timer();
    int index = 0;

    public HoleSnapModule() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;
        Node start = Node.from(client.player.getPos());
        path.clear();
        index = 0;
        boolean pathFound = false;

        List<BlockPos> blocks = WorldUtils.cube(client.player.getBlockPos(), searchRange.getValue());
        List<BlockPos> holes = blocks.stream()
                .filter(block -> BlockUtils.equal(block, Blocks.AIR))
                .filter(block -> {
                    int found = 0;
                    for (Direction value : Direction.values()) {
                        if (value == Direction.UP) continue;
                        if (BlockUtils.equal(block.add(value.getVector()), Blocks.BEDROCK))
                            found++;
                    }

                    return found == 5 && BlockUtils.equal(block.up(), Blocks.AIR) && BlockUtils.equal(block.up().up(), Blocks.AIR);
                }).toList();

        holes = holes.stream().sorted(Comparator.comparing(hole -> Math.cbrt(hole.getSquaredDistance(client.player.getX(),  client.player.getY(), client.player.getZ())))).toList();

        while (!pathFound) {
            BlockPos hole = holes.stream().findFirst().orElse(null);
            if (hole == null) {
                disable();
                NotificationUtils.Builder()
                        .setTitle("Failed...")
                        .setMessage("Couldn't found any hole...")
                        .setType(Notification.NotificationType.ERROR)
                        .setId(hashCode() + 1).buildAndAdd();
                break;
            }

            Node goal = Node.from(hole).up();

            path.addAll(pathFinder.find(start, goal, timeout.getValue()));

            if (path.isEmpty()) {
                holes = holes.stream().filter(h -> !h.equals(hole)).toList();

                if (holes.isEmpty()) {
                    disable();
                    NotificationUtils.Builder()
                            .setTitle("Failed...")
                            .setMessage("Couldn't found any path...")
                            .setType(Notification.NotificationType.ERROR)
                            .setId(hashCode() + 1).buildAndAdd();
                    break;
                }
            } else {
                pathFound = true;
                path.add(goal.down());
            }
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (nullCheck()) return;
        client.player.setVelocity(0, 0, 0);
        if (timer.passed(25)) {
            if (index >= path.size()) {
                disable();
                return;
            }
            double dx = path.get(index).getX() - client.player.getPos().getX();
            double dy = path.get(index).getY() - client.player.getPos().getY();
            double dz = path.get(index).getZ() - client.player.getPos().getZ();
            client.player.move(MovementType.PLAYER, new Vec3d(dx + 0.5, dy, dz + 0.5));
            timer.reset();
            index++;
        }
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;
        AtomicReference<Node> old = new AtomicReference<>();
//        Renderer3d.renderLine(event.matrices(), SimpleColor.of(new Color(0xB800FF8C, true)), new Vec3d(old.get().getX() + 0.5, old.get().getY() + 0.1, old.get().getZ() + 0.5), new Vec3d(p.getX() + 0.5, p.getY() + 0.1, p.getZ() + 0.5));

        path.forEach(p -> Renderer3d.renderFilled(event.matrices(), SimpleColor.of(new Color(0xB800FF8C, true)), new Vec3d(p.getX(), p.getY(), p.getZ()), new Vec3d(1, 1, 1)));
    }

    private static boolean isPassable(BlockPos pos) {
        BlockState state = client.world.getBlockState(pos);
        return state.isAir();
    }

    private static class HolePathFinder extends AStarPathFinder {

        @Override
        public boolean isPassable(Node next) {
            Node old = next.getParent();
            int deltaX = old.getX() - next.getX();
            int deltaZ = old.getZ() - next.getZ();
            if (Math.abs(deltaX) + Math.abs(deltaZ) != 2)
                return BlockUtils.equal(next.getAsBlockPos().up(), Blocks.AIR) && BlockUtils.equal(next.getAsBlockPos(), Blocks.AIR);
            return BlockUtils.equal(next.getAsBlockPos().up(), Blocks.AIR) && BlockUtils.equal(next.getAsBlockPos(), Blocks.AIR) && BlockUtils.equal(next.getAsBlockPos().add(next.getX() - deltaX, next.getY(), next.getZ()), Blocks.AIR) && BlockUtils.equal(next.getAsBlockPos().add(next.getX(), next.getY(), next.getZ() - deltaZ), Blocks.AIR);
        }

        @Override
        public Map<Node, Boolean> collect() {
            List<BlockPos> posList = WorldUtils.cube(client.player.getBlockPos(), INSTANCE.searchRange.getValue());
            Map<Node, Boolean> nodeMap = new HashMap<>();
            posList.forEach(pos -> nodeMap.put(Node.from(pos), BlockUtils.equal(pos, Blocks.AIR)));

            return nodeMap;
        }
    }
}
