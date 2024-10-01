package net.minearchive.module.modules.misc;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.PacketReceiveEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.IntegerSetting;
import net.minearchive.util.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

@ModuleInfo(name = "DeathEffect", category = Category.MISC)
public class DeathEffectModule extends Module {
    public final IntegerSetting volume = add(new IntegerSetting("Volume", 100, 1, 100));
    public final BooleanSetting onlySelf = add(new BooleanSetting("OnlySelf", false));

    @Subscribe
    public void onPacketReceive(PacketReceiveEvent event) {
        if (nullCheck()) return;
        if (!(event.getPacket() instanceof EntityStatusS2CPacket packet)) return;
        if (!(packet.getEntity(client.world) instanceof PlayerEntity)) return;
        if (packet.getStatus() != 3) return;
        if (onlySelf.getValue() && packet.getEntity(client.world) != client.player) return;

        SoundUtils.playSound("death_effect.wav", volume.getValue());
    }
}
