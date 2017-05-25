package singleton;

import net.jcip.annotations.NotThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * {@code LazySingleton} will defer instant operation while it has to.
 * It's prefer to use this approach while instant operation will cost a lot.
 * However it is not thread-safe singleton. while multiple threads invoke
 * {@code getInstance()} method, all threads may initialization their own
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
