package producerconsumer;

/**
 * Created by kennylbj on 16/9/10.
 */
public interface Consumer<E> {
    //consume a element of type E
    void consume(E e);
}
