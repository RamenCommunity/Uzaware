package net.minearchive;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.KeyPressEvent;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Module;
import net.minearchive.util.FPSCalculator;

public class AlwaysListener {
    @Subscribe
    public void onKey(KeyPressEvent event) {
        Uzaware.moduleManager.modules.stream().filter(m -> m.bind.getValue().getKey() == event.code()).forEach(Module::toggle);
    }

    @Subscribe
    public void onRender2D(Render2DStartEvent event) {
        FPSCalculator.INSTANCE.render(event);
    }
}
