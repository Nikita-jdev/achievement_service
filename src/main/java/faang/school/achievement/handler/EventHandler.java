package faang.school.achievement.handler;

public interface EventHandler<T> {
    void handler(T event);
}