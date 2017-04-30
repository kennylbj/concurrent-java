package optional;

import java.util.Random;

/**
 * Created by kennylbj on 2017/4/29.
 * A hud must have a brand name.
 */
final class Hud {
    private static final String[] optional = {"Benz hud", "BMW hud", "Audi Hud"};
    private static final Random random = new Random(System.nanoTime());
    private final String brand;

    Hud() {
        brand = optional[random.nextInt(3)];
    }

    String getBrand() {
        return brand;
    }

}
