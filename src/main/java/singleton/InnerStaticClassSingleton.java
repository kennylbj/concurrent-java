package singleton;

import net.jcip.annotations.ThreadSafe;

/**
 * Created by kennylbj on 2017/5/25.
 * Notice the private inner static class that contains the instance of
 * the singleton class. When the singleton class is loaded, SingletonHelper
 * class is not loaded into memory and only when someone calls the getInstance
 * method, this class gets loaded and creates the Singleton class instance.
 * It's prefer to use this approach to implement a thread-safety singleton.
 */
@ThreadSafe
public class InnerStaticClassSingleton {

    private static class Holder {
        private static InnerStaticClassSingleton singleton = new InnerStaticClassSingleton();
    }

    private InnerStaticClassSingleton(){}

    public static InnerStaticClassSingleton getSingleton(){
        return Holder.singleton;
    }
}
