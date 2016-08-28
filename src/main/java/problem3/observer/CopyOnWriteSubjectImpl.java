package problem3.observer;

import net.jcip.annotations.ThreadSafe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kennylbj on 16/8/28.
 * CopyOnWriteArrayList version of subject
 */
@ThreadSafe
public class CopyOnWriteSubjectImpl implements Subject {
    private final List<Observer> observers;

    public CopyOnWriteSubjectImpl() {
        this.observers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unRegister(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void postTopic(String topic) {
        System.out.println("CopyOnWrite subject post a topic : " + topic);
        notifyAllObservers(topic);
    }

    private void notifyAllObservers(String topic) {
        observers.forEach(observer -> observer.onNotify(topic));
    }
}
