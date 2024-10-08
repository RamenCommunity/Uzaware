package net.minearchive.module.modules.hud;

import net.minearchive.Uzaware;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.event.events.UpdateEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.module.modules.hud.targetHud.TargetHud;
import net.minearchive.module.modules.hud.targetHud.impl.UzawareTargetHud;
import net.minearchive.setting.settings.EnumSetting;
import net.minecraft.entity.LivingEntity;

@ModuleInfo(name = "TargetHud", category = Category.HUD)
@HudInfo()
public class TargetHudModule extends HudModule {

    public EnumSetting<TargetHudMode> mode = add(new EnumSetting<>("Mode", TargetHudMode.Uzaware)).setOnValueChange(v -> targetHud = v.getHud()).build();

    private LivingEntity target;

    private TargetHud targetHud;

    @Override
    public void onRender(Render2DStartEvent event) {
        if (nullCheck()) return;
        if (target == null || targetHud == null) return;

        targetHud.render(target);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (Uzaware.entityManager.getTargeting() instanceof LivingEntity) {
            target = (LivingEntity) Uzaware.entityManager.getTargeting();
        }
    }

    public enum TargetHudMode {
        Uzaware(new UzawareTargetHud());

        private final TargetHud hud;
        TargetHudMode(TargetHud hud) {
            this.hud = hud;
        }

        public TargetHud getHud() {
            return hud;
        }
    }
}
