package guava.cache.resource;

public class ResourceBuilder {
    // build a resource by the given key
    public static Resource build(String key) {
        return new ResourceImpl().compute(key);
    }

}
