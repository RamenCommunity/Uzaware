package net.minearchive.util;

public class FPSCalculator {
    int currentFPS;
    private final FPS[] timers;

    public FPSCalculator() {
        currentFPS = 0;
        timers = new FPS[20];
        long startTime = System.nanoTime();
        for (int i = 0; i < timers.length; i++) {
            long plus = startTime + ((long) i * 1000000000 / timers.length);
            timers[i] = new FPS(plus);
        }
    }

    public void update() {
        for (FPS fps : timers) {
            fps.updateFPSCount();
            if (fps.isOver()) {
                this.currentFPS = fps.getFpsCount();
                fps.updateStartTime();
            }
        }
    }

    public int getCurrentFPS() {
        return currentFPS;
    }

    public static class FPS {
        private long startTime;
        private int fpsCount;

        public FPS(long startTime) {
            this.startTime = startTime;
            fpsCount = 0;
        }

        public void updateFPSCount() {
            fpsCount++;
        }

        public boolean isOver() {
            return System.nanoTime() - startTime >= 1000000000;
        }

        public void updateStartTime() {
            while (isOver()) {
                startTime += 1000000000;
            }
            fpsCount = 0;
        }

        public int getFpsCount() {
            return fpsCount;
        }
    }
}
