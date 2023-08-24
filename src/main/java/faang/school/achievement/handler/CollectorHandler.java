package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.GoalSetEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CollectorHandler extends GoalHandler {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @Override
    public void handle(GoalSetEventDto goalSetEventDto) {
        asyncTaskExecutor.execute(() -> {
            Achievement collector = achievementCache.get("Collector");
            long userId = goalSetEventDto.getUserId();
            long achievementId = collector.getId();

            if (achievementService.hasAchievement(userId, achievementId)) {
                log.info("User: {} already has achievement {}", userId, collector.getTitle());
                return;
            }

            AchievementProgress progress = achievementService.getAchievementProgress(userId, achievementId);
            if (progress.getCurrentPoints() < collector.getPoints()) {
                achievementService.incrementProgress(progress);
                log.info("Incremented progress Id: {}. CurrentPoints: {}", progress.getId(), progress.getCurrentPoints());
                if (progress.getCurrentPoints() >= collector.getPoints()) {
                    achievementService.giveAchievement(userId, collector);
                    log.info("Given achievement {} to user {}", collector.getTitle(), userId);
                }
            }
        });
    }

    @Override
    public void handleAsync(GoalSetEventDto goalSetEventDto) {
        handle(goalSetEventDto);
    }
}
