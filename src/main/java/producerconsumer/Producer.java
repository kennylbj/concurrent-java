package producerconsumer;

/**
 * Created by kennylbj on 16/9/10.
 */
public interface Producer<E> {
    //produce a element of type E
    E produce();
}
