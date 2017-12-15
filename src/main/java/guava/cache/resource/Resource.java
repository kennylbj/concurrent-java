package guava.cache.resource;

/**
 * A {@code Resource} is an object that occupy
 * a lot of memory and is expensive to compute
 * for the given key.
 * A resource could grows stale after a certain
 * amount of time.
 */
public interface Resource {

    // Compute a resource from a given key.
    // The computation could be time consuming.
    Resource compute(String key);

    // Release the related resource it hold on.
    void release();

    // Check whether this resource is valid
    boolean isValid();

    // Memory usage this resource occupied
    int memoryUsage();
}
