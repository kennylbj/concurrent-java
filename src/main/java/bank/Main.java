package bank;


import java.util.*;

/**
 * Created by kennylbj on 16/8/27.
 */
public class Main {
    private static final int CUSTOMER_NUM = 10;
    private static final int THRESHOLD = 10;
    private static final int MONEY = 200000;
    private static final int SLEEP_TIME = 10 * 1000;

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
        Arrays.stream(customers).forEach(customer -> new Thread(customer).start());

        //wait a little bit of time
        Thread.sleep(SLEEP_TIME);

        //stop all threads
        Arrays.stream(customers).forEach(Customer::stop);


        int sum = accounts.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("we expected to be " + (MONEY * CUSTOMER_NUM));
        System.out.println("normal customer sum is " + sum);
        assert sum == MONEY*CUSTOMER_NUM;

        int totalCount = Arrays.stream(customers).mapToInt(Customer::getCount).sum();
        System.out.println("normal customer transfer " + totalCount + " times");

        int poorTimes = Arrays.stream(customers).mapToInt(Customer::getPoorTimes).sum();
        System.out.println("normal customer total poor times " + poorTimes);

        EffectiveCustomer[] effectiveCustomers = new EffectiveCustomer[CUSTOMER_NUM];
        for (int i = 0; i < CUSTOMER_NUM; i++) {
            //FIXME: recursive pass value, is that ok?
            effectiveCustomers[i] = new EffectiveCustomer(MONEY, i, effectiveCustomers, THRESHOLD);
        }

        Arrays.stream(effectiveCustomers).forEach(customer -> new Thread(customer).start());

        Thread.sleep(SLEEP_TIME);
        Arrays.stream(effectiveCustomers).forEach(EffectiveCustomer::stop);

        sum = Arrays.stream(effectiveCustomers).mapToInt(EffectiveCustomer::getMoney).sum();
        System.out.println("effective customer sum is " + sum);
        assert sum == MONEY*CUSTOMER_NUM;

        totalCount = Arrays.stream(effectiveCustomers).mapToInt(EffectiveCustomer::getCount).sum();
        System.out.println("effective customer transfer " + totalCount + " times");
        poorTimes = Arrays.stream(effectiveCustomers).mapToInt(EffectiveCustomer::getPoorTimes).sum();
        System.out.println("effective customer total poor times " + poorTimes);

    }

}
