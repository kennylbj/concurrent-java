package observer;

/**
 * Created by kennylbj on 16/8/28.
 * Three versions of Subject-Observer implements
 */
public class Main {
    public static void main(String[] args) {
        Subject defensiveCopySubject = new DefensiveCopySubjectImpl();

        defensiveCopySubject.register(new ObserverImpl("google", defensiveCopySubject));
        defensiveCopySubject.register(new ObserverImpl("twitter", defensiveCopySubject));
        defensiveCopySubject.register(new ObserverImpl("facebook", defensiveCopySubject));
        defensiveCopySubject.postTopic("A defensive copy topic");
        defensiveCopySubject.postTopic("Another defensive copy topic\n");

        Subject copyOnWriteSubject = new CopyOnWriteSubjectImpl();
        copyOnWriteSubject.register(new ObserverImpl("yahoo", copyOnWriteSubject));
        copyOnWriteSubject.register(new ObserverImpl("amazon", copyOnWriteSubject));
        copyOnWriteSubject.register(new ObserverImpl("ebay", copyOnWriteSubject));
        copyOnWriteSubject.postTopic("A CopyOnWrite topic");
        copyOnWriteSubject.postTopic("Another CopyOnWrite topic\n");

        Subject eventBusSubject = new EventBusSubjectImpl();
        eventBusSubject.register(new EventBusObserverImpl("tencent", eventBusSubject));
        eventBusSubject.register(new EventBusObserverImpl("alibaba", eventBusSubject));
        eventBusSubject.register(new EventBusObserverImpl("baidu", eventBusSubject));
        eventBusSubject.postTopic("A EventBus topic");
        eventBusSubject.postTopic("Another EventBus topic\n");
    }
}
