package net.minearchive.module;

import com.google.common.eventbus.Subscribe;
import net.minearchive.AccessMC;
import net.minearchive.Uzaware;
import net.minearchive.event.events.TickStartEvent;
import net.minearchive.event.events.UpdateEvent;
import net.minearchive.module.modules.hud.NotificationModule;
import net.minearchive.setting.KeyBind;
import net.minearchive.setting.Setting;
import net.minearchive.setting.settings.KeyBindSetting;
import net.minearchive.util.ChatUtil;
import net.minearchive.util.NotificationUtils;
import net.minearchive.util.notification.Notification;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Module implements AccessMC {
    public final String name = getAnnotation().name();
    public final String description = getAnnotation().description();
    public final Category category = getAnnotation().category();
    public boolean enabled = getAnnotation().enable();

    public final List<Setting<?>> settings = new ArrayList<>();

    public final KeyBindSetting bind = add(new KeyBindSetting("Bind", new KeyBind(getAnnotation().keybind())));

    public void toggle() {
        enabled = !enabled;
        if (enabled) enable();
        else disable();
    }

    public final void enable() {
        Uzaware.registerEventBus(this);
        if (!nullCheck() && NotificationModule.INSTANCE.chatNotify.getValue())
            ChatUtil.addMessage(this.hashCode(), Text.of(String.format("§d%s§r | §7%s§r toggled §aon§r" , Uzaware.modName, this.name)));
        if (!nullCheck() && NotificationModule.INSTANCE.enabled)
            new NotificationUtils.Builder()
                    .setId(hashCode())
                    .setTitle(name)
                    .setMessage("Enabled")
                    .setType(Notification.NotificationType.SUCCESS)
                    .buildAndAdd();
        enabled = true;
        onEnable();
    }

    public final void disable() {
        Uzaware.unRegisterEventBus(this);
        if (!nullCheck() && NotificationModule.INSTANCE.chatNotify.getValue())
            ChatUtil.addMessage(this.hashCode(), Text.of(String.format("§d%s§r | §7%s§r toggled §coff§r" , Uzaware.modName, this.name)));
        if (!nullCheck() && NotificationModule.INSTANCE.enabled)
            new NotificationUtils.Builder()
                    .setId(hashCode())
                    .setTitle(name)
                    .setMessage("Disabled")
                    .setType(Notification.NotificationType.ERROR)
                    .buildAndAdd();
        enabled = false;
        onDisable();
    }

    @Subscribe
    public void onTick(TickStartEvent event) { }

    @Subscribe
    public void onUpdate(UpdateEvent event) { }

    public void onEnable() { }

    public void onDisable() { }

    public boolean nullCheck() {
        return client.world == null || client.player == null;
    }

    public <T extends Setting<?>> T add(T t) {
        settings.add(t);
        return t;
    }

    public ModuleInfo getAnnotation() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class))
            return this.getClass().getAnnotation(ModuleInfo.class);
        else throw new RuntimeException("ModuleInfo Annotation is not found! Can't initialize module!");
    }

    public void addMessage(String msg) {
        ChatUtil.addMessage(msg);
    }
}
