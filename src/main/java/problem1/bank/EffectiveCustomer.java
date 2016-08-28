package problem1.bank;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Created by kennylbj on 16/8/28.
 * Obtain lock in order to avoid dead lock.
 */
@ThreadSafe
class EffectiveCustomer implements Runnable {
    @GuardedBy("this")
    private int money;
    private final int id;
    private final EffectiveCustomer[] customers;
    private final int threshold;

    private volatile boolean stop = false;
    private int count = 0;
    private int poorTimes = 0;


    EffectiveCustomer(int money, int id, EffectiveCustomer[] customers, int threshold) {
        this.money = money;
        this.id = id;
        this.customers = customers;
        this.threshold = threshold;

    }

    int getMoney() {
        if (!stop) {
            throw new ConcurrentModificationException("can not access count");
        }
        return money;
    }

    int getCount() {
        if (!stop) {
            throw new ConcurrentModificationException("can not access count");
        }
        return count;
    }

    int getPoorTimes () {
        if (!stop) {
            throw new ConcurrentModificationException("can not access poorTimes");
        }
        return poorTimes;
    }

    void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            //accounts's size is a constant we so don't need to synchronize it
            int size = customers.length;
            int pickOne = new Random().nextInt(size);
            //pick myself
            if (pickOne == id) {
                continue;
            }
            count++;
            //transfer a random amount of money [1, threshold]
            int transfer = new Random().nextInt(threshold) + 1;

            //synchronize in order to avoid dead lock
            int smaller = Math.min(id, pickOne);
            int bigger = Math.max(id, pickOne);
            synchronized (customers[smaller]) {
                synchronized (customers[bigger]) {
                    if (money < transfer) {
                        poorTimes++;
                        continue;
                    }
                    this.money -= transfer;
                    customers[pickOne].money += transfer;
                }
            }
        }
    }
}
