### Guava Cache

Caches are tremendously useful in a wide variety of use cases. 
For example, you should consider using caches when a value is expensive to compute or retrieve, 
and you will need its value on a certain input more than once.  

This example demonstrate the usage of guava ```LoadingCache```.  

### Resource

A ```Resource``` is an object that occupy a lot of memory and is expensive to compute for the given key.
And it could grows stale after a certain amount of time.

The ```Resource``` of this example has following features:

1. Each resource occupies a large amount of memory, so the maximum number of cache items is limited.
2. Each resource takes a lot of time to compute, so it is worthwhile to cache.
3. Each resource is invalidate and need to be refreshed after a given time. resource could use old value to
compute new value.
4. Each resource is invalidate and need to be removed after a given time due to the memory limited.
5. Each resource needs to perform some release operation after when it is removed.  

The [cache example](./Cache.java) demonstrates the implementation above.