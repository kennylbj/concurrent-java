package completableFuture;

/**
 * Created by kennylbj on 2017/4/30.
 */
public class Main {

    private static long getDuration(long start) {
        return (System.nanoTime() - start) / 1000000;
    }
    public static void main(String[] args) {

        Forecaster forecaster = new Forecaster();
        long start = System.nanoTime();
        forecaster.queryTemperature("New York").forEach(System.out::println);
        System.out.println("Query temperature in " + getDuration(start) + " ms.\n");

        start = System.nanoTime();
        forecaster.queryTemperatureParallelByStream("Los Angeles").forEach(System.out::println);
        System.out.println("Query temperature parallel by Stream in " + getDuration(start) + " ms.\n");

        start = System.nanoTime();
        forecaster.queryTemperatureParallelByCompletableFuture("Beijing").forEach(System.out::println);
        System.out.println("Query temperature parallel by CompletableFuture in " + getDuration(start) + " ms.\n");

        start = System.nanoTime();
        forecaster.queryWeather("London").forEach(System.out::println);
        System.out.println("Query weather in " + getDuration(start) + " ms.\n");

        start = System.nanoTime();
        forecaster.queryComfortLevel("Berlin").forEach(System.out::println);
        System.out.println("Query comfort level in " + getDuration(start) + " ms.");
    }
}
