package net.minearchive.module.modules.misc;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.MessageSendEvent;
import net.minearchive.module.Category;
import net.minearchive.module.Module;
import net.minearchive.module.ModuleInfo;

import java.util.HashMap;
import java.util.Map;

@ModuleInfo(name = "ChatSuffix", category = Category.MISC)
public class ChatSuffixModule extends Module {

    private static final Map<String, String> smallCapsMap = new HashMap<>();

    public ChatSuffixModule() {
        smallCapsMap.put("a", "ᴀ");
        smallCapsMap.put("b", "ʙ");
        smallCapsMap.put("c", "ᴄ");
        smallCapsMap.put("d", "ᴅ");
        smallCapsMap.put("e", "ᴇ");
        smallCapsMap.put("f", "ꜰ");
        smallCapsMap.put("g", "ɢ");
        smallCapsMap.put("h", "ʜ");
        smallCapsMap.put("i", "ɪ");
        smallCapsMap.put("j", "ᴊ");
        smallCapsMap.put("k", "ᴋ");
        smallCapsMap.put("l", "ʟ");
        smallCapsMap.put("m", "ᴍ");
        smallCapsMap.put("n", "ɴ");
        smallCapsMap.put("o", "ᴏ");
        smallCapsMap.put("p", "ᴩ");
        smallCapsMap.put("q", "q");
        smallCapsMap.put("r", "ʀ");
        smallCapsMap.put("s", "ꜱ");
        smallCapsMap.put("t", "ᴛ");
        smallCapsMap.put("u", "ᴜ");
        smallCapsMap.put("v", "ᴠ");
        smallCapsMap.put("w", "ᴡ");
        smallCapsMap.put("x", "x");
        smallCapsMap.put("y", "ʏ");
        smallCapsMap.put("z", "ᴢ");
    }

    @Subscribe
    public void onMessageSend(MessageSendEvent event) {
        String message = event.getMessage() + convert(" | Uzaware");
        event.setMessage(message);
    }

    private String convert(String string) {
        StringBuilder sb = new StringBuilder();

        for (char c : string.toCharArray()) {
            if (smallCapsMap.containsKey(String.valueOf(c)))
                sb.append(smallCapsMap.get(String.valueOf(c)));
            else sb.append(c);
        }

        return sb.toString();
    }

}
