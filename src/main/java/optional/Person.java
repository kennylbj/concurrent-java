package optional;

import java.util.Optional;
import java.util.Random;

/**
 * Created by kennylbj on 2017/4/29.
 * A Person may or may not have a car.
 */
public final class Person {
    private static final Random random = new Random();
    private Car car;

    Person() {
        if (random.nextInt(3) != 0) {
            car = new Car();
        }
    }

    Optional<Car> getCar() {
        return Optional.ofNullable(car);
    }
}
