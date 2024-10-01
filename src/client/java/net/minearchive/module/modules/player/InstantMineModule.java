package net.minearchive.module.modules.player;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.systems.RenderSystem;
import me.x150.renderer.render.Renderer3d;
import me.x150.renderer.util.RendererUtils;
import net.minearchive.event.events.DamageBlockEvent;
import net.minearchive.event.events.Render3DEvent;
import net.minearchive.event.events.UpdateEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.KeyBind;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.EnumSetting;
import net.minearchive.setting.settings.IntegerSetting;
import net.minearchive.setting.settings.KeyBindSetting;
import net.minearchive.util.InventoryUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.timer.Timer;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@ModuleInfo(name = "InstantMine", category = Category.PLAYER)
@SuppressWarnings("DataFlowIssue")
public class InstantMineModule extends Module {
    public static InstantMineModule INSTANCE;

    public final EnumSetting<Page> page = add(new EnumSetting<>("Page", Page.General));

    /* =============================== General =============================== */
    public final EnumSetting<Mode> mode = add(new EnumSetting<>("Mode", Mode.Instant)).setVisible(() -> page.getValue() == Page.General).build();
    public final BooleanSetting crystal = add(new BooleanSetting("Place Crystal", false)).setVisible(() -> page.getValue() == Page.General).build();
    public final KeyBindSetting crystalBind = add(new KeyBindSetting("Crystal Bind", new KeyBind(GLFW.GLFW_KEY_UNKNOWN))).setVisible(() -> page.getValue() == Page.General && crystal.getValue()).build();

    /* ================================ DELAY ================================ */
    public final IntegerSetting packetDelay = add(new IntegerSetting("Packet Delay", 50, 1, 200)).setVisible(() -> page.getValue() == Page.Delay).build();
    public final IntegerSetting crystalDelay = add(new IntegerSetting("Crystal Delay", 50, 1, 200)).setVisible(() -> page.getValue() == Page.Delay).build();
    public final IntegerSetting switchDelay = add(new IntegerSetting("Packet Delay", 50, 1, 200)).setVisible(() -> page.getValue() == Page.Delay).build();

    /* ================================ Switch ================================ */
    public final EnumSetting<SwitchMode> switchMode = add(new EnumSetting<>("Switch Mode", SwitchMode.None)).setVisible(() -> page.getValue() == Page.Switch).build();
    public final BooleanSetting silent = add(new BooleanSetting("Silent", false)).setVisible(() -> page.getValue() == Page.Switch && switchMode.getValue() == SwitchMode.Auto).build();

    public enum Mode { Instant, Once }
    public enum Page { General, Delay, Switch }
    public enum SwitchMode { None, Auto }

    private final Timer packetTimer = new Timer();

    @Nullable
    private BlockPos pos = null, dualPos = null;
    @Nullable
    private Direction direction;
    private boolean breaking = false;

    public InstantMineModule() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (nullCheck()) return;
        if (pos == null) return;

        if (packetTimer.passed(packetDelay.getValue())) {
            if (client.world.getBlockState(pos).getBlock() == Blocks.AIR) return;
            int pick = InventoryUtils.findBestToolSlot(client.world.getBlockState(pos));
            int old = client.player.getInventory().selectedSlot;
            if (pick == -1) return;

            if (switchMode.getValue() == SwitchMode.Auto) {
                InventoryUtils.swapInv(pick);
                InventoryUtils.updateHotBar();
            }

            client.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction));

            if (silent.getValue()) {
                InventoryUtils.swapInv(old);
                InventoryUtils.updateHotBar();
            }
        }
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;
        if (pos == null) return;
        Renderer3d.renderThroughWalls();
        Renderer3d.renderFilled(event.matrices(), SimpleColor.of(new Color(0x45454545, true)), new Vec3d(pos.getX(), pos.getY(), pos.getZ()), new Vec3d(1, 1, 1));
        Renderer3d.stopRenderThroughWalls();
    }

    @Subscribe
    public void onDamageBlock(DamageBlockEvent event) {
        pos = event.getPos();
        direction = event.getDirection();
    }
}
