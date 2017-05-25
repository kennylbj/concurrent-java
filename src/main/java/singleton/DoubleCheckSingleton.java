package singleton;

import net.jcip.annotations.NotThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * Warning: this implement of singleton is NOT correct!
 * It seems like that {@code getInstance} method will not block at null-check
 * logic each time if the instance has already initiate. However, Java memory model can not guarantee
 * the visibility of instance. {@see https://en.wikipedia.org/wiki/Java_memory_model}
 * Consider the following situation:
 * 1. Thread A check that instance is null and go to line 31
 * 2. Thread B also check that instance is null and go to line 31
 * 3. Thread A obtain the lock and double check the instance, it find that
 *    instance is null and initialize it and then assign to instance field.
 *    and then it quit the critical section.
 * 4. Thread B wait until thread A release the lock and enter the section.
 *    Thread B may still find that instance is null Because Java memory model
 *    can not guarantee that the assignment operation of thread A will be seen by thread B.
 *    So it will initialize instance again and break the law.
 * We can use volatile modifier to solve this problem,
 */
@NotThreadSafe
public class DoubleCheckSingleton {
    private static DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    public synchronized DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
