package singleton;

import net.jcip.annotations.ThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * The instance of Singleton Class is created at the time of class loading.
 * This is the easiest method to create a singleton class but it has a drawback
 * that instance is created even though client application might not be using it.
 * If your singleton class is not using a lot of resources, this is the approach to use.
 * But in most of the scenarios, Singleton classes are created for resources such as
 * File System, Database connections etc and we should avoid the instantiation until
 * unless client calls the getInstance method.
 */
@ThreadSafe
public class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}
