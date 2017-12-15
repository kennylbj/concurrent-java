package guava.cache;

import com.google.common.cache.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import guava.cache.resource.Resource;
import guava.cache.resource.ResourceBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Cache {
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        LoadingCache<String, Resource> resources = CacheBuilder.newBuilder()

                // max memory size
                .maximumWeight(1000)

                // memory usage each item occupy
                .weigher((Weigher<String, Resource>) (key, value) -> value.memoryUsage())

                // evict the cache once the given duration have elapsed
                .expireAfterWrite(2, TimeUnit.MINUTES)

                // mark the cache refreshable once the given duration have elapsed
                // The old value (if any) is still returned while the key is being refreshed,
                // in contrast to eviction, which forces retrievals to wait until the value is loaded anew.
                .refreshAfterWrite(1, TimeUnit.MINUTES)

                // invoke the operation once cache is removed
                .removalListener(notification -> {
                    Resource resource = notification.getValue();
                    resource.release();
                })
                .build(new CacheLoader<String, Resource>() {
                    // build the cache for the given key
                    @Override
                    public Resource load(String key) {
                        return ResourceBuilder.build(key);
                    }

                    // async build the cache if it need to be refreshed
                    // the new value could be compute by old value
                    @Override
                    public ListenableFuture<Resource> reload(String key, Resource oldValue) {
                        if (oldValue.isValid()) {
                            return Futures.immediateFuture(oldValue);
                        }
                        ListenableFutureTask<Resource> task = ListenableFutureTask.create(() -> ResourceBuilder.build(key));
                        executor.execute(task);
                        return task;
                    }
                });

        // get the cache for the given key
        resources.getUnchecked("Resource");
    }
}
