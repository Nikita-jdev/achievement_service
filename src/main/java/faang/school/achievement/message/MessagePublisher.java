package faang.school.achievement.message;

public interface MessagePublisher {

    void publish(String topic, Object message);
}
