package guava.cache.resource;

/**
 * A {@code Resource} is an object that occupy
 * a lot of memory and is expensive to compute
 * for the given key.
 * A resource could grows stale after a certain
 * amount of time.
 */
public interface Resource {

    // compute a resource from a given key
    // the computation could be time consuming
    Resource compute(String key);

    // release the related resource it hold on
    void release();

    // check whether this resource is valid
    boolean isValid();

    // memory usage this resource occupied
    int memoryUsage();
}
