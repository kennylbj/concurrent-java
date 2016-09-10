package problem5.producerconsumer.blockingqueue;

import problem5.producerconsumer.IProducer;
import problem5.producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by kennylbj on 16/9/10.
 */
public class BlockingQueueProducer implements IProducer<Item>, Runnable {
    private final BlockingQueue<Item> buffer;
    private final Random random = new Random(System.nanoTime());

    public BlockingQueueProducer(BlockingQueue buffer) {
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
        System.out.println("[BlockingQueue] Producer produces item " + item.toString());
        return item;
    }
}
