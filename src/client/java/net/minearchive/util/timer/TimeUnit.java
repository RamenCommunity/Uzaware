package net.minearchive.util.timer;

public enum TimeUnit {
    MILLISECONDS(1L), TICKS(50L), SECONDS(1000L), MINUTES(60000L);

    private final long multiplier;

    TimeUnit(long multiplier) {
        this.multiplier = multiplier;
    }

    public long multiplier() {
        return multiplier;
    }
}
