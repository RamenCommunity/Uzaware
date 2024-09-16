package net.minearchive.module.modules.hud;

import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.manager.EntityManager;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minecraft.entity.Entity;

@ModuleInfo(name = "TargetHud", category = Category.HUD)
@HudInfo()
public class TargetHud extends HudModule {

    private Entity target;

    @Override
    public void onRender(Render2DStartEvent event) {
        if (nullCheck()) return;

        if (target == null) return;
    }

    @Override
    public void onTick() {
        target = EntityManager.getEnemies().stream().findFirst().orElse(null);
    }
}
