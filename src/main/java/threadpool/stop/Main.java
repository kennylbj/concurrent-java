package threadpool.stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This example demonstrate the right way to stop a thread pool.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            executor.submit(new Task());
        }
        Thread.sleep(3000);
        executor.shutdownNow();
        executor.awaitTermination(3, TimeUnit.SECONDS);
    }
}
