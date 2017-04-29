package observer;

/**
 * Created by kennylbj on 16/8/28.
 */
public class ObserverImpl implements Observer {
    private final String observerName;
    private final Subject subject;

    public ObserverImpl(String observerName, Subject subject) {
        this.observerName = observerName;
        this.subject = subject;
    }

    @Override
    public void onNotify(String topic) {
        System.out.println(observerName + " receive a topic : " + topic);
        //Unexpected user may call unRegister method in onNotify even
        //can hold another lock. that's way we need a defencive copy
        //from Observers
        subject.unRegister(this);
    }
}
