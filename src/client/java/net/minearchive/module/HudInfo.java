package net.minearchive.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HudInfo {
    float x()       default 0f;
    float y()       default 0f;
    float width()   default 0f;
    float height()  default 0f;
    float round()   default 0f;
}
