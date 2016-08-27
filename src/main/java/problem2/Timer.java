package problem2;

import net.jcip.annotations.ThreadSafe;

import static com.google.common.base.Preconditions.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by kennybjliu on 16/8/27.
 * Timer is designed to be thread-safe.
 *
 */
@ThreadSafe
public class Timer {
    private static final int SECONDS_TO_NANOSECONDS = 1000 * 1000 * 1000;
    //in order to add task from other threads.
    private final BlockingQueue<TimerEvent> timers;
    private volatile boolean stop = false;

    public Timer() {
        timers = new PriorityBlockingQueue<>();
    }

    public void addTimerEventInSeconds(long expirationTime, Runnable task) {
        addTimerEventInNanoSeconds(expirationTime * SECONDS_TO_NANOSECONDS, task);
    }

    public void addTimerEventInNanoSeconds(long expirationTime, Runnable task) {
        checkArgument(expirationTime >= 0, "expiration time can't not less than 0");
        checkNotNull(task);
        long expirationNs = System.nanoTime() + expirationTime;
        timers.add(new TimerEvent(expirationNs, task));
    }

    //FIXME wait and notify
    public void loop() {
        while (!stop) {
            triggerExpiredTimers(System.nanoTime());
        }
    }

    public void stop() {
        stop = true;
    }

    //FIXME should we synchronize it? timers will only be add but never
    //removed from other thread, so it's safe to do in this way.
    private void triggerExpiredTimers(long currentTime) {
        while (!timers.isEmpty()) {
            long nextExpiredTime = timers.peek().getExpirationTime();
            if (nextExpiredTime <= currentTime) {
                timers.poll().getTask().run();
            } else {
                return;
            }
        }
    }


    private static class TimerEvent implements Comparable<TimerEvent> {
        private final long expirationTime;
        private final Runnable task;

        TimerEvent(long expirationTime, Runnable task) {
            this.expirationTime = expirationTime;
            this.task = task;
        }

        @Override
        public int compareTo(TimerEvent other) {
            return Long.compare(expirationTime, other.expirationTime);
        }

        public long getExpirationTime() {
            return expirationTime;
        }

        public Runnable getTask() {
            return task;
        }
    }
}
