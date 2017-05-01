package completableFuture;

/**
 * Created by kennylbj on 2017/4/30.
 */
final class Weather {
    private final int temperature;
    private final double moisture;

    Weather(int temperature, double moisture) {
        this.temperature = temperature;
        this.moisture = moisture;
    }

    int getTemperature() {
        return temperature;
    }

    double getMoisture() {
        return moisture;
    }

    @Override
    public String toString() {
        return String.format("temperature: %d, moisture: %.2f", temperature, moisture);
    }
}
