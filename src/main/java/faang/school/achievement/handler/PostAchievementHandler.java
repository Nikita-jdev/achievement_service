package faang.school.achievement.handler;

import faang.school.achievement.dto.EventPostDto;
import org.springframework.stereotype.Component;

@Component
public abstract class PostAchievementHandler implements EventHandler<EventPostDto> {
    @Override
    public void handle(EventPostDto event) {

    }
}
