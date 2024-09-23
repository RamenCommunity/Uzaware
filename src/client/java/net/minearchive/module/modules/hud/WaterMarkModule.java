package net.minearchive.module.modules.hud;

import com.google.common.eventbus.Subscribe;
import net.minearchive.Uzaware;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.EnumSetting;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.AnimateValue;
import net.minearchive.util.easing.EnumEasing;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@ModuleInfo(name = "WaterMark", category = Category.HUD)
@HudInfo(width = 340f, height = 120f, round = 10)
public class WaterMarkModule extends HudModule {

    public final EnumSetting<Mode> enumSetting = add(new EnumSetting<>("Mode" , Mode.Uzawa1));
    public enum Mode { Uzawa1, Uzawa2 }

    final AnimateValue animateValue = new AnimateValue(EnumEasing.QUART, 14);
    final FPSCalculator fpsCalculator = new FPSCalculator();

    @Override
    public void onRender(Render2DStartEvent event) {
        Uzaware.nanoVGManager.begin(false);
        switch (enumSetting.getValue()) {
            case Uzawa1 -> {
                fpsCalculator.render(event);
                NanoVGUtils.shadow(x, y, 340, 120, 10, SimpleColor.of(0x40000000));
                Uzaware.textureManager.createTexture(getClass().getResourceAsStream("/assets/uzaware/texture/reisa.png"), "uzawa");
                NanoVGUtils.rounded(x, y, 340, 120, 10, SimpleColor.of(0x50ffffff), NanoVGUtils.Pattern.FILL);
                NanoVGUtils.rounded(x, y, 120, 120, 10, Uzaware.textureManager.getTextures("uzawa", x, y, 120, 120), NanoVGUtils.Pattern.FILL);
                NanoVGUtils.ntr.draw(Uzaware.modName + " v" + Uzaware.version, x + 130, y + 10, 32, 0xffffffff);
                String timeStamp = new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

                animateValue.draw(x + 130, y + 40, 32, timeStamp, 300, SimpleColor.of(0xffffffff));

                NanoVGUtils.ntr.draw("FPS: " + fpsCalculator.currentFPS, x + 130, y + 70, 32, 0xffffffff);
            }
            case Uzawa2 -> NanoVGUtils.ntr.draw("UzaWere v"  + Uzaware.version, x + 5, y + 5, 32, 0xffffffff);
        }
        Uzaware.nanoVGManager.end();
    }

    public static class FPSCalculator {
        private int currentFPS;
        private final FPS[] timers;

        public FPSCalculator() {
            currentFPS = 0;
            timers = new FPS[20];
            long startTime = System.nanoTime();
            for (int i = 0; i < timers.length; i++) {
                long plus = startTime + ((long) i * 1000000000 / timers.length);
                timers[i] = new FPS(plus);
            }
        }

        @Subscribe
        public void render(Render2DStartEvent event) {
            for (FPS fps : timers) {
                fps.updateFPSCount();
                if (fps.isOver()) {
                    this.currentFPS = fps.getFpsCount();
                    fps.updateStartTime();
                }
            }
        }

        public static class FPS {
            private long startTime;
            private int fpsCount;

            public FPS(long startTime) {
                this.startTime = startTime;
                fpsCount = 0;
            }

            public void updateFPSCount() {
                fpsCount++;
            }

            public boolean isOver() {
                return System.nanoTime() - startTime >= 1000000000;
            }

            public void updateStartTime() {
                while (isOver()) {
                    startTime += 1000000000;
                }
                fpsCount = 0;
            }

            public int getFpsCount() {
                return fpsCount;
            }
        }
    }
}
