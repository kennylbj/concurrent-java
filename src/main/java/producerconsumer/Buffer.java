package producerconsumer;

/**
 * Created by kennylbj on 16/9/10.
 */
public abstract class Buffer<E> {

    public abstract void putItem(E e);

    public abstract E popItem();

    public abstract int getSize();

    public abstract int getCapacity();
}
