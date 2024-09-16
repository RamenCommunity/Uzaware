package net.minearchive.util;

import org.lwjgl.glfw.GLFW;

import java.util.Locale;
import java.util.Map;

public class KeyUtils {

    private static final Map<Integer, String> keyMap = Map.<Integer, String>ofEntries(
            Map.entry(GLFW.GLFW_KEY_UNKNOWN, "None"),
            Map.entry(GLFW.GLFW_KEY_SPACE, "Space"),
            Map.entry(GLFW.GLFW_KEY_SLASH, "None"),
            Map.entry(GLFW.GLFW_KEY_ESCAPE, "Escape"),
            Map.entry(GLFW.GLFW_KEY_ENTER, "Enter"),
            Map.entry(GLFW.GLFW_KEY_TAB, "Tab"),
            Map.entry(GLFW.GLFW_KEY_BACKSPACE, "Backspace"),
            Map.entry(GLFW.GLFW_KEY_INSERT, "Insert"),
            Map.entry(GLFW.GLFW_KEY_DELETE, "None"),
            Map.entry(GLFW.GLFW_KEY_RIGHT, "Right"),
            Map.entry(GLFW.GLFW_KEY_LEFT, "Left"),
            Map.entry(GLFW.GLFW_KEY_DOWN, "Down"),
            Map.entry(GLFW.GLFW_KEY_UP, "Up"),
            Map.entry(GLFW.GLFW_KEY_PAGE_UP, "Page Up"),
            Map.entry(GLFW.GLFW_KEY_PAGE_DOWN, "Page Down"),
            Map.entry(GLFW.GLFW_KEY_HOME, "Home"),
            Map.entry(GLFW.GLFW_KEY_END, "End"),
            Map.entry(GLFW.GLFW_KEY_CAPS_LOCK, "Caps Lock"),
            Map.entry(GLFW.GLFW_KEY_SCROLL_LOCK, "Scroll Lock"),
            Map.entry(GLFW.GLFW_KEY_NUM_LOCK, "Num Lock"),
            Map.entry(GLFW.GLFW_KEY_PRINT_SCREEN, "Print Screen"),
            Map.entry(GLFW.GLFW_KEY_PAUSE, "Pause"),
            Map.entry(GLFW.GLFW_KEY_F1, "F1"),
            Map.entry(GLFW.GLFW_KEY_F2, "F2"),
            Map.entry(GLFW.GLFW_KEY_F3, "F3"),
            Map.entry(GLFW.GLFW_KEY_F4, "F4"),
            Map.entry(GLFW.GLFW_KEY_F5, "F5"),
            Map.entry(GLFW.GLFW_KEY_F6, "F6"),
            Map.entry(GLFW.GLFW_KEY_F7, "F7"),
            Map.entry(GLFW.GLFW_KEY_F8, "F8"),
            Map.entry(GLFW.GLFW_KEY_F9, "F9"),
            Map.entry(GLFW.GLFW_KEY_F10, "F10"),
            Map.entry(GLFW.GLFW_KEY_F11, "F11"),
            Map.entry(GLFW.GLFW_KEY_F12, "F12"),
            Map.entry(GLFW.GLFW_KEY_F13, "F13"),
            Map.entry(GLFW.GLFW_KEY_F14, "F14"),
            Map.entry(GLFW.GLFW_KEY_F15, "F15"),
            Map.entry(GLFW.GLFW_KEY_F16, "F16"),
            Map.entry(GLFW.GLFW_KEY_F17, "F17"),
            Map.entry(GLFW.GLFW_KEY_F18, "F18"),
            Map.entry(GLFW.GLFW_KEY_F19, "F19"),
            Map.entry(GLFW.GLFW_KEY_F20, "F20"),
            Map.entry(GLFW.GLFW_KEY_F21, "F21"),
            Map.entry(GLFW.GLFW_KEY_F22, "F22"),
            Map.entry(GLFW.GLFW_KEY_F23, "F23"),
            Map.entry(GLFW.GLFW_KEY_F24, "F24"),
            Map.entry(GLFW.GLFW_KEY_F25, "F25"),
            Map.entry(GLFW.GLFW_KEY_KP_0, "Keypad 0"),
            Map.entry(GLFW.GLFW_KEY_KP_1, "Keypad 1"),
            Map.entry(GLFW.GLFW_KEY_KP_2, "Keypad 2"),
            Map.entry(GLFW.GLFW_KEY_KP_3, "Keypad 3"),
            Map.entry(GLFW.GLFW_KEY_KP_4, "Keypad 4"),
            Map.entry(GLFW.GLFW_KEY_KP_5, "Keypad 5"),
            Map.entry(GLFW.GLFW_KEY_KP_6, "Keypad 6"),
            Map.entry(GLFW.GLFW_KEY_KP_7, "Keypad 7"),
            Map.entry(GLFW.GLFW_KEY_KP_8, "Keypad 8"),
            Map.entry(GLFW.GLFW_KEY_KP_9, "Keypad 9"),
            Map.entry(GLFW.GLFW_KEY_KP_DECIMAL, "Keypad ."),
            Map.entry(GLFW.GLFW_KEY_KP_DIVIDE, "Keypad /"),
            Map.entry(GLFW.GLFW_KEY_KP_MULTIPLY, "Keypad *"),
            Map.entry(GLFW.GLFW_KEY_KP_SUBTRACT, "Keypad -"),
            Map.entry(GLFW.GLFW_KEY_KP_ADD, "Keypad +"),
            Map.entry(GLFW.GLFW_KEY_KP_ENTER, "Keypad Enter"),
            Map.entry(GLFW.GLFW_KEY_KP_EQUAL, "Keypad ="),
            Map.entry(GLFW.GLFW_KEY_LEFT_SHIFT, "Left Shift"),
            Map.entry(GLFW.GLFW_KEY_LEFT_CONTROL, "Left Control"),
            Map.entry(GLFW.GLFW_KEY_LEFT_ALT, "Left Alt"),
            Map.entry(GLFW.GLFW_KEY_LEFT_SUPER, "Left Super"),
            Map.entry(GLFW.GLFW_KEY_RIGHT_SHIFT, "Right Shift"),
            Map.entry(GLFW.GLFW_KEY_RIGHT_CONTROL, "Right Control"),
            Map.entry(GLFW.GLFW_KEY_RIGHT_ALT, "Right Alt"),
            Map.entry(GLFW.GLFW_KEY_RIGHT_SUPER, "Right Super"),
            Map.entry(GLFW.GLFW_KEY_MENU, "Menu")
    );

    public static String getNameByKey(int key) {
        if (key == GLFW.GLFW_KEY_UNKNOWN) return "None";
        String name = GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
        if (name == null) return keyMap.getOrDefault(key, "Unknown");
        return name.toUpperCase(Locale.JAPANESE);
    }
}
