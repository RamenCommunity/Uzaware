package net.minearchive.module.modules.player;

import com.google.common.eventbus.Subscribe;
import me.x150.renderer.render.Renderer3d;
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
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;
import net.minearchive.util.timer.Timer;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
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
    public final BooleanSetting placeCrystal = add(new BooleanSetting("Place Crystal", false)).setVisible(() -> page.getValue() == Page.General).build();
    public final BooleanSetting breakCrystal = add(new BooleanSetting("Break Crystal", false)).setVisible(() -> page.getValue() == Page.General && placeCrystal.getValue()).build();
    public final KeyBindSetting crystalBind = add(new KeyBindSetting("Crystal Bind", new KeyBind(GLFW.GLFW_KEY_UNKNOWN))).setVisible(() -> page.getValue() == Page.General && placeCrystal.getValue()).build();

    /* ================================ Delay ================================ */
    public final IntegerSetting packetDelay = add(new IntegerSetting("Packet Delay", 50, 1, 200)).setVisible(() -> page.getValue() == Page.Delay).build();
    public final IntegerSetting crystalDelay = add(new IntegerSetting("Crystal Delay", 50, 1, 200)).setVisible(() -> page.getValue() == Page.Delay).build();
    public final IntegerSetting switchDelay = add(new IntegerSetting("Packet Delay", 50, 1, 200)).setVisible(() -> page.getValue() == Page.Delay).build();

    /* ================================ Switch ================================ */
    public final EnumSetting<SwitchMode> switchMode = add(new EnumSetting<>("Switch Mode", SwitchMode.None)).setVisible(() -> page.getValue() == Page.Switch).build();

    /* ================================ Render ================================ */
    private final BooleanSetting animation = add(new BooleanSetting("Animation", true));

    public enum Page { General, Delay, Switch }
    public enum Mode { Instant, Once }
    public enum SwitchMode { None, Auto, Silent }

    private final Timer packetTimer = new Timer();
    private final Timer crystalTimer = new Timer();

    private final Animation scale = new Animation(0, EnumEasing.CIRCLE.getEasing());

    @Nullable
    private BlockPos pos = null;
    @Nullable
    private final BlockPos dualPos = null;
    @Nullable
    private Direction direction;
    private final boolean breaking = false;

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

            if (switchMode.getValue() == SwitchMode.Auto || switchMode.getValue() == SwitchMode.Silent) {
                InventoryUtils.swapInv(pick);
//                InventoryUtils.updateHotBar();
            }

            client.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction));

            if (switchMode.getValue() == SwitchMode.Silent) {
                InventoryUtils.swapInv(old);
                InventoryUtils.updateHotBar();
            }
            packetTimer.reset();
        }

        if (placeCrystal.getValue() && crystalBind.pressed() && crystalTimer.passed(crystalDelay.getValue())) {


            crystalTimer.reset();
        }

        if (breakCrystal.getValue()) {

        }
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;
        if (pos == null) return;
        if (animation.getValue()) scale.animateTo(1, 500);
        else scale.setValue(1);
        Renderer3d.renderThroughWalls();
        Renderer3d.renderFilled(
                event.matrices(),
                SimpleColor.of(new Color(0xa0ffffff, true)),
                new Vec3d(
                        pos.getX() + 0.5 - (0.5 * scale.getValue()),
                        pos.getY() + 0.5 - (0.5 * scale.getValue()),
                        pos.getZ() + 0.5 - (0.5 * scale.getValue())
                ),
                new Vec3d(scale.getValue(), scale.getValue(), scale.getValue())
        );
        Renderer3d.renderOutline(
                event.matrices(),
                SimpleColor.of(0xffffffff),
                new Vec3d(
                        pos.getX() + 0.5 - (0.5 * scale.getValue()),
                        pos.getY() + 0.5 - (0.5 * scale.getValue()),
                        pos.getZ() + 0.5 - (0.5 * scale.getValue())
                ),
                new Vec3d(scale.getValue(), scale.getValue(), scale.getValue())
        );
        Renderer3d.stopRenderThroughWalls();
    }

    @Subscribe
    public void onDamageBlock(DamageBlockEvent event) {
        pos = event.getPos();
        direction = event.getDirection();
        scale.setValue(0);
    }
}
