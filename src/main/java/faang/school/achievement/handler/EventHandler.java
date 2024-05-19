package faang.school.achievement.handler;

import org.springframework.scheduling.annotation.Async;

public interface EventHandler<E> {
    @Async("executorService")
    void handle(E event);
}