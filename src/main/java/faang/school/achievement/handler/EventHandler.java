package faang.school.achievement.handler;

import faang.school.achievement.dto.EventDto;

public interface EventHandler<T extends EventDto>{
    void handle(T event);
}
