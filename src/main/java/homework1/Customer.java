package homework1;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Random;

/**
 * Created by kennybjliu on 16/8/27.
 * A runnable to randomly transfer money to
 * other account.
 */
class Customer implements Runnable {

    private final Map<Integer, Integer> accounts;
    private final int id;
    private final int threshold;
    //volatile guarantee the memory visibility
    private volatile boolean stop = false;
    //count the transfer times. single-thread-access guarantee
    private int count;
    private int poorTimes;

    Customer(int id, Map<Integer, Integer> accounts, int threshold) {
        this.id = id;
        this.accounts = accounts;
        this.threshold = threshold;
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
            int pickOne = new Random().nextInt(size);
            //pick myself
            if (pickOne == id) {
                continue;
            }
            //transfer a random amount of money [1, threshold]
            int transfer = new Random().nextInt(threshold) + 1;
            //increase counter
            count++;
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
            }
        }
    }
}
