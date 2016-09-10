package problem5.producerconsumer.blockingqueue;

import problem5.producerconsumer.IConsumer;
import problem5.producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kennylbj on 16/9/10.
 */
public class BlockingQueueConsumer implements IConsumer<Item>, Runnable {
    private final BlockingQueue<Item> buffer;
    private final Random random = new Random(System.nanoTime());

    public BlockingQueueConsumer(BlockingQueue buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Item item = buffer.take();
                consume(item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void consume(Item item) {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[BlockingQueue] Consumer consumes item " + item.toString());
    }
}
