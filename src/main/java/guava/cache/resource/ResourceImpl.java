package guava.cache.resource;

import java.util.Random;

public class ResourceImpl implements Resource {
    private final Random random = new Random(System.nanoTime());
    @Override
    public Resource compute(String key) {
        try {
            // Sleep about [500, 1500) milliseconds
            Thread.sleep(random.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void release() {
        try {
            Thread.sleep(random.nextInt(500) + 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid() {
        return random.nextBoolean();
    }

    @Override
    public int memoryUsage() {
        // memory usage is [1, 11)
        return random.nextInt(10) + 1;
    }
}
