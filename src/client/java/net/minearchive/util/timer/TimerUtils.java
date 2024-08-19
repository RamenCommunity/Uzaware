package net.minearchive.util.timer;

public class TimerUtils {
    private long time;
    private TimeUnit unit;

    public TimerUtils(TimeUnit unit) {
        this.time = -1L;
        this.unit = unit;
    }

    public boolean passed(long time) {
        return current() - this.time >= time * this.unit.multiplier();
    }

    public long passedTime() {
        return current() - this.time;
    }

    public TimeUnit unit() {
        return this.unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public void reset() {
        this.time = current();
    }

    private long current() {
        return System.currentTimeMillis();
    }
}
