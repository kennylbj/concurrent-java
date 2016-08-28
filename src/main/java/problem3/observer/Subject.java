package problem3.observer;

/**
 * Created by kennylbj on 16/8/28.
 *
 */
public interface Subject {
    void register(Observer observer);

    void unRegister(Observer observer);

    void postTopic(String topic);
}
