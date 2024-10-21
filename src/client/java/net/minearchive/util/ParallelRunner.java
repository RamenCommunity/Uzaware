package net.minearchive.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelRunner {

    public static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void runParallel(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(thread::interrupt));
    }
}
