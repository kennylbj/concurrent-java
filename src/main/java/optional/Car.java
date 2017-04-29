package optional;

import java.util.Optional;
import java.util.Random;

/**
 * Created by kennylbj on 2017/4/29.
 * A car may or may not have a Hud.
 */
final class Car {
    private static final Random random = new Random();
    private Hud hud;

    Car() {
        if (random.nextInt(3) != 0) {
            hud = new Hud();
        }
    }

    Optional<Hud> getHud() {
        return Optional.ofNullable(hud);
    }
}
