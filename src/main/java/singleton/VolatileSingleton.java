package singleton;

import net.jcip.annotations.ThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * Warning: It's thread-safe unless the version of jdk is not less than 1.5
 * {@see https://en.wikipedia.org/wiki/Volatile_(computer_programming)#In_Java}
 * This is the thread-safe version of double-checked singleton but it limited by jdk.
 * Volatile modifier will guarantee the visibility of instance.
 */
@ThreadSafe
public class VolatileSingleton {
    private static volatile VolatileSingleton instance;

    private VolatileSingleton() {
    }

    public synchronized VolatileSingleton getInstance() {
        if (instance == null) {
            synchronized (VolatileSingleton.class) {
                if (instance == null) {
                    instance = new VolatileSingleton();
                }
            }
        }
        return instance;
    }
}
