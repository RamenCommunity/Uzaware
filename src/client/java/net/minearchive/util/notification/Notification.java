package net.minearchive.util.notification;

import net.minearchive.AccessMC;
import net.minearchive.util.render.NanoVGUtils;
import net.minearchive.util.SimpleColor;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;
import net.minearchive.util.timer.TimeUnit;
import net.minearchive.util.timer.Timer;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class Notification implements AccessMC {
    private NotificationData data;

    private final Animation xAnim = new Animation(0, EnumEasing.BACK.getEasing());
    private Animation yAnim;
    private Stage stage = Stage.In;
    private final Timer timer = new Timer(TimeUnit.MILLISECONDS);
    public Notification(NotificationData data) {
        this.data = data;
    }

    public void draw(int i, int duration, int keep) {
        switch (stage) {
            case In -> {
                xAnim.setEasing(EnumEasing.BACK.getEasing());
                if (timer.passed(duration)) {
                    stage = Stage.Display;
                    timer.reset();
                }
            }
            case Display -> {
                if (timer.passed(keep)) {
                    stage = Stage.Out;
                    timer.reset();
                }
            }
            case Out -> {
                xAnim.setEasing(EnumEasing.IN.getEasing());
                if (timer.passed(duration)) {
                    stage = Stage.End;
                    timer.reset();
                }
            }
            default -> { }
        }
        float w = NanoVGUtils.ntr.width(data.title, 26) + 50;
        float ww = NanoVGUtils.ntr.width(data.message, 26) + 15;
        float width= w + ww + 40;

        if (yAnim == null) yAnim = new Animation(i * 60, EnumEasing.QUART.getEasing());

        yAnim.animateTo(i * 60, 350);
        xAnim.animateTo((stage == Stage.Out ? -1 : 1) * width, duration);

        NanoVG.nvgSave(NanoVGUtils.context);
        NanoVG.nvgTranslate(NanoVGUtils.context, client.getWindow().getWidth() - xAnim.getValue() - 10, client.getWindow().getHeight() - yAnim.getValue() - 60);

        NanoVGUtils.rounded(0, 0, width, 50, 10, SimpleColor.of(0x5FFFFFFF), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.rounded(7.25f, 7.25f, 36, 36, 5, SimpleColor.of(0x38000000), NanoVGUtils.Pattern.FILL);
        NanoVGUtils.symbols.draw("\ue5ca", 25.25f, 26.25f, 26, data.type.color.color(), NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVGUtils.ntr.draw(data.message, w + 40, 30, 26, SimpleColor.of(data.type.color).color(), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
        NanoVGUtils.ntr.draw(data.title, 50f, 30, 26, 0xFFFFFFFF, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);

        NanoVG.nvgTranslate(NanoVGUtils.context, -100, 100);
        NanoVG.nvgRestore(NanoVGUtils.context);
    }

    public boolean shouldDestroy() {
        return stage == Stage.End;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        if (stage == Stage.Display) timer.reset();
        if (stage == Stage.Out) {
            timer.reset();
            stage = Stage.In;
        }
        this.data = data;
    }

    private enum Stage {
        In, Display, Out, End
    }

    public enum NotificationType {
        SUCCESS(SimpleColor.of(0xFF09FF30)),
        WARN(SimpleColor.of(0xFFFF6008)),
        INFO(SimpleColor.of(0xFF448ADE)),
        ERROR(SimpleColor.of(0xFFFF0000));

        private final SimpleColor color;
        NotificationType(SimpleColor color) {
            this.color = color;
        }

        public SimpleColor getColor() {
            return color;
        }
    }

    public static class NotificationData {
        public final String title;
        public final String message;
        public Color titleColor, messageColor;
        public final NotificationType type;
        public final int id;

        public NotificationData(String title, String message, NotificationType type, int id) {
            this.title = title;
            this.message = message;
            this.type = type;
            this.id = id;
        }
    }
}
