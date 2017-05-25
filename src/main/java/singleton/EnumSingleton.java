package singleton;

/**
 * Created by kennylbj on 2017/5/25.
 * Joshua Bloch(Effective Java) suggests the use of Enum to implement
 * Singleton design pattern as Java ensures that any enum value is
 * instantiated only once in a Java program. Since Java Enum values
 * are globally accessible, so is the singleton.
 * However, Enums often require more than twice as much memory as
 * static constants. You should strictly avoid using enums on Android.
 */
public enum EnumSingleton {

    INSTANCE;

    public static void doSomething(){
        //do something
    }
}