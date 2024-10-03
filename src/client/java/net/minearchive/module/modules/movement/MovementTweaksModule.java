package net.minearchive.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.PacketSendEvent;
import net.minearchive.mixin.mixins.IPlayerMoveC2SPacket;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

@ModuleInfo(name = "MovementTweaks", category = Category.MOVEMENT)
public class MovementTweaksModule extends Module {
    public static MovementTweaksModule INSTANCE;

    public final BooleanSetting noSwim = add(new BooleanSetting("No Block Swim", false));
    public final BooleanSetting noJumpDelay = add(new BooleanSetting("No Jump Delay", false));
    public final BooleanSetting noFall = add(new BooleanSetting("No Fall Damage", false));
    public final BooleanSetting noRiptide = add(new BooleanSetting("No Riptide", false));

    public MovementTweaksModule() {
        INSTANCE = this;
    }

    @Subscribe
    public void onPacketSend(PacketSendEvent event) {
        if (noFall.getValue() && event.getPacket() instanceof PlayerMoveC2SPacket packet) {
            ((IPlayerMoveC2SPacket) packet).setOnGround(false);
        }
    }
}
