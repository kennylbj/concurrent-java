package completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by kennylbj on 2017/4/30.
 * Take a few queries from {@code WeatherCenter} and {@code HealthCenter}
 * This class demonstrates the usage of {@code CompletableFuture}
 */
final class Forecaster {
    private final List<WeatherCenter> centers = new ArrayList<>();
    private final HealthCenter healthCenter = new HealthCenter();
    private final Executor executor;    // Custom a Executor pool for CompletableFuture to execute

    Forecaster() {
        centers.add(new WeatherCenter("The National Weather"));
        centers.add(new WeatherCenter("Weather Underground"));
        centers.add(new WeatherCenter("Forecast.io"));
        centers.add(new WeatherCenter("Accu Weather"));
        centers.add(new WeatherCenter("BBC Weather"));

        executor = Executors.newFixedThreadPool(Math.min(centers.size()*2, 100), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    /**
     * Query temperatures for location {@code location} from all weather centers sequentially.
     * @param location Specific location to be queried.
     * @return String list represent temperatures from all weather centers.
     */
    List<String> queryTemperature(String location) {
        return centers.stream()
                .map(center -> center.getName() + " temperature is " + center.getTemperature(location))
                .collect(Collectors.toList());
    }

    /**
     * Using parallel stream to query temperatures
     * the param and return result is same as {@code queryTemperature(String location)}
     */
    List<String> queryTemperatureParallelByStream(String location) {
        return centers.parallelStream()
                .map(center -> center.getName() + " temperature is " + center.getTemperature(location))
                .collect(Collectors.toList());
    }

    /**
     * Using {@code CompletableFuture} to query temperatures
     * the param and return result is same as {@code queryTemperature(String location)}
     * Note that we separate the stream into two parts to achieve parallel effect.
     * Next {@code CompletableFuture} will block until current one finished if we combine
     * two parts into single stream.
     */
    List<String> queryTemperatureParallelByCompletableFuture(String location) {
        List<CompletableFuture<String>> temperatureFutures = centers.stream()
                .map(center -> CompletableFuture.supplyAsync(()
                        -> center.getName() + " temperature is " + center.getTemperature(location), executor))
                .collect(Collectors.toList());  // Split into two streams to avoid sequential execution.
        return temperatureFutures.stream()
                .map(CompletableFuture::join)   // Wait until CompletableFuture is finished.
                .collect(Collectors.toList());
    }

    /**
     * {@code getTemperature(String location)} and {@code getMoisture(String location)} are two
     * independent operations which can be execute parallel.
     * We use {@code CompletableFuture} to combine them into single query.
     */
    List<Weather> queryWeather(String location) {
         List<CompletableFuture<Weather>> weatherFutures = centers.stream()
                .map(center -> CompletableFuture.supplyAsync(() -> center.getTemperature(location), executor)
                        .thenCombine(CompletableFuture.supplyAsync(() -> center.getMoisture(location), executor),
                                Weather::new))
                .collect(Collectors.toList());
         return weatherFutures.stream()
                 .map(CompletableFuture::join)
                 .collect(Collectors.toList());
    }

    /**
     * Querying comfort level operation must be done after querying weather operation was finished.
     * We use {@code CompletableFuture} to compose these relationship into single query.
     */
    List<Integer> queryComfortLevel(String location) {
        List<CompletableFuture<Integer>> comfortLevelFutures = centers.stream()
                .map(center -> CompletableFuture.supplyAsync(() -> center.getTemperature(location), executor)
                        .thenCombine(CompletableFuture.supplyAsync(() -> center.getMoisture(location), executor),
                                Weather::new))                      // CompletableFuture<Weather>
                .map(future -> future
                        .thenCompose(weather -> CompletableFuture   // Using weather as param to get comfort level
                                .supplyAsync(() -> healthCenter.comfortLevel(weather), executor)))
                .collect(Collectors.toList());

        return comfortLevelFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}
