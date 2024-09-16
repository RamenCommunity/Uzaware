package net.minearchive.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.TickEndEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.FloatSetting;
import net.minearchive.util.PlayerUtils;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

import static net.minearchive.util.PlayerUtils.isMoving;

@ModuleInfo(name = "Flight", category = Category.MOVEMENT)
public class FlightModule extends Module {
    public final FloatSetting speed = add(new FloatSetting("Speed", 1.0F, 0.1F, 10.0F));

    @Subscribe
    public void onTick(TickEndEvent event) {
        if (nullCheck()) return;
        //noinspection DataFlowIssue
        client.player.setVelocity(0, 0, 0);
        Vec3d dir = PlayerUtils.directionSpeed(speed.getValue());
        boolean up = client.player.input.jumping, down = client.player.input.sneaking;
        double y = speed.getValue() / 1.5;

        client.player.move(MovementType.PLAYER, new Vec3d(isMoving() ? dir.x : 0F, (up && down) ? 0.0F : up ? y : down ? -y : 0.0F, isMoving() ? dir.z : 0F));
    }
}
