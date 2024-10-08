package net.minearchive.util;

import net.minearchive.AccessMC;

import java.util.HashMap;
import java.util.Map;

public class InputUtils implements AccessMC {
    private static final Map<Integer, Boolean> keyMap = new HashMap<>();

    public static void set(int key, boolean pressed) {
        keyMap.put(key, pressed);
    }

    public static boolean pressed(int key) {
        return keyMap.getOrDefault(key, false);
    }
}
