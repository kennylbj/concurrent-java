package producerconsumer;

import producerconsumer.blockingqueue.BlockingQueueConsumer;
import producerconsumer.blockingqueue.BlockingQueueProducer;
import producerconsumer.condition.ConditionConsumer;
import producerconsumer.condition.ConditionProducer;
import producerconsumer.semaphore.SemaphoreConsumer;
import producerconsumer.semaphore.SemaphoreProducer;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kennylbj on 16/9/10.
 * Implement 3 versions of P/C
 * 1) use Semaphore
 * 2) use Condition
 * 3) use BlockingQueue
 * All versions support multiple producers and consumers
 */
public class Main {
    private static final int BUFFER_SIZE = 100;
    private static final int PRODUCER_NUM = 3;
    private static final int CONSUMER_NUM = 2;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();

        // Buffers of all P/C
        Buffer<Item> semaphoreBuffer = new LinkListBuffer(BUFFER_SIZE);
        Buffer<Item> conditionBuffer = new LinkListBuffer(BUFFER_SIZE);
        BlockingQueue<Item> blockingQueueBuffer = new LinkedBlockingQueue<>(BUFFER_SIZE);

        // Semaphores for Semaphore version of P/C
        Semaphore fullCount = new Semaphore(0);
        Semaphore emptyCount = new Semaphore(BUFFER_SIZE);

        // Lock and conditions for Condition version of P/C
        Lock lock = new ReentrantLock();
        Condition full = lock.newCondition();
        Condition empty = lock.newCondition();

        for (int i = 0; i < PRODUCER_NUM; i++) {
            pool.execute(new SemaphoreProducer(semaphoreBuffer, fullCount, emptyCount));
            pool.execute(new ConditionProducer(conditionBuffer, lock, full, empty));
            pool.execute(new BlockingQueueProducer(blockingQueueBuffer));
        }
        for (int i = 0; i < CONSUMER_NUM; i++) {
            pool.execute(new SemaphoreConsumer(semaphoreBuffer, fullCount, emptyCount));
            pool.execute(new ConditionConsumer(conditionBuffer, lock, full, empty));
            pool.execute(new BlockingQueueConsumer(blockingQueueBuffer));
        }

        Thread.sleep(10 * 1000);

        pool.shutdownNow();
        pool.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println("Buffer size " + semaphoreBuffer.getSize()
                + " : " + conditionBuffer.getSize() + " : " + blockingQueueBuffer.size());
    }
}
