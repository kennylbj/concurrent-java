package producerconsumer.blockingqueue;

import producerconsumer.Producer;
import producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kennylbj on 16/9/10.
 * Producer implemented by BlockingQueue
 */
public class BlockingQueueProducer implements Producer<Item>, Runnable {
    private final BlockingQueue<Item> buffer;
    private final Random random = new Random(System.nanoTime());

    public BlockingQueueProducer(BlockingQueue<Item> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Item item = produce();
                buffer.put(item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Item produce() {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Item item = Item.generate();
        System.out.println("[BlockingQueue] Producer produces item " + item.toString()
                + " by thread " + Thread.currentThread().getId());
        return item;
    }
}
