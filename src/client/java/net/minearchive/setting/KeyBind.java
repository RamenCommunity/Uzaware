package net.minearchive.setting;

import net.minearchive.util.KeyUtils;

public class KeyBind {
    private int key;

    public KeyBind(int key) {
        this.key = key;
    }

    public String getKeyName() {
        return KeyUtils.getNameByKey(key);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
