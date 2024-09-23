package net.minearchive.module;

import com.google.common.eventbus.Subscribe;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;
import net.minecraft.client.MinecraftClient;
import org.joml.Math;

public class HudModule extends Module {

    public float x = getHudAnnotation().x(), y = getHudAnnotation().y(),
            width = getHudAnnotation().width(), height = getHudAnnotation().height(),
            round = getHudAnnotation().round(),
            oldMouseX = 0f, oldMouseY = 0f;

    public final Animation alpha = new Animation(0.0f, EnumEasing.QUART.getEasing());

    @Subscribe
    public void onRender(Render2DStartEvent event) { }
    public void onMouseClick(float x, float y, int mouseButton) { }

    public HudInfo getHudAnnotation() {
        if (this.getClass().isAnnotationPresent(HudInfo.class)) return this.getClass().getAnnotation(HudInfo.class);
        else throw new RuntimeException("HudInfo Annotation is not found! Can't initialize hud module!");
    }
}
