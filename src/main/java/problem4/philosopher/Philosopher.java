package problem4.philosopher;

import java.util.Random;

/**
 * Created by kennylbj on 16/8/28.
 * Obtain lock in sequence order.
 */
public class Philosopher implements Runnable {
    private final int id;
    private final Chopstick first, second;
    private final Random random;
    private int count = 0;
    private static final int SLEEP_TIME = 1000;
    private volatile boolean stop = false;

    public Philosopher(int id, Chopstick left, Chopstick right) {
        this.id = id;
        if (left.getId() < right.getId()) {
            first = left;
            second = right;
        } else {
            first = right;
            second = left;
        }
        this.random = new Random();
    }

    public int getCount() {
        return this.count;
    }

    public void stop() {
        this.stop = true;
    }

    private void think() throws InterruptedException {
        System.out.println("philosopher " + id  + " is thinking");
        Thread.sleep(random.nextInt(SLEEP_TIME));
    }

    private void eat() throws InterruptedException{
        System.out.println("philosopher " +id + " is eating");
        Thread.sleep(random.nextInt(SLEEP_TIME));
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                think();
                synchronized (first) {
                    synchronized (second) {
                        eat();
                    }
                }
                count++;
            }
        } catch (InterruptedException e) {
            System.out.println("failed to run");
        }
    }
}
