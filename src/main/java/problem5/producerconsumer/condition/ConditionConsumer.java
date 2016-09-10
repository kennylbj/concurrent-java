package problem5.producerconsumer.condition;

import net.jcip.annotations.GuardedBy;
import problem5.producerconsumer.Buffer;
import problem5.producerconsumer.IConsumer;
import problem5.producerconsumer.Item;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by kennylbj on 16/9/10.
 * Consumer implemented by Condition
 */
public class ConditionConsumer implements IConsumer<Item>, Runnable {
    @GuardedBy("lock")
    private final Buffer<Item> buffer;
    private final Lock lock;
    private final Condition full;
    private final Condition empty;
    private final Random random = new Random(System.nanoTime());

    public ConditionConsumer(Buffer<Item> buffer, Lock lock, Condition full, Condition empty) {
        this.buffer = buffer;
        this.lock = lock;
        this.full = full;
        this.empty = empty;
    }

    @Override
    public void consume(Item item) {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Condition] Consumer consumes item " + item.toString());
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Item item;
                lock.lock();
                try {
                    //buffer empty, so wait until no empty
                    while (buffer.getSize() == 0) {
                        empty.await();
                    }
                    item = buffer.popItem();

                    if (buffer.getSize() == buffer.getCapacity() - 1) {
                        full.signal();
                    }
                } finally {
                    lock.unlock();
                }
                //outside critical section
                consume(item);
            }
        } catch (InterruptedException e) {
            System.out.println("failed to consume items");
            Thread.currentThread().interrupt();
        }
    }

}
