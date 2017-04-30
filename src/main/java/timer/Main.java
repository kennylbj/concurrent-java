package timer;

import java.time.LocalDateTime;

/**
 * Created by kennylbj on 16/8/27.
 */
public class Main {
    private static final int COUNT = 10;
    public static void main(String[] args) {
        final Timer timer = new Timer();

        final Runnable r = new Runnable() {
            // count will only be updated in timer's loop() thread
            // so it doesn't need to be synchronized.
            private int count = 0;
            @Override
            public void run() {
                if (++count > COUNT) {
                    timer.stop();
                } else {
                    System.out.println("add timer at " + LocalDateTime.now());
                    final long timeInterval = 1;
                    timer.addTimerEventInSeconds(timeInterval, this);
                }
            }
        };
        new Thread(() -> {
            // Only this add method will be called outside timer loop
            timer.addTimerEventInSeconds(0, r);
        }).start();

        // Start timer loop
        timer.loop();

    }

}
