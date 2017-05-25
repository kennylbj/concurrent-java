package singleton;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * The easier way to create a thread-safe singleton class is to
 * make the global access method synchronized.
 * This implementation works fine and provides thread-safety but
 * it reduces the performance because of cost associated with the
 * synchronized method, all {@code getInstance()} will block at
 * synchronized method although only a few of them will initialize
 * it.
 */
@ThreadSafe
public class SynchronizedSingleton {
    @GuardedBy("this")
    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {
    }

    public synchronized SynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}
