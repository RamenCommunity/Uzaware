package net.minearchive.util;

public class ParallelRunner {

    public static void runParallel(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(thread::interrupt));
    }
}
