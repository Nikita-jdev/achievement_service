package faang.school.achievement.service.handler.followHandler;

import faang.school.achievement.dto.follow.FollowEventDto;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.service.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CelebrityAchievementHandler implements EventHandler<FollowEventDto> {
    private final AchievementRepository achievementRepository;

    @Override
    @Async("eventHandlerExecutor")
    public void handle(FollowEventDto event) {

    }
}
