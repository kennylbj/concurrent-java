package completableFuture;

import net.jcip.annotations.ThreadSafe;

import java.util.Random;


/**
 * Created by kennylbj on 2017/4/30.
 * {@code WeatherCenter} offers a synchronous API for getting
 * temperature and moisture at a certain location.
 * These query operations may take some time to execute.
 */
@ThreadSafe
final class WeatherCenter {
    private static final int RANGE = 40;
    private static final Random random = new Random(System.nanoTime());
    private final String name;

    WeatherCenter(String name) {
        this.name = name;
    }

    int getTemperature(String location) {
        delayFixDuration(1);
        return (location.hashCode() + name.hashCode()) % RANGE;
    }

    double getMoisture(String location) {
        delayFixDuration(1);
        return random.nextDouble();
    }

    String getName() {
        return name;
    }

    private void delayFixDuration(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
