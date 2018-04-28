package threadpool.stop;

public class Task implements Runnable {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread: " + Thread.currentThread().getId() + " is running");
                // throws InterruptedException if other thread calls interrupt() method
                Thread.sleep(1000);
            }
        // break the while loop if interrupted exception occurred
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread: " + Thread.currentThread().getId() + " stopped");
        }
    }
}
