package net.minearchive.module.modules.hud;

import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.BooleanSetting;
import net.minearchive.setting.settings.IntegerSetting;
import net.minearchive.util.NotificationUtils;
import net.minearchive.util.notification.Notification;

import java.util.concurrent.atomic.AtomicInteger;

@HudInfo()
@ModuleInfo(name = "Notification", category = Category.HUD)
public class NotificationModule extends HudModule {
    public static NotificationModule INSTANCE;

    public final BooleanSetting chatNotify = add(new BooleanSetting("ChatNotify", true));
    public final IntegerSetting duration = add(new IntegerSetting("InOut Duration", 600, 100, 1000));
    public final IntegerSetting keep = add(new IntegerSetting("Keep Duration", 3500, 10, 5000));

    public NotificationModule() {
        INSTANCE = this;
    }

    @Override
    public void onRender(Render2DStartEvent event) {
        AtomicInteger i = new AtomicInteger();
        NotificationUtils.notifications.forEach(notification -> notification.draw(i.getAndIncrement(), duration.getValue(), keep.getValue()));
        NotificationUtils.notifications.removeIf(Notification::shouldDestroy);
    }

}

