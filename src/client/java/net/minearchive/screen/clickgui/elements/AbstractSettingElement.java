package net.minearchive.screen.clickgui.elements;

import net.minearchive.screen.clickgui.AbstractElement;
import net.minearchive.util.easing.Animation;
import net.minearchive.util.easing.EnumEasing;

public abstract class AbstractSettingElement<S> extends AbstractElement<S> {
    protected final Animation globalAlpha = new Animation(1, EnumEasing.SINE.getEasing());

    public AbstractSettingElement(S setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    public Animation getGlobalAlpha() {
        return globalAlpha;
    }
}
