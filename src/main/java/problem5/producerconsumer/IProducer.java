package problem5.producerconsumer;

/**
 * Created by kennylbj on 16/9/10.
 */
public interface IProducer<E> {
    //produce a element of type E
    E produce();
}
