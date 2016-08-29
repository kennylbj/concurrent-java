package problem4.philosopher;


/**
 * Created by kennylbj on 16/8/28.
 */
public class Chopstick implements Comparable<Chopstick> {
    private final int id;

    public Chopstick(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Chopstick other) {
        return this.id - other.id;
    }
}
