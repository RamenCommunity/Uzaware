package net.minearchive.screen;

import net.minecraft.client.gui.DrawContext;

public abstract class AbstractElement<T> implements IElement {
    protected final T t;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float offset;

    public AbstractElement(T t, float x, float y, float width, float height) {
        this.t = t;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta, float offset) {
        this.offset = offset;
    }

    public T t() {
        return t;
    }
}
