package net.minearchive.util.easing;

public class Animation {

    private float value;
    private long lastUpdateTime;
    private IEasing easing;

    public Animation(float value, IEasing easing) {
        this.value = value;
        this.lastUpdateTime = System.nanoTime();
        this.easing = easing;
    }

    public void animateTo(float target, double duration) {
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        float progress = (float) ((double) (deltaTime / 100L) / (duration * 10000L));
        float easedProgress = (float) easing.ease(progress);

        float deltaValue = (target - value) * Math.min(easedProgress, 1);

        value += deltaValue;
    }

    public float getValue() {
        return value;
    }

    public void setEasing(IEasing easing) {
        this.easing = easing;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
