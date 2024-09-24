package net.minearchive.module.modules.hud;

import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.FloatSetting;
import net.minearchive.util.NotificationUtils;
import net.minearchive.util.notification.Notification;

import java.util.concurrent.atomic.AtomicInteger;

@HudInfo()
@ModuleInfo(name = "Notification", category = Category.HUD)
public class NotificationModule extends HudModule {
    public static NotificationModule INSTANCE;

    public final BooleanSetting chatNotify = add(new BooleanSetting("ChatNotify", true));
    public final FloatSetting duration = add(new FloatSetting("InOut Duration", 600f, 100f, 1000));
    public final FloatSetting keep = add(new FloatSetting("Keep Duration", 3500f, 10f, 5000));

    public NotificationModule() {
        INSTANCE = this;
    }

    @Override
    public void onRender(Render2DStartEvent event) {
        AtomicInteger i = new AtomicInteger();
        NotificationUtils.notifications.forEach(notification -> notification.draw(i.getAndIncrement(), Math.round(duration.getValue()), Math.round(keep.getValue())));
        NotificationUtils.notifications.removeIf(Notification::shouldDestroy);
    }

}

