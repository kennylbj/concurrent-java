package producerconsumer.semaphore;

import net.jcip.annotations.GuardedBy;
import producerconsumer.Buffer;
import producerconsumer.IProducer;
import producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by kennylbj on 16/9/10.
 * Producer implemented by Semaphore.
 */
public class SemaphoreProducer implements IProducer<Item>, Runnable {
    @GuardedBy("buffer")
    private final Buffer<Item> buffer;
    private final Semaphore fullCount;
    private final Semaphore emptyCount;
    private final Random random = new Random(System.nanoTime());

    public SemaphoreProducer(Buffer<Item> buffer, Semaphore fullCount, Semaphore emptyCount) {
        this.buffer = buffer;
        this.fullCount = fullCount;
        this.emptyCount = emptyCount;
    }

    //produce operation may be time-consuming
    @Override
    public Item produce() {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Item item = Item.generate();
        System.out.println("[Semaphore] Producer produces item " + item.toString()
                + " by thread " + Thread.currentThread().getId());
        return item;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Item item = produce();
                emptyCount.acquire();
                synchronized (buffer) {
                    buffer.putItem(item);
                }
                fullCount.release();

            }
        } catch (InterruptedException e) {
            System.out.println("failed to produce");
            Thread.currentThread().interrupt();
        }

    }
}
