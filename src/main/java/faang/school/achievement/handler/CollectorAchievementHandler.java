package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.GoalSetEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollectorAchievementHandler implements EventHandler<GoalSetEvent> {

    private final AchievementService achievementService;
    private final AchievementCache achievementCache;
    @Value("${achievement.collector}")
    private String achievementTitle;

    @Override
    @Transactional
    public void handle(GoalSetEvent goalSetEvent) {
        Achievement achievement = achievementCache.get(achievementTitle);
        long userId = goalSetEvent.getUserId();
        achievementService.workWithAchievement(userId, achievementTitle,achievement);
    }
}
