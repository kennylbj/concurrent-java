package observer;

import com.google.common.eventbus.EventBus;
import net.jcip.annotations.ThreadSafe;

/**
 * Created by kennylbj on 16/8/28.
 * Google EventBus version of Subject
 */
@ThreadSafe
public class EventBusSubjectImpl implements Subject {
    private final EventBus eventBus;

    EventBusSubjectImpl() {
        this.eventBus = new EventBus();
    }


    @Override
    public void register(Observer observer) {
        eventBus.register(observer);
    }

    @Override
    public void unRegister(Observer observer) {
        eventBus.unregister(observer);
    }

    @Override
    public void postTopic(String topic) {
        System.out.println("Post a EventBus topic : " + topic);
        eventBus.post(topic);
    }
}
