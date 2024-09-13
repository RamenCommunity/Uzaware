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

    AnimateValue.SingleValue[] values = new AnimateValue.SingleValue[10];

    public WaterMark() {
        for (int i = 0; i < values.length; i++) {
            values[i] = new AnimateValue.SingleValue(EnumEasing.SINE);
        }
    }

    @Override
    public void onRender(Render2DStartEvent event) {;
        Uzaware.nanoVGManager.begin(false);
        Uzaware.textureManager.createTexture(getClass().getResourceAsStream("/assets/uzaware/texture/reisa.png"), "uzawa");
        NanoVGUtils.rounded(x, y, 340, 120, 10, SimpleColor.of(0x50ffffff), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.rounded(x, y, 120, 120, 10, Uzaware.textureManager.getTextures("uzawa", 120, 120), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.ntr.draw(Uzaware.modName + " v" + Uzaware.version, x + 130, y + 10, 32, 0xff000000);
        String timeStamp = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
        char[] chars = timeStamp.toCharArray();
        float width = NanoVGUtils.ntr.width("0", 32);
        String[] separators = {"/", " ", ":", ":"};

        float currentX = x + 130;

        for (int i = 0, sepIdx = 0; i < chars.length; i += 2, sepIdx++) {
            values[i].draw(currentX, y + 40, 32, SimpleColor.of(0xff000000), Integer.valueOf(String.valueOf(chars[i])), 300);
            currentX += width;
            values[i + 1].draw(currentX, y + 40, 32, SimpleColor.of(0xff000000), Integer.valueOf(String.valueOf(chars[i + 1])), 300);
            currentX += width;
            if (sepIdx < separators.length) {
                float separatorWidth = NanoVGUtils.ntr.width(separators[sepIdx], 32); // 区切り文字の幅を計算
                NanoVGUtils.ntr.draw(separators[sepIdx], currentX, y + 40, 32, 0xff000000);
                currentX += separatorWidth;
            }
        }

        Uzaware.nanoVGManager.end();
    }

    @Override
    public void onDrag(float x, float y, float deltaX, float deltaY) {
        super.onDrag(x, y, deltaX, deltaY);
    }
}
