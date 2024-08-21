package net.minearchive.module;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.RenderStartEvent;

public class HudModule extends Module {

    public float x = 0f, y = 0f, width = 0f, height = 0f;

    @Subscribe
    public void onRender(RenderStartEvent event) {

    }

    public void onMouseClick(float x, float y, int mouseButton) {

    }

    public void onDrag(float x, float y, float deltaX, float deltaY) {

    }
}
