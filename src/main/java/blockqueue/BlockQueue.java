package blockqueue;

import static com.google.common.base.Preconditions.*;
import net.jcip.annotations.ThreadSafe;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fishdicks on 16/9/9.
 */
@ThreadSafe
public class BlockQueue<E> extends AbstractQueue<E> {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private Node head;
    private Node last;
    private final AtomicInteger count = new AtomicInteger(0);
    private final int capacity;

    public BlockQueue(int capacity) {
        checkArgument(capacity > 0, "capacity must larger than 0");
        this.capacity = capacity;
    }

    public BlockQueue() {
        this(Integer.MAX_VALUE);
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node(E item) {
            this.item = item;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        checkNotNull(e, "offer element can't be null");
        if (count.get() == capacity) {
            return false;
        }
        Node<E> node = new Node<E>(e);
        int c = -1;
        lock.lock();
        try {
            if (count.get() < capacity) {
                enqueue(node);
                c = count.getAndIncrement();
                if (c+1 < capacity) {
                    notFull.signal();
                }
            }
        } finally {
            lock.unlock();
        }
        return c >= 0;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    private void enqueue(BlockQueue.Node<E> node) {
        last = last.next = node;
    }
}
