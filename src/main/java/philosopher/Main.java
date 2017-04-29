package philosopher;


import java.util.Arrays;

/**
 * Created by kennylbj on 16/8/28.
 * Dining Philosopher problems
 * wiki https://en.wikipedia.org/wiki/Dining_philosophers_problem
 */
public class Main {
    private static final int PHI_NUM = 5;
    private static final int SLEEP_TIME = 10 * 1000;
    public static void main(String[] args) throws InterruptedException {
        Chopstick[] chopsticks = new Chopstick[PHI_NUM];
        for (int i = 0; i < PHI_NUM; i++) {
            chopsticks[i] = new Chopstick(i);
        }
        Philosopher[] philosophers = new Philosopher[PHI_NUM];
        for (int i = 0; i < PHI_NUM; i++) {
            philosophers[i] = new Philosopher(i,
                    chopsticks[i % PHI_NUM], chopsticks[(i+1) % PHI_NUM]);
            new Thread(philosophers[i]).start();
        }

        Thread.sleep(SLEEP_TIME);

        Arrays.stream(philosophers).forEach(Philosopher::stop);

        int sum = Arrays.stream(philosophers).mapToInt(Philosopher::getCount).sum();
        System.out.println("sum is " + sum);

    }
}
