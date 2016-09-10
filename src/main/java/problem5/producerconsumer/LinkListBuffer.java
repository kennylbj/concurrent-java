package problem5.producerconsumer;

import net.jcip.annotations.NotThreadSafe;

import java.util.LinkedList;

/**
 * Created by kennylbj on 16/9/10.
 * Non thread-safe version of buffer, so invoker must
 * synchronize it.
 * Use LinkedList to implement it.
 */
@NotThreadSafe
public class LinkListBuffer extends Buffer<Item> {
    private final LinkedList<Item> buffer = new LinkedList<>();
    private final int capacity;

    public LinkListBuffer(int capacity) {
        this.capacity = capacity;
    }

    public LinkListBuffer() {
        this(Integer.MAX_VALUE);
    }

    @Override
    public void putItem(Item item) {
        buffer.addLast(item);
    }

    @Override
    public Item popItem() {
        return buffer.removeFirst();
    }

    @Override
    public int getSize() {
        return buffer.size();
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
