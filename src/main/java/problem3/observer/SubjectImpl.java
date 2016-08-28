package problem3.observer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import static com.google.common.base.Preconditions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kennylbj on 16/8/28.
 * A thread-safe subject which can post topic to
 * all his observers. It is implemented by defensive-copy.
 * CopyOnWrite tech is another way to achieve this.
 */
@ThreadSafe
public class SubjectImpl implements Subject{
    @GuardedBy("this")
    private final ArrayList<Observer> observers;

    public SubjectImpl() {
        observers = new ArrayList<>();
    }

    @Override
    public void register(Observer observer) {
        checkNotNull(observer);
        synchronized (this) {
            observers.add(observer);
        }
    }

    @Override
    public void unRegister(Observer observer) {
        checkNotNull(observer);
        synchronized (this) {
            observers.remove(observer);
        }
    }

    @Override
    public void postTopic(String topic) {
        System.out.println("post a topic : " + topic);
        notifyAllObservers(topic);
    }

    /**
     * Make a defensive copy of observers because this method
     * will be invoked in user logic and can do anything (such
     * as hold another lock or call unRegister method and so on)
     * @param topic the topic being posted.
     */
    private void notifyAllObservers(String topic) {
        List<Observer> observerCopy;
        synchronized (this) {
            observerCopy = (List<Observer>)observers.clone();
        }
        observerCopy.forEach(observer -> observer.onNotify(topic));
    }


}
