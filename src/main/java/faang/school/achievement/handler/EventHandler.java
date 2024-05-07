package faang.school.achievement.handler;

import org.springframework.scheduling.annotation.Async;

public interface EventHandler <T>{
    @Async(value = "executorService")
    void handle(T event);
}
