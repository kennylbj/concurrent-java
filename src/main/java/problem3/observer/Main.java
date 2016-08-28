package problem3.observer;

/**
 * Created by kennylbj on 16/8/28.
 */
public class Main {
    public static void main(String[] args) {
        Subject myTopic = new SubjectImpl();
        myTopic.register(new ObserverImpl("google", myTopic));
        myTopic.register(new ObserverImpl("twitter", myTopic));
        myTopic.register(new ObserverImpl("facebook", myTopic));

        myTopic.postTopic("say a topic");
        myTopic.postTopic("post another topic");
    }
}
