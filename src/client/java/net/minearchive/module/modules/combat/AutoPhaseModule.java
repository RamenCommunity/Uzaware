package net.minearchive.module.modules.combat;

import net.minearchive.event.events.UpdateEvent;
import net.minearchive.manager.ModuleManager;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.module.modules.movement.MovementTweaksModule;
import net.minearchive.setting.KeyBind;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.IntegerSetting;
import net.minearchive.setting.settings.KeyBindSetting;
import net.minearchive.util.InventoryUtils;
import net.minearchive.util.NotificationUtils;
import net.minearchive.util.notification.Notification;
import net.minearchive.util.timer.Timer;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

@ModuleInfo(name = "AutoPhase", category = Category.COMBAT)
@SuppressWarnings("DataFlowIssue")
public class AutoPhaseModule extends Module {
    public final KeyBindSetting hook = add(new KeyBindSetting("Trap Bind", new KeyBind(GLFW_KEY_UNKNOWN)));
    public final IntegerSetting delay = add(new IntegerSetting("delay", 100, 100, 1000));

    private final Timer timerUtils = new Timer();

    @Override
    public void onUpdate(UpdateEvent event) {
        if (nullCheck()) return;

        if (hook.pressed()) {
            //try trap
            if (!((MovementTweaksModule) ModuleManager.INSTANCE.getInstance(MovementTweaksModule.class).get()).noSwim.getValue()) return;
            if (!timerUtils.passed(delay.getValue())) return;
            timerUtils.reset();
            int trap = InventoryUtils.findHotBarItem(Items.IRON_TRAPDOOR);
            if (trap == -1) {
                NotificationUtils.Builder()
                        .setId(hashCode())
                        .setType(Notification.NotificationType.ERROR)
                        .setTitle("No Item")
                        .setMessage("Can't find TrapDoor!")
                        .buildAndAdd();
                return;
            }

            BlockPos[] sides = new BlockPos[] {
                    new BlockPos(1, 0, 0),
                    new BlockPos(-1, 0, 0),
                    new BlockPos(0, 0, 1),
                    new BlockPos(0, 0, -1)
            };

            BlockPos playerPos = new BlockPos(
                    (int) Math.floor(client.player.getPos().x),
                    (int) Math.floor(client.player.getPos().y),
                    (int) Math.floor(client.player.getPos().z)
            );

            BlockPos trapPos = null;
            for (BlockPos offset : sides) {
                BlockPos pos = playerPos.add(offset);
                if (checkEntity(pos) || client.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) continue;
                trapPos = pos;
            }

            if (trapPos == null) {
                NotificationUtils.Builder()
                        .setId(hashCode())
                        .setType(Notification.NotificationType.ERROR)
                        .setTitle("No Space")
                        .setMessage("Can't find TrapPos!")
                        .buildAndAdd();
                return;
            }

            Hand hand = Hand.MAIN_HAND;

            int old = client.player.getInventory().selectedSlot;

            InventoryUtils.swapInv(trap);
            InventoryUtils.updateHotBar();

            double x = client.player.getPos().x, y = client.player.getPos().y, z = client.player.getPos().z;
            client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.2, z, client.player.isOnGround()));

            Direction facing = null;
            for (Direction value : Direction.values()) {
                if (!trapPos.add(value.getVector()).equals(playerPos)) continue;
                facing = value;
                break;
            }
            Vec3d hit = new Vec3d(trapPos.getX(), trapPos.getY(), trapPos.getZ())
                    .add(new Vec3d(0.5, 0.8, 0.5))
                    .add(new Vec3d(facing.getUnitVector().x / 2f, facing.getUnitVector().y / 2f, facing.getUnitVector().z / 2f));

            client.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(
                    hand,
                    new BlockHitResult(hit, facing, trapPos, false),
                    0)
            );
            client.player.networkHandler.sendPacket(new HandSwingC2SPacket(hand));

            client.player.setPosition(x, y, z);

            InventoryUtils.swapInv(old);
            InventoryUtils.updateHotBar();
        }
    }

    private Vec3d calc() {
        return null;
    }

    private boolean checkEntity(BlockPos pos) {
        return client.player.getBoundingBox().intersects(
                pos.getX() - 1,
                pos.getY() - 1,
                pos.getZ() - 1,
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );
    }

    private record Spot(double x, double y, double z, float score) { }
}
