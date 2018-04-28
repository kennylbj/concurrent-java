package rxjava.helloworld;

import io.reactivex.Flowable;

/**
 * Created by kennylbj on 22/11/2017.
 * Hello world example using RxJava
 */
public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::print);
    }
}
