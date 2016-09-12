package problem5.producerconsumer.semaphore;

import net.jcip.annotations.GuardedBy;
import problem5.producerconsumer.Buffer;
import problem5.producerconsumer.IConsumer;
import problem5.producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by kennylbj on 16/9/10.
 * Consumer implemented by Semaphore
 */
public class SemaphoreConsumer implements IConsumer<Item>, Runnable {
    @GuardedBy("buffer")
    private final Buffer<Item> buffer;
    private final Semaphore fullCount;
    private final Semaphore emptyCount;
    private final Random random = new Random(System.nanoTime());

    public SemaphoreConsumer(Buffer<Item> buffer, Semaphore fullCount, Semaphore emptyCount) {
        this.buffer = buffer;
        this.fullCount = fullCount;
        this.emptyCount = emptyCount;
    }

    @Override
    public void consume(Item item) {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Semaphore] Consumer consumes item " + item.toString()
                + " by thread " + Thread.currentThread().getId());
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                fullCount.acquire();
                Item item;
                synchronized (buffer) {
                    item = buffer.popItem();
                }
                emptyCount.release();
                consume(item);
            }
        } catch (InterruptedException e) {
            System.out.println("failed to consume");
            Thread.currentThread().interrupt();
        }
    }
}
