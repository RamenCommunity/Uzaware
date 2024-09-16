package net.minearchive;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.KeyPressEvent;
import net.minearchive.module.Module;

public class AlwaysListener {
    @Subscribe
    public void onKey(KeyPressEvent event) {
        Uzaware.moduleManager.modules.stream().filter(m -> m.bind.getValue().getKey() == event.code()).forEach(Module::toggle);
    }
}
