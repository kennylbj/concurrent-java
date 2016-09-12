package problem5.producerconsumer.condition;

import net.jcip.annotations.GuardedBy;
import problem5.producerconsumer.Buffer;
import problem5.producerconsumer.IProducer;
import problem5.producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by kennylbj on 16/9/10.
 * Producer implemented by Condition.
 * Using monitors makes race conditions much less likely than when using semaphores.
 */
public class ConditionProducer implements IProducer<Item>, Runnable {
    @GuardedBy("lock")
    private final Buffer<Item> buffer;
    private final Lock lock;
    private final Condition full;
    private final Condition empty;
    private final Random random = new Random(System.nanoTime());

    public ConditionProducer(Buffer<Item> buffer, Lock lock, Condition full, Condition empty) {
        this.buffer = buffer;
        this.lock = lock;
        this.full = full;
        this.empty = empty;
    }

    @Override
    public Item produce() {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Item item = Item.generate();
        System.out.println("[Condition] Producer produces item " + item.toString()
                + " by thread " + Thread.currentThread().getId());
        return item;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                //outside critical section
                Item item = produce();

                lock.lock();
                try {
                    //buffer is full, so wait until no full
                    while (buffer.getSize() == buffer.getCapacity()) {
                        full.await();
                    }
                    buffer.putItem(item);
                    //signal consumers when buffer size is one
                    //to avoid herd effect
                    if (buffer.getSize() == 1) {
                        empty.signal();
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
