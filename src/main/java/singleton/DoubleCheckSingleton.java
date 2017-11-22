package singleton;

import net.jcip.annotations.NotThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * Warning: this implement of singleton is NOT correct!
 * It seems that {@code getInstance} method will not block at null-check logic each time
 * if the instance has already be initiated. However, Java memory model can not guarantee
 * the visibility of instance. {@see https://en.wikipedia.org/wiki/Java_memory_model}
 * Considering the following situation:
 * 1. Thread A checks that instance is null and go to line 32.
 * 2. Thread B also checks that instance is null and go to line 32.
 * 3. Thread A obtains the lock and double-checks the instance. It find that
 *    instance is null and initializes it and then assigns to instance field.
 *    Finally it exits the critical section.
 * 4. Thread B waits until thread A release the lock and then enters the section.
 *    Thread B may still find that instance is null because Java memory model
 *    can not guarantee that the assignment operation of thread A will be seen by thread B.
 *    So it may initialize instance again and break the law.
 * We can use volatile modifier to solve this problem.
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
