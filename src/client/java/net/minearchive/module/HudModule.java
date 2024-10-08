package net.minearchive.module;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;

public class HudModule extends Module {

    public float x = getHudAnnotation().x();
    public float y = getHudAnnotation().y();
    public float width = getHudAnnotation().width();
    public float height = getHudAnnotation().height();
    public float round = getHudAnnotation().round();
    public float oldMouseX = 0f;
    public float oldMouseY = 0f;

    public final Animation alpha = new Animation(0.0f, EnumEasing.QUART.getEasing());

    @Subscribe
    public void onRender(Render2DStartEvent event) { }
    public void onMouseClick(float x, float y, int mouseButton) { }

    public HudInfo getHudAnnotation() {
        if (this.getClass().isAnnotationPresent(HudInfo.class)) return this.getClass().getAnnotation(HudInfo.class);
        else throw new RuntimeException("HudInfo Annotation is not found! Can't initialize hud module!");
    }
}
