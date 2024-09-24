package net.minearchive.util;

import net.minearchive.module.Module;
import net.minearchive.util.notification.Notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class NotificationUtils {

    public static List<Notification> notifications = new CopyOnWriteArrayList<>();

    public static void add(Notification notification) {
        AtomicBoolean added = new AtomicBoolean(false);
        notifications.stream().filter(notification1 -> notification1.getData().id == notification.getData().id).forEach(notification1 -> {
            notification1.setData(notification.getData());
            added.set(true);
        });
        if (!added.get()) notifications.add(notification);
    }

    public static class Builder {
        private String title, message;
        private Notification.NotificationType type;
        private int id = -1;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setType(Notification.NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder create(Module module) {
            this.id = module.hashCode();
            this.title = module.name;
            this.message = module.enabled ? "Enabled" : "Disabled";
            this.type = module.enabled ? Notification.NotificationType.SUCCESS : Notification.NotificationType.ERROR;
            return this;
        }

        public Notification build() {
            return new Notification(
                    new Notification.NotificationData(
                            title, message, type, id
                    )
            );
        }

        public void buildAndAdd() {
            add(new Notification(new Notification.NotificationData(title, message, type, id)));
        }
    }
}