package faang.school.achievement.service.handler;

public interface EventHandler<T> {
    void handle(T event);
}
