package problem3.observer;

import com.google.common.eventbus.Subscribe;

/**
 * Created by kennylbj on 16/8/28.
 * google EventBus version of Observer
 * we can get rid of Observer interface since
 * @Subscribe annotation
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
