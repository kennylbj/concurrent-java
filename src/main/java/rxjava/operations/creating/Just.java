package rxjava.operations.creating;


import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by fishdicks on 22/11/2017.
 */
public class Just {
    public static void main(String[] args) {
        Observable.just("Hello world")
                .delay(1, TimeUnit.SECONDS);
    }
}
