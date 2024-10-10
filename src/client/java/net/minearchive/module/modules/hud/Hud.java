package net.minearchive.module.modules.hud;

import net.minearchive.Uzaware;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.PlayerUtils;

@HudInfo(width = 340f, height = 120f, round = 10)
@ModuleInfo(name = "Hud", category = Category.HUD, enable = true)
public class Hud extends HudModule {
    public static Hud INSTANCE;

    public final BooleanSetting watermark = add(new BooleanSetting("Watermark", true));
    public final BooleanSetting fps = add(new BooleanSetting("Fps", true));
    public final BooleanSetting ping = add(new BooleanSetting("ping", true));
    public final BooleanSetting tps = add(new BooleanSetting("Tps", true));
    public final BooleanSetting speed = add(new BooleanSetting("Speed", true));
    public final BooleanSetting coord = add(new BooleanSetting("Coord", true));

    public Hud() {INSTANCE = this;}

    @Override
    public void onRender(Render2DStartEvent event) {
        if (watermark.getValue())
            NanoVGUtils.ntr.draw(Uzaware.modName + Uzaware.version, x + 5, y + 5, 32, 0xffffffff);
        if (fps.getValue())
            NanoVGUtils.ntr.draw("Fps " + Uzaware.FPS.getCurrentFPS(), x + 5, y + 70, 28, 0xffffffff);
        if (ping.getValue())
            NanoVGUtils.ntr.draw("ping " + PlayerUtils.getPing(client.player), x + 5, y + 95, 28, 0xffffffff);
    }
}
