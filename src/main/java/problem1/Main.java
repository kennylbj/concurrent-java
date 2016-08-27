package problem1;

import java.util.*;

/**
 * Created by kennybjliu on 16/8/27.
 * Suppose we have N customers and each of them
 * has M money. All they will do is to transfer
 * some of it's money to other random customer's account.
 * Can you simulate that?
 * Suppose N = 10 && M = 2000 && transaction money is [1,10]
 */
public class Main {
    private static final int CUSTOMER_NUM = 10;
    private static final int THRESHOLD = 10;
    private static final int MONEY = 200000;
    private static final int WAIT = 2 * 1000;
    public static void main(String[] args) throws InterruptedException {
        //init accounts
        Map<Integer, Integer> accounts = new HashMap<>(CUSTOMER_NUM);
        for (int i = 0; i < CUSTOMER_NUM; i++) {
            accounts.put(i, MONEY);
        }

        //init customers
        Customer customers[] = new Customer[CUSTOMER_NUM];
        for (int i = 0; i < CUSTOMER_NUM; i++) {
            customers[i] = new Customer(i, accounts, THRESHOLD);
        }

        //start thread
        for (int i = 0; i < CUSTOMER_NUM; i++) {
            new Thread(customers[i]).start();
        }

        //wait a bit of time
        Thread.sleep(WAIT);

        //stop all threads
        for (int i = 0; i < CUSTOMER_NUM; i++) {
            customers[i].stop();
        }

        accounts.entrySet().stream().forEach(System.out::println);

        int sum = accounts.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("now the sum is " + sum);
        System.out.println("we expected to be " + (MONEY * CUSTOMER_NUM));

        int totalCount = Arrays.stream(customers).mapToInt(Customer::getCount).sum();
        System.out.println("transfer " + totalCount + " times");

        int poorTimes = Arrays.stream(customers).mapToInt(Customer::getPoorTimes).sum();
        System.out.println("total poor times " + poorTimes);

    }

}
