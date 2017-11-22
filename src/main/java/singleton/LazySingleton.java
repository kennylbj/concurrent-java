package singleton;

import net.jcip.annotations.NotThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * Initialize operation is deferred until after it has to.
 * It's preferred to use this approach if instant operation will cost a lot.
 * However it is not thread-safe. While multiple threads invoke
 * {@code getInstance()} method, all threads may initialize their own
 * singleton and break the agreement of Singleton pattern.
 */
@NotThreadSafe
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
