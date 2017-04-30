package completableFuture;

import net.jcip.annotations.ThreadSafe;

/**
 * Created by kennylbj on 2017/4/30.
 * {@code HealthCenter} offers a synchronous API for getting
 * comfort level by certain weather.
 * The query operation may take some time to execute.
 */
@ThreadSafe
final class HealthCenter {
    private static final int LEVEL = 5;

    int comfortLevel(Weather weather) {
        delayFixDuration(1);
        return (int)((double)weather.getTemperature() * weather.getMoisture()) % LEVEL;
    }

    private void delayFixDuration(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
