package producerconsumer.blockingqueue;

import producerconsumer.Consumer;
import producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kennylbj on 16/9/10.
 * Consumer implemented by BlockingQueue
 */
public class BlockingQueueConsumer implements Consumer<Item>, Runnable {
    private final BlockingQueue<Item> buffer;
    private final Random random = new Random(System.nanoTime());

    public BlockingQueueConsumer(BlockingQueue<Item> buffer) {
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
        System.out.println("[BlockingQueue] Consumer consumes item " + item.toString()
                + " by thread " + Thread.currentThread().getId());
    }
}
