package problem3.observer;

/**
 * Created by kennylbj on 16/8/28.
 */
public class Main {
    public static void main(String[] args) {
        Subject defensiveCopySubject = new DefensiveCopySubjectImpl();
        defensiveCopySubject.register(new ObserverImpl("google", defensiveCopySubject));
        defensiveCopySubject.register(new ObserverImpl("twitter", defensiveCopySubject));
        defensiveCopySubject.register(new ObserverImpl("facebook", defensiveCopySubject));

        defensiveCopySubject.postTopic("say a topic");
        defensiveCopySubject.postTopic("post another topic");

        Subject copyOnWriteSubject = new CopyOnWriteSubjectImpl();
        copyOnWriteSubject.register(new ObserverImpl("yahoo", copyOnWriteSubject));
        copyOnWriteSubject.register(new ObserverImpl("apple", copyOnWriteSubject));
        copyOnWriteSubject.postTopic("post a CopyOnWrite topic");
        copyOnWriteSubject.postTopic("post another anther CopyOnWrite topic");
    }
}
