package bank;

import net.jcip.annotations.GuardedBy;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Random;

/**
 * Created by kennylbj on 16/8/27.
 * A runnable to randomly transfer money to
 * other account.
 */
class Customer implements Runnable {
    @GuardedBy("accounts")
    private final Map<Integer, Integer> accounts;
    private final int id;
    private final int threshold;
    private final Random random;
    //volatile guarantee the memory visibility
    private volatile boolean stop = false;
    //count the transfer times. single-thread-access guarantee
    private int count;
    private int poorTimes;

    Customer(int id, Map<Integer, Integer> accounts, int threshold) {
        this.id = id;
        this.accounts = accounts;
        this.threshold = threshold;
        this.random = new Random();
        this.count = 0;
        this.poorTimes = 0;
    }

    void stop() {
        this.stop = true;
    }

    int getCount() {
        if (!stop) {
            throw new ConcurrentModificationException("can't not access count");
        }
        return count;
    }

    int getPoorTimes() {
        if (!stop) {
            throw new ConcurrentModificationException("can't not access poorTimes");
        }
        return poorTimes;
    }

    @Override
    public void run() {
        while (!stop) {
            //accounts's size is a constant we so don't need to synchronize it
            int size = accounts.size();
            int pickOne = random.nextInt(size);

            //pick myself
            if (pickOne == id) {
                continue;
            }
            //transfer a random amount with money [1, threshold]
            int transfer = random.nextInt(threshold) + 1;

            //critical section
            synchronized (accounts) {
                int from = accounts.get(id);
                if (from < transfer) {
                    poorTimes++;
                    continue;
                }
                int to = accounts.get(pickOne);
                from -= transfer;
                to += transfer;
                accounts.put(id, from);
                accounts.put(pickOne, to);
                count++;
            }
        }
    }
}
