package problem5.producerconsumer;

/**
 * Created by kennylbj on 16/9/10.
 */
public interface IConsumer<E> {
    //consume a element of type E
    void consume(E e);
}
