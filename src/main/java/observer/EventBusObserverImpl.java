package observer;

import com.google.common.eventbus.Subscribe;

/**
 * Created by kennylbj on 16/8/28.
 * Google EventBus version of Observer
 */
public class EventBusObserverImpl implements Observer {
    private final String observerName;
    private final Subject subject;

    public EventBusObserverImpl(String observerName, Subject subject) {
        this.observerName = observerName;
        this.subject = subject;
    }

    @Override
    @Subscribe
    public void onNotify(String topic) {
        System.out.println(observerName + " receive a topic : " + topic);
        subject.unRegister(this);
    }
}
