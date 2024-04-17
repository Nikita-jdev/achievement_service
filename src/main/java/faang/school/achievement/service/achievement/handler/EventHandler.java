package faang.school.achievement.service.achievement.handler;

public interface EventHandler<E> {
    void handle(E event);
}
