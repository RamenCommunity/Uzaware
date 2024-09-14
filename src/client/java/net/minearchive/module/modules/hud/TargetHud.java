package net.minearchive.module.modules.hud;

import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minecraft.entity.Entity;

@ModuleInfo(name = "TargetHud", category = Category.HUD)
public class TargetHud extends HudModule {

    private Entity entity;

    @Override
    public void onRender(Render2DStartEvent event) {
        if (entity == null) return;

    }
}
