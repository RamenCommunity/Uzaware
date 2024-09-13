package net.minearchive.module.modules.hud;

import net.minearchive.Uzaware;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.util.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.AnimateValue;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@ModuleInfo(name = "WaterMark", category = Category.HUD)
public class WaterMark extends HudModule {

    AnimateValue animateValue = new AnimateValue(EnumEasing.SINE, 14);

    @Override
    public void onRender(Render2DStartEvent event) {;
        Uzaware.nanoVGManager.begin(false);
        Uzaware.textureManager.createTexture(getClass().getResourceAsStream("/assets/uzaware/texture/reisa.png"), "uzawa");
        NanoVGUtils.rounded(x, y, 340, 120, 10, SimpleColor.of(0x50ffffff), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.rounded(x, y, 120, 120, 10, Uzaware.textureManager.getTextures("uzawa", 120, 120), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.ntr.draw(Uzaware.modName + " v" + Uzaware.version, x + 130, y + 10, 32, 0xff000000);
        String timeStamp = new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        animateValue.draw(x + 130, y + 40, 32, timeStamp, 300, SimpleColor.of(0xff000000));

        Uzaware.nanoVGManager.end();
    }

    @Override
    public void onDrag(float x, float y, float deltaX, float deltaY) {
        super.onDrag(x, y, deltaX, deltaY);
    }
}
