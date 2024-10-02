package net.minearchive.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.TickEndEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;

@ModuleInfo(name = "Sprint", category = Category.MOVEMENT)
public class SprintModule extends Module {
    @Subscribe
    public void onTick(TickEndEvent event) {
        if (nullCheck()) return;
        client.options.sprintKey.setPressed(true);
    }
}
