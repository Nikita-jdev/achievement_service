package faang.school.achievement.handler;


public interface EventHandler<E> {
    public void handle(E event);
}
