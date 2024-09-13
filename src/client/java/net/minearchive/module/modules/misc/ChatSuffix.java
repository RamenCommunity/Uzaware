package net.minearchive.module.modules.misc;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.MessageSendEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;

@ModuleInfo(name = "ChatSuffix", category = Category.MISC)
public class ChatSuffix extends Module {

    public static ChatSuffix INSTANCE;

    public ChatSuffix() {
        INSTANCE = this;
    }

    @Subscribe
    public void onMessageSend(MessageSendEvent event) {
        String message = event.getMessage() + " | Uzaware";
        event.setMessage(message);
    }

}
